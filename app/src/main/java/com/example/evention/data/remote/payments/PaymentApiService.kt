package com.example.evention.data.remote.payments

import com.example.evention.model.CreateFeedbackRequest
import com.example.evention.model.CreatePaymentRequest
import com.example.evention.model.Feedback
import com.example.evention.model.Payment
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentApiService {

    @POST("payment/api/payments")
    suspend fun createPayment(
        @Body request: CreatePaymentRequest,
    ): Payment
}