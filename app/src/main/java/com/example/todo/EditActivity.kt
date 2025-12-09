package com.example.todo

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.todo.data.AppDatabase
import com.example.todo.data.Task
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {

    private var taskId: Int = 0
    private var currentTask: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val editTitle = findViewById<EditText>(R.id.editTitle)
        val editDescription = findViewById<EditText>(R.id.editDescription)
        val checkDone = findViewById<CheckBox>(R.id.checkDone)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        taskId = intent.getIntExtra("task_id", 0)
        if (taskId == 0) {
            finish()
            return
        }

        lifecycleScope.launch {
            val dao = AppDatabase.getInstance().taskDao()
            val task = dao.getById(taskId)
            if (task == null) {
                finish()
            } else {
                currentTask = task
                editTitle.setText(task.title)
                editDescription.setText(task.description ?: "")
                checkDone.isChecked = task.done
            }
        }

        btnSave.setOnClickListener {
            val t = currentTask ?: return@setOnClickListener
            val newTitle = editTitle.text.toString().trim()
            val newDescription = editDescription.text.toString().trim()
            val newDone = checkDone.isChecked

            if (newTitle.isEmpty()) {
                editTitle.error = "Заглавието е задължително"
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val dao = AppDatabase.getInstance().taskDao()
                dao.update(
                    t.copy(
                        title = newTitle,
                        description = newDescription,
                        done = newDone
                    )
                )
                finish()
            }
        }

        btnDelete.setOnClickListener {
            val t = currentTask ?: return@setOnClickListener
            lifecycleScope.launch {
                val dao = AppDatabase.getInstance().taskDao()
                dao.delete(t)
                finish()
            }
        }
    }
}
