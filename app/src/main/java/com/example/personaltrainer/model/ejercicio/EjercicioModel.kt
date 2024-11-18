package com.example.personaltrainer.model.ejercicio

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.personaltrainer.database.DBHelper

data class EjercicioModel(
    var id: Int = 0,
    var nombre: String,
    var instrucciones: String,
    var linkVideo: String
)

class EjercicioModelBD(context: Context) {  // Cambiado aquí
    private val dbHelper = DBHelper(context)  // Instancia DBHelper correctamente

    fun addEjercicio(ejercicio: EjercicioModel): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COLUMN_EJERCICIO_NOMBRE, ejercicio.nombre)
            put(DBHelper.COLUMN_EJERCICIO_INSTRUCCIONES, ejercicio.instrucciones)
            put(DBHelper.COLUMN_EJERCICIO_LINK_VIDEO, ejercicio.linkVideo)
        }

        val success = db.insert(DBHelper.TABLE_EJERCICIO, null, values)
        db.close()
        return success
    }

    fun updateEjercicio(ejercicio: EjercicioModel): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COLUMN_EJERCICIO_NOMBRE, ejercicio.nombre)
            put(DBHelper.COLUMN_EJERCICIO_INSTRUCCIONES, ejercicio.instrucciones)
            put(DBHelper.COLUMN_EJERCICIO_LINK_VIDEO, ejercicio.linkVideo)
        }

        val success = db.update(DBHelper.TABLE_EJERCICIO, values, "${DBHelper.COLUMN_EJERCICIO_ID}=?", arrayOf(ejercicio.id.toString()))
        db.close()
        return success
    }

    fun deleteEjercicio(id: Int): Int {
        val db = dbHelper.writableDatabase
        val success = db.delete(DBHelper.TABLE_EJERCICIO, "${DBHelper.COLUMN_EJERCICIO_ID}=?", arrayOf(id.toString()))
        db.close()
        return success
    }

    fun getAllEjercicios(): List<EjercicioModel> {
        val ejercicioList = mutableListOf<EjercicioModel>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            DBHelper.TABLE_EJERCICIO,
            null, null, null, null, null, null
        )

        if (cursor.moveToFirst()) {
            do {
                val ejercicio = EjercicioModel(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_EJERCICIO_ID)),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_EJERCICIO_NOMBRE)),
                    instrucciones = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_EJERCICIO_INSTRUCCIONES)),
                    linkVideo = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_EJERCICIO_LINK_VIDEO))
                )
                ejercicioList.add(ejercicio)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return ejercicioList
    }

    fun getEjercicioById(id: Int): EjercicioModel? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DBHelper.TABLE_EJERCICIO, null, "${DBHelper.COLUMN_EJERCICIO_ID}=?", arrayOf(id.toString()),
            null, null, null
        )
        var ejercicio: EjercicioModel? = null
        if (cursor.moveToFirst()) {
            ejercicio = EjercicioModel(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_EJERCICIO_ID)),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_EJERCICIO_NOMBRE)),
                instrucciones = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_EJERCICIO_INSTRUCCIONES)),
                linkVideo = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_EJERCICIO_LINK_VIDEO))
            )
        }
        cursor.close()
        db.close()
        return ejercicio
    }
}
