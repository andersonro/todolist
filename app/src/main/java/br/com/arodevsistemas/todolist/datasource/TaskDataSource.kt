package br.com.arodevsistemas.todolist.datasource

import br.com.arodevsistemas.todolist.model.Task

object TaskDataSource {

    private val list = arrayListOf<Task>()

    fun getList() = list.toList()

    fun insertTask(task : Task) {
        if (task.id.toInt() == 0){
            task.id = (list.size+1).toLong()
            list.add(task)
        }else{
            removeTask(task)
            list.add(task)
        }
    }

    fun removeTask(task: Task){
        list.remove(task)
    }

    fun findById(intExtra: Int)  = list.find { it.id == intExtra.toLong() }

    fun confirmedTask(task : Task) {
        var i = list.find { it.id == task.id }
        i?.let {
            it.status = "C"
        }
        if (i != null){
            removeTask(task)
            list.add(i)
        }
    }


}