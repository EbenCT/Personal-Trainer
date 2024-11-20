package com.example.personaltrainer.controller.template

import com.example.personaltrainer.model.ejercicio.EjercicioModelBD

class ProcessDeleteEjercicios(
    private val ejercicioModelBD: EjercicioModelBD
) : ProcessDelete() {

    override fun eliminar(id: Int) {
        ejercicioModelBD.deleteEjercicio(id)
    }

    override fun nombreElemento(): String = "Ejercicio"
}