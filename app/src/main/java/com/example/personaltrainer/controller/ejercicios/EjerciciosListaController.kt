package com.example.personaltrainer.controller.ejercicios

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
import com.example.personaltrainer.TableDynamic
import com.example.personaltrainer.controller.template.ProcessDelete
import com.example.personaltrainer.controller.template.ProcessDeleteEjercicios
import com.example.personaltrainer.model.ejercicio.EjercicioModelBD

class EjerciciosListaController : AppCompatActivity(), View.OnClickListener {

    private lateinit var context: Context
    private lateinit var tableLayout: TableLayout
    private lateinit var etId: EditText
    private lateinit var ejercicioModelBD: EjercicioModelBD
    private val header = arrayOf("ID", "Nombre", "Instrucciones", "Link Video")
    private val rows: ArrayList<Array<String>> = ArrayList()
    private lateinit var processDeleteEjercicios: ProcessDelete

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ejercicios_lista)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init()
    }

    private fun init() {
        context = applicationContext
        ejercicioModelBD = EjercicioModelBD(context)
        tableLayout = findViewById(R.id.table)
        etId = findViewById(R.id.et_id)
        processDeleteEjercicios = ProcessDeleteEjercicios(ejercicioModelBD)

        val tableDynamic = TableDynamic(tableLayout, context)
        tableDynamic.addHeader(header)
        val rows = obtenerEjercicios()

        if (rows.isNotEmpty()) {
            tableDynamic.addData(rows)
        } else {
            Toast.makeText(this, "No hay ejercicios para mostrar", Toast.LENGTH_SHORT).show()
        }

        tableDynamic.backgroundHeader(resources.getColor(R.color.black))
        tableDynamic.textColorHeader(resources.getColor(R.color.white))


        findViewById<View>(R.id.btn_registrar_ejercicio).setOnClickListener(this)
        findViewById<View>(R.id.btn_modificar_ejercicio).setOnClickListener(this)
        findViewById<View>(R.id.btn_eliminar_ejercicio).setOnClickListener(this)
    }

    private fun obtenerEjercicios(): ArrayList<Array<String>> {
        val ejercicios = ejercicioModelBD.getAllEjercicios()
        for (ejercicio in ejercicios) {
            rows.add(
                arrayOf(
                    ejercicio.id.toString(),
                    ejercicio.nombre,
                    ejercicio.instrucciones,
                    ejercicio.linkVideo
                )
            )
        }
        Log.d("Ejercicios", "Ejercicios obtenidos: ${rows.size}")
        return rows
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_registrar_ejercicio -> {
                val intent = Intent(context, EjerciciosFormController::class.java)
                startActivity(intent)
            }

            R.id.btn_modificar_ejercicio -> {
                val idText = etId.text.toString()
                if (idText.isNotEmpty()) {
                    val id = idText.toInt()
                    val ejercicio = ejercicioModelBD.getEjercicioById(id)
                    if (ejercicio != null) {
                        val intent = Intent(context, EjerciciosFormController::class.java)
                        intent.putExtra("id", ejercicio.id)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Ejercicio no encontrado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.btn_eliminar_ejercicio -> {
                val idText = etId.text.toString()
                if (idText.isNotEmpty()) {
                    ejercicioModelBD.deleteEjercicio(idText.toInt())
                    Toast.makeText(this, "Ejercicio eliminado", Toast.LENGTH_SHORT).show()
                    recreate() // Refrescar la vista después de eliminar
                } else {
                    Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.btn_eliminar_ejercicio -> processDeleteEjercicios.generateProcessDelete(this, etId.text.toString())
        }
    }
}
