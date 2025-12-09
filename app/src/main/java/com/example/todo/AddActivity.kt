package com.example.todo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.todo.data.AppDatabase
import com.example.todo.data.Task
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val editTitle = findViewById<EditText>(R.id.editTitle)
        val editDescription = findViewById<EditText>(R.id.editDescription)
        val save = findViewById<Button>(R.id.btnSave)

        save.setOnClickListener {
            val title = editTitle.text.toString().trim()
            val description = editDescription.text.toString().trim()

            if (title.isNotEmpty()) {
                lifecycleScope.launch {
                    AppDatabase.getInstance().taskDao().insert(
                        Task(
                            title = title,
                            description = description,
                            done = false
                        )
                    )
                    finish()
                }
            } else {
                editTitle.error = "Задължително поле"
            }
        }
    }
}
