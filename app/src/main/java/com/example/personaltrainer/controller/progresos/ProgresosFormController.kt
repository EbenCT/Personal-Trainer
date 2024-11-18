package com.example.personaltrainer.controller.progresos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.personaltrainer.R
import com.example.personaltrainer.model.progreso.ProgresoModel
import com.example.personaltrainer.model.progreso.ProgresoModelBD

class ProgresosFormController : AppCompatActivity() {

    private lateinit var progresoModelBD: ProgresoModelBD
    private lateinit var etFecha: EditText
    private lateinit var etPeso: EditText
    private lateinit var etNivel: EditText
    private lateinit var etObjetivo: EditText
    private lateinit var etObservacion: EditText
    private var clienteId: Int = 0
    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.progresos_form)

        progresoModelBD = ProgresoModelBD(this)

        etFecha = findViewById(R.id.etFecha)
        etPeso = findViewById(R.id.etPeso)
        etNivel = findViewById(R.id.etNivel)
        etObjetivo = findViewById(R.id.etObjetivo)
        etObservacion = findViewById(R.id.etObservacion)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarProgreso)

        clienteId = intent.getIntExtra("cliente_id", 0)
        id = intent.getIntExtra("id", -1)
        if (id != -1) {
            cargarDatosProgreso(id!!)
        }

        btnGuardar.setOnClickListener {
            val fecha = etFecha.text.toString()
            val peso = etPeso.text.toString().toDouble()
            val nivel = etNivel.text.toString()
            val objetivo = etObjetivo.text.toString()
            val observacion = etObservacion.text.toString()

            if (id == -1) {
                guardarNuevoProgreso(fecha, peso,nivel, objetivo, observacion, clienteId)
            } else {
                actualizarProgreso(id!!, fecha, peso,nivel, objetivo, observacion, clienteId)
            }
        }
    }

    private fun cargarDatosProgreso(id: Int) {
        val progreso = progresoModelBD.getProgresoById(id)
        progreso?.let {
            etFecha.setText(it.fecha)
            etPeso.setText(it.peso.toString())
            etNivel.setText(it.nivel)
            etObjetivo.setText(it.objetivo)
            etObservacion.setText(it.observacion)
        }
    }

    private fun guardarNuevoProgreso(fecha: String, peso: Double, nivel: String, objetivo: String, observacion: String, clienteId: Int) {
        val progreso = ProgresoModel(0, fecha, peso, nivel, objetivo, observacion, clienteId)
        progresoModelBD.addProgreso(progreso)
        Toast.makeText(this, "Progreso registrado", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun actualizarProgreso(id: Int, fecha: String, peso: Double, nivel: String, objetivo: String, observacion: String, clienteId: Int) {
        val progreso = ProgresoModel(id, fecha, peso, nivel, objetivo, observacion, clienteId)
        progresoModelBD.updateProgreso(progreso)
        Toast.makeText(this, "Progreso actualizado", Toast.LENGTH_SHORT).show()
        finish()
    }
}
