package com.example.produtracker2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registro.*

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val categoriasIntent = Intent(this, RegistroActivity::class.java)

        // Botones
        btnTablaRegistro.setOnClickListener{ this.finish() }
        btnCategoriasRegistro.setOnClickListener{
            startActivity(categoriasIntent)
            finish()
        }

        cargarFila()
    }

    fun cargarFila() {
        val fila = layoutInflater.inflate(R.layout.registrosemanal, null)
        registros.addView(fila)
    }
}