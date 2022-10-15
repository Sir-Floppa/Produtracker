package com.example.produtracker2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.widget.Toast

class SQLManager(context: Context): SQLiteOpenHelper(context, "produtracker.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE categorias (idCategoria INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nombre VARCHAR(24), prioridad INT)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    // Categorias
    fun anadirCategoria(context: Context, datos: CategoriaClass): Boolean {
        var response = true

        var contentValues = ContentValues()
        contentValues.putNull("idCategoria")
        contentValues.put("nombre", datos.nombre)
        contentValues.put("prioridad", datos.prioridad)

        var db = SQLManager(context)
        var manager = db.writableDatabase

        try {
            manager.insert("categorias", null, contentValues)
        }
        catch(e: Exception) {
            print(e.message)
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            response = false
        }
        finally {
            // db.close()
        }

        return response
    }

    fun leerCategorias(context: Context): Array<CategoriaClass> {
        var db = SQLManager(context)
        var manager = db.writableDatabase

        var resArray = arrayOf<CategoriaClass>()

        try {
            val ans = manager.query(
                "categorias",
                arrayOf("idCategoria", "nombre", "prioridad"),
                "",
                arrayOf(),
                "",
                null,
                null
            )

            with(ans) {
                while(moveToNext()) {
                    var id = getInt(getColumnIndexOrThrow("idCategoria"))
                    var nombre = getString(getColumnIndexOrThrow("nombre"))
                    var prioridad = getInt(getColumnIndexOrThrow("prioridad"))

                    resArray += CategoriaClass(id, nombre, prioridad)
                }
            }
        }
        catch(e: Exception) {
            print(e.message)
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }

        return resArray
    }

    fun eliminarCategoria(context: Context, id: Int): Boolean {
        var response = true

        var db = SQLManager(context)
        var manager = db.writableDatabase

        try {
            manager.delete("categorias", "idCategoria LIKE ?", arrayOf(id.toString()))
        }
        catch(e: Exception) {
            print(e.message)
            Toast.makeText(context, "Categoría eliminada con éxito.", Toast.LENGTH_LONG).show()
            response = false
        }

        return response
    }

    fun actualizarCategoria(context: Context, datos: CategoriaClass): Boolean {
        var response = true
        var db = SQLManager(context)
        var manager = db.writableDatabase

        try {
            var contentValues = ContentValues()
            contentValues.put("nombre", datos.nombre)
            contentValues.put("prioridad", datos.prioridad)

            manager.update("categorias", contentValues, "idCategoria LIKE ?", arrayOf(datos.id.toString()))
        }
        catch(e: Exception) {
            print(e.message)
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            response = false
        }
        return response
    }

}
