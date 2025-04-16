package com.goodwy.dialer.spamdetection

import android.content.Context
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object checkIfNumberIsSpam {
    fun checkIfNumberIsSpam(number: String, context: Context){
        val request = PhoneRequest(number)
        ApiClient.spamApi.checkSpam(request).enqueue(object : Callback<SpamResponse> {
            override fun onResponse(call: Call<SpamResponse>, response: Response<SpamResponse>) {
                if (response.isSuccessful) {
                    val isSpam = response.body()?.spam ?: false
                    if (isSpam) {
                        Toast.makeText(context, "⚠️ Spam Number Detected!", Toast.LENGTH_LONG).show()
                        // Handle blocking or UI alert
                    }
                }
            }

            override fun onFailure(call: Call<SpamResponse>, t: Throwable) {
                Log.e("SpamCheck", "Error: ${t.message}")
            }
        })
    }

}
