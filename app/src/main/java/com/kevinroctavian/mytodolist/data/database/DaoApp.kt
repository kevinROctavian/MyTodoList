package com.kevinroctavian.mytodolist.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kevinroctavian.mytodolist.data.models.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged


@Dao
interface DaoApp {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTodo(task: Todo)

    @Update
    fun updateTodo(task: Todo)

    @Delete
    fun deleteTodo(task: Todo)

    @Query("SELECT * FROM todo ORDER BY id DESC")
    fun getAllTodo() : Flow<List<Todo>>
    fun getAllTodoDistinctUntilChanged() = getAllTodo().distinctUntilChanged()
}