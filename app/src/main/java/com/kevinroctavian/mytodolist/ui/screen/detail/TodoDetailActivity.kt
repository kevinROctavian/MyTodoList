package com.kevinroctavian.mytodolist.ui.screen.detail

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.kevinroctavian.mytodolist.data.models.Todo
import com.kevinroctavian.mytodolist.ui.theme.MyTodoListTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TodoDetailActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTodoListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val todo = intent.extras?.getParcelable<Todo>(Todo::class.simpleName)
                    val viewModel = ViewModelProvider(this).get(TodoDetailViewModel::class.java)
                    TodoDetailView(viewModel, todo)
                }
            }
        }
    }
}