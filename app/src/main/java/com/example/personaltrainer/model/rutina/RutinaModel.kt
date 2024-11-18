package com.example.personaltrainer.model.rutina
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.personaltrainer.database.DBHelper

data class RutinaModel(
    var fecha: String,
    var comentario: String,
    var id: Int = 0
)

class RutinaModelBD(context: Context) {

    private val dbHelper = DBHelper(context) // Usar un DBHelper genérico

    fun insertRutina(rutina: RutinaModel): Long {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("fecha", rutina.fecha)
            put("comentario", rutina.comentario)
        }
        return db.insert("rutinas", null, values)
    }

    fun updateRutina(rutina: RutinaModel): Int {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("fecha", rutina.fecha)
            put("comentario", rutina.comentario)
        }
        return db.update("Rutina", values, "id=?", arrayOf(rutina.id.toString()))
    }

    fun deleteRutina(id: Int): Int {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        return db.delete("Rutina", "id=?", arrayOf(id.toString()))
    }

    fun getAllRutinas(): List<RutinaModel> {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            "Rutina", null, null, null, null, null, null
        )

        val rutinas = ArrayList<RutinaModel>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val fecha = getString(getColumnIndexOrThrow("fecha"))
                val comentario = getString(getColumnIndexOrThrow("comentario"))
                rutinas.add(RutinaModel(fecha, comentario, id))
            }
        }
        cursor.close()
        return rutinas
    }

    fun getRutinaById(id: Int): RutinaModel? {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            "Rutina", null, "id=?", arrayOf(id.toString()), null, null, null
        )

        var rutina: RutinaModel? = null
        if (cursor.moveToFirst()) {
            val fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))
            val comentario = cursor.getString(cursor.getColumnIndexOrThrow("comentario"))
            rutina = RutinaModel(fecha, comentario, id)
        }
        cursor.close()
        return rutina
    }
}
