package jp.panta.navigationtodoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "todo_table"
)
data class Task(
    val title: String,
    val description: String,
    val isCompleted: Boolean
){
    @PrimaryKey(autoGenerate = true) var todoId: Long? = null

}