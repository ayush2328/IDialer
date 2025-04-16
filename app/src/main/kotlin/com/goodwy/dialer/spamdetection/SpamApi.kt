package com.goodwy.dialer.spamdetection

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SpamApi {
    @POST("/predict")
    fun checkSpam(@Body number: PhoneRequest): Call<SpamResponse>
}

data class PhoneRequest(val phone_number: String)
data class SpamResponse(val prediction: Int, val result: String)
