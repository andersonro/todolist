package br.com.arodevsistemas.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tb_task")
data class Task(
    @PrimaryKey(autoGenerate = true) var id : Long = 0,
    var title : String,
    var description: String,
    var date : String,
    var hour : String = "P",
    var status : String = ""
) : Serializable
