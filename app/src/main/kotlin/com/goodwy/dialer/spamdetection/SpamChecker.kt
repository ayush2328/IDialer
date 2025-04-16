package com.goodwy.dialer.spamdetection

import android.content.Context
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SpamChecker {
    fun check(number: String, context: Context, onResult: (Boolean) -> Unit) {
        val request = PhoneRequest(number)
        ApiClient.spamApi.checkSpam(request).enqueue(object : Callback<SpamResponse> {
            override fun onResponse(call: Call<SpamResponse>, response: Response<SpamResponse>) {
                if (response.isSuccessful) {
                    val isSpam = response.body()?.spam ?: false
                    Log.d("SpamChecker", "Result: $number isSpam=$isSpam")
                    onResult(isSpam)
                } else {
                    Log.e("SpamChecker", "API failed: ${response.errorBody()?.string()}")
                    onResult(false)
                }
            }

            override fun onFailure(call: Call<SpamResponse>, t: Throwable) {
                Log.e("SpamChecker", "API Error: ${t.message}")
                onResult(false) // fail safe
            }
        })
    }

}
