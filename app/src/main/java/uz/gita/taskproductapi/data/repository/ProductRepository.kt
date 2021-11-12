package uz.gita.taskproductapi.data.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.taskproductapi.data.request.EditRequest
import uz.gita.taskproductapi.data.request.InsertRequest
import uz.gita.taskproductapi.data.response.Product
import uz.gita.taskproductapi.data.response.ProductType

interface ProductRepository {

    fun getAllData(): Flow<Result<List<Product>>>
    fun insertData(data: InsertRequest): Flow<Result<Unit>>
    fun editData(data: EditRequest): Flow<Result<Unit>>
    fun deleteData(id: Int): Flow<Result<Unit>>
    fun getAllDataPaging(page: Int, perPage: Int): Flow<Result<List<Product>>>
    fun getProductType(): Flow<Result<List<ProductType>>>

}