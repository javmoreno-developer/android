package com.jma.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jma.todolist.databinding.ItemRecyclerBinding
import com.jma.todolist.entidades.tarea

class TaskAdapter(val lista: MutableList<tarea>) : RecyclerView.Adapter<TaskAdapter.TaskContenedor>() {

    private var borrarTask: (Int)-> Unit = {}
    fun getBorrar(param: (Int)->Unit) {
        this.borrarTask = param
    }

    private var updateTask: (Int)-> Unit = {}
    fun getUpdate(param: (Int) -> Unit) {
        this.updateTask = param
    }

    /**
     * Contenedor
     */
    inner class TaskContenedor(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(t: tarea) {
            with(binding) {
                txtTask.text = t.titulo
                if(t.completada == 1) {
                    Glide.with(binding.root).load(R.drawable.ic_completa).centerCrop().into(iconFirst)
                } else {
                    Glide.with(binding.root).load(R.drawable.ic_incompleta).centerCrop().into(iconFirst)
               }
                btnDel.setOnClickListener { borrarTask(adapterPosition) }
                iconFirst.setOnClickListener { updateTask(adapterPosition) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskContenedor {
        val inflador = LayoutInflater.from(parent.context)
        val binding = ItemRecyclerBinding.inflate(inflador,parent,false)
        return TaskContenedor(binding)
    }

    override fun onBindViewHolder(holder: TaskContenedor, position: Int) {
        holder.bindItem(this.lista[position])
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }


}