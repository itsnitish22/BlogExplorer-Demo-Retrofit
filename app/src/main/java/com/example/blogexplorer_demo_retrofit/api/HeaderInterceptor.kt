package com.example.blogexplorer_demo_retrofit.api

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request()
                .newBuilder()
                .addHeader("User-Agent", "Blog-Explorer-Sample")
                .build()
        return chain.proceed(request)
    }
}