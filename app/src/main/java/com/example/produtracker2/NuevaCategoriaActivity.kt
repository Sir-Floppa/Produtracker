package com.example.produtracker2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_nueva_categoria2.*

class NuevaCategoriaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_categoria2)

        val categoriasIntent = Intent(this, CategoriasActivity::class.java)

        btnAceptar.setOnClickListener{
            if(testData()) {
                var prioridad: Int = 0
                when(priorityGroup.checkedRadioButtonId){
                    R.id.prioridadAlta -> prioridad = 3
                    R.id.prioridadMedia -> prioridad = 2
                    R.id.prioridadBaja -> prioridad = 1
                    R.id.prioridadNula -> prioridad = 0
                }

                var datos: CategoriaClass = CategoriaClass(null, txtFldNombre.text.toString(), prioridad)

                var sqlManager = SQLManager(this)
                var response = sqlManager.anadirCategoria(this, datos)

                if(response) {
                    Toast.makeText(this, "Se ha creado la nueva categoría: ${txtFldNombre.text.toString()}", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this, "Se produjo un error.", Toast.LENGTH_SHORT).show()
                }

                cargarVista(categoriasIntent)
            }
            else {
                Toast.makeText(this, "Ingrese valores válidos para la nueva categoría.", Toast.LENGTH_SHORT).show()
            }
        }
        btnCancelar.setOnClickListener{
            cargarVista(categoriasIntent)
        }
    }

    fun cargarVista(vista: Intent) {
        startActivity(vista)
        this.finish()
    }

    fun testData(): Boolean {
        var response = true

        if (txtFldNombre.text.isEmpty() || priorityGroup.checkedRadioButtonId == null) {
            response = false
        }

        return response
    }
}