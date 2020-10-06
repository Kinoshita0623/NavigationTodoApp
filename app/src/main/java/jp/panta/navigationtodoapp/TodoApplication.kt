package jp.panta.navigationtodoapp

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import jp.panta.navigationtodoapp.model.Task
import jp.panta.navigationtodoapp.model.TodoDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.Exception

class TodoApplication : Application(), TodoStore {

    /*companion object CREATOR : Parcelable.Creator<TodoApplication> {
        override fun createFromParcel(parcel: Parcel): TodoApplication {
            return TodoApplication(parcel)
        }

        override fun newArray(size: Int): Array<TodoApplication?> {
            return arrayOfNulls(size)
        }
    }*/
    private val mTodosLiveData = MutableLiveData<List<Task>>()
    override val todos: LiveData<List<Task>> = mTodosLiveData

    private lateinit var mDatabase: DataBase

    private lateinit var mTodoDAO: TodoDAO

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val mErrorLiveData = MutableLiveData<Exception>()
    override val errors: LiveData<Exception> = mErrorLiveData


    override fun onCreate() {
        super.onCreate()

        mDatabase = Room.databaseBuilder(this, DataBase::class.java, "todo_database").build()
        mTodoDAO = mDatabase.getTodoDAO()

        applicationScope.launch(Dispatchers.IO) {
            try{
                errorSafeLoadTodos()
            }catch(e: Exception){
                e.handleException()
            }
        }

    }

    override fun actionToggleComplete(task: Task){
        val updated = task.copy(isCompleted = !task.isCompleted)
        applicationScope.launch(Dispatchers.IO){
            try{
                mTodoDAO.update(updated)
                errorSafeLoadTodos()
            }catch(e: Exception){
                e.handleException()
            }

        }

    }

    override fun actionCreateTodo(task: Task) {
        applicationScope.launch(Dispatchers.IO){
            try{
                mTodoDAO.insert(task)
                errorSafeLoadTodos()
            }catch(e: Exception){
                e.handleException()
            }
        }
    }

    override fun actionDeleteTodo(task: Task) {
        applicationScope.launch(Dispatchers.IO){
            try{
                mTodoDAO.delete(task)
                errorSafeLoadTodos()
            }catch(e: Exception){
                e.handleException()
            }
        }
    }

    private suspend fun errorSafeLoadTodos(){
        try{
            mTodosLiveData.postValue(mTodoDAO.findAll())
        }catch(e: Exception){
            e.handleException()
        }
    }

    private fun Exception.handleException(){
        mErrorLiveData.postValue(this)
    }




}