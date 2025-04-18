package com.ae.aetask.ui.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ae.aetask.R
import com.ae.aetask.database.table.TaskTable
import com.ae.aetask.ui.viewModel.TaskViewModel

@Composable
fun TaskScreen() {
    val taskViewModel: TaskViewModel = viewModel()
    taskViewModel.initializeDb(context = LocalContext.current)
    val tasks by taskViewModel.getTasks().collectAsState(emptyList())

    AddNewTaskButton(taskViewModel = taskViewModel)
    LazyColumn(
        modifier = Modifier
            .padding(top = 120.dp, bottom = 60.dp, start = 15.dp, end = 15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(items = tasks, key = { it.id }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.onSurface),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(30.dp),
                    text = it.taskDbBody,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                MarkTaskDone(taskViewModel = taskViewModel, task = it)
            }
        }
    }
}

@Composable
fun MarkTaskDone(taskViewModel: TaskViewModel, task: TaskTable) {
    var isAlertDialogVisible by remember { mutableStateOf(false) }
    val taskText = task.taskDbBody
    ElevatedButton(
        onClick = { isAlertDialogVisible = true },
        modifier = Modifier.padding(10.dp),
        shape = ShapeDefaults.Medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Icon(
            modifier = Modifier.size(15.dp),
            painter = painterResource(R.drawable.check_solid),
            contentDescription = "checkmark",
            tint = Color.White
        )
    }
    ShowAlertDialog(
        taskViewModel = taskViewModel,
        showDialog = isAlertDialogVisible,
        onDismiss = { isAlertDialogVisible = false },
        taskId = task.id,
        title = "Task finished",
        isAddTask = false,
        text = "Mark task '$taskText' as done?"
    )
}

@Composable
fun AddNewTaskButton(taskViewModel: TaskViewModel) {
    var isAlertDialogVisible by remember { mutableStateOf(false) }
    ElevatedButton(
        onClick = {
            isAlertDialogVisible = true
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp, start = 15.dp, end = 15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        shape = ShapeDefaults.Small
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "add task", tint = Color.White)
    }
    ShowAlertDialog(
        taskViewModel = taskViewModel,
        showDialog = isAlertDialogVisible,
        onDismiss = { isAlertDialogVisible = false },
        taskId = 0,
        title = "Create new task",
        isAddTask = true,
        text = "Task"
    )
}

@Composable
fun ShowAlertDialog(
    taskViewModel: TaskViewModel,
    taskId: Int,
    title: String,
    isAddTask: Boolean,
    text: String,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    var input by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            title = {
                Text(text = title, color = MaterialTheme.colorScheme.primary)
            },
            text = {
                if (isAddTask) {
                    OutlinedTextField(
                        value = input,
                        onValueChange = { input = it },
                        label = {
                            Text(text = text)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.primary
                        ),
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
                    )
                } else {
                    Text(text = text)
                }
            },
            onDismissRequest = { onDismiss() },
            dismissButton = {
                if (isAddTask) {
                    Button(onClick = onDismiss, text = "Cancel")
                } else {
                    Button(onClick = onDismiss, "No")
                }
            },
            confirmButton = {
                if (isAddTask) {
                    Button(onClick = {
                        if (input.isNotEmpty()) {
                            taskViewModel.createTask(task = input.toString())
                            input = ""
                            onDismiss()
                        }
                    }, text = "Create")
                } else {
                    Button(
                        onClick = {
                            taskViewModel.markTaskDone(
                                taskId = taskId
                            )
                            onDismiss()
                        },
                        text = "Yes"
                    )
                }
            },
        )
    }
}

@Composable
fun Button(onClick: () -> Unit, text: String) {
    FilledTonalButton(onClick = onClick, shape = ShapeDefaults.Small) {
        Text(text = text)
    }
}