package com.example.produtracker2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registro.*

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val main = Intent(this, MainActivity::class.java)
        val categoriasIntent = Intent(this, CategoriasActivity::class.java)

        // Botones
        btnTablaRegistro.setOnClickListener{ cargarVista(main) }
        btnCategoriasRegistro.setOnClickListener{ cargarVista(categoriasIntent) }

        cargarFila()
    }

    fun cargarFila() {
        val fila = layoutInflater.inflate(R.layout.registrosemanal, null)
        registros.addView(fila)
    }

    fun cargarVista(vista: Intent) {
        startActivity(vista)
        this.finish()
    }
}