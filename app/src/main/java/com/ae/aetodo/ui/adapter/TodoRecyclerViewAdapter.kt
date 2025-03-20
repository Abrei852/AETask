package com.ae.aetodo.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.ae.aetodo.database.table.TodoTable
import com.ae.aetodo.databinding.FragmentTodoBinding
import com.ae.aetodo.ui.interfaces.TodoInterface
import com.google.android.material.button.MaterialButton

class TodoRecyclerViewAdapter(
    private val todos: List<TodoTable>,
    private val todoInterface: TodoInterface
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
            holder.body.text = it.todoDbBody
            holder.todoDone.setOnClickListener {
                todoInterface.markTodoDone(todos[position])
            }
        }
    }

    override fun getItemCount(): Int = todos.size

    inner class ViewHolder(binding: FragmentTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val body: TextView = binding.tvTodoBody
        val todoDone: MaterialButton = binding.btnDone

        override fun toString(): String {
            return super.toString() + " '" + body.text + "'"
        }
    }

}