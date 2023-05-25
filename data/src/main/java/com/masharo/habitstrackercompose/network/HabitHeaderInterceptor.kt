package com.masharo.habitstrackercompose.network

import okhttp3.Interceptor
import okhttp3.Response

class HabitHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain
                .request()
                .newBuilder()
                .header("Authorization", TOKEN)
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .build()
        )
    }

}