package com.example.evention.ui.screens.home.payment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evention.data.remote.payments.PaymentRemoteDataSource
import com.example.evention.di.NetworkModule
import com.example.evention.model.Payment
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PaymentViewModel : ViewModel() {

    private val TAG = "PaymentViewModel"

    private val remoteDataSource = NetworkModule.paymentRemoteDataSource

    private val _paymentResult = MutableStateFlow<Payment?>(null)
    val paymentResult: StateFlow<Payment?> = _paymentResult

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun createPayment(
        ticketID: String,
        userId: String,
        totalValue: Number,
        paymentType: String,
    ) {
        Log.d(TAG, "Iniciando criação de pagamento:")
        Log.d(TAG, "ticketID = $ticketID")
        Log.d(TAG, "userId = $userId")
        Log.d(TAG, "totalValue = $totalValue")
        Log.d(TAG, "paymentType = $paymentType")

        viewModelScope.launch {
            try {
                val payment = remoteDataSource.createPayment(
                    ticketID, userId, totalValue, paymentType
                )
                _paymentResult.value = payment
                Log.d(TAG, "Pagamento criado com sucesso: $payment")
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e(TAG, "Erro ao criar pagamento", e)
            }
        }
    }
}