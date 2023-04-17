package hu.bme.aut.android.qnotes.expenses.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.qnotes.expenses.ExpensesFragment
import hu.bme.aut.android.qnotes.expenses.data.ExpensesItem
import hu.bme.aut.android.qnotes.databinding.ItemExpensesListBinding

class ExpensesAdapter (private val listener: ExpensesItemClickListener) :
    RecyclerView.Adapter<ExpensesAdapter.ExpensesViewHolder>() {

    private val expenses = mutableListOf<ExpensesItem>()

    private var sumOfMoney: Int = 0
    private var sumOfExpense: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ExpensesViewHolder(
        ItemExpensesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ExpensesViewHolder, position: Int) {
        val expense = expenses[position]

        holder.binding.totalAmount.text = expense.totalAmount.toString()
    }

    fun delete() {
        expenses.clear()
        listener.onExpenseDeleted()
        notifyDataSetChanged()
    }

    fun addItem(item: ExpensesItem) {
        expenses.add(item)
        notifyItemInserted(expenses.size - 1)
    }

    fun update(expenseItems: List<ExpensesItem>) {
        expenses.clear()
        expenses.addAll(expenseItems)
        if (ExpensesFragment.count < 2) {
            for (expense in expenseItems)
                sumOfMoney -= expense.totalAmount
            listener.onInitSumE(sumOfMoney)
            ++ExpensesFragment.count
        }
        notifyDataSetChanged()
    }

    private fun deleteAll() {
        expenses.clear()
        notifyDataSetChanged()
    }

    fun copyList() {
        if (expenses.size > 1) {
            val copyExpenseList = expenses.toMutableList()
            sumOfExpense = copyExpenseList[0].totalAmount

            for (expense in copyExpenseList)
                sumOfExpense += expense.totalAmount

            sumOfExpense -= copyExpenseList[0].totalAmount

            val newExpense = ExpensesItem(null, sumOfExpense)

            deleteAll()
            addItem(newExpense)
            listener.onMergingItems(newExpense)
        }
    }

    override fun getItemCount(): Int = expenses.size

    interface ExpensesItemClickListener {
        fun onItemChanged(item: ExpensesItem)
        fun onExpenseDeleted()
        fun onInitSumE(sum: Int)
        fun onMergingItems(item: ExpensesItem)
    }

    inner class ExpensesViewHolder(val binding: ItemExpensesListBinding) : RecyclerView.ViewHolder(binding.root)
}