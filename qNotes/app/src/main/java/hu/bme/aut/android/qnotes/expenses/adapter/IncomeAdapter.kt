package hu.bme.aut.android.qnotes.expenses.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.qnotes.expenses.ExpensesFragment
import hu.bme.aut.android.qnotes.expenses.data.IncomeItem
import hu.bme.aut.android.qnotes.databinding.ItemExpensesListBinding

class IncomeAdapter (private val listener: IncomeItemClickListener) :
    RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder>() {

    private val incomes = mutableListOf<IncomeItem>()

    private var sumOfMoney: Int = 0
    private var sumOfIncome: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IncomeViewHolder(
        ItemExpensesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: IncomeViewHolder, position: Int) {
        val income = incomes[position]

        holder.binding.totalAmount.text = income.totalAmount.toString()
    }

    fun delete() {
        incomes.clear()
        listener.onIncomeDeleted()
        notifyDataSetChanged()
    }

    fun addItem(item: IncomeItem) {
        incomes.add(item)
        notifyItemInserted(incomes.size - 1)
    }

    fun update(incomeItems: List<IncomeItem>) {
        incomes.clear()
        incomes.addAll(incomeItems)
        if (ExpensesFragment.count < 2) {
            for (income in incomeItems)
                sumOfMoney += income.totalAmount
            listener.onInitSumI(sumOfMoney)
            ++ExpensesFragment.count
        }
        notifyDataSetChanged()
    }

    private fun deleteAll() {
        incomes.clear()
        notifyDataSetChanged()
    }

    fun copyList() {
        if (incomes.size > 1) {
            val copyIncomeList = incomes.toMutableList()
            sumOfIncome = copyIncomeList[0].totalAmount

            for (income in copyIncomeList)
                sumOfIncome += income.totalAmount

            sumOfIncome -= copyIncomeList[0].totalAmount

            val newIncome = IncomeItem(null, sumOfIncome)

            deleteAll()
            addItem(newIncome)
            listener.onMergingItems(newIncome)
        }
    }

    override fun getItemCount(): Int = incomes.size

    interface IncomeItemClickListener {
        fun onItemChanged(item: IncomeItem)
        fun onIncomeDeleted()
        fun onInitSumI(sum: Int)
        fun onMergingItems(item: IncomeItem)
    }

    inner class IncomeViewHolder(val binding: ItemExpensesListBinding) : RecyclerView.ViewHolder(binding.root)
}