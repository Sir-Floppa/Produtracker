package com.example.produtracker2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_categorias.*
import kotlinx.android.synthetic.main.categoria.view.*

class CategoriasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorias)

        val registroIntent = Intent(this, RegistroActivity::class.java)
        val nuevaCategoriaIntent = Intent(this, NuevaCategoriaActivity::class.java)

        // Botones
        btnTablaCategorias.setOnClickListener{ this.finish() }
        btnRegistroCategorias.setOnClickListener{
            startActivity(registroIntent)
            this.finish()
        }
        btnNuevaCategoria.setOnClickListener{
            startActivity(nuevaCategoriaIntent)
        }

        cargarCategoria("Prueba", 3)
        cargarCategoria("Prueba 2", 1)
        cargarCategoria("Prueba 3", 2)
        cargarCategoria("Prueba 4", 0)
        cargarCategoria("Prueba", 3)
        cargarCategoria("Prueba 2", 1)
        cargarCategoria("Prueba 3", 2)
        cargarCategoria("Prueba 4", 0)

    }

    fun cargarCategoria(nombre: String, prioridad: Int){
        val fila = layoutInflater.inflate(R.layout.categoria, null)
        fila.nombreCategoria.setText(nombre)
        when(prioridad) {
            0 -> fila.prioridad.setText("Nula")
            1 -> fila.prioridad.setText("Baja")
            2 -> fila.prioridad.setText("Media")
            3 -> fila.prioridad.setText("Alta")
        }

        categorias.addView(fila)
    }
}