package com.example.personaltrainer.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "personal_trainer.db"
        private const val DATABASE_VERSION = 1

        // Tablas
        const val TABLE_CLIENTE = "Cliente"
        const val TABLE_PROGRESO = "Progreso"
        const val TABLE_ENTRENAMIENTO = "Entrenamiento"
        const val TABLE_RUTINA = "Rutina"
        const val TABLE_ASIGNAR_RUTINA = "Asignar_rutina"
        const val TABLE_EJERCICIO = "Ejercicio"
        const val TABLE_DETALLE_EJERCICIO = "Detalle_ejercicio"

        // Columnas de la tabla Cliente
        const val COLUMN_CLIENTE_ID = "ID"
        const val COLUMN_CLIENTE_NOMBRE = "Nombre"
        const val COLUMN_CLIENTE_APELLIDO = "Apellido"
        const val COLUMN_CLIENTE_EDAD = "Edad"
        const val COLUMN_CLIENTE_SEXO = "Sexo"
        const val COLUMN_CLIENTE_TELEFONO = "Telefono"
        const val COLUMN_CLIENTE_EMAIL = "Email"
        const val COLUMN_CLIENTE_ALTURA = "Altura"

        // Columnas de la tabla Progreso
        const val COLUMN_PROGRESO_ID = "ID"
        const val COLUMN_PROGRESO_FECHA = "Fecha"
        const val COLUMN_PROGRESO_PESO = "Peso"
        const val COLUMN_PROGRESO_NIVEL = "Nivel"
        const val COLUMN_PROGRESO_OBJETIVO = "Objetivo"
        const val COLUMN_PROGRESO_OBSERVACION = "Observacion"
        const val COLUMN_PROGRESO_CLIENTE_ID = "cliente_id"

        // Columnas de la tabla Entrenamiento
        const val COLUMN_ENTRENAMIENTO_ID = "ID"
        const val COLUMN_ENTRENAMIENTO_FECHA_INI = "Fecha_INI"
        const val COLUMN_ENTRENAMIENTO_FECHA_FIN = "Fecha_FIN"
        const val COLUMN_ENTRENAMIENTO_CLIENTE_ID = "cliente_id"

        // Columnas de la tabla Rutina
        const val COLUMN_RUTINA_ID = "ID"
        const val COLUMN_RUTINA_FECHA = "Fecha"
        const val COLUMN_RUTINA_COMENTARIO = "Comentario"

        // Columnas de la tabla Asignar_rutina
        const val COLUMN_ASIGNAR_RUTINA_ENTRENA_ID = "entrena_id"
        const val COLUMN_ASIGNAR_RUTINA_RUTINA_ID = "rutina_id"

        // Columnas de la tabla Ejercicio
        const val COLUMN_EJERCICIO_ID = "ID"
        const val COLUMN_EJERCICIO_NOMBRE = "Nombre"
        const val COLUMN_EJERCICIO_INSTRUCCIONES = "Instrucciones"
        const val COLUMN_EJERCICIO_LINK_VIDEO = "link_video"

        // Columnas de la tabla Detalle_ejercicio
        const val COLUMN_DETALLE_EJERCICIO_ID = "ID"
        const val COLUMN_DETALLE_EJERCICIO_DURACION = "Duracion"
        const val COLUMN_DETALLE_EJERCICIO_REPETICIONES = "Repeticiones"
        const val COLUMN_DETALLE_EJERCICIO_SERIES = "Series"
        const val COLUMN_DETALLE_EJERCICIO_RUTINA_ID = "rutina_id"
        const val COLUMN_DETALLE_EJERCICIO_EJERCICIO_ID = "ejercicio_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tabla Cliente
        val createTableCliente = ("CREATE TABLE $TABLE_CLIENTE (" +
                "$COLUMN_CLIENTE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_CLIENTE_NOMBRE TEXT NOT NULL, " +
                "$COLUMN_CLIENTE_APELLIDO TEXT NOT NULL, " +
                "$COLUMN_CLIENTE_EDAD INTEGER NOT NULL, " +
                "$COLUMN_CLIENTE_SEXO TEXT NOT NULL, " +
                "$COLUMN_CLIENTE_TELEFONO TEXT NOT NULL, " +
                "$COLUMN_CLIENTE_EMAIL TEXT NOT NULL, " +
                "$COLUMN_CLIENTE_ALTURA REAL NOT NULL)")
        db.execSQL(createTableCliente)

        // Crear tabla Progreso
        val createTableProgreso = ("CREATE TABLE $TABLE_PROGRESO (" +
                "$COLUMN_PROGRESO_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_PROGRESO_FECHA DATE NOT NULL, " +
                "$COLUMN_PROGRESO_PESO REAL NOT NULL, " +
                "$COLUMN_PROGRESO_NIVEL TEXT NOT NULL, " +
                "$COLUMN_PROGRESO_OBJETIVO TEXT NOT NULL, " +
                "$COLUMN_PROGRESO_OBSERVACION TEXT, " +
                "$COLUMN_PROGRESO_CLIENTE_ID INTEGER NOT NULL, " +
                "FOREIGN KEY ($COLUMN_PROGRESO_CLIENTE_ID) REFERENCES $TABLE_CLIENTE($COLUMN_CLIENTE_ID))")
        db.execSQL(createTableProgreso)

        // Crear tabla Entrenamiento
        val createTableEntrenamiento = ("CREATE TABLE $TABLE_ENTRENAMIENTO (" +
                "$COLUMN_ENTRENAMIENTO_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_ENTRENAMIENTO_FECHA_INI DATE NOT NULL, " +
                "$COLUMN_ENTRENAMIENTO_FECHA_FIN DATE, " +
                "$COLUMN_ENTRENAMIENTO_CLIENTE_ID INTEGER NOT NULL, " +
                "FOREIGN KEY ($COLUMN_ENTRENAMIENTO_CLIENTE_ID) REFERENCES $TABLE_CLIENTE($COLUMN_CLIENTE_ID))")
        db.execSQL(createTableEntrenamiento)

        // Crear tabla Rutina
        val createTableRutina = ("CREATE TABLE $TABLE_RUTINA (" +
                "$COLUMN_RUTINA_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_RUTINA_FECHA DATE NOT NULL, " +
                "$COLUMN_RUTINA_COMENTARIO TEXT)")
        db.execSQL(createTableRutina)

        // Crear tabla Asignar_rutina
        val createTableAsignarRutina = ("CREATE TABLE $TABLE_ASIGNAR_RUTINA (" +
                "$COLUMN_ASIGNAR_RUTINA_ENTRENA_ID INTEGER NOT NULL, " +
                "$COLUMN_ASIGNAR_RUTINA_RUTINA_ID INTEGER NOT NULL, " +
                "PRIMARY KEY ($COLUMN_ASIGNAR_RUTINA_ENTRENA_ID, $COLUMN_ASIGNAR_RUTINA_RUTINA_ID), " +
                "FOREIGN KEY ($COLUMN_ASIGNAR_RUTINA_ENTRENA_ID) REFERENCES $TABLE_ENTRENAMIENTO($COLUMN_ENTRENAMIENTO_ID), " +
                "FOREIGN KEY ($COLUMN_ASIGNAR_RUTINA_RUTINA_ID) REFERENCES $TABLE_RUTINA($COLUMN_RUTINA_ID))")
        db.execSQL(createTableAsignarRutina)

        // Crear tabla Ejercicio
        val createTableEjercicio = ("CREATE TABLE $TABLE_EJERCICIO (" +
                "$COLUMN_EJERCICIO_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_EJERCICIO_NOMBRE TEXT NOT NULL, " +
                "$COLUMN_EJERCICIO_INSTRUCCIONES TEXT NOT NULL, " +
                "$COLUMN_EJERCICIO_LINK_VIDEO TEXT)")
        db.execSQL(createTableEjercicio)

        // Crear tabla Detalle_ejercicio
        val createTableDetalleEjercicio = ("CREATE TABLE $TABLE_DETALLE_EJERCICIO (" +
                "$COLUMN_DETALLE_EJERCICIO_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_DETALLE_EJERCICIO_DURACION INTEGER NOT NULL, " +
                "$COLUMN_DETALLE_EJERCICIO_REPETICIONES INTEGER NOT NULL, " +
                "$COLUMN_DETALLE_EJERCICIO_SERIES INTEGER NOT NULL, " +
                "$COLUMN_DETALLE_EJERCICIO_RUTINA_ID INTEGER NOT NULL, " +
                "$COLUMN_DETALLE_EJERCICIO_EJERCICIO_ID INTEGER NOT NULL, " +
                "FOREIGN KEY ($COLUMN_DETALLE_EJERCICIO_RUTINA_ID) REFERENCES $TABLE_RUTINA($COLUMN_RUTINA_ID), " +
                "FOREIGN KEY ($COLUMN_DETALLE_EJERCICIO_EJERCICIO_ID) REFERENCES $TABLE_EJERCICIO($COLUMN_EJERCICIO_ID))")
        db.execSQL(createTableDetalleEjercicio)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DETALLE_EJERCICIO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EJERCICIO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ASIGNAR_RUTINA")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_RUTINA")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ENTRENAMIENTO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PROGRESO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CLIENTE")
        onCreate(db)
    }
}
