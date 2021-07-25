package br.com.arodevsistemas.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arodevsistemas.todolist.databinding.ActivityMainBinding
import br.com.arodevsistemas.todolist.datasource.TaskDataSource
import br.com.arodevsistemas.todolist.extensions.format
import br.com.arodevsistemas.todolist.extensions.text
import br.com.arodevsistemas.todolist.model.Task
import br.com.arodevsistemas.todolist.repository.TaskRepository
import br.com.arodevsistemas.todolist.viewmodel.TaskViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var recyclerView: RecyclerView

    private val taskViewModel by viewModels<TaskViewModel>()
    lateinit var adapter: TaskListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.rvTask
        adapter = TaskListAdapter(this)

        taskViewModel.allTask.observe(this, Observer{
            adapter.list = it
            emptyState(adapter.itemCount > 0)
            adapter.notifyDataSetChanged()

        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        insertListeners()

        binding.ivRemoveDate.setOnClickListener {
            removeDate()
        }
    }

    private fun insertListeners() {
        binding.fabNewTask.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }

        binding.tilTaskDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1

                var date = Date(it + offset).format()

                binding.tilTaskDate.text = date

                taskViewModel.loadTaskDate(date.toString()).observe(this, Observer{
                    adapter.list = it
                    emptyState(adapter.itemCount > 0)
                    adapter.notifyDataSetChanged()
                })

            }

            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }
    }

    fun removeDate(){
        binding.tilTaskDate.text = ""
        taskViewModel.allTask.observe(this, Observer{
            adapter.list = it
            emptyState(adapter.itemCount > 0)
            adapter.notifyDataSetChanged()
        })
    }

    fun emptyState(visible: Boolean){

        binding.iEmptyState.clEmptyState.visibility = if (visible) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

}

