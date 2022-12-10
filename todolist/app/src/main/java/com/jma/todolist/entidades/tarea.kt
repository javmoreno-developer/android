package com.jma.todolist.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class tarea(
    @PrimaryKey(autoGenerate = true) var idTar: Int = 0,
    @ColumnInfo var titulo: String,
    @ColumnInfo var texto: String,
    @ColumnInfo var inicio: String,
    @ColumnInfo var fin: String,
    @ColumnInfo var completada: Int,

) : java.io.Serializable