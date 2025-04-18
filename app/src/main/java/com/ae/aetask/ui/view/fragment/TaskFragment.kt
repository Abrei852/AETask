package com.ae.aetask.ui.view.fragment

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
import com.ae.aetask.R
import com.ae.aetask.database.table.TaskTable
import com.ae.aetask.ui.adapter.TaskRecyclerViewAdapter
import com.ae.aetask.ui.interfaces.TaskInterface
import com.ae.aetask.ui.viewModel.TaskViewModel
import com.ae.aetask.databinding.FragmentTaskListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class TaskFragment : Fragment(), TaskInterface {

    private lateinit var binding: FragmentTaskListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var taskAdapter: RecyclerView.Adapter<*>

    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_list, container, false)

        binding.apply {
            viewModel = taskViewModel
            lifecycleOwner = viewLifecycleOwner

            btnNewTodo.setOnClickListener {
                showAlertWithTextInputLayout(requireContext())
            }
        }
        taskViewModel.getTasks(requireContext())
        taskViewModel.tasks.observe(viewLifecycleOwner) {
            if (it != null) {
                manager = LinearLayoutManager(context)
                taskAdapter = TaskRecyclerViewAdapter(it.getTasksList(), this)
                recyclerView = binding.rvTodoList.apply {
                    layoutManager = manager
                    adapter = taskAdapter
                }
            }
        }


        return binding.root
    }

    private fun showAlertWithTextInputLayout(context: Context) {
        val textInputLayout = TextInputLayout(context)
        val input = TextInputEditText(context)
        input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
        textInputLayout.hint = "Task"
        textInputLayout.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
        textInputLayout.setPadding(50, 15, 50, 15)
        textInputLayout.addView(input)

        val alert = MaterialAlertDialogBuilder(context).setTitle("New Task")
            .setMessage("Please enter your task").setView(textInputLayout)
            .setPositiveButton("Create") { dialog, _ ->
                if (input.text.toString().isNotEmpty()) {
                    taskViewModel.createTask(context, input.text.toString())
                    taskViewModel.getTasks(requireContext())
                }
            }
        alert.show()
    }

    override fun markTaskDone(task: TaskTable) {
        MaterialAlertDialogBuilder(requireContext()).setTitle("Task done")
            .setMessage("Mark (${task.taskDbBody}) as done ?")
            .setPositiveButton("YES") { dialog, _ ->
                taskViewModel.markTaskDone(requireContext(), task.id)
                taskViewModel.getTasks(requireContext())
            }.setNegativeButton("NO") { dialog, _ ->
                dialog.cancel()
            }.show()
    }

}
