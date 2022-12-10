package com.jma.todolist.basedatos

import androidx.room.*
import com.jma.todolist.entidades.tarea

@Dao
interface TodolistDao {
    @Delete
    fun deleteTask(t:tarea)

    @Insert
    fun insertTask(vararg t: tarea)

    @Query("SELECT * FROM tarea")
    fun getAll() : MutableList<tarea>

    @Query("SELECT * FROM tarea WHERE completada=1")
    fun getCompleteTask() : MutableList<tarea>

    @Update
    fun updateTask(t: tarea)
}