package uz.gita.taskproductapi.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.taskproductapi.data.repository.impl.ProductRepositoryImpl
import uz.gita.taskproductapi.data.response.Product
import uz.gita.taskproductapi.utils.isConnected
import uz.gita.taskproductapi.viewmodel.PagingViewModel

class PagingViewModelImpl: ViewModel(), PagingViewModel {
    override val errorMessageLiveData = MutableLiveData<String>()
    override val progressLiveData = MutableLiveData<Boolean>()
    override val connectionLiveData = MutableLiveData<Boolean>()
    override val allDataLiveData = MutableLiveData<List<Product>>()
    override val insertLiveData = MutableLiveData<Unit>()
    override val editLiveData = MutableLiveData<Unit>()
    override val deleteLiveData = MutableLiveData<Unit>()

    private val repository=ProductRepositoryImpl()
    override fun getAllPaging(page: Int, perPage: Int) {
        if(!isConnected()){
            connectionLiveData.value=true
            return
        }
        progressLiveData.postValue(true)
        repository.getAllDataPaging(page, perPage).onEach {
            it.onSuccess { res->
                progressLiveData.postValue(false)
               allDataLiveData.postValue(res)
            }.onFailure { error->
                errorMessageLiveData.postValue(error.localizedMessage)
            }
        }.launchIn(viewModelScope)
    }
}