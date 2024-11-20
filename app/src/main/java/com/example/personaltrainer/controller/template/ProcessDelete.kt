package com.example.personaltrainer.controller.template

import android.app.Activity
import android.content.Context
import android.widget.Toast

abstract class ProcessDelete {

    // Método plantilla
    fun generateProcessDelete(context: Context, idText: String) {
        if (idText.isNotEmpty()) {
            try {
                val id = idText.toInt()
                eliminar(id)
                Toast.makeText(context, "${nombreElemento()} eliminado", Toast.LENGTH_SHORT).show()
                (context as? Activity)?.recreate()
            } catch (e: Exception) {
                Toast.makeText(context, "Error al eliminar ${nombreElemento()}: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Ingrese un ID", Toast.LENGTH_SHORT).show()
        }
    }

    abstract fun eliminar(id: Int)

    abstract fun nombreElemento(): String
}