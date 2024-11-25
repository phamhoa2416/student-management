package com.example.studentmanagement

import android.app.ActionBar
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagement.controller.StudentAdapter
import com.example.studentmanagement.database.StudentData
import com.example.studentmanagement.model.Student
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var addStudentLauncher: ActivityResultLauncher<Intent>
    private var currentPosition: Int = 0
    private val students = StudentData.students
    private lateinit var studentAdapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val students = StudentData.students
        studentAdapter = StudentAdapter(students)

        addStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val name = result.data?.getStringExtra("name")
                val id = result.data?.getStringExtra("id")
                if (!name.isNullOrBlank() && !id.isNullOrBlank()) {
                    val newStudent = Student(name, id)
                    students.add(newStudent)
                    studentAdapter.notifyItemInserted(students.size - 1)
                }
            }
        }

        findViewById<RecyclerView>(R.id.recycler_view_students).run {
            registerForContextMenu(this)
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                val intent = Intent(this, AddActivity::class.java)
                addStudentLauncher.launch(intent)
                true
            } else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit-> {
                val menuInfo = item.menuInfo
                if (menuInfo is AdapterView.AdapterContextMenuInfo) {
                    val position = menuInfo.position
                    val student = students[position]
                    val intent = Intent(this, EditActivity::class.java)
                    intent.putExtra("name", student.studentName)
                    intent.putExtra("id", student.studentId)
                    intent.putExtra("position", position)
                    startActivityForResult(intent, 1)
                }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}