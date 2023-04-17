package hu.bme.aut.android.qnotes.expenses.data

import androidx.room.*

@Dao
interface ExpensesItemDao {
    @Query("SELECT * FROM expensesitem")
    fun getAll(): List<ExpensesItem>

    @Insert
    fun insert(expensesItems: ExpensesItem): Long

    @Update
    fun update(expensesItems: ExpensesItem)

    @Query("DELETE FROM expensesitem")
    fun deleteAll()
}