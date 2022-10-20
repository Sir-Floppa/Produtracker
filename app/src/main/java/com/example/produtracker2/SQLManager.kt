package com.example.produtracker2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.provider.BaseColumns
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.contentValuesOf
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters
import java.util.*
import kotlin.reflect.typeOf

class SQLManager(context: Context): SQLiteOpenHelper(context, "produtracker4.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE categorias (idCategoria INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nombre VARCHAR(24), prioridad INT)")
        db!!.execSQL("CREATE TABLE filasTablaSemanal (idFila INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, LValue	INTEGER DEFAULT 0, MValue INTEGER DEFAULT 0, XValue	INTEGER DEFAULT 0, JValue INTEGER DEFAULT 0, VValue	INTEGER DEFAULT 0, SValue INTEGER DEFAULT 0, DValue	INTEGER DEFAULT 0, idCategoria	INTEGER NOT NULL, FOREIGN KEY(idCategoria) REFERENCES categorias(idCategorias))")
        db!!.execSQL("CREATE TABLE registros (idRegistro INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, fechaInicio TEXT, fechaFinal TEXT, valor INTEGER)")
        db!!.execSQL("CREATE TABLE uso (eliminacionSemanal INTEGER DEFAULT 1)")
        db!!.execSQL("INSERT INTO uso VALUES(1)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    // Uso
    fun comprobarEliminacion(context: Context): Boolean {
        var db = SQLManager(context)
        var manager = db.writableDatabase

        var response = true

        var res = manager.query(
            "uso",
            arrayOf("eliminacionSemanal"),
            null,
            arrayOf(),
            "",
            null,
            null
        )

        with(res) {
            while(moveToNext()) {
                response = getInt(getColumnIndexOrThrow("eliminacionSemanal")) == 1
            }
        }

        return response
    }

    fun actualizarUso(context: Context, valor: Int) {
        var db = SQLManager(context)
        var manager = db.writableDatabase

        var contentValues = ContentValues()

        contentValues.put("eliminacionSemanal", valor)

        manager.update(
            "uso",
            contentValues,
            null,
            null
        )
    }

    // Registro
    @RequiresApi(Build.VERSION_CODES.O)
    fun anadirRegistro(context: Context): Boolean {
        var response = true

        var db = SQLManager(context)
        var manager = db.writableDatabase

        val today = LocalDate.now()
        var previousMonday = today.with(TemporalAdjusters.previous(DayOfWeek.MONDAY))
        previousMonday = previousMonday.with(TemporalAdjusters.previous(DayOfWeek.MONDAY))

        var previousSunday = today.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY))

        var categorias = leerCategorias(context)

        var lsum = 0
        var msum = 0
        var xsum = 0
        var jsum = 0
        var vsum = 0
        var ssum = 0
        var dsum = 0
        for (categoria in categorias) {
            var fila = seleccionarFila(context, categoria.id)
            lsum += fila.LValue * categoria.prioridad!!
            msum += fila.LValue * categoria.prioridad!!
            xsum += fila.LValue * categoria.prioridad!!
            jsum += fila.LValue * categoria.prioridad!!
            vsum += fila.LValue * categoria.prioridad!!
            ssum += fila.LValue * categoria.prioridad!!
            dsum += fila.LValue * categoria.prioridad!!
        }

        var total = lsum + msum + xsum + jsum + vsum + ssum + dsum

        var contentValues = ContentValues()
        contentValues.putNull("idRegistro")
        contentValues.put("fechaInicio", previousMonday.toString())
        contentValues.put("fechaFinal", previousSunday.toString())
        contentValues.put("valor", total)

        try {
            manager.insert("registros", null, contentValues)
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

    fun reiniciarFilas(context: Context) {
        var db = SQLManager(context)
        var manager = db.writableDatabase

        var contentValues = ContentValues()
        contentValues.put("LValue", 0)
        contentValues.put("MValue", 0)
        contentValues.put("XValue", 0)
        contentValues.put("JValue", 0)
        contentValues.put("VValue", 0)
        contentValues.put("SValue", 0)
        contentValues.put("DValue", 0)

        manager.update("filasTablaSemanal", contentValues,null, null)
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
