package com.kevinroctavian.mytodolist.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevinroctavian.mytodolist.data.models.Todo
import com.kevinroctavian.mytodolist.data.repository.TodoReprository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class TodoDetailViewModel @Inject constructor(private val repository: TodoReprository) :
    ViewModel() {

    fun addTodo(id: Int?, title: String, description: String, dueDate: String) {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val date: Date = formatter.parse(dueDate)
        val todo = Todo(id, title, description, date.time)

        viewModelScope.launch(Dispatchers.IO) {
            if (id != null) {
                repository.updateTodo(todo)
            }else {
                repository.addTodo(todo)
            }
        }
    }
}