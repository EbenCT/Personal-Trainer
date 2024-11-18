package com.example.personaltrainer.controller.rutinas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltrainer.R
import com.example.personaltrainer.model.rutina.RutinaModel
import com.example.personaltrainer.model.rutina.RutinaModelBD

class RutinasFormController : AppCompatActivity() {

    private lateinit var rutinaModelBD: RutinaModelBD
    private var rutinaId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rutinas_form)

        rutinaModelBD = RutinaModelBD(this)

        val etFecha = findViewById<EditText>(R.id.etFecha)
        val etComentario = findViewById<EditText>(R.id.etComentario)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        rutinaId = intent.getIntExtra("id", 0)
        if (rutinaId != 0) {
            val rutina = rutinaModelBD.getRutinaById(rutinaId)
            if (rutina != null) {
                etFecha.setText(rutina.fecha)
                etComentario.setText(rutina.comentario)
            }
        }

        btnGuardar.setOnClickListener {
            val fecha = etFecha.text.toString()
            val comentario = etComentario.text.toString()

            if (fecha.isNotEmpty() && comentario.isNotEmpty()) {
                val rutina = RutinaModel(fecha, comentario)
                if (rutinaId == 0) {
                    rutinaModelBD.insertRutina(rutina)
                    Toast.makeText(this, "Rutina registrada", Toast.LENGTH_SHORT).show()
                } else {
                    rutina.id = rutinaId
                    rutinaModelBD.updateRutina(rutina)
                    Toast.makeText(this, "Rutina modificada", Toast.LENGTH_SHORT).show()
                }
                startActivity(Intent(this, RutinasListaController::class.java))
            } else {
                Toast.makeText(this, "Por favor complete los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
