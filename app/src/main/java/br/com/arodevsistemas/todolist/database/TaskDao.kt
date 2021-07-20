package br.com.arodevsistemas.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.arodevsistemas.todolist.model.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM tb_task WHERE id = :taskId")
    fun loadById(taskId: Long) : Task

    @Query("SELECT * FROM tb_task ORDER BY id")
    fun load() : LiveData<List<Task>>

    @Query("SELECT * FROM tb_task WHERE date = :date ORDER BY id")
    fun loadDate(date: String) : LiveData<List<Task>>

    @Insert
    fun insert(task: Task) : Long

    @Update
    fun update(task: Task) : Int

    @Delete
    fun delete(task: Task)
}