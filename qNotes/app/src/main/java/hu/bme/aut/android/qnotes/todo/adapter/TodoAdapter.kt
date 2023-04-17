package hu.bme.aut.android.qnotes.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.qnotes.todo.data.TodoItem
import hu.bme.aut.android.qnotes.databinding.ItemTodoListBinding

class TodoAdapter(private val listener: TodoItemClickListener) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private val items = mutableListOf<TodoItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TodoViewHolder(
        ItemTodoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todoItem = items[position]

        holder.binding.tvTodoItem.text = todoItem.name
        holder.binding.cbIsDone.isChecked = todoItem.isDone

        holder.binding.cbIsDone.setOnCheckedChangeListener { _, isChecked ->
            todoItem.isDone = isChecked
            listener.onItemChanged(todoItem)
        }

    }

    fun delete() {
        items.removeAll{ it.isDone }
        listener.onItemDeleted()
        notifyDataSetChanged()
    }

    fun addItem(item: TodoItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(todoItems: List<TodoItem>) {
        items.clear()
        items.addAll(todoItems)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    interface TodoItemClickListener {
        fun onItemChanged(item: TodoItem)
        fun onItemDeleted()
    }

    inner class TodoViewHolder(val binding: ItemTodoListBinding) : RecyclerView.ViewHolder(binding.root)
}