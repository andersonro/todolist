package br.com.arodevsistemas.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.arodevsistemas.todolist.model.Task
import br.com.arodevsistemas.todolist.repository.TaskRepository

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskRepository = TaskRepository(getApplication())

    val allTask = taskRepository.AllTask

    fun loadTaskDate(date : String) = taskRepository.taskLoadDate(date.toString())

    fun loadTaskById(taskId: Long) = taskRepository.taskLoadById(taskId.toLong())

    fun insert(task: Task) :Long = taskRepository.insert(task)

    fun update(task: Task) = taskRepository.update(task)

    fun delete(task: Task) = taskRepository.delete(task)

}