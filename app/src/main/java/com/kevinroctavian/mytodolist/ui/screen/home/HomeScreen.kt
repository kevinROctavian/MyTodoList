package com.kevinroctavian.mytodolist.ui.screen.home

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kevinroctavian.mytodolist.data.models.Todo
import com.kevinroctavian.mytodolist.data.models.dueDateString
import com.kevinroctavian.mytodolist.ui.screen.detail.TodoDetailActivity

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeView(viewModel: HomeViewModel) {
    val list: State<List<Todo>> = viewModel.listTodo.collectAsState(listOf())
    val context = LocalContext.current

    Column {
        Text(
            text = "My Todo List",
            fontSize = 25.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp)
        )

        LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
            items(list.value, key = { todo -> todo.id!! }) { todo ->

                val openDialog = remember { mutableStateOf(false) }
                if (openDialog.value) {
                    AlertDialog(
                        onDismissRequest = { openDialog.value = false },
                        title = { Text(text = "Warning") },
                        text = { Text("Are you sure want to delete " + todo.title + "?") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    viewModel.deleteTodo(todo)
                                    openDialog.value = false
                                }) {
                                Text("Yes")
                            }
                        },
                        dismissButton = {
                            Button(
                                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.DarkGray),
                                onClick = { openDialog.value = false }) {
                                Text("Cancel", color = Color.White)
                            }
                        }
                    )
                }

                ItemTodo(
                    todo = todo, modifier = Modifier
                        .combinedClickable(
                            onLongClick = {
                                openDialog.value = true
                            },
                            onClick = {
                                var intent = Intent(context, TodoDetailActivity::class.java)
                                intent.putExtra(Todo::class.simpleName, todo)
                                context.startActivity(intent)
                            })
                )
            }
        }
    }
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        ExtendedFloatingActionButton(
            modifier = Modifier
                .size(150.dp, 55.dp),
            text = { Text(text = "ADD TODO") },
            onClick = {
                context.startActivity(Intent(context, TodoDetailActivity::class.java))
            },
            icon = { Icon(imageVector = Icons.Default.Add, contentDescription = "") },
            elevation = FloatingActionButtonDefaults.elevation(8.dp)
        )
    }
}

@Composable
fun ItemTodo(todo: Todo, modifier: Modifier) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 5.dp),
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = todo.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = todo.dueDateString("dd/MM/yyyy"),
                    fontSize = 15.sp
                )
            }
            if (todo.description != null) {
                Text(
                    text = todo.description!!,
                    fontSize = 15.sp
                )
            }
        }
    }
}