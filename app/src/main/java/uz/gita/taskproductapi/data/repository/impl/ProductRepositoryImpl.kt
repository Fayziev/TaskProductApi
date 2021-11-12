package uz.gita.taskproductapi.data.repository.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.taskproductapi.data.ApiClient
import uz.gita.taskproductapi.data.api.ProductApi
import uz.gita.taskproductapi.data.repository.ProductRepository
import uz.gita.taskproductapi.data.request.EditRequest
import uz.gita.taskproductapi.data.request.InsertRequest
import uz.gita.taskproductapi.data.response.Product
import uz.gita.taskproductapi.data.response.ProductType

class ProductRepositoryImpl : ProductRepository {
    private val api = ApiClient.getProduct().create(ProductApi::class.java)

    override fun getAllData(): Flow<Result<List<Product>>> = flow {
        val response = api.getAllProduct()
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!))
        } else {
            val message = "Xatolik yuzaga keldi!"
            response.errorBody()?.let {
                emit(Result.failure<List<Product>>(Throwable(message)))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun insertData(data: InsertRequest): Flow<Result<Unit>> = flow {
        val response = api.insertProduct(data)
        if (response.isSuccessful) {
            emit(Result.success(Unit))
        } else {
            val message = "Xatolik yuzaga keldi"
            emit(Result.failure(Throwable(message)))
        }
    }.flowOn(Dispatchers.IO)

    override fun editData(data: EditRequest): Flow<Result<Unit>> = flow {
        val response = api.editProduct(data)
        if (response.isSuccessful) {
            emit(Result.success(Unit))
        } else {
            val message = "Xatolik yuzaga keldi"
            emit(Result.failure(Throwable(message)))
        }

    }.flowOn(Dispatchers.IO)

    override fun deleteData(id: Int): Flow<Result<Unit>> = flow {
        val response = api.deleteProduct(id)
        if (response.isSuccessful) {
            emit(Result.success(Unit))
        } else {
            val message = "Xatolik yuzaga keldi"
            emit(Result.failure(Throwable(message)))
        }
    }.flowOn(Dispatchers.IO)

    override fun getAllDataPaging(page: Int, perPage: Int): Flow<Result<List<Product>>> = flow {
        val response = api.getAllDataPaging(page, perPage)
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!))
        } else {
            val message = "Xatolik yuzaga keldi!"
            response.errorBody()?.let {
                emit(Result.failure<List<Product>>(Throwable(message)))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getProductType(): Flow<Result<List<ProductType>>> = flow {
        val response = api.getProductTypes()
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!))
        } else {
            val message = "Xatolik yuzaga keldi!"
            response.errorBody()?.let {
                emit(Result.failure<List<ProductType>>(Throwable(message)))
            }
        }
    }.flowOn(Dispatchers.IO)

}

