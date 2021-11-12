package uz.gita.taskproductapi.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.taskproductapi.data.request.EditRequest
import uz.gita.taskproductapi.data.request.InsertRequest
import uz.gita.taskproductapi.data.response.Product

interface ProductViewModel {

    val errorMessageLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>
    val connectionLiveData: LiveData<String>
    val allDataLiveData: LiveData<List<Product>>
    val insertLiveData: LiveData<Unit>
    val editLiveData: LiveData<Unit>
    val deleteLiveData: LiveData<Unit>

    fun getAllData()
    fun editData(data: EditRequest)
    fun delete(id: Int)
    fun insertData(data: InsertRequest)

}