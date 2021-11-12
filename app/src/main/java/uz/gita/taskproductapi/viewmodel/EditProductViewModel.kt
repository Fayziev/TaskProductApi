package uz.gita.taskproductapi.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.taskproductapi.data.request.EditRequest
import uz.gita.taskproductapi.data.request.InsertRequest

interface EditProductViewModel {
    val editLiveData: LiveData<Unit>
    val openMainFragmentLiveData: LiveData<Unit>
    val errorMessageLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>
    val connectionLiveData: LiveData<String>

    fun editData(data: EditRequest)
}