package br.com.arodevsistemas.todolist.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import br.com.arodevsistemas.todolist.R
import br.com.arodevsistemas.todolist.databinding.ActivityAddTaskBinding
import br.com.arodevsistemas.todolist.datasource.TaskDataSource
import br.com.arodevsistemas.todolist.extensions.format
import br.com.arodevsistemas.todolist.extensions.text
import br.com.arodevsistemas.todolist.model.Task
import br.com.arodevsistemas.todolist.repository.TaskRepository
import br.com.arodevsistemas.todolist.viewmodel.TaskViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private var intExtra : Int = 0

    private val taskViewModel by viewModels<TaskViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra(TASK_ID)){
            intExtra = intent.getIntExtra(TASK_ID, 0)

            taskViewModel.loadTaskById(intExtra.toLong())?.let {
                binding.tilTaskTitle.text = it.title
                binding.tilTaskDescription.text = it.description
                binding.tilTaskDate.text = it.date
                binding.tilTaskHour.text = it.hour
            }

        }

        insertListerners()
        toolbarOnBackPressed(intExtra)
    }

    private fun insertListerners() {
        binding.tilTaskDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tilTaskDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.tilTaskHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            timePicker.addOnPositiveButtonClickListener {
                val minute = if(timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if(timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.tilTaskHour.text = "${hour}:${minute}:00"
            }

            timePicker.show(supportFragmentManager, null)
        }

        binding.btnCancelTask.setOnClickListener {
            finish()
        }

        binding.btnNewTask.setOnClickListener {
            if (validaForm()) {
                val task = Task(
                    title = binding.tilTaskTitle.text,
                    description = binding.tilTaskDescription.text,
                    date = binding.tilTaskDate.text,
                    hour = binding.tilTaskHour.text,
                    id = intExtra.toLong()
                )

                var newTask = if (intExtra.toLong() > 0) {
                    taskViewModel.update(task).also {
                        it.toLong()
                    }
                } else {
                    taskViewModel.insert(task)
                }

                if (newTask == null){
                    alertToast("Erro ao salvar a tarefa.")
                }else{
                    finish()
                }

            } else {
                binding.btnNewTask.isEnabled = true
            }

        }
    }

    fun alertToast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    fun validaForm() : Boolean {

        binding.btnNewTask.isEnabled = false

        if (binding.tilTaskTitle.text.isNullOrEmpty()) {
            alertToast("É necessário preencher o título")
            return false
        }
        if (binding.tilTaskDate.text.isNullOrEmpty()) {
            alertToast("É necessário preencher a data")
            return false
        }
        if (binding.tilTaskHour.text.isNullOrEmpty()) {
            alertToast("É necessário preencher a hora")
            return false
        }

        return true
    }

    private fun toolbarOnBackPressed(intExtra : Int){
        val toolbar = binding.mtNewToolbar
        var title = if(intExtra > 0){
            getString(R.string.toolbar_title_edit)
        }else {
            getString(R.string.toolbar_title_add)
        }
        toolbar.setTitle(title)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    companion object {
        const val TASK_ID = "task_id"
    }
}