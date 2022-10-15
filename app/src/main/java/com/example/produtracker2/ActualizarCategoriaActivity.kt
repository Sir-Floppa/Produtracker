package com.example.produtracker2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_actualizar_categoria.*
import kotlinx.android.synthetic.main.activity_nueva_categoria2.*
import kotlinx.android.synthetic.main.activity_nueva_categoria2.btnCancelar
import kotlinx.android.synthetic.main.activity_nueva_categoria2.priorityGroup
import kotlinx.android.synthetic.main.activity_nueva_categoria2.txtFldNombre

class ActualizarCategoriaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_categoria)

        var id: Int = intent.getIntExtra("id", 0)
        var nombre = intent.getStringExtra("nombre")
        var prioridad: Int = intent.getIntExtra("prioridad", 0)

        val categoriasIntent = Intent(this, CategoriasActivity::class.java)

        txtFldNombre.setText(nombre)
        when(prioridad) {
            3 -> priorityGroup.check(R.id.prioridadAlta)
            2 -> priorityGroup.check(R.id.prioridadMedia)
            1 -> priorityGroup.check(R.id.prioridadBaja)
            0 -> priorityGroup.check(R.id.prioridadNula)
        }

        btnActualizar.setOnClickListener{
            if(testData()) {
                var prioridad: Int = 0
                when(priorityGroup.checkedRadioButtonId){
                    R.id.prioridadAlta -> prioridad = 3
                    R.id.prioridadMedia -> prioridad = 2
                    R.id.prioridadBaja -> prioridad = 1
                    R.id.prioridadNula -> prioridad = 0
                }

                var datos: CategoriaClass = CategoriaClass(id, txtFldNombre.text.toString(), prioridad)

                var sqlManager = SQLManager(this)
                var response = sqlManager.actualizarCategoria(this, datos)

                if(response) {
                    Toast.makeText(this, "Se ha actualizado la categoría: ${txtFldNombre.text.toString()}", Toast.LENGTH_SHORT).show()
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