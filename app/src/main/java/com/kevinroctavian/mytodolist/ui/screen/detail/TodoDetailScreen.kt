package com.kevinroctavian.mytodolist.ui.screen.detail

import android.app.Activity
import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kevinroctavian.mytodolist.data.models.Todo
import com.kevinroctavian.mytodolist.data.models.dueDateString
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailView(viewModel: TodoDetailViewModel, todo: Todo?) {

    val activity = (LocalContext.current as? Activity)

    var title by remember { mutableStateOf(todo?.title ?: "") }
    var description by remember { mutableStateOf(todo?.description ?:"") }
    var dueDateDateText by remember { mutableStateOf(todo?.dueDateString("dd/MM/yyyy") ?:"") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.height(56.dp)) {
            Text(
                text = if  (todo != null) {
                    "Edit Todo"
                } else {
                    "Add Todo"
                },
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
            IconButton(
                onClick = { activity?.finish() },
                modifier = Modifier
                    .size(56.dp)
                    .padding(start = 20.dp, top = 20.dp)) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "", tint = Color.Black,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(3.dp)
                )
            }
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp, end = 20.dp),
            value = title,
            onValueChange = { title = it },
            label = { Text(text = "Title") },
            singleLine = true
        )
        OutlinedTextField(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp, end = 20.dp),
            value = description,
            onValueChange = { description = it },
            label = { Text(text = "Description") }
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Due date")
            val datePicker = DatePickerDialog(
                context,
                { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                    dueDateDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                }, year, month, dayOfMonth
            )
            datePicker.datePicker.minDate = calendar.timeInMillis
            Button(onClick = { datePicker.show() }) {
                Text(
                    text = if (dueDateDateText.isNotEmpty()) {
                        "$dueDateDateText"
                    } else {
                        "Please pick a date"
                    }
                )
            }
        }

        Button(
            onClick = {
                if (title.isEmpty()) Toast.makeText(
                    activity?.baseContext,
                    "Title is empty",
                    Toast.LENGTH_SHORT
                ).show()
                else if (dueDateDateText.isEmpty()) Toast.makeText(
                    activity?.baseContext,
                    "Due date is empty",
                    Toast.LENGTH_SHORT
                ).show()
                else {
                    viewModel.addTodo(todo?.id ,title, description, dueDateDateText)
                    activity?.finish()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(top = 50.dp, start = 20.dp, end = 20.dp),
        ) { Text(text = "SAVE") }
    }
}