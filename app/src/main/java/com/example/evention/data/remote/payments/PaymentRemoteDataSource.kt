package com.example.evention.data.remote.payments

import com.example.evention.model.CreatePaymentRequest
import com.example.evention.model.Payment


class PaymentRemoteDataSource(private val api: PaymentApiService) {

    suspend fun createPayment(
        ticketID: String,
        userId: String,
        totalValue: Number,
        paymentType: String,
    ): Payment {
        val request = CreatePaymentRequest(
            ticketID = ticketID,
            userId = userId,
            totalValue = totalValue,
            paymentType = paymentType
        )
        return api.createPayment(request)
    }
}
