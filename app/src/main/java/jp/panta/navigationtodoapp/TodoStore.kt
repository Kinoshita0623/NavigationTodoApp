package jp.panta.navigationtodoapp

import androidx.lifecycle.LiveData
import jp.panta.navigationtodoapp.model.Task

interface TodoStore {

    val todos: LiveData<List<Task>>

    val errors: LiveData<Exception>

    fun actionToggleComplete(task: Task)

    fun actionDeleteTodo(task: Task)

    fun actionCreateTodo(task: Task)


}