package com.base.mvvmproject.ui.main.viewmodle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.base.mvvmproject.data.repository.MainRepository
import com.base.mvvmproject.utils.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel (private val mainRepository: MainRepository) : ViewModel() {

    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getUsers()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}