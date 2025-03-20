package com.ae.aetodo.ui.view.fragment

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ae.aetodo.R
import com.ae.aetodo.database.table.TodoTable
import com.ae.aetodo.databinding.FragmentTodoListBinding
import com.ae.aetodo.ui.adapter.TodoRecyclerViewAdapter
import com.ae.aetodo.ui.interfaces.TodoInterface
import com.ae.aetodo.ui.viewModel.TodoViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class TodoFragment : Fragment(), TodoInterface {

    private lateinit var binding: FragmentTodoListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var todoAdapter: RecyclerView.Adapter<*>

    private val todoViewModel: TodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_list, container, false)

        binding.apply {
            viewModel = todoViewModel
            lifecycleOwner = viewLifecycleOwner

            btnNewTodo.setOnClickListener {
                showAlertWithTextInputLayout(requireContext())
            }
        }
        todoViewModel.getTodos(requireContext())
        todoViewModel.todos.observe(viewLifecycleOwner) {
            if (it != null) {
                manager = LinearLayoutManager(context)
                todoAdapter = TodoRecyclerViewAdapter(it.getTodosList(), this)
                recyclerView = binding.rvTodoList.apply {
                    layoutManager = manager
                    adapter = todoAdapter
                }
            }
        }


        return binding.root
    }

    private fun showAlertWithTextInputLayout(context: Context) {
        val textInputLayout = TextInputLayout(context)
        val input = TextInputEditText(context)
        input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        textInputLayout.hint = "Todo"
        textInputLayout.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
        textInputLayout.setPadding(50, 15, 50, 15)
        textInputLayout.addView(input)

        val alert = MaterialAlertDialogBuilder(context).setTitle("New Todo")
            .setMessage("Please enter your todo").setView(textInputLayout)
            .setPositiveButton("Create") { dialog, _ ->
                if (input.text.toString().isNotEmpty()) {
                    todoViewModel.createTodo(context, input.text.toString())
                    todoViewModel.getTodos(requireContext())
                }
            }
        alert.show()
    }

    override fun markTodoDone(todo: TodoTable) {
        MaterialAlertDialogBuilder(requireContext()).setTitle("Todo done")
            .setMessage("Mark (${todo.todoDbBody}) as done ?")
            .setPositiveButton("YES") { dialog, _ ->
                todoViewModel.markTodoDone(requireContext(), todo.id)
                todoViewModel.getTodos(requireContext())
            }.setNegativeButton("NO") { dialog, _ ->
                dialog.cancel()
            }.show()
    }

}
