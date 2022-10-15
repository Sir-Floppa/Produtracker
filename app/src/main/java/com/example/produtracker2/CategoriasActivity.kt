package com.example.produtracker2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_actualizar_categoria.view.*
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
        val sqlManager = SQLManager(this)

        fila.nombreCategoria.setText(dato.nombre)
        when(dato.prioridad) {
            0 -> fila.prioridad.setText("Nula")
            1 -> fila.prioridad.setText("Baja")
            2 -> fila.prioridad.setText("Media")
            3 -> fila.prioridad.setText("Alta")
        }

        fila.btnEliminar.setOnClickListener {
            dato.id?.let { it1 -> sqlManager.eliminarCategoria(this, it1) }
            startActivity(intent)
            finish()
        }

        fila.btnEditor.setOnClickListener {
            var actualizarCategoriaIntent = Intent(this, ActualizarCategoriaActivity::class.java).also{
                it.putExtra("id", dato.id)
                it.putExtra("nombre", dato.nombre)
                it.putExtra("prioridad", dato.prioridad)
            }
            cargarVista(actualizarCategoriaIntent)
        }

        categorias.addView(fila)
    }

    fun cargarVista(vista: Intent) {
        startActivity(vista)
        this.finish()
    }
}