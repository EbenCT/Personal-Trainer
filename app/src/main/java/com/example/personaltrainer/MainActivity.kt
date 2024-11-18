package com.example.personaltrainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.example.personaltrainer.controller.clientes.ClientesListaController
import com.example.personaltrainer.controller.ejercicios.EjerciciosListaController
import com.example.personaltrainer.controller.entrenamiento.EntrenamientoListaController
import com.example.personaltrainer.controller.rutinas.RutinasListaController



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Tarjetas
        val cardNuevoCliente = findViewById<CardView>(R.id.cardNuevoCliente)
        val cardCrearRutina = findViewById<CardView>(R.id.cardCrearRutina)
        val cardCrearEntrenamiento = findViewById<CardView>(R.id.cardCrearEntrenamiento)
        val cardNuevoEjercicio = findViewById<CardView>(R.id.cardNuevoEjercicio)


        // Listener para Clientes
        cardNuevoCliente.setOnClickListener {
            val intent = Intent(this, ClientesListaController::class.java)
            startActivity(intent)
        }

        // Listener para Rutinas
        cardCrearRutina.setOnClickListener {
            val intent = Intent(this, RutinasListaController::class.java)
            startActivity(intent)
        }

        // Listener para Entrenamientos
        cardCrearEntrenamiento.setOnClickListener {
            val intent = Intent(this, EntrenamientoListaController::class.java)
            startActivity(intent)
        }

        // Listener para Ejercicios
        cardNuevoEjercicio.setOnClickListener {
            val intent = Intent(this, EjerciciosListaController::class.java)
            startActivity(intent)
        }
    }
}