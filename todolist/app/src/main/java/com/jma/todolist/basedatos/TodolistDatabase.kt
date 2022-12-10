package com.jma.todolist.basedatos

import android.content.Context
import androidx.room.Room

object TodolistDatabase {
    private var instace : todolistConnection? = null

    fun getInstance(context: Context) : TodolistDao? {
        if(instace == null) {
            instace = Room.databaseBuilder(context,todolistConnection::class.java,"todolist").allowMainThreadQueries().build()
        }
        return instace?.dao()
    }
}