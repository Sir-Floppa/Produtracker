package com.example.produtracker2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tablerow.view.*
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val categoriasIntent = Intent(this, CategoriasActivity::class.java)
        val registroIntent = Intent(this, RegistroActivity::class.java)

        // Botones
        btnCategoriasTabla.setOnClickListener{ cargarVista(categoriasIntent) }
        btnRegistroTabla.setOnClickListener{ cargarVista(registroIntent) }

        cargarFila("Prueba 1", 1, 2, 3, 4, 5, 6, 7)
        cargarFila("Prueba 2", 3, 4, 5, 7, 4, 5, 4)
        cargarFila("Prueba 1", 1, 2, 3, 4, 5, 6, 7)
        cargarFila("Prueba 2", 3, 4, 5, 7, 4, 5, 4)
        cargarFila("Prueba 1", 1, 2, 3, 4, 5, 6, 7)
        cargarFila("Prueba 2", 3, 4, 5, 7, 4, 5, 4)
        cargarFila("Prueba 1", 1, 2, 3, 4, 5, 6, 7)
        cargarFila("Prueba 2", 3, 4, 5, 7, 4, 5, 4)
        cargarFila("Prueba 1", 1, 2, 3, 4, 5, 6, 7)
        cargarFila("Prueba 2", 3, 4, 5, 7, 4, 5, 4)
        cargarFila("Prueba 1", 1, 2, 3, 4, 5, 6, 7)
        cargarFila("Prueba 2", 3, 4, 5, 7, 4, 5, 4)
        cargarFila("Prueba 1", 1, 2, 3, 4, 5, 6, 7)
        cargarFila("Prueba 2", 3, 4, 5, 7, 4, 5, 4)
    }

    fun cargarFila(
        categoria: String,
        LVal: Int, MVal: Int, XVal: Int,
        JVal: Int, VVal: Int, SVal: Int, DVal: Int) {
        val fila = layoutInflater.inflate(R.layout.tablerow, null)

        fila.btnCategoria.setText(categoria)
        fila.LValue.setText(LVal.toString())
        fila.MValue.setText(MVal.toString())
        fila.XValue.setText(XVal.toString())
        fila.JValue.setText(JVal.toString())
        fila.VValue.setText(VVal.toString())
        fila.SValue.setText(SVal.toString())
        fila.DValue.setText(DVal.toString())

        tabla.addView(fila)
    }

    fun cargarVista(vista: Intent) {
        startActivity(vista)
        this.finish()
    }
}