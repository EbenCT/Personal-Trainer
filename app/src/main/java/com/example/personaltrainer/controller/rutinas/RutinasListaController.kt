package com.example.personaltrainer.controller.rutinas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TableLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltrainer.R
import com.example.personaltrainer.model.rutina.RutinaModelBD
import com.example.personaltrainer.TableDynamic

@Suppress("DEPRECATION")
class RutinasListaController : AppCompatActivity(), View.OnClickListener {

    private lateinit var context: Context
    private lateinit var tableLayout: TableLayout
    private lateinit var etId: EditText
    private lateinit var rutinaModelBD: RutinaModelBD
    private val header = arrayOf("ID", "Fecha", "Comentario")
    private val rows = ArrayList<Array<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rutinas_lista)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init()
    }

    private fun init() {
        context = this
        rutinaModelBD = RutinaModelBD(context)
        tableLayout = findViewById(R.id.table)
        etId = findViewById(R.id.et_id)

        val tableDynamic = TableDynamic(tableLayout, context)
        tableDynamic.addHeader(header)
        obtenerRutinas()

        if (rows.isNotEmpty()) {
            tableDynamic.addData(rows)
        } else {
            Toast.makeText(this, "No hay rutinas para mostrar", Toast.LENGTH_SHORT).show()
        }

        tableDynamic.backgroundHeader(resources.getColor(R.color.black))
        tableDynamic.textColorHeader(resources.getColor(R.color.white))
        findViewById<View>(R.id.btn_registrar_rutina).setOnClickListener(this)
        findViewById<View>(R.id.btn_modificar_rutina).setOnClickListener(this)
        findViewById<View>(R.id.btn_eliminar_rutina).setOnClickListener(this)
    }

    private fun obtenerRutinas() {
        rows.clear() // Limpiar la lista antes de llenarla
        val rutinas = rutinaModelBD.getAllRutinas()
        for (rutina in rutinas) {
            rows.add(arrayOf(rutina.id.toString(), rutina.fecha, rutina.comentario))
        }
    }

    private fun updateTable() {
        tableLayout.removeAllViews() // Limpiar la tabla
        val tableDynamic = TableDynamic(tableLayout, this)
        tableDynamic.addHeader(header)
        obtenerRutinas()

        if (rows.isNotEmpty()) {
            tableDynamic.addData(rows)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_registrar_rutina -> {
                val intent = Intent(this, RutinasFormController::class.java)
                startActivity(intent)
            }

            R.id.btn_modificar_rutina -> {
                val idText = etId.text.toString()
                if (idText.isNotEmpty()) {
                    val id = idText.toInt()
                    val rutina = rutinaModelBD.getRutinaById(id)
                    if (rutina != null) {
                        val intent = Intent(this, RutinasFormController::class.java)
                        intent.putExtra("id", rutina.id)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Rutina no encontrada", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.btn_eliminar_rutina -> {
                val idText = etId.text.toString()
                if (idText.isNotEmpty()) {
                    rutinaModelBD.deleteRutina(idText.toInt())
                    Toast.makeText(this, "Rutina eliminada", Toast.LENGTH_SHORT).show()
                    updateTable() // Actualizar la tabla sin recrear la actividad
                } else {
                    Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
