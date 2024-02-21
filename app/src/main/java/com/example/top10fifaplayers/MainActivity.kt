package com.example.top10fifaplayers

import Carta
import HeroeTopAppBar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.top10fifaplayers.dataResourece.DataSource
import com.example.top10fifaplayers.ui.theme.Top10FifaPlayersTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Top10FifaPlayersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    JugadoresApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JugadoresApp() {
    val dataSource = DataSource()
    val jugadores = dataSource.getJugadores()

    Scaffold(topBar = {
        HeroeTopAppBar()
    }) { it ->
        LazyColumn(
            contentPadding = it
        ) {
            items(jugadores) {
                Carta(
                    jugador = it,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
    }


}



@Preview(showBackground = true)
@Composable
fun JugadoresPreview() {
    Top10FifaPlayersTheme() {
        JugadoresApp()
    }
}

@Preview(showBackground = true)
@Composable
fun JugadoresDarkPreview() {
    Top10FifaPlayersTheme(darkTheme = true) {
        JugadoresApp()
    }
}