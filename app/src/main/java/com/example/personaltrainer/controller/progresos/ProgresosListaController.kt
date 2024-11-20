package com.example.personaltrainer.controller.progresos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TableLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltrainer.R
import com.example.personaltrainer.model.progreso.ProgresoModelBD
import com.example.personaltrainer.database.DBHelper
import com.example.personaltrainer.TableDynamic
import com.example.personaltrainer.controller.template.ProcessDelete
import com.example.personaltrainer.controller.template.ProcessDeleteEjercicios
import com.example.personaltrainer.controller.template.ProcessDeleteProgresos

class ProgresosListaController : AppCompatActivity(), View.OnClickListener {

    private lateinit var context: Context
    private lateinit var tableLayout: TableLayout
    private lateinit var etId: EditText
    private lateinit var progresoModelBD: ProgresoModelBD // Cambiar el nombre a progresoModelBD
    private val header = arrayOf("ID", "Fecha", "Peso", "Nivel", "Objetivo", "Observación")
    private val rows: ArrayList<Array<String>> = ArrayList()
    private var clienteId: Int = -1  // Para almacenar el ID del cliente
    private lateinit var processDeleteProgresos: ProcessDelete

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.progresos_lista)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Obtener el ID del cliente desde el Intent
        clienteId = intent.getIntExtra("id", -1)
        if (clienteId == -1) {
            Toast.makeText(this, "Cliente no válido", Toast.LENGTH_SHORT).show()
            finish()  // Cerrar la actividad si no se pasa un cliente válido
            return
        }

        init()
    }

    private fun init() {
        context = applicationContext

        progresoModelBD = ProgresoModelBD(context) // Inicializar progresoModelBD con DBHelper
        tableLayout = findViewById(R.id.table)
        etId = findViewById(R.id.et_id)
        processDeleteProgresos = ProcessDeleteProgresos(progresoModelBD)

        val tableDynamic = TableDynamic(tableLayout, context)
        tableDynamic.addHeader(header)
        val rows = obtenerProgresos()

        if (rows.isNotEmpty()) {
            tableDynamic.addData(rows)
        } else {
            Toast.makeText(this, "No hay progresos para mostrar", Toast.LENGTH_SHORT).show()
        }

        tableDynamic.backgroundHeader(resources.getColor(R.color.black))
        tableDynamic.textColorHeader(resources.getColor(R.color.white))
        findViewById<View>(R.id.btn_registrar_progreso).setOnClickListener(this)
        findViewById<View>(R.id.btn_modificar_progreso).setOnClickListener(this)
        findViewById<View>(R.id.btn_eliminar_progreso).setOnClickListener(this)
    }

    private fun obtenerProgresos(): ArrayList<Array<String>> {
        // Filtrar los progresos por el ID del cliente
        val progresos = progresoModelBD.getProgresosByClienteId(clienteId)
        for (progreso in progresos) {
            rows.add(
                arrayOf(
                    progreso.id.toString(),
                    progreso.fecha,
                    progreso.peso.toString(),
                    progreso.nivel,
                    progreso.objetivo,
                    progreso.observacion
                )
            )
        }
        return rows
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_registrar_progreso -> {
                val intent = Intent(context, ProgresosFormController::class.java)
                intent.putExtra("cliente_id", clienteId) // Pasamos cliente_id
                startActivity(intent)
            }

            R.id.btn_modificar_progreso -> {
                val idText = etId.text.toString()
                if (idText.isNotEmpty()) {
                    val id = idText.toInt()
                    val progreso = progresoModelBD.getProgresoById(id) // Cambiar a progresoModelBD
                    if (progreso != null) {
                        val intent = Intent(context, ProgresosFormController::class.java)
                        intent.putExtra("id", progreso.id)
                        intent.putExtra("cliente_id", clienteId)  // Pasamos cliente_id
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Progreso no encontrado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.btn_eliminar_progreso -> processDeleteProgresos.generateProcessDelete(this, etId.text.toString())
        }
    }
}
