package com.example.personaltrainer.model.cliente
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.personaltrainer.database.DBHelper

data class ClienteModel(
    var id: Int = 0,
    var nombre: String,
    var apellido: String,
    var edad: Int,
    var sexo: String,
    var altura: Int,
    var telefono: String,
    var email: String
)

class ClienteModelBD(context: Context) {
    private val dbHelper = DBHelper(context)

    // Agregar cliente
    fun addCliente(clienteModel: ClienteModel): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COLUMN_CLIENTE_NOMBRE, clienteModel.nombre)
            put(DBHelper.COLUMN_CLIENTE_APELLIDO, clienteModel.apellido)
            put(DBHelper.COLUMN_CLIENTE_EDAD, clienteModel.edad)
            put(DBHelper.COLUMN_CLIENTE_SEXO, clienteModel.sexo)
            put(DBHelper.COLUMN_CLIENTE_ALTURA, clienteModel.altura)
            put(DBHelper.COLUMN_CLIENTE_TELEFONO, clienteModel.telefono)
            put(DBHelper.COLUMN_CLIENTE_EMAIL, clienteModel.email)
        }
        val success = db.insert(DBHelper.TABLE_CLIENTE, null, values)
        db.close()
        return success
    }

    // Obtener cliente por ID
    fun getClienteById(id: Int): ClienteModel? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            DBHelper.TABLE_CLIENTE,
            arrayOf(DBHelper.COLUMN_CLIENTE_ID, DBHelper.COLUMN_CLIENTE_NOMBRE, DBHelper.COLUMN_CLIENTE_APELLIDO, DBHelper.COLUMN_CLIENTE_EDAD,
                DBHelper.COLUMN_CLIENTE_SEXO, DBHelper.COLUMN_CLIENTE_ALTURA, DBHelper.COLUMN_CLIENTE_TELEFONO, DBHelper.COLUMN_CLIENTE_EMAIL),
            "${DBHelper.COLUMN_CLIENTE_ID}=?",
            arrayOf(id.toString()), null, null, null
        )

        return if (cursor.moveToFirst()) {
            val cliente = ClienteModel(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CLIENTE_ID)),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CLIENTE_NOMBRE)),
                apellido = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CLIENTE_APELLIDO)),
                edad = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CLIENTE_EDAD)),
                sexo = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CLIENTE_SEXO)),
                altura = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CLIENTE_ALTURA)),
                telefono = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CLIENTE_TELEFONO)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CLIENTE_EMAIL))
            )
            cursor.close()
            db.close()
            cliente
        } else {
            cursor.close()
            db.close()
            null
        }
    }

    // Modificar cliente
    fun updateCliente(clienteModel: ClienteModel): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COLUMN_CLIENTE_NOMBRE, clienteModel.nombre)
            put(DBHelper.COLUMN_CLIENTE_APELLIDO, clienteModel.apellido)
            put(DBHelper.COLUMN_CLIENTE_EDAD, clienteModel.edad)
            put(DBHelper.COLUMN_CLIENTE_SEXO, clienteModel.sexo)
            put(DBHelper.COLUMN_CLIENTE_ALTURA, clienteModel.altura)
            put(DBHelper.COLUMN_CLIENTE_TELEFONO, clienteModel.telefono)
            put(DBHelper.COLUMN_CLIENTE_EMAIL, clienteModel.email)
        }
        val success = db.update(DBHelper.TABLE_CLIENTE, values, "${DBHelper.COLUMN_CLIENTE_ID}=?", arrayOf(clienteModel.id.toString()))
        db.close()
        return success
    }

    // Eliminar cliente
    fun deleteCliente(id: Int): Int {
        val db = dbHelper.writableDatabase
        val success = db.delete(DBHelper.TABLE_CLIENTE, "${DBHelper.COLUMN_CLIENTE_ID}=?", arrayOf(id.toString()))
        db.close()
        return success
    }
    // Obtener todos los clientes
    fun getAllClientes(): List<ClienteModel> {
        val clienteList = mutableListOf<ClienteModel>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            DBHelper.TABLE_CLIENTE,
            arrayOf(DBHelper.COLUMN_CLIENTE_ID, DBHelper.COLUMN_CLIENTE_NOMBRE, DBHelper.COLUMN_CLIENTE_APELLIDO, DBHelper.COLUMN_CLIENTE_EDAD,
                DBHelper.COLUMN_CLIENTE_SEXO, DBHelper.COLUMN_CLIENTE_ALTURA, DBHelper.COLUMN_CLIENTE_TELEFONO, DBHelper.COLUMN_CLIENTE_EMAIL),
            null, null, null, null, null
        )

        if (cursor.moveToFirst()) {
            do {
                val cliente = ClienteModel(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CLIENTE_ID)),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CLIENTE_NOMBRE)),
                    apellido = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CLIENTE_APELLIDO)),
                    edad = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CLIENTE_EDAD)),
                    sexo = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CLIENTE_SEXO)),
                    altura = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CLIENTE_ALTURA)),
                    telefono = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CLIENTE_TELEFONO)),
                    email = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CLIENTE_EMAIL))
                )
                clienteList.add(cliente)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return clienteList
    }
}