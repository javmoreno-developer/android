package com.jma.todolist.basedatos

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jma.todolist.entidades.tarea

@Database(entities = arrayOf(tarea::class), version = 1)
abstract class todolistConnection : RoomDatabase() {
    abstract fun dao(): TodolistDao
}