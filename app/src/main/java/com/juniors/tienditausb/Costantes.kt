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
        "Juguetes y figuras"
    )

    val categoriasIcono = arrayOf(
        R.drawable.ic_categoria_todos,
        R.drawable.ic_categoria_mobiles,
        R.drawable.ic_categoria_ordenadores,
        R.drawable.ic_categoria_electrodomesticos,
        R.drawable.ic_categoria_vehiculos,
        R.drawable.ic_categoria_consolas,
        R.drawable.ic_categoria_muebles,
        R.drawable.ic_categoria_belleza,
        R.drawable.ic_categoria_libros,
        R.drawable.ic_categoria_deportes,
        R.drawable.ic_categoria_juguetes
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