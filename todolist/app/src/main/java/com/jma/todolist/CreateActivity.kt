package com.jma.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jma.todolist.databinding.ActivityCreateBinding
import com.jma.todolist.entidades.tarea

class CreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            btnCancell.setOnClickListener {
                setResult(RESULT_CANCELED)
                finish()
                return@setOnClickListener
            }
            btnAccept.setOnClickListener {
                var fecha = fechaInput.text.toString()
                var titulo = tituloInput.text.toString()
                var desc = descripcionInput.text.toString()
                // enviamos los datos
                val i = Intent()
                var t = tarea(0,titulo,desc,fecha,"",0)
                i.putExtra("task",t)
                setResult(RESULT_OK,i)
                finish()
                return@setOnClickListener
            }
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}