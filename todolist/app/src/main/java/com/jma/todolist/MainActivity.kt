package com.jma.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.jma.todolist.basedatos.TodolistDatabase
import com.jma.todolist.databinding.ActivityMainBinding
import com.jma.todolist.databinding.ItemRecyclerBinding
import com.jma.todolist.entidades.tarea

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var datos: MutableList<tarea>
    private lateinit var adaptador: TaskAdapter
    private var total_task = 0
    private var complete_task = 0
    private var uncomplete_task = 0
    private var registerForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        notify(it)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*var datos = mutableListOf<tarea>(
            tarea(0,"t1","ta1","i1","f1",1),
            tarea(0,"t2","ta2","i1","f1",1),
            tarea(0,"t3","ta3","i1","f1",1),
        )*/

        var db = TodolistDatabase.getInstance(this@MainActivity)
       /* db?.insertTask(
            tarea(0,"ir al gym","t1","i1","f1",1),
            tarea(0,"sacar al perro","t2","i2","f2",0),
            tarea(0,"hacer la compra","t3","i3","f3",1),
        );*/

        datos = db?.getAll() ?: mutableListOf()
        Log.i("XXX",datos.toString())
        with(binding) {
            // recycler view
            adaptador = TaskAdapter(datos)
            // borrar task
            adaptador.getBorrar { position ->
                MaterialAlertDialogBuilder(this@MainActivity)
                    .setTitle("Borrar tarea")
                    .setMessage("¿Quieres borrar la tarea?")

                    .setNegativeButton("No") { dialog, which ->
                       //
                    }
                    .setPositiveButton("Si") { dialog, which ->
                        total_task--
                        var o = datos[position]
                        if(o.completada == 1) {
                            complete_task--
                        } else {
                            uncomplete_task--
                        }
                        actChip(total_task,complete_task,uncomplete_task)
                        db?.deleteTask(datos[position])
                        datos.removeAt(position)
                        adaptador.notifyItemRemoved(position)


                    }
                    .show()
            }
            // actualizar task
            adaptador.getUpdate { position ->
                var n = datos[position]
                if(n.completada == 0) {
                    n.completada = 1
                    complete_task++
                    uncomplete_task--
                    actChip(total_task,complete_task,uncomplete_task)
                } else {
                    n.completada = 0
                    complete_task--
                    uncomplete_task++
                    actChip(total_task,complete_task,uncomplete_task)
                }

                db?.updateTask(n)
                datos[position] = n
                adaptador.notifyItemChanged(position)
            }
            // asignacion del adaptador
            rvTask.adapter = adaptador
           // rvTask.layoutManager = LinearLayoutManager(this@MainActivity)

        }

        // chips
        total_task = datos.size
        complete_task = db?.getCompleteTask()?.size!!
        uncomplete_task = complete_task?.let { total_task - complete_task } ?: 0

        complete_task?.let {
            actChip(total_task, complete_task, uncomplete_task)
        }

    }

    fun notify(param: ActivityResult) {
        val mensaje = when(param.resultCode) {
            RESULT_CANCELED -> "No se ha insertado la tarea"
            RESULT_OK -> "Se ha insertado la tarea"
            else -> ""
        }
        Snackbar.make(binding.root,mensaje,Snackbar.LENGTH_LONG).show()

        if(param.resultCode == RESULT_OK) {
            var r = param.data?.extras?.get("task") as tarea
            // inyectamos en la base de datos
            var db = TodolistDatabase.getInstance(this@MainActivity)

            db?.insertTask(r)
            datos.add(r)
            adaptador.notifyItemInserted(this.datos.size-1)

            total_task++
            uncomplete_task++
            actChip(total_task,complete_task,uncomplete_task)

        }
    }
    fun actChip(total: Int,complete: Int,uncomplete: Int) {
        with(binding) {
            chpTotalTask.text = resources.getString(R.string.total_task,total)
            chpCompleteTask.text = resources.getString(R.string.complete_task,complete)
            chpUncompleteTask.text = resources.getString(R.string.uncomplete_task,uncomplete)
            btnCrear.setOnClickListener {
                val i = Intent(this@MainActivity,CreateActivity::class.java)
                registerForResult.launch(i)
            }
        }
    }
}