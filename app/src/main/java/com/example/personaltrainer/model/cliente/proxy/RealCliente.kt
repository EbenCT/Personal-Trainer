package com.example.personaltrainer.model.cliente.proxy

import android.content.Context
import com.example.personaltrainer.model.cliente.ClienteModel
import com.example.personaltrainer.model.cliente.ClienteModelBD

class RealCliente(context: Context) : ICliente {
    private val clienteModelBD = ClienteModelBD(context)

    override fun addCliente(cliente: ClienteModel): Long {
        return clienteModelBD.addCliente(cliente)
    }

    override fun getClienteById(id: Int): ClienteModel? {
        return clienteModelBD.getClienteById(id)
    }

    override fun updateCliente(cliente: ClienteModel): Int {
        return clienteModelBD.updateCliente(cliente)
    }

    override fun deleteCliente(id: Int): Int {
        return clienteModelBD.deleteCliente(id)
    }

    override fun getAllClientes(): List<ClienteModel> {
        return clienteModelBD.getAllClientes()
    }
}