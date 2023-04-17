package hu.bme.aut.android.qnotes.expenses.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.qnotes.R
import hu.bme.aut.android.qnotes.expenses.data.ExpensesItem
import hu.bme.aut.android.qnotes.expenses.data.IncomeItem
import hu.bme.aut.android.qnotes.databinding.DialogNewExpensesItemBinding

class NewExpensesItemDialogFragment : DialogFragment() {
    interface NewExpensesItemDialogListener {
        fun onExpensesItemCreated(newItem: ExpensesItem)
        fun onIncomeItemCreated(newItem: IncomeItem)
    }

    private lateinit var listener: NewExpensesItemDialogListener

    private lateinit var binding: DialogNewExpensesItemBinding

    fun setListener(_listener: NewExpensesItemDialogListener) {
        listener = _listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewExpensesItemBinding.inflate(LayoutInflater.from(context))

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_expenses_item)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { _, _ ->
                if (isValid()) {
                    if (isExpense())
                        listener.onExpensesItemCreated(getExpenseItem())
                    else
                        listener.onIncomeItemCreated(getIncomeItem())
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    private fun isExpense() = binding.cbIsExpense.isChecked

    private fun isValid() = binding.etAmount.text.isNotEmpty()

    private fun getExpenseItem() = ExpensesItem(
        totalAmount = binding.etAmount.text.toString().toIntOrNull() ?: 0
    )

    private fun getIncomeItem() = IncomeItem(
        totalAmount = binding.etAmount.text.toString().toIntOrNull() ?: 0
    )

    companion object {
        const val TAG = "NewExpensesItemDialogFragment"
    }
}