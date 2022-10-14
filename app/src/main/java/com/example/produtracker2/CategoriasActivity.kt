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

        val main = Intent(this, MainActivity::class.java)
        val registroIntent = Intent(this, RegistroActivity::class.java)
        val nuevaCategoriaIntent = Intent(this, NuevaCategoriaActivity::class.java)

        // Botones
        btnTablaCategorias.setOnClickListener{ cargarVista(main) }
        btnRegistroCategorias.setOnClickListener{ cargarVista(registroIntent) }
        btnNuevaCategoria.setOnClickListener{ cargarVista(nuevaCategoriaIntent) }

        var sqlManager = SQLManager(this)
        var categorias = sqlManager.leerCategorias(this)

        for(categoria in categorias) {
            cargarCategoria(categoria)
        }
    }

    fun cargarCategoria(dato: CategoriaClass){
        val fila = layoutInflater.inflate(R.layout.categoria, null)
        fila.nombreCategoria.setText(dato.nombre)
        when(dato.prioridad) {
            0 -> fila.prioridad.setText("Nula")
            1 -> fila.prioridad.setText("Baja")
            2 -> fila.prioridad.setText("Media")
            3 -> fila.prioridad.setText("Alta")
        }

        categorias.addView(fila)
    }

    fun cargarVista(vista: Intent) {
        startActivity(vista)
        this.finish()
    }
}