package com.example.produtracker2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tablerow.view.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val categoriasIntent = Intent(this, CategoriasActivity::class.java)
        val registroIntent = Intent(this, RegistroActivity::class.java)

        val sqlManager = SQLManager(this)
        val categorias = sqlManager.leerCategorias(this)

        // Botones
        btnCategoriasTabla.setOnClickListener{ cargarVista(categoriasIntent) }
        btnRegistroTabla.setOnClickListener{ cargarVista(registroIntent) }

        for(categoria in categorias) {
            try{
                cargarFila(categoria, sqlManager.seleccionarFila(this, categoria.id))
            }
            catch(e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun cargarFila(
        categoria: CategoriaClass, fila: FilaClass) {
        val nuevaFila = layoutInflater.inflate(R.layout.tablerow, null)

        val LValue = fila.LValue
        val MValue = fila.MValue
        val XValue = fila.XValue
        val JValue = fila.JValue
        val VValue = fila.VValue
        val SValue = fila.SValue
        val DValue = fila.DValue

        nuevaFila.btnCategoria.setText(categoria.nombre)
        nuevaFila.LValue.setText(LValue.toString())
        nuevaFila.MValue.setText(MValue.toString())
        nuevaFila.XValue.setText(XValue.toString())
        nuevaFila.JValue.setText(JValue.toString())
        nuevaFila.VValue.setText(VValue.toString())
        nuevaFila.SValue.setText(SValue.toString())
        nuevaFila.DValue.setText(DValue.toString())

        nuevaFila.btnCategoria.setOnClickListener{
            val tablaIntent = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

            val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_WEEK)

            val sqlManager = SQLManager(this)

            var dayVal = ""

            var nuevoValor = 0
            var colActual = nuevaFila.LValue

            when(day) {
                Calendar.MONDAY -> {dayVal = "L"; nuevoValor = LValue; colActual = nuevaFila.LValue}
                Calendar.TUESDAY -> {dayVal = "M"; nuevoValor = MValue; colActual = nuevaFila.MValue}
                Calendar.WEDNESDAY -> {dayVal = "X"; nuevoValor = XValue; colActual = nuevaFila.XValue}
                Calendar.THURSDAY -> {dayVal = "J"; nuevoValor = JValue; colActual = nuevaFila.JValue}
                Calendar.FRIDAY -> {dayVal = "V"; nuevoValor = VValue; colActual = nuevaFila.VValue}
                Calendar.SATURDAY -> {dayVal = "S"; nuevoValor = SValue; colActual = nuevaFila.SValue}
                Calendar.SUNDAY -> {dayVal = "D"; nuevoValor = DValue; colActual = nuevaFila.DValue}
            }

            nuevoValor++

            sqlManager.actualizarFila(this, categoria.id, "${dayVal}Value", nuevoValor)
            cargarVista(tablaIntent)
        }

        tabla.addView(nuevaFila)
    }

    fun cargarVista(vista: Intent) {
        startActivity(vista)
        this.finish()
    }
}