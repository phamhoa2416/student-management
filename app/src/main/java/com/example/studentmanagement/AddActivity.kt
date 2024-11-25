package com.example.studentmanagement

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_dialog_add)

        val name: String = intent.getStringExtra("name") ?: ""
        val id: String = intent.getStringExtra("id") ?: ""

        if (name.isNotBlank()) {
            findViewById<EditText>(R.id.edit_name).setText(name)
        }

        if (id.isNotBlank()) {
            findViewById<EditText>(R.id.edit_id).setText(id)
        }

        findViewById<Button>(R.id.btn_add).setOnClickListener{
            val newName = findViewById<EditText>(R.id.edit_name).text.toString()
            val newId = findViewById<EditText>(R.id.edit_id).text.toString()
            intent.putExtra("name", newName)
            intent.putExtra("id", newId)
            setResult(RESULT_OK, intent)
            finish()
        }

        findViewById<Button>(R.id.btn_cancel).setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}