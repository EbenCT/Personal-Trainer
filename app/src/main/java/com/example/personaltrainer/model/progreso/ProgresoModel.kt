package com.example.personaltrainer.model.progreso

import android.content.ContentValues
import android.content.Context
import com.example.personaltrainer.database.DBHelper

data class ProgresoModel(
    var id: Int = 0,
    var fecha: String,
    var peso: Double,
    var nivel: String,
    var objetivo: String,
    var observacion: String,
    var clienteId: Int
)

class ProgresoModelBD(context: Context) {
    private val dbHelper = DBHelper(context)
    fun addProgreso(progreso: ProgresoModel) {
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(DBHelper.COLUMN_PROGRESO_FECHA, progreso.fecha)
        values.put(DBHelper.COLUMN_PROGRESO_PESO, progreso.peso)
        values.put(DBHelper.COLUMN_PROGRESO_NIVEL, progreso.nivel)
        values.put(DBHelper.COLUMN_PROGRESO_OBJETIVO, progreso.objetivo)
        values.put(DBHelper.COLUMN_PROGRESO_OBSERVACION, progreso.observacion)
        values.put(DBHelper.COLUMN_PROGRESO_CLIENTE_ID, progreso.clienteId)

        db.insert(DBHelper.TABLE_PROGRESO, null, values)
        db.close()
    }

    fun updateProgreso(progreso: ProgresoModel): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(DBHelper.COLUMN_PROGRESO_FECHA, progreso.fecha)
        values.put(DBHelper.COLUMN_PROGRESO_PESO, progreso.peso)
        values.put(DBHelper.COLUMN_PROGRESO_NIVEL, progreso.nivel)
        values.put(DBHelper.COLUMN_PROGRESO_OBJETIVO, progreso.objetivo)
        values.put(DBHelper.COLUMN_PROGRESO_OBSERVACION, progreso.observacion)
        values.put(DBHelper.COLUMN_PROGRESO_CLIENTE_ID, progreso.clienteId)

        val success = db.update(DBHelper.TABLE_PROGRESO, values, "${DBHelper.COLUMN_PROGRESO_ID}=?", arrayOf(progreso.id.toString()))
        db.close()
        return success
    }

    fun deleteProgreso(id: Int): Int {
        val db = dbHelper.writableDatabase
        val success = db.delete(DBHelper.TABLE_PROGRESO, "${DBHelper.COLUMN_PROGRESO_ID}=?", arrayOf(id.toString()))
        db.close()
        return success
    }

    fun getProgresosByClienteId(clienteId: Int): List<ProgresoModel> {
        val progresoList = mutableListOf<ProgresoModel>()
        val selectQuery = "SELECT * FROM ${DBHelper.TABLE_PROGRESO} WHERE ${DBHelper.COLUMN_PROGRESO_CLIENTE_ID} = $clienteId"
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val progreso = ProgresoModel(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROGRESO_ID)),
                    fecha = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROGRESO_FECHA)),
                    peso = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROGRESO_PESO)),
                    nivel = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROGRESO_NIVEL)),
                    objetivo = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROGRESO_OBJETIVO)),
                    observacion = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROGRESO_OBSERVACION)),
                    clienteId = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROGRESO_CLIENTE_ID))
                )
                progresoList.add(progreso)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return progresoList
    }
    fun getProgresoById(id: Int): ProgresoModel? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DBHelper.TABLE_PROGRESO,
            null,
            "${DBHelper.COLUMN_PROGRESO_ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        return if (cursor != null && cursor.moveToFirst()) {
            val progreso = ProgresoModel(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROGRESO_ID)),
                fecha = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROGRESO_FECHA)),
                peso = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROGRESO_PESO)),
                nivel = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROGRESO_NIVEL)),
                objetivo = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROGRESO_OBJETIVO)),
                observacion = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROGRESO_OBSERVACION)),
                clienteId = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROGRESO_CLIENTE_ID))
            )
            cursor.close()
            db.close()
            progreso
        } else {
            cursor?.close()
            db.close()
            null // Si no se encontró el progreso
        }
    }
}
