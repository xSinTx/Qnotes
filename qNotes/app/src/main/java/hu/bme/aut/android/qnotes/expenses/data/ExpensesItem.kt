package hu.bme.aut.android.qnotes.expenses.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="expensesitem")
data class ExpensesItem (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "totalamount") var totalAmount: Int
)