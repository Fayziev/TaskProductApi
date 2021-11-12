package uz.gita.taskproductapi.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.taskproductapi.data.repository.impl.ProductRepositoryImpl
import uz.gita.taskproductapi.data.request.EditRequest
import uz.gita.taskproductapi.utils.isConnected
import uz.gita.taskproductapi.viewmodel.EditProductViewModel

class EditProductViewModelImpl : ViewModel(), EditProductViewModel {
    private val repository = ProductRepositoryImpl()

    override val editLiveData = MutableLiveData<Unit>()
    override val openMainFragmentLiveData = MutableLiveData<Unit>()
    override val errorMessageLiveData = MutableLiveData<String>()
    override val progressLiveData = MutableLiveData<Boolean>()
    override val connectionLiveData = MutableLiveData<String>()
    override fun editData(data: EditRequest) {

        if (!isConnected()) {
            connectionLiveData.value = "The Internet is not available"
            return
        }
        progressLiveData.value = true
        repository.editData(data).onEach {
            progressLiveData.value = false
            it.onSuccess {
                editLiveData.value = Unit
            }

            it.onFailure { throwable ->
                errorMessageLiveData.value = throwable.message
            }

        }.catch { throwable ->
            errorMessageLiveData.value = throwable.message
        }.launchIn(viewModelScope)

    }

}

