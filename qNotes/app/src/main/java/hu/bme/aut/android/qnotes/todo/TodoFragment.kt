package hu.bme.aut.android.qnotes.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.qnotes.MainActivity.Companion.database
import hu.bme.aut.android.qnotes.todo.data.TodoItem
import hu.bme.aut.android.qnotes.databinding.FragmentTodoBinding
import hu.bme.aut.android.qnotes.todo.dialog.NewTodoItemDialogFragment
import hu.bme.aut.android.qnotes.todo.adapter.TodoAdapter
import kotlin.concurrent.thread

class TodoFragment : Fragment(),
    TodoAdapter.TodoItemClickListener,
    NewTodoItemDialogFragment.NewTodoItemDialogListener {

    private lateinit var binding: FragmentTodoBinding

    private lateinit var adapter: TodoAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTodoBinding.inflate(inflater, container, false)

        initRecyclerView()

        binding.fab.setOnClickListener{
            val dialog = NewTodoItemDialogFragment()
            dialog.setListener(this)
            dialog.show(
                requireActivity().supportFragmentManager,
                NewTodoItemDialogFragment.TAG
            )
        }

        binding.fabDel.setOnClickListener{
            adapter.delete()
        }

        return binding.root
    }

    private fun initRecyclerView() {
        adapter = TodoAdapter(this)

        binding.rvTodo.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTodo.adapter = adapter

        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.todoItemDao().getAll()
            requireActivity().runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onItemChanged(item: TodoItem) {
        thread {
            database.todoItemDao().update(item)
        }
    }

    override fun onItemDeleted() {
        thread {
            database.todoItemDao().deleteIsDone()
        }
    }

    override fun onTodoItemCreated(newItem: TodoItem) {
        thread {
            val insertId = database.todoItemDao().insert(newItem)
            newItem.id = insertId
            requireActivity().runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }
}