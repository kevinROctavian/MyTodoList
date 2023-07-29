package com.kevinroctavian.mytodolist.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevinroctavian.mytodolist.data.models.Todo
import com.kevinroctavian.mytodolist.data.repository.TodoReprository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: TodoReprository) : ViewModel() {

    val listTodo : Flow<List<Todo>> = flow {
        repository.getAllTodo().collect {
            emit(it)
        }
    }

    fun deleteTodo(todo: Todo){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteTodo(todo)
        }
    }
}