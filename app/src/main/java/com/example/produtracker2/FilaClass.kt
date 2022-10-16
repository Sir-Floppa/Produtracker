package com.example.produtracker2

class FilaClass {
    var id: Int? = null
    var LValue: Int = 0
    var MValue: Int = 0
    var XValue: Int = 0
    var JValue: Int = 0
    var VValue: Int = 0
    var SValue: Int = 0
    var DValue: Int = 0
    var idCategoria: Int? = null

    constructor(
        id: Int?,
        idCategoria: Int?,
        LValue: Int = 0,
        MValue: Int = 0,
        XValue: Int = 0,
        JValue: Int = 0,
        VValue: Int = 0,
        SValue: Int = 0,
        DValue: Int = 0
    ) {
        this.id = id
        this.LValue = LValue
        this.MValue = MValue
        this.XValue = XValue
        this.JValue = JValue
        this.VValue = VValue
        this.SValue = SValue
        this.DValue = DValue
        this.idCategoria = idCategoria
    }
}