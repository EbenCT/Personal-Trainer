package com.example.personaltrainer.controller.clientes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.personaltrainer.R
import com.example.personaltrainer.model.cliente.ClienteModel
import com.example.personaltrainer.model.cliente.ClienteModelBD

class ClientesFormController : AppCompatActivity() {
    private lateinit var clienteModelBD: ClienteModelBD
    private var clienteId: Int = 0 // Variable para almacenar el ID del cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.clientes)

        clienteModelBD = ClienteModelBD(this)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etApellido = findViewById<EditText>(R.id.etApellido)
        val etEdad = findViewById<EditText>(R.id.etEdad)
        val etSexo = findViewById<EditText>(R.id.etSexo)
        val etAltura = findViewById<EditText>(R.id.etAltura)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        // Revisar si el intent contiene un ID
        clienteId = intent.getIntExtra("id", 0)
        if (clienteId != 0) {
            // Si el clienteId es distinto de 0, cargar los datos del cliente
            val cliente = clienteModelBD.getClienteById(clienteId)
            if (cliente != null) {
                etNombre.setText(cliente.nombre)
                etApellido.setText(cliente.apellido)
                etEdad.setText(cliente.edad.toString())
                etSexo.setText(cliente.sexo)
                etAltura.setText(cliente.altura.toString())
                etTelefono.setText(cliente.telefono)
                etEmail.setText(cliente.email)
            }
        }

        // Listener para guardar o modificar el cliente
        btnGuardar.setOnClickListener {
            val clienteModel = ClienteModel(
                id = clienteId, // Si clienteId es 0, se crea un nuevo cliente, si no, se actualiza el existente
                nombre = etNombre.text.toString(),
                apellido = etApellido.text.toString(),
                edad = etEdad.text.toString().toInt(),
                sexo = etSexo.text.toString(),
                altura = etAltura.text.toString().toInt(),
                telefono = etTelefono.text.toString(),
                email = etEmail.text.toString()
            )

            if (clienteId == 0) {
                // Agregar nuevo cliente
                val success = clienteModelBD.addCliente(clienteModel)
                if (success > -1) {
                    Toast.makeText(this, "Cliente agregado con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al agregar cliente", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Actualizar cliente existente
                val success = clienteModelBD.updateCliente(clienteModel)
                if (success > 0) {
                    Toast.makeText(this, "Cliente actualizado con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al actualizar cliente", Toast.LENGTH_SHORT).show()
                }
            }

            // Redirigir de nuevo a la lista de clientes
            val intent = Intent(this, ClientesListaController::class.java)
            startActivity(intent)
        }
    }
}
