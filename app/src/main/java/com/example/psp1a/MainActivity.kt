
package com.example.psp1a

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Process
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.psp1a.ui.theme.Psp1ATheme
import es.dam.psp.ut1.servicio.DescargaService

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Psp1ATheme {
                PantallaDescarga()
            }
        }
    }
}

@Composable
fun PantallaDescarga() {

    val context = LocalContext.current

    val permisoNotificaciones = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permisoNotificaciones.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Práctica 5 - Servicio Android"
        )

        Text(
            text = "PID de la actividad: ${Process.myPid()}"
        )

        Button(
            onClick = {
                val intent = Intent(
                    context,
                    DescargaService::class.java
                )

                ContextCompat.startForegroundService(
                    context,
                    intent
                )
            }
        ) {
            Text("Iniciar descarga")
        }

        Button(
            onClick = {
                val intent = Intent(
                    context,
                    DescargaService::class.java
                )

                context.stopService(intent)
            }
        ) {
            Text("Parar descarga")
        }
    }
}
