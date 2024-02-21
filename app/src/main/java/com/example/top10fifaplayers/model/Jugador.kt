package com.example.top10fifaplayers.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Jugador(
    @StringRes val nombre: Int,
    @StringRes val equipo: Int,
    @DrawableRes val fotoJugador: Int,
    @DrawableRes val escudo: Int,
    @StringRes val posicion: Int,
    @StringRes val dorsal: Int,
    @StringRes val altura: Int,
    @StringRes val edad: Int,
    @StringRes val nacionalidad: Int,
    @DrawableRes val estadio: Int,
    @StringRes val nombreEstadio: Int,
    @StringRes val linkEstadio: Int,
    @StringRes val valorMercado: Int

)
