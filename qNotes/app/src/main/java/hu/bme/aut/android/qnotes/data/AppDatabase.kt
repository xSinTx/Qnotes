package hu.bme.aut.android.qnotes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hu.bme.aut.android.qnotes.expenses.data.ExpensesItem
import hu.bme.aut.android.qnotes.expenses.data.ExpensesItemDao
import hu.bme.aut.android.qnotes.expenses.data.IncomeItem
import hu.bme.aut.android.qnotes.expenses.data.IncomeItemDao
import hu.bme.aut.android.qnotes.todo.data.TodoItem
import hu.bme.aut.android.qnotes.todo.data.TodoItemDao

@Database(entities = [TodoItem::class, ExpensesItem::class, IncomeItem::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoItemDao(): TodoItemDao
    abstract fun expensesItemDao(): ExpensesItemDao
    abstract fun incomeItemDao(): IncomeItemDao

    companion object {
        fun getDatabase(applicationContext: Context): AppDatabase {
            return Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "app"
            ).build()
        }
    }
}