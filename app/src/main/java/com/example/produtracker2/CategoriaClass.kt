package com.example.produtracker2

class CategoriaClass {
    var id: Int? = null
    var nombre: String = ""
    var prioridad: Int? = null

    constructor(id: Int?, nombre: String, prioridad: Int?) {
        this.id = id
        this.nombre = nombre
        this.prioridad = prioridad
    }
}