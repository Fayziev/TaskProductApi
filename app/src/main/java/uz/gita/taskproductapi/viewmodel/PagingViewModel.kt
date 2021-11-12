package uz.gita.taskproductapi.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.taskproductapi.data.response.Product

interface PagingViewModel {
    val errorMessageLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>
    val connectionLiveData: LiveData<Boolean>
    val allDataLiveData: LiveData<List<Product>>
    val insertLiveData: LiveData<Unit>
    val editLiveData: LiveData<Unit>
    val deleteLiveData: LiveData<Unit>

    fun getAllPaging(page:Int,perPage:Int)
}