package com.example.personaltrainer.controller.entrenamiento

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TableLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltrainer.R
import com.example.personaltrainer.model.entrenamiento.EntrenamientoModelBD
import com.example.personaltrainer.TableDynamic

class EntrenamientoListaController : AppCompatActivity(), View.OnClickListener {

    private lateinit var context: Context
    private lateinit var tableLayout: TableLayout
    private lateinit var etId: EditText
    private lateinit var entrenamientoModelBD: EntrenamientoModelBD
    private val header = arrayOf("ID", "Cliente ID", "Fecha Inicio", "Fecha Fin")
    private val rows: ArrayList<Array<String>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entrenamiento_lista)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init()
    }

    private fun init() {
        context = applicationContext
        entrenamientoModelBD = EntrenamientoModelBD(context)
        tableLayout = findViewById(R.id.table)
        etId = findViewById(R.id.et_id)

        val tableDynamic = TableDynamic(tableLayout, context)
        tableDynamic.addHeader(header)
        val rows = obtenerEntrenamientos()

        if (rows.isNotEmpty()) {
            tableDynamic.addData(rows)
        } else {
            Toast.makeText(this, "No hay entrenamientos para mostrar", Toast.LENGTH_SHORT).show()
        }

        tableDynamic.backgroundHeader(resources.getColor(R.color.black))
        tableDynamic.textColorHeader(resources.getColor(R.color.white))
        findViewById<View>(R.id.btn_registrar_entrenamiento).setOnClickListener(this)
        findViewById<View>(R.id.btn_modificar_entrenamiento).setOnClickListener(this)
        findViewById<View>(R.id.btn_eliminar_entrenamiento).setOnClickListener(this)
    }

    private fun obtenerEntrenamientos(): ArrayList<Array<String>> {
        val entrenamientos = entrenamientoModelBD.getAllEntrenamientos()
        for (entrenamiento in entrenamientos) {
            rows.add(
                arrayOf(
                    entrenamiento.id.toString(),
                    entrenamiento.clienteId.toString(),
                    entrenamiento.fechaInicio,
                    entrenamiento.fechaFin
                )
            )
        }
        Log.d("Entrenamientos", "Entrenamientos obtenidos: ${rows.size}")
        return rows
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_registrar_entrenamiento -> {
                val intent = Intent(context, EntrenamientoFormController::class.java)
                startActivity(intent)
            }

            R.id.btn_modificar_entrenamiento -> {
                val idText = etId.text.toString()
                if (idText.isNotEmpty()) {
                    val id = idText.toInt()
                    val entrenamiento = entrenamientoModelBD.getEntrenamientoById(id)
                    if (entrenamiento != null) {
                        val intent = Intent(context, EntrenamientoFormController::class.java)
                        intent.putExtra("id", entrenamiento.id)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Entrenamiento no encontrado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.btn_eliminar_entrenamiento -> {
                val idText = etId.text.toString()
                if (idText.isNotEmpty()) {
                    entrenamientoModelBD.deleteEntrenamiento(idText.toInt())
                    Toast.makeText(this, "Entrenamiento eliminado", Toast.LENGTH_SHORT).show()
                    recreate() // Refrescar la vista después de eliminar
                } else {
                    Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
