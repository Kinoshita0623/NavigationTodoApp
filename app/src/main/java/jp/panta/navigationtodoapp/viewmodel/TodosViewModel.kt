package jp.panta.navigationtodoapp.viewmodel

import androidx.lifecycle.*
import jp.panta.navigationtodoapp.TodoApplication
import jp.panta.navigationtodoapp.TodoStore
import jp.panta.navigationtodoapp.model.Task

class TodosViewModel(val todoStore: TodoStore) : ViewModel(){

    @Suppress("UNCHECKED_CAST")
    class Factory(private val todoApplication: TodoApplication) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TodosViewModel(todoApplication) as T
        }
    }

    val todos: LiveData<List<Task>> = todoStore.todos

    val completedTodos = Transformations.map(todos){
        it?.filter{ todo ->
            todo.isCompleted
        }?: emptyList()
    }

    val unCompleteTodos = Transformations.map(todos){ list ->
        list?.filterNot{
            it.isCompleted
        }?: emptyList()
    }



    fun toggleComplete(task: Task){
        todoStore.actionToggleComplete(task)
    }


}