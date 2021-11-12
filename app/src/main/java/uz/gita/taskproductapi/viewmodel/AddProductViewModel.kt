package uz.gita.taskproductapi.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.taskproductapi.data.request.InsertRequest

interface AddProductViewModel {
    val insertLiveData: LiveData<Unit>
    val openMainFragmentLiveData: LiveData<Unit>
    val errorMessageLiveData: LiveData<String>
    val successMessageLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>
    val connectionLiveData: LiveData<String>

    fun insertData(data: InsertRequest)

}