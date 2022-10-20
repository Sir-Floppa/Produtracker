package com.example.produtracker2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registro.*
import kotlinx.android.synthetic.main.registrosemanal.view.*

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val main = Intent(this, MainActivity::class.java)
        val categoriasIntent = Intent(this, CategoriasActivity::class.java)

        // Botones
        btnTablaRegistro.setOnClickListener{ cargarVista(main) }
        btnCategoriasRegistro.setOnClickListener{ cargarVista(categoriasIntent) }

        var sqlManager = SQLManager(this)

        var registros = sqlManager.leerRegistros(this)

        for(registro in registros) {
            cargarFila(registro)
        }
    }

    fun cargarFila(reg: RegistroClass) {
        val fila = layoutInflater.inflate(R.layout.registrosemanal, null)
        fila.txtFecha.text = "${reg.fInicio} - ${reg.fFinal}"
        fila.txtValor.text = reg.valor.toString()
        registros.addView(fila)
    }

    fun cargarVista(vista: Intent) {
        startActivity(vista)
        this.finish()
    }
}