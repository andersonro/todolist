package br.com.arodevsistemas.todolist.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.arodevsistemas.todolist.model.Task

@Database(entities = arrayOf(Task::class), version = 1)
abstract class AppDateBase : RoomDatabase() {

    abstract fun TaskDao() : TaskDao

    companion object {

        @Volatile
        private var INSTANCE: AppDateBase? = null

        fun getDatabase(context: Application) : AppDateBase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDateBase::class.java,
                    "app_task")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance

                instance

            }
        }
    }
}