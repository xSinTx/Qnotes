package hu.bme.aut.android.qnotes.expenses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.qnotes.MainActivity.Companion.database
import hu.bme.aut.android.qnotes.expenses.dialog.NewExpensesItemDialogFragment
import hu.bme.aut.android.qnotes.expenses.data.ExpensesItem
import hu.bme.aut.android.qnotes.expenses.data.IncomeItem
import hu.bme.aut.android.qnotes.databinding.FragmentExpensesBinding
import hu.bme.aut.android.qnotes.expenses.adapter.ExpensesAdapter
import hu.bme.aut.android.qnotes.expenses.adapter.IncomeAdapter
import kotlin.concurrent.thread

class ExpensesFragment : Fragment()
    , ExpensesAdapter.ExpensesItemClickListener
    , IncomeAdapter.IncomeItemClickListener
    , NewExpensesItemDialogFragment.NewExpensesItemDialogListener {

    private lateinit var binding: FragmentExpensesBinding

    private lateinit var adapterExpenses: ExpensesAdapter
    private lateinit var adapterIncome: IncomeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentExpensesBinding.inflate(inflater, container, false)

        initRecyclerView()

        binding.fabAdd.setOnClickListener{
            val dialog = NewExpensesItemDialogFragment()
            dialog.setListener(this)
            dialog.show(
                requireActivity().supportFragmentManager,
                NewExpensesItemDialogFragment.TAG
            )
        }

        binding.fabDelete.setOnClickListener{
            adapterExpenses.delete()
            adapterIncome.delete()
            sumOfMoney = 0
            binding.tvAmountNumber.text = "0"
        }

        binding.tvAmountNumber.text = sumOfMoney.toString()

        binding.btnIncome.setOnClickListener {
            adapterIncome.copyList()
        }

        binding.btnExpenses.setOnClickListener {
            adapterExpenses.copyList()
        }

        return binding.root
    }

    private fun initRecyclerView() {
        adapterExpenses = ExpensesAdapter(this)
        adapterIncome = IncomeAdapter(this)

        binding.rvExpenses.layoutManager = LinearLayoutManager(requireContext())
        binding.rvExpenses.adapter = adapterExpenses

        binding.rvIncome.layoutManager = LinearLayoutManager(requireContext())
        binding.rvIncome.adapter = adapterIncome

        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val expenses = database.expensesItemDao().getAll()
            requireActivity().runOnUiThread {
                adapterExpenses.update(expenses)
            }
        }
        thread {
            val incomes = database.incomeItemDao().getAll()
            requireActivity().runOnUiThread {
                adapterIncome.update(incomes)
            }
        }
    }

    override fun onItemChanged(item: ExpensesItem) {
        thread {
            database.expensesItemDao().update(item)
        }
    }

    override fun onItemChanged(item: IncomeItem) {
        thread {
            database.incomeItemDao().update(item)
        }
    }

    override fun onIncomeDeleted() {
        thread {
            database.incomeItemDao().deleteAll()
        }
    }

    override fun onExpenseDeleted() {
        thread {
            database.expensesItemDao().deleteAll()
        }
    }

    override fun onInitSumE(sum: Int) {
        sumInitialize(sum)
    }

    override fun onInitSumI(sum: Int) {
        sumInitialize(sum)
    }

    override fun onMergingItems(item: IncomeItem) {
        thread {
            database.incomeItemDao().deleteAll()
            val insertId = database.incomeItemDao().insert(item)
            item.id = insertId
        }
    }

    override fun onMergingItems(item: ExpensesItem) {
        thread {
            database.expensesItemDao().deleteAll()
            val insertId = database.expensesItemDao().insert(item)
            item.id = insertId
        }
    }

    private fun sumInitialize(sum: Int) {
        sumOfMoney += sum
        binding.tvAmountNumber.text = sumOfMoney.toString()
    }

    override fun onExpensesItemCreated(newItem: ExpensesItem) {
        thread {
            val insertId = database.expensesItemDao().insert(newItem)
            newItem.id = insertId
            requireActivity().runOnUiThread {
                sumOfMoney -= newItem.totalAmount
                binding.tvAmountNumber.text = sumOfMoney.toString()
                adapterExpenses.addItem(newItem)
            }
        }
    }

    override fun onIncomeItemCreated(newItem: IncomeItem) {
        thread {
            val insertId = database.incomeItemDao().insert(newItem)
            newItem.id = insertId
            requireActivity().runOnUiThread {
                sumOfMoney += newItem.totalAmount
                binding.tvAmountNumber.text = sumOfMoney.toString()
                adapterIncome.addItem(newItem)
            }
        }
    }

    companion object {
        var sumOfMoney: Int = 0
        var count: Int = 0
    }
}