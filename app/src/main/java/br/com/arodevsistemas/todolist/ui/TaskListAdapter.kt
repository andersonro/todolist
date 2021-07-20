package br.com.arodevsistemas.todolist.ui

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import br.com.arodevsistemas.todolist.R
import br.com.arodevsistemas.todolist.databinding.ItemTaskBinding
import br.com.arodevsistemas.todolist.datasource.TaskDataSource
import br.com.arodevsistemas.todolist.model.Task
import br.com.arodevsistemas.todolist.repository.TaskRepository

class TaskListAdapter(val context: Context) :
    RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    var list: List<Task> = emptyList()

    inner class ViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Task){
            binding.tvTitle.text = item.title
            binding.tvDateHour.text = "${item.date} ${item.hour}"
            binding.tvStatus.text = item.status.let {
                when (it) {
                    "C" -> context.getString(R.string.task_status_ok)
                    else -> context.getString(R.string.task_status_pending)
                }
            }

            var color = item.status.let {
                when (it) {
                    "C" -> "#228B22"
                    else -> "#DAA520"
                }
            }

            binding.tvStatus.setTextColor(Color.parseColor(color))

            binding.ivTaskMenuOptions.setOnClickListener {
                showMenuPopup(item)
            }
        }

        private fun showMenuPopup(task: Task) {
            val ivTaskMenuOptions = binding.ivTaskMenuOptions
            val popupMenu = PopupMenu(ivTaskMenuOptions.context, ivTaskMenuOptions)
            popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)

            if (task.status=="C"){
                popupMenu.menu.removeItem(R.id.menu_item_confirm)
            }

            popupMenu.setOnMenuItemClickListener {
                when (it.itemId){
                    R.id.menu_item_confirm -> {
                        setUpdateTask(task.copy(status = "C"))
                    }
                    R.id.menu_item_edit -> {
                        val intent = Intent(context, AddTaskActivity::class.java)
                        intent.putExtra(AddTaskActivity.TASK_ID, task.id.toInt())
                        context.startActivity(intent)
                    }
                    R.id.menu_item_delete -> {
                        setDeleteTask(task)
                    }
                }
                return@setOnMenuItemClickListener true
            }

            popupMenu.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflate, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemTask: Task = list[position]
        holder.bind(itemTask)
    }

    override fun getItemCount(): Int = list.size

    private fun setUpdateTask(task: Task){
        val taskRepository = TaskRepository(Application())
        taskRepository.update(task)
    }

    private fun setDeleteTask(task: Task){
        val taskRepository = TaskRepository(Application())
        taskRepository.delete(task)
    }

}
