package com.nazirjon.testtaskapp.viewModel

import androidx.lifecycle.*
import com.nazirjon.testtaskapp.repository.Repository
import com.nazirjon.testtaskapp.network.pojo.Payment
import com.nazirjon.testtaskapp.shpreference.SharedPreference
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PaymentsViewModel(private val repository: Repository, private val sharedPreference: SharedPreference) : ViewModel() {

    val payments = MutableLiveData<List<Payment>?>(null)
    val isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    fun getPayments(token: String) = viewModelScope.launch {
        isLoading.value = true
        payments.value = repository.payments(token)
        isLoading.value = false
    }

    fun logout() {
        sharedPreference.deleteToken()
    }
}

class PaymentsViewModelFactory(private val repository: Repository, private val sharedPreference: SharedPreference): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaymentsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PaymentsViewModel(repository, sharedPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}