package com.juniors.tienditausb

import android.text.format.DateFormat
import java.util.Calendar
import java.util.Locale

object Costantes {

    const val anuncio_disponible = "Disponible"
    const val anuncio_vendido = "Vendido"

    val categorias = arrayOf(
        "Todos",
        "Móbiles",
        "Ordenadores/Laptops",
        "Electrónica y electrodomésticos",
        "Vehículos",
        "Consolas y videojuegos",
        "Hogar y muebles",
        "Belleza y cuidado personal",
        "Libros",
        "Deportes",
        "Juguetes y figuras",
        "Mascotas"
    )
    val condiciones = arrayOf(
        "Todos",
        "Nuevo",
        "Usado",
        "Remodelado"
    )

    fun obtenerTiempoDis() : Long{
        return System.currentTimeMillis()
    }

    fun obtenerFecha(tiempo : Long) : String{
        val calendario = Calendar.getInstance(Locale.ENGLISH)
        calendario.timeInMillis = tiempo

        return DateFormat.format("dd/MM/yyyy", calendario).toString()
    }
}