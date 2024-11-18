package com.example.personaltrainer.controller.entrenamiento

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.personaltrainer.R
import com.example.personaltrainer.model.entrenamiento.EntrenamientoModel
import com.example.personaltrainer.model.entrenamiento.EntrenamientoModelBD

class EntrenamientoFormController : AppCompatActivity() {
    private lateinit var entrenamientoModelBD: EntrenamientoModelBD
    private var entrenamientoId: Int = 0 // Variable para almacenar el ID del entrenamiento

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entrenamiento_form)

        entrenamientoModelBD = EntrenamientoModelBD(this)

        val etClienteId = findViewById<EditText>(R.id.etClienteId)
        val etFechaInicio = findViewById<EditText>(R.id.etFechaInicio)
        val etFechaFin = findViewById<EditText>(R.id.etFechaFin)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        // Revisar si el intent contiene un ID de entrenamiento
        entrenamientoId = intent.getIntExtra("id", 0)
        if (entrenamientoId != 0) {
            // Si entrenamientoId es distinto de 0, cargar los datos del entrenamiento
            val entrenamiento = entrenamientoModelBD.getEntrenamientoById(entrenamientoId)
            if (entrenamiento != null) {
                etFechaInicio.setText(entrenamiento.fechaInicio)
                etFechaFin.setText(entrenamiento.fechaFin)
                etClienteId.setText(entrenamiento.clienteId.toString()) // Cargar el clienteId si existe
            }
        }

        // Listener para guardar o modificar el entrenamiento
        btnGuardar.setOnClickListener {
            val clienteId = etClienteId.text.toString().toIntOrNull()
            if (clienteId == null) {
                Toast.makeText(this, "Ingrese un Cliente ID válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val entrenamientoModel = EntrenamientoModel(
                id = entrenamientoId, // Si entrenamientoId es 0, se crea un nuevo entrenamiento, si no, se actualiza el existente
                clienteId = clienteId,
                fechaInicio = etFechaInicio.text.toString(),
                fechaFin = etFechaFin.text.toString()
            )

            if (entrenamientoId == 0) {
                // Agregar nuevo entrenamiento
                val success = entrenamientoModelBD.addEntrenamiento(entrenamientoModel)
                if (success > -1) {
                    Toast.makeText(this, "Entrenamiento agregado con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al agregar entrenamiento", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Actualizar entrenamiento existente
                val success = entrenamientoModelBD.updateEntrenamiento(entrenamientoModel)
                if (success > 0) {
                    Toast.makeText(this, "Entrenamiento actualizado con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al actualizar entrenamiento", Toast.LENGTH_SHORT).show()
                }
            }

            // Redirigir de nuevo a la lista de entrenamientos
            val intent = Intent(this, EntrenamientoListaController::class.java)
            startActivity(intent)
        }
    }
}
