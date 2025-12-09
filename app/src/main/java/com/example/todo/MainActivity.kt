package com.example.todo

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.todo.data.AppDatabase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var currentTasks: List<com.example.todo.data.Task> = emptyList()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listView)
        val btnAdd = findViewById<FloatingActionButton>(R.id.btnAdd)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList())
        listView.adapter = adapter

        lifecycleScope.launch {
            AppDatabase.getInstance().taskDao().getAll().collect { tasks ->
                currentTasks = tasks

                adapter.clear()
                adapter.addAll(
                    tasks.map { task ->
                        val doneMark = if (task.done) " ✓" else ""
                        val cleanDescription = task.description
                        if (!cleanDescription.isNullOrEmpty())
                            "${task.title} - $cleanDescription$doneMark"
                        else
                            "${task.title}$doneMark"
                    }
                )

                adapter.notifyDataSetChanged()
            }

        }
        // EDIT (one click)
        listView.setOnItemClickListener { _, _, position, _ ->
            val task = currentTasks[position]
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("task_id", task.id)
            startActivity(intent)
        }


        // DELETE (long press)
        listView.setOnItemLongClickListener { _, _, position, _ ->
            lifecycleScope.launch {
                val tasks = AppDatabase.getInstance().taskDao().getAll().first()
                val task = tasks[position]
                AppDatabase.getInstance().taskDao().delete(task)
            }
            true
        }

        btnAdd.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
    }
}
