package com.example.studentmanagement

import android.app.ActionBar
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagement.controller.StudentAdapter
import com.example.studentmanagement.database.StudentData
import com.example.studentmanagement.model.Student
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val students = StudentData.students
        val studentAdapter = StudentAdapter(students)
        studentAdapter.onUpdateClickListener = { selectedStudent ->
            val selectedPosition = StudentData.students.indexOf(selectedStudent)
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.layout_dialog_edit)

            dialog.window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val buttonUpdate = dialog.findViewById<Button>(R.id.btn_update)
            val buttonCancel = dialog.findViewById<Button>(R.id.btn_cancel_update)

            val updateName = dialog.findViewById<EditText>(R.id.update_name)
            val updateID = dialog.findViewById<EditText>(R.id.update_id)

            updateName.setText(selectedStudent.studentName)
            updateID.setText(selectedStudent.studentId)

            buttonUpdate.setOnClickListener {
                val updatedName = updateName.text.toString()
                val updatedID = updateID.text.toString()

                val updatedStudent = Student(updatedName, updatedID)
                StudentData.students[selectedPosition] = updatedStudent
                studentAdapter.notifyItemChanged(selectedPosition)

                Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            buttonCancel.setOnClickListener {
                dialog.dismiss()
            }
        }
        studentAdapter.onRemoveClickListener = { selectedStudent ->
            val selectedPosition = StudentData.students.indexOf(selectedStudent)
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.layout_dialog_remove)

            dialog.window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val buttonRemove = dialog.findViewById<Button>(R.id.btn_remove)
            val buttonCancel = dialog.findViewById<Button>(R.id.btn_cancel_remove)

            buttonRemove.setOnClickListener{
                StudentData.students.remove(selectedStudent)
                studentAdapter.notifyItemRemoved(selectedPosition)
                val snackbar = Snackbar.make(
                    findViewById(R.id.recycler_view_students),
                    "Xóa thông tin sinh viên thành công",
                    Snackbar.LENGTH_SHORT)

                snackbar.setAction("Hoàn tác") {
                    StudentData.students.add(selectedPosition, selectedStudent)
                    studentAdapter.notifyItemInserted(selectedPosition)
                }

                snackbar.show()
                dialog.dismiss()
            }

            buttonCancel.setOnClickListener{
                dialog.dismiss()
            }
        }

        findViewById<Button>(R.id.btn_add_new).setOnClickListener{
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.layout_dialog_add)

            dialog.window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val btnSave = dialog.findViewById<Button>(R.id.btn_add)
            val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel)

            btnSave.setOnClickListener{
                val name = dialog.findViewById<EditText>(R.id.edit_name).text.toString()
                val id = dialog.findViewById<EditText>(R.id.edit_id).text.toString()

                students.add(Student(name, id))
                studentAdapter.notifyItemInserted(students.size - 1)
                dialog.dismiss()
            }

            btnCancel.setOnClickListener{
                dialog.dismiss()
            }
        }

        findViewById<RecyclerView>(R.id.recycler_view_students).run {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}