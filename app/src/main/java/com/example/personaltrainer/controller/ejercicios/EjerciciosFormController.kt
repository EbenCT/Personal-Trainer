package com.example.personaltrainer.controller.ejercicios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.personaltrainer.R
import com.example.personaltrainer.model.ejercicio.EjercicioModel
import com.example.personaltrainer.model.ejercicio.EjercicioModelBD

class EjerciciosFormController : AppCompatActivity() {
    private lateinit var ejercicioModelBD: EjercicioModelBD
    private var ejercicioId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ejercicios_form)

        ejercicioModelBD = EjercicioModelBD(this)

        val etNombre = findViewById<EditText>(R.id.etNombreEjercicio)
        val etLinkVideo = findViewById<EditText>(R.id.etLinkVideo)
        val etInstrucciones = findViewById<EditText>(R.id.etInstrucciones)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarEjercicio)

        // Revisar si el intent contiene un ID
        ejercicioId = intent.getIntExtra("id", 0)
        if (ejercicioId != 0) {
            // Si el ejercicioId es distinto de 0, cargar los datos del ejercicio
            val ejercicio = ejercicioModelBD.getEjercicioById(ejercicioId)
            if (ejercicio != null) {
                etNombre.setText(ejercicio.nombre)
                etLinkVideo.setText(ejercicio.linkVideo)
                etInstrucciones.setText(ejercicio.instrucciones)
            }
        }

        // Listener para guardar o modificar el ejercicio
        btnGuardar.setOnClickListener {
            val ejercicioModel = EjercicioModel(
                id = ejercicioId, // Si ejercicioId es 0, se crea un nuevo ejercicio, si no, se actualiza el existente
                nombre = etNombre.text.toString(),
                instrucciones = etInstrucciones.text.toString(),
                linkVideo = etLinkVideo.text.toString()
            )

            if (ejercicioId == 0) {
                // Agregar nuevo ejercicio
                val success = ejercicioModelBD.addEjercicio(ejercicioModel)
                if (success > -1) {
                    Toast.makeText(this, "Ejercicio agregado con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al agregar ejercicio", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Actualizar ejercicio existente
                val success = ejercicioModelBD.updateEjercicio(ejercicioModel)
                if (success > 0) {
                    Toast.makeText(this, "Ejercicio actualizado con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al actualizar ejercicio", Toast.LENGTH_SHORT).show()
                }
            }

            // Redirigir de nuevo a la lista de ejercicios
            val intent = Intent(this, EjerciciosListaController::class.java)
            startActivity(intent)
        }
    }
}
