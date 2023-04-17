package hu.bme.aut.android.qnotes.todo.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.qnotes.R
import hu.bme.aut.android.qnotes.todo.data.TodoItem
import hu.bme.aut.android.qnotes.databinding.DialogNewTodoItemBinding

class NewTodoItemDialogFragment : DialogFragment() {
    interface NewTodoItemDialogListener {
        fun onTodoItemCreated(newItem: TodoItem)
    }

    private lateinit var listener: NewTodoItemDialogListener

    private lateinit var binding: DialogNewTodoItemBinding

    fun setListener(_listener: NewTodoItemDialogListener) {
        listener = _listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewTodoItemBinding.inflate(LayoutInflater.from(context))

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_todo_item)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { _, _ ->
                if (isValid()) {
                    listener.onTodoItemCreated(getTodoItem())
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    private fun isValid() = binding.etTodoName.text.isNotEmpty()

    private fun getTodoItem() = TodoItem(
        name = binding.etTodoName.text.toString(),
        isDone = binding.cbIsAlreadyDone.isChecked
    )

    companion object {
        const val TAG = "NewTodoItemDialogFragment"
    }
}