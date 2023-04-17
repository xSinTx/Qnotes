package hu.bme.aut.android.qnotes.expenses.data

import androidx.room.*

@Dao
interface IncomeItemDao {
    @Query("SELECT * FROM incomeitem")
    fun getAll(): List<IncomeItem>

    @Insert
    fun insert(incomeItems: IncomeItem): Long

    @Update
    fun update(incomeItems: IncomeItem)

    @Query("DELETE FROM incomeitem")
    fun deleteAll()
}