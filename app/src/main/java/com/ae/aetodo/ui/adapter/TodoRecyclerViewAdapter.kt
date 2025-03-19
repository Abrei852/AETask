package com.ae.aetodo.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.ae.aetodo.databinding.FragmentTodoBinding
import com.ae.aetodo.ui.model.TodoModel

class TodoRecyclerViewAdapter(
    private val todos: List<TodoModel>
) : RecyclerView.Adapter<TodoRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        todos[position].let {
            holder.title.text = it.getTitle()
            holder.body.text = it.getBody()
        }
    }

    override fun getItemCount(): Int = todos.size

    inner class ViewHolder(binding: FragmentTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.tvTodoTitle
        val body: TextView = binding.tvTodoBody

        override fun toString(): String {
            return super.toString() + " '" + title.text + "'"
        }
    }

}