package com.example.dailypad

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.room.Update
import java.util.Calendar

class add_todayNote : AppCompatActivity() {
    private lateinit var duedate: TextView
    private lateinit var calendar: Calendar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_today_note)
        val todoViewModel = ViewModelProvider(this).get(todoViewModel::class.java)

        var title = findViewById<EditText>(R.id.title)
        var summary = findViewById<EditText>(R.id.summary)
        var add = findViewById<Button>(R.id.add)
        duedate = findViewById(R.id.dueDate)
        calendar = Calendar.getInstance()

        val isUpdate=intent.getBooleanExtra("isUpdate",false)

      if(isUpdate){
          val edittitle=intent.getStringExtra("clickedNoteTitle")
          val editdesc=intent.getStringExtra("clickedNoteDesc")
          val updateId=intent.getLongExtra("id",-1L)
          title.setText(edittitle)
          summary.setText(editdesc)
          duedate.setOnClickListener(View.OnClickListener {
              showDatePicker()
          })
          add.text="Update"


          add.setOnClickListener(View.OnClickListener {
              var title = title.text.toString()
              var summary = summary.text.toString()
              if (title.isNotEmpty() && summary.isNotEmpty()) {
                  val currentTime = System.currentTimeMillis()
                  val createddate = Calendar.getInstance().time
                  val todo = Note(id =updateId,title = title,desc = summary, createdDate = createddate, duedate = calendar.time)
                  todoViewModel.update(todo)
                  startActivity(Intent(this, MainActivity::class.java))
              }
          })

      }else{
          duedate.setOnClickListener(View.OnClickListener {
              showDatePicker()
          })
          add.setOnClickListener(View.OnClickListener {
              var title = title.text.toString()
              var summary = summary.text.toString()
              if (title.isNotEmpty() && summary.isNotEmpty()) {
                  val currentTime = System.currentTimeMillis()
                  val createddate = Calendar.getInstance().time
                  val todo = Note(id =0,title = title,desc = summary, createdDate = createddate, duedate = calendar.time)
                  todoViewModel.insert(todo)
                  startActivity(Intent(this, MainActivity::class.java))
              }
          })
      }

//        duedate.setOnClickListener(View.OnClickListener {
//            showDatePicker()
//        })
//
//        add.setOnClickListener(View.OnClickListener {
//            var title = title.text.toString()
//            var summary = summary.text.toString()
//            if (title.isNotEmpty() && summary.isNotEmpty()) {
//                val currentTime = System.currentTimeMillis()
//                val createddate = Calendar.getInstance().time
//                val todo = Note(id =0,title = title,desc = summary, createdDate = createddate, duedate = calendar.time)
//                todoViewModel.insert(todo)
//                startActivity(Intent(this, MainActivity::class.java))
//            }
//        })
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDueDateTextView()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun updateDueDateTextView() {
        val formattedDate = String.format(
            "%02d/%02d/%d",
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.YEAR)
        )
        duedate.text = formattedDate
    }
}
