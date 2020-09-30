package com.codemobiles.androidhero.services

import com.codemobiles.androidhero.models.ProductAllResponse
import retrofit2.Call
import retrofit2.http.GET


interface APIService {
    @GET("product")
    fun getProducts(): Call<List<ProductAllResponse>>

//    @PUT("$API_PRODUCT/{$API_PRODUCT_PARAMS_ID}")
//    fun editProduct(@Path(API_PRODUCT_PARAMS_ID) id: Int, @Body product: ProductRequest): Call<Any>
//
//    @DELETE("$API_PRODUCT/{$API_PRODUCT_PARAMS_ID}")
//    fun deleteProduct(@Path(API_PRODUCT_PARAMS_ID) id: Int): Call<Any>
//
//    @Multipart
//    @POST(API_PRODUCT)
//    fun addProduct(
//        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>,
//        @Part image: MultipartBody.Part?
//    ): Call<ResponseBody>
}