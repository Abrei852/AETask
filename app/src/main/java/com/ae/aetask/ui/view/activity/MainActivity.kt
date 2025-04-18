package com.ae.aetask.ui.view.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ae.aetask.ui.view.screen.TaskScreen
import com.ae.aetask.ui.theme.AETaskTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AETaskTheme {
                TaskScreen()
            }
        }
    }
}