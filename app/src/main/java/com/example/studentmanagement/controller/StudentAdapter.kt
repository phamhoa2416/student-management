package com.example.studentmanagement.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagement.R
import com.example.studentmanagement.model.Student

class StudentAdapter(private var students: MutableList<Student>): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    var onUpdateClickListener: ((Student) -> Unit)? = null
    var onRemoveClickListener: ((Student) -> Unit)? = null

    class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val studentName: TextView = itemView.findViewById(R.id.student_name)
        val studentID: TextView = itemView.findViewById(R.id.student_id)
        val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
        val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]

        holder.studentName.text = student.studentName
        holder.studentID.text= student.studentId

        holder.imageEdit.setOnClickListener {
            onUpdateClickListener?.invoke(student)
        }
        holder.imageRemove.setOnClickListener {
            onRemoveClickListener?.invoke(student)
        }
    }
}