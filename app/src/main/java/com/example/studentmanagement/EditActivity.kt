package com.example.studentmanagement

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_dialog_edit)

        val name = intent.getStringExtra("name")
        val id = intent.getStringExtra("id")

        findViewById<EditText>(R.id.update_name).setText(name)
        findViewById<EditText>(R.id.update_id).setText(id)

        findViewById<Button>(R.id.btn_update).setOnClickListener{
            val newName = findViewById<EditText>(R.id.edit_name).text.toString()
            val newId = findViewById<EditText>(R.id.edit_id).text.toString()
            intent.putExtra("name", newName)
            intent.putExtra("id", newId)
            setResult(RESULT_OK, intent)
            finish()
        }

        findViewById<Button>(R.id.btn_cancel_update).setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}