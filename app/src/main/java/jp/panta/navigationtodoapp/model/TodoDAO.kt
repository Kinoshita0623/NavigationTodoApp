package jp.panta.navigationtodoapp.model

import androidx.room.*


@Dao
interface TodoDAO {

    @Insert
    suspend fun insert(task: Task): Long

    @Update
    suspend fun update(task: Task)

    @Query("SELECT * FROM todo_table")
    suspend fun findAll() : List<Task>

    @Query("SELECT * FROM todo_table WHERE isCompleted=1")
    suspend fun findCompletedTodos() : List<Task>

    @Query("SELECT * FROM todo_table WHERE isCompleted=0")
    suspend fun findUnCompletedTodos() : List<Task>

    @Delete
    suspend fun delete(task: Task)

}