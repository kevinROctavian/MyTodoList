package com.kevinroctavian.mytodolist.data.repository

import com.kevinroctavian.mytodolist.data.database.DaoApp
import com.kevinroctavian.mytodolist.data.models.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class TodoReprository @Inject constructor(private val daoApp: DaoApp)  {

    fun addTodo(task: Todo) = daoApp.addTodo(task)

    fun getAllTodo() : Flow<List<Todo>> = flow {
        daoApp.getAllTodoDistinctUntilChanged().collect {
            emit(it)
        }
    }

    fun updateTodo(todo: Todo) = daoApp.updateTodo(todo)

    fun deleteTodo(todo: Todo) = daoApp.deleteTodo(todo)

}