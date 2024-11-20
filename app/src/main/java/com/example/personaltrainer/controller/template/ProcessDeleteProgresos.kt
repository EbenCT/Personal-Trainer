package com.example.personaltrainer.controller.template

import com.example.personaltrainer.model.progreso.ProgresoModelBD

class ProcessDeleteProgresos(
    private val progresoModelBD: ProgresoModelBD
) : ProcessDelete() {

    override fun eliminar(id: Int) {
        progresoModelBD.deleteProgreso(id)
    }

    override fun nombreElemento(): String = "Progreso"
}