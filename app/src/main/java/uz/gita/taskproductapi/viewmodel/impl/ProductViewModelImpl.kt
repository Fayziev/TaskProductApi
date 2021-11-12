package uz.gita.taskproductapi.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.taskproductapi.data.repository.impl.ProductRepositoryImpl
import uz.gita.taskproductapi.data.request.EditRequest
import uz.gita.taskproductapi.data.request.InsertRequest
import uz.gita.taskproductapi.data.response.Product
import uz.gita.taskproductapi.utils.isConnected
import uz.gita.taskproductapi.viewmodel.ProductViewModel

class ProductViewModelImpl : ViewModel(), ProductViewModel {
    private val repository = ProductRepositoryImpl()

    override val errorMessageLiveData = MutableLiveData<String>()
    override val progressLiveData = MutableLiveData<Boolean>()
    override val connectionLiveData = MutableLiveData<String>()
    override val allDataLiveData = MutableLiveData<List<Product>>()
    override val deleteLiveData = MutableLiveData<Unit>()
    override val editLiveData = MutableLiveData<Unit>()
    override val insertLiveData = MutableLiveData<Unit>()

    override fun getAllData() {

        if (!isConnected()) {
            connectionLiveData.value = "The Internet is not available"
            return
        }

        progressLiveData.value = true
        repository.getAllData().onEach {
            progressLiveData.value = false
            it.onSuccess { list ->
                allDataLiveData.value = list
            }
            it.onFailure { throwable ->
                errorMessageLiveData.value = throwable.message
            }
        }.catch { throwable ->
            errorMessageLiveData.value = throwable.message
        }.launchIn(viewModelScope)
    }

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

    override fun delete(id: Int) {

        if (!isConnected()) {
            connectionLiveData.value = "The Internet is not available"
            return
        }

        progressLiveData.value = true
        repository.deleteData(id).onEach {
            progressLiveData.value = false
            it.onSuccess {
                deleteLiveData.value = Unit
            }
            it.onFailure { throwable ->
                errorMessageLiveData.value = throwable.message
            }
        }.catch { throwable ->
            errorMessageLiveData.value = throwable.message
        }.launchIn(viewModelScope)

    }

    override fun insertData(data: InsertRequest) {
        if (!isConnected()) {
            connectionLiveData.value = "The Internet is not available"
            return
        }
        progressLiveData.value = true
        repository.insertData(data).onEach {
            progressLiveData.value = false
            it.onSuccess {
                insertLiveData.value = Unit
            }
            it.onFailure { throwable ->
                errorMessageLiveData.value = throwable.message
            }
        }.catch { throwable ->
            errorMessageLiveData.value = throwable.message
        }.launchIn(viewModelScope)
    }
}

