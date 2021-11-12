package uz.gita.taskproductapi.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.taskproductapi.viewmodel.SplashScreenViewModel

class SplashViewModel: ViewModel(), SplashScreenViewModel {
    override val openMainFragmentViewModel = MutableLiveData<Unit>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(3000)
            openMainFragmentViewModel.postValue(Unit)
        }
    }
}