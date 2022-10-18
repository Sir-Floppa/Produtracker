package com.example.produtracker2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.widget.Toast
import kotlin.reflect.typeOf

class SQLManager(context: Context): SQLiteOpenHelper(context, "produtracker1.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE categorias (idCategoria INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nombre VARCHAR(24), prioridad INT)")
        db!!.execSQL("CREATE TABLE filasTablaSemanal (idFila INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, LValue	INTEGER DEFAULT 0, MValue INTEGER DEFAULT 0, XValue	INTEGER DEFAULT 0, JValue INTEGER DEFAULT 0, VValue	INTEGER DEFAULT 0, SValue INTEGER DEFAULT 0, DValue	INTEGER DEFAULT 0, idCategoria	INTEGER NOT NULL, FOREIGN KEY(idCategoria) REFERENCES categorias(idCategorias))")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    // Filas
    fun anadirFila(context:Context, ans: Int): Boolean {
        var response = true

        var db = SQLManager(context)
        var manager = db.writableDatabase

        var contentValues = ContentValues()
        contentValues.putNull("idFila")
        contentValues.put("idCategoria", ans)

        try {
            manager.insert("filasTablaSemanal", null, contentValues)
        }
        catch(e: Exception) {
            print(e.message)
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            response = false
        }
        finally {
            db.close()
        }

        return response
    }

    fun actualizarFila(context: Context, id: Int?, campo: String, valor: Int) {
        var db = SQLManager(context)
        var manager = db.writableDatabase

        var contentValues = ContentValues()
        contentValues.put(campo, valor)
        try {
            manager.update("filasTablaSemanal", contentValues, "idCategoria LIKE ${id}", null)
        }
        catch(e: Exception) {
            print(e.message)
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
        finally {
            db.close()
        }
    }

    fun seleccionarFila(context: Context, id: Int?): FilaClass {
        var db = SQLManager(context)
        var manager = db.writableDatabase

        var res = arrayOf<FilaClass>()

        try {
            val ans = manager.query(
                "filasTablaSemanal",
                arrayOf(
                    "idFila",
                    "LValue",
                    "MValue",
                    "XValue",
                    "JValue",
                    "VValue",
                    "SValue",
                    "DValue",
                    "idCategoria"),
                "idCategoria == ${id}",
                arrayOf(),
                "",
                null,
                null
            )

            with(ans) {
                while(moveToNext()) {
                    var id = getInt(getColumnIndexOrThrow("idFila"))
                    var LValue = getInt(getColumnIndexOrThrow("LValue"))
                    var MValue = getInt(getColumnIndexOrThrow("MValue"))
                    var XValue = getInt(getColumnIndexOrThrow("XValue"))
                    var JValue = getInt(getColumnIndexOrThrow("JValue"))
                    var VValue = getInt(getColumnIndexOrThrow("VValue"))
                    var SValue = getInt(getColumnIndexOrThrow("SValue"))
                    var DValue = getInt(getColumnIndexOrThrow("DValue"))
                    var idCategoria = getInt(getColumnIndexOrThrow("idCategoria"))

                    res += FilaClass(id, idCategoria, LValue, MValue, XValue, JValue, VValue, SValue, DValue)
                }
            }
        }
        catch(e: Exception) {
            print(e.message)
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
        finally {
            db.close()
        }

        return res[0]
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
            var ans = manager.insert("categorias", null, contentValues)
            anadirFila(context, ans.toInt())
        }
        catch(e: Exception) {
            print(e.message)
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            response = false
        }
        finally {
            db.close()
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
                "prioridad DESC"
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
        finally {
            db.close()
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
        finally {
            db.close()
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
        finally {
            db.close()
        }
        return response
    }

}
