package com.codemobiles.androidhero.services

import com.codemobiles.androidhero.API_PRODUCT
import com.codemobiles.androidhero.models.ProductAllResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface APIService {
    @GET("product")
    fun getProducts(): Call<List<ProductAllResponse>>

//    @PUT("$API_PRODUCT/{$API_PRODUCT_PARAMS_ID}")
//    fun editProduct(@Path(API_PRODUCT_PARAMS_ID) id: Int, @Body product: ProductRequest): Call<Any>
//
//    @DELETE("$API_PRODUCT/{$API_PRODUCT_PARAMS_ID}")
//    fun deleteProduct(@Path(API_PRODUCT_PARAMS_ID) id: Int): Call<Any>
//
    @Multipart
    @POST(API_PRODUCT)
    fun addProduct(
    @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>,
    @Part image: MultipartBody.Part?
    ): Call<ResponseBody>
}