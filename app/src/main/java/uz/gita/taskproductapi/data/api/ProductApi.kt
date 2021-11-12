package uz.gita.taskproductapi.data.api

import retrofit2.Response
import retrofit2.http.*
import uz.gita.taskproductapi.data.request.EditRequest
import uz.gita.taskproductapi.data.request.InsertRequest
import uz.gita.taskproductapi.data.response.Product
import uz.gita.taskproductapi.data.response.ProductType

interface ProductApi {

    @GET("product")
    suspend fun getAllProduct(): Response<List<Product>>

    @POST("product")
    suspend fun insertProduct(@Body data: InsertRequest): Response<Unit>

    @PUT("product")
    suspend fun editProduct(@Body data: EditRequest): Response<Unit>

    @DELETE("product/{id}")
    suspend fun deleteProduct(@Path("id") itemId: Int): Response<Unit>

    @GET("product/")
    suspend fun getAllDataPaging(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int
    ): Response<List<Product>>

    @GET("product/get-product-types")
    suspend fun getProductTypes(): Response<List<ProductType>>

}