package com.example.personaltrainer.model.cliente.proxy

import com.example.personaltrainer.model.cliente.ClienteModel

interface ICliente {
    fun addCliente(clienteModel: ClienteModel): Long
    fun getClienteById(id: Int): ClienteModel?
    fun updateCliente(clienteModel: ClienteModel): Int
    fun deleteCliente(id: Int): Int
    fun getAllClientes(): List<ClienteModel>
}