package com.example.personaltrainer.model.cliente.proxy

import android.content.Context
import com.example.personaltrainer.model.cliente.ClienteModel

class ProxyCliente(context: Context) : ICliente {
    private val realCliente = RealCliente(context)

    // Validar cliente completo
    private fun validarCliente(cliente: ClienteModel): Boolean {
        return validarNombre(cliente.nombre) &&
                validarApellido(cliente.apellido) &&
                validarEdad(cliente.edad) &&
                validarSexo(cliente.sexo) &&
                validarAltura(cliente.altura) &&
                validarTelefono(cliente.telefono) &&
                validarEmail(cliente.email)
    }

    // Validaciones individuales
    private fun validarNombre(nombre: String): Boolean {
        val regex = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{1,50}$".toRegex()
        return nombre.isNotBlank() && regex.matches(nombre)
    }

    private fun validarApellido(apellido: String): Boolean {
        val regex = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{1,50}$".toRegex()
        return apellido.isNotBlank() && regex.matches(apellido)
    }

    private fun validarEdad(edad: Int): Boolean {
        return edad in 10..120
    }

    private fun validarSexo(sexo: String): Boolean {
        val opcionesValidas = listOf("Masculino", "Femenino", "Otro")
        return sexo in opcionesValidas
    }

    private fun validarAltura(altura: Int): Boolean {
        return altura in 50..250
    }

    private fun validarTelefono(telefono: String): Boolean {
        val regex = "^\\d{10}$".toRegex()
        return regex.matches(telefono)
    }

    private fun validarEmail(email: String): Boolean {
        val regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex()
        return regex.matches(email)
    }

    // Métodos Proxy con validaciones específicas

    override fun addCliente(cliente: ClienteModel): Long {
        if (cliente.nombre.isBlank() || cliente.apellido.isBlank()) {
            throw IllegalArgumentException("El nombre y el apellido no pueden estar vacíos.")
        }
        if (cliente.edad <= 0) {
            throw IllegalArgumentException("La edad debe ser mayor a 0.")
        }
        if (!validarCliente(cliente)) {
            throw IllegalArgumentException("Datos del cliente no válidos.")
        }
        return realCliente.addCliente(cliente)
    }

    override fun getClienteById(id: Int): ClienteModel? {
        if (id <= 0) {
            throw IllegalArgumentException("El ID debe ser mayor a 0.")
        }
        return realCliente.getClienteById(id)
    }

    override fun updateCliente(cliente: ClienteModel): Int {
        if (cliente.id <= 0) {
            throw IllegalArgumentException("El ID no es válido.")
        }
        if (!validarCliente(cliente)) {
            throw IllegalArgumentException("Datos del cliente no válidos.")
        }
        return realCliente.updateCliente(cliente)
    }

    override fun deleteCliente(id: Int): Int {
        if (id <= 0) {
            throw IllegalArgumentException("El ID no es válido.")
        }
        return realCliente.deleteCliente(id)
    }

    override fun getAllClientes(): List<ClienteModel> {
        return realCliente.getAllClientes()
    }
}