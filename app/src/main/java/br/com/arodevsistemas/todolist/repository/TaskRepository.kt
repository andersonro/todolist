package br.com.arodevsistemas.todolist.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.arodevsistemas.todolist.database.AppDateBase
import br.com.arodevsistemas.todolist.model.Task

class TaskRepository (application: Application) {

    private val taskDao = AppDateBase.getDatabase(application).TaskDao()

    val AllTask = taskDao.load()

    fun taskLoadDate(date: String) : LiveData<List<Task>> = taskDao.loadDate(date)

    fun taskLoadById(taskId: Long) : Task = taskDao.loadById(taskId)

    fun insert(task: Task) : Long = taskDao.insert(task)

    fun update(task: Task) = taskDao.update(task)

    fun delete(task: Task) = taskDao.delete(task)

}