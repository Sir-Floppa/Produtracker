package com.example.produtracker2

class RegistroClass {
    var id: Int? = null
    var fInicio: String = ""
    var fFinal: String = ""
    var valor: Int? = null

    constructor(id: Int?, fInicio: String, fFinal: String, valor: Int?) {
        this.id = id
        this.fInicio = fInicio
        this.fFinal = fFinal
        this.valor = valor
    }
}