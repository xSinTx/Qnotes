package hu.bme.aut.android.qnotes.todo.data

import androidx.room.*

@Dao
interface TodoItemDao {
    @Query("SELECT * FROM todoitem")
    fun getAll(): List<TodoItem>

    @Insert
    fun insert(todoItems: TodoItem): Long

    @Update
    fun update(todoItems: TodoItem)

    @Query("DELETE FROM todoitem WHERE is_done = 1")
    fun deleteIsDone()
}