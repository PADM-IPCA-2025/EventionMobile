package com.example.evention.ui.screens.home.search

import android.content.Context
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

import kotlinx.coroutines.withTimeout
import java.util.Locale
import java.util.concurrent.TimeoutException

fun fetchCoordinates(context: Context, query: String, cameraPositionState: CameraPositionState) {
    CoroutineScope(Dispatchers.Main).launch {  // <<--- Garantir que roda na Main
        try {
            val result = withTimeout(10_000) {
                withContext(Dispatchers.IO) {
                    Geocoder(context).getFromLocationName(query, 1)
                }
            }

            if (result?.isNotEmpty() == true) {
                val latLng = LatLng(result[0].latitude, result[0].longitude)

                // Atualiza a câmera (já está no Main Thread)
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLngZoom(latLng, 15f),
                    durationMs = 1000
                )
            } else {
                Toast.makeText(context, "Local não encontrado", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}

