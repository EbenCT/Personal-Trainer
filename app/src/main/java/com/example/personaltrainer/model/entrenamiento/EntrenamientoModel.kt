package com.example.personaltrainer.model.entrenamiento

import android.content.ContentValues
import android.content.Context
import com.example.personaltrainer.database.DBHelper

data class EntrenamientoModel(
    var id: Int = 0,
    var clienteId: Int,
    var fechaInicio: String,
    var fechaFin: String
)

class EntrenamientoModelBD(context: Context) {
    private val dbHelper = DBHelper(context)

    fun addEntrenamiento(entrenamiento: EntrenamientoModel): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(DBHelper.COLUMN_ENTRENAMIENTO_CLIENTE_ID, entrenamiento.clienteId)
        values.put(DBHelper.COLUMN_ENTRENAMIENTO_FECHA_INI, entrenamiento.fechaInicio)
        values.put(DBHelper.COLUMN_ENTRENAMIENTO_FECHA_FIN, entrenamiento.fechaFin)

        val success = db.insert(DBHelper.TABLE_ENTRENAMIENTO, null, values)
        db.close()
        return success
    }

    fun getEntrenamientoById(id: Int): EntrenamientoModel? {
        val db = dbHelper.readableDatabase
        var entrenamiento: EntrenamientoModel? = null
        val selectQuery = "SELECT * FROM ${DBHelper.TABLE_ENTRENAMIENTO} WHERE ${DBHelper.COLUMN_ENTRENAMIENTO_ID} = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(id.toString()))

        if (cursor.moveToFirst()) {
            entrenamiento = EntrenamientoModel(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ENTRENAMIENTO_ID)),
                clienteId = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ENTRENAMIENTO_CLIENTE_ID)),
                fechaInicio = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ENTRENAMIENTO_FECHA_INI)),
                fechaFin = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ENTRENAMIENTO_FECHA_FIN))
            )
        }

        cursor.close()
        db.close()
        return entrenamiento
    }


    fun updateEntrenamiento(entrenamiento: EntrenamientoModel): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(DBHelper.COLUMN_ENTRENAMIENTO_CLIENTE_ID, entrenamiento.clienteId)
        values.put(DBHelper.COLUMN_ENTRENAMIENTO_FECHA_INI, entrenamiento.fechaInicio)
        values.put(DBHelper.COLUMN_ENTRENAMIENTO_FECHA_FIN, entrenamiento.fechaFin)

        val success = db.update(DBHelper.TABLE_ENTRENAMIENTO, values, "${DBHelper.COLUMN_ENTRENAMIENTO_ID}=?", arrayOf(entrenamiento.id.toString()))
        db.close()
        return success
    }

    fun deleteEntrenamiento(id: Int): Int {
        val db = dbHelper.writableDatabase
        val success = db.delete(DBHelper.TABLE_ENTRENAMIENTO, "${DBHelper.COLUMN_ENTRENAMIENTO_ID}=?", arrayOf(id.toString()))
        db.close()
        return success
    }

    fun getAllEntrenamientos(): List<EntrenamientoModel> {
        val entrenamientosList = mutableListOf<EntrenamientoModel>()
        val selectQuery = "SELECT * FROM ${DBHelper.TABLE_ENTRENAMIENTO}"
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val entrenamiento = EntrenamientoModel(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ENTRENAMIENTO_ID)),
                    clienteId = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ENTRENAMIENTO_CLIENTE_ID)),
                    fechaInicio = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ENTRENAMIENTO_FECHA_INI)),
                    fechaFin = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ENTRENAMIENTO_FECHA_FIN))
                )
                entrenamientosList.add(entrenamiento)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return entrenamientosList
    }
}
