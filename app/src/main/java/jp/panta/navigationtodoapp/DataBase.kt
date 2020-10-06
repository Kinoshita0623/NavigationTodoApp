package jp.panta.navigationtodoapp

import androidx.room.Database
import androidx.room.RoomDatabase
import jp.panta.navigationtodoapp.model.Task
import jp.panta.navigationtodoapp.model.TodoDAO

@Database(
    entities = [
        Task::class
    ],
    version = 1,
    exportSchema = true
)
abstract class DataBase : RoomDatabase(){

    abstract fun getTodoDAO() : TodoDAO

}