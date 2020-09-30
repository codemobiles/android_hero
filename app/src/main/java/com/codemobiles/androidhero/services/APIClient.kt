package com.codemobiles.androidhero.services

import com.codemobiles.androidhero.BASE_URL
import com.codemobiles.androidhero.IMAGE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    // Singleton
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    fun getImageURL() = BASE_URL + "${IMAGE_URL}/"
}
