package com.example.personaltrainer.controller.clientes

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
import com.example.personaltrainer.controller.progresos.ProgresosListaController
import com.example.personaltrainer.model.cliente.ClienteModelBD

class ClientesListaController : AppCompatActivity(), View.OnClickListener {

    private lateinit var context: Context
    private lateinit var tableLayout: TableLayout
    private lateinit var etId: EditText
    private lateinit var clienteModelBD: ClienteModelBD
    private val header = arrayOf("ID", "Nombre", "Apellido", "Edad", "Sexo", "Altura", "Teléfono", "Email")
    private val rows: ArrayList<Array<String>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.clientes_lista)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init()
    }

    private fun init() {
        context = applicationContext
        clienteModelBD = ClienteModelBD(context)
        tableLayout = findViewById(R.id.table)
        etId = findViewById(R.id.et_id)

        val tableDynamic = TableDynamic(tableLayout, context)
        tableDynamic.addHeader(header)
        val rows = obtenerClientes()

        if (rows.isNotEmpty()) {
            tableDynamic.addData(rows)
        } else {
            Toast.makeText(this, "No hay clientes para mostrar", Toast.LENGTH_SHORT).show()
        }
/*

        tableDynamic.backgroundData(resources.getColor(R.color.black), resources.getColor(R.color.white))
        tableDynamic.textColorData(resources.getColor(R.color.black))
        */
        tableDynamic.backgroundHeader(resources.getColor(R.color.black))
        tableDynamic.textColorHeader(resources.getColor(R.color.white))
        findViewById<View>(R.id.btn_registrar_cliente).setOnClickListener(this)
        findViewById<View>(R.id.btn_modificar_cliente).setOnClickListener(this)
        findViewById<View>(R.id.btn_eliminar_cliente).setOnClickListener(this)
        findViewById<View>(R.id.btn_progreso).setOnClickListener(this)
    }

    private fun obtenerClientes(): ArrayList<Array<String>> {
        val clientes = clienteModelBD.getAllClientes()
        for (cliente in clientes) {
            rows.add(
                arrayOf(
                    cliente.id.toString(),
                    cliente.nombre,
                    cliente.apellido,
                    cliente.edad.toString(),
                    cliente.sexo,
                    cliente.altura.toString(),
                    cliente.telefono,
                    cliente.email
                )
            )
        }
        Log.d("Clientes", "Clientes obtenidos: ${rows.size}")
        return rows
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_registrar_cliente -> {
                val intent = Intent(context, ClientesFormController::class.java)
                startActivity(intent)
            }

            R.id.btn_modificar_cliente -> {
                val idText = etId.text.toString()
                if (idText.isNotEmpty()) {
                    val id = idText.toInt()
                    val cliente = clienteModelBD.getClienteById(id)
                    if (cliente != null) {
                        val intent = Intent(context, ClientesFormController::class.java)
                        intent.putExtra("id", cliente.id)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Cliente no encontrado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.btn_eliminar_cliente -> {
                val idText = etId.text.toString()
                if (idText.isNotEmpty()) {
                    clienteModelBD.deleteCliente(idText.toInt())
                    Toast.makeText(this, "Cliente eliminado", Toast.LENGTH_SHORT).show()
                    recreate() // Refrescar la vista después de eliminar
                } else {
                    Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.btn_progreso -> {
                val idText = etId.text.toString()
                if (idText.isNotEmpty()) {
                    val id = idText.toInt()
                    val cliente = clienteModelBD.getClienteById(id)
                    if (cliente != null) {
                        val intent = Intent(context, ProgresosListaController::class.java)
                        intent.putExtra("id", cliente.id)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Cliente no encontrado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
