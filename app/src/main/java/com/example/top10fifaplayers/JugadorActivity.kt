import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import com.example.top10fifaplayers.R
import com.example.top10fifaplayers.model.Jugador
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity


@Composable
fun Carta(jugador: Jugador, modifier: Modifier) {
    var rotated by remember { mutableStateOf(false) }
    val rotar by animateFloatAsState(
        targetValue = if (rotated) 360f else 0f,
        animationSpec = tween(600)
    )

    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .padding(dimensionResource(id = R.dimen.padding_small))
            .graphicsLayer {
                rotationY = rotar
                cameraDistance = 8 * density
            }
            .clickable { rotated = !rotated }
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inversePrimary)

        ) {
            if (!rotated) {
                CartaJugador(jugador, expanded) { expanded = it }
            } else {
                CartaDetras(jugador)
            }
        }
    }
}

@Composable
fun CartaDetras(jugador: Jugador) {
    var context = LocalContext.current
    var estadio: String = stringResource(id = jugador.linkEstadio)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(jugador.nombreEstadio),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_small))
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Image(
            painter = painterResource(jugador.estadio),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .size(dimensionResource(R.dimen.jugador_size))
        )
        ClickableText(
            text = AnnotatedString(stringResource(id = R.string.estadio)),
            style = MaterialTheme.typography.displaySmall,
            onClick = {
                val uri = Uri.parse(estadio)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(context, intent, null)
            },
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_small))
                .fillMaxWidth()
        )

    }
}

@Composable
fun CartaJugador(
    jugador: Jugador, expanded: Boolean, expandedChange: (Boolean) -> Unit
) {


    Column(
        modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )

            )

    ) {

        FotoJugador(jugador.fotoJugador)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            InformacionJugador(
                jugador.nombre, jugador.equipo, modifier = Modifier
                    .weight(1f)
                    .padding(end = dimensionResource(id = R.dimen.padding_big))
            )
            EscudoJugador(escudo = jugador.escudo)
            JugadorStatsButton(
                expanded = expanded,
                onClick = { expandedChange(!expanded) }
            )
        }

        if (expanded) {
            JugadorStats(
                nacionalidad = jugador.nacionalidad,
                edad = jugador.edad,
                altura = jugador.altura,
                valor = jugador.valorMercado,
                posicion = jugador.posicion,
                dorsal = jugador.dorsal
            )

        }

    }


}

@Composable
fun EscudoJugador(
    @DrawableRes escudo: Int,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(dimensionResource(R.dimen.escudo_size))
            .clip(MaterialTheme.shapes.medium),
        contentScale = ContentScale.Crop,
        painter = painterResource(escudo),
        contentDescription = null
    )
}

@Composable
fun FotoJugador(
    @DrawableRes fotoJugador: Int,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .size(dimensionResource(R.dimen.jugador_size)),
        contentScale = ContentScale.Crop,
        painter = painterResource(fotoJugador),
        contentDescription = null
    )
}

@Composable
fun InformacionJugador(
    @StringRes nombre: Int,
    @StringRes equipo: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(nombre),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
        )
        Text(
            text = stringResource(equipo),
            style = MaterialTheme.typography.displaySmall
        )
    }
}

@Composable
private fun JugadorStatsButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = stringResource(R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun JugadorStats(
    @StringRes nacionalidad: Int,
    @StringRes edad: Int,
    @StringRes altura: Int,
    @StringRes valor: Int,
    @StringRes posicion: Int,
    @StringRes dorsal: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Row {
            columnaStats(R.string.nacionalidad, nacionalidad, R.string.valor, valor)
            Spacer(modifier = Modifier.weight(1f))
            columnaStats(R.string.edad, edad, R.string.posicion, posicion)
            Spacer(modifier = Modifier.weight(1f))
            columnaStats(R.string.altura, altura, R.string.dorsal, dorsal)
        }
    }

}

@Composable
fun columnaStats(
    @StringRes campo: Int,
    @StringRes valor: Int,
    @StringRes campo1: Int,
    @StringRes valor1: Int
) {
    Column {
        Text(
            text = stringResource(campo),
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = stringResource(valor),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(campo1),
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = stringResource(valor1),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroeTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = modifier
                        .size(dimensionResource(R.dimen.logo_size)),

                    painter = painterResource(R.drawable.logoapp),
                    contentDescription = null
                )
            }
        },
        modifier = modifier
    )
}