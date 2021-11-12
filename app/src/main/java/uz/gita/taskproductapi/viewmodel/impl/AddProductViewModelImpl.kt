package uz.gita.taskproductapi.viewmodel.impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.taskproductapi.data.repository.impl.ProductRepositoryImpl
import uz.gita.taskproductapi.data.request.InsertRequest
import uz.gita.taskproductapi.utils.isConnected
import uz.gita.taskproductapi.viewmodel.AddProductViewModel

class AddProductViewModelImpl : ViewModel(), AddProductViewModel {
    private val repository = ProductRepositoryImpl()

    override val insertLiveData = MutableLiveData<Unit>()
    override val openMainFragmentLiveData = MutableLiveData<Unit>()
    override val errorMessageLiveData = MutableLiveData<String>()
    override val successMessageLiveData = MutableLiveData<String>()
    override val progressLiveData = MutableLiveData<Boolean>()
    override val connectionLiveData = MutableLiveData<String>()
    override fun insertData(data: InsertRequest) {

        if (!isConnected()) {
            connectionLiveData.value = "The Internet is not available"
            return
        }
        progressLiveData.value = true
        repository.insertData(data).onEach {
            progressLiveData.value = false
            it.onSuccess {
                successMessageLiveData.value = "Success"
                insertLiveData.value=Unit
            }

            it.onFailure { throwable ->
                errorMessageLiveData.value = throwable.message
            }

        }.catch { throwable ->
            Log.d("RRR", "insertData: ${throwable.message}")
            errorMessageLiveData.value = throwable.message
        }.launchIn(viewModelScope)

    }

}

