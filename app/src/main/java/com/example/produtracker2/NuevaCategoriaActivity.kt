package com.example.produtracker2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_nueva_categoria2.*

class NuevaCategoriaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_categoria2)

        btnAceptar.setOnClickListener{
            this.finish()
        }
        btnCancelar.setOnClickListener{
            this.finish()
        }
    }
}