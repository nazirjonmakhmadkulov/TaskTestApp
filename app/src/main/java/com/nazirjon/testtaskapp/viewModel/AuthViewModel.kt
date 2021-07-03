package com.nazirjon.testtaskapp.viewModel

import androidx.lifecycle.*
import com.nazirjon.testtaskapp.event.Event
import com.nazirjon.testtaskapp.repository.Repository
import com.nazirjon.testtaskapp.shpreference.SharedPreference
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: Repository,
                    private val sharedPreference: SharedPreference,) : ViewModel() {

    private val _token = MutableLiveData<Event<String?>>(null)
    val token: LiveData<Event<String?>>
        get() = _token

    private val _showSnackBar = MutableLiveData<Event<String>>()
    val showSnackBar: LiveData<Event<String>>
        get() = _showSnackBar

    fun showWrongUserData(mes:String) {
        _showSnackBar.value = Event(mes)
    }

    fun signIn(login: String?, password: String?) = viewModelScope.launch {
        if (login != null && password != null && login.isNotEmpty() && password.isNotEmpty()) {
            val loginResult = repository.signIn(login, password)
            if (loginResult != null) {
                _token.value = Event(loginResult)
            }
            else {
                showWrongUserData(WRONG_USER_DATA)
            }
        }
        else {
            showWrongUserData(EMPTY_USER_DATA)
        }
    }

    fun getToken() {
        _token.value = Event(sharedPreference.getToken())

    }

    companion object {
        private const val WRONG_USER_DATA = "Неверный логин или пароль"
        private const val EMPTY_USER_DATA = "Поля логин или пароль пустой"
    }
}

class AuthViewModelFactory(private val repository: Repository,
                           private val sharedPreference: SharedPreference,) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repository, sharedPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}