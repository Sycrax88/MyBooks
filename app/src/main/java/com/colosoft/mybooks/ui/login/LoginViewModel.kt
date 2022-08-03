package com.colosoft.mybooks.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.colosoft.mybooks.data.ResourceRemote
import com.colosoft.mybooks.data.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private val _errorMsg: MutableLiveData<String?> = MutableLiveData()
    val errorMsg: LiveData<String?> = _errorMsg

    private val _loginSuccess: MutableLiveData<String?> = MutableLiveData()
    val loginSuccess: LiveData<String?> = _loginSuccess

    fun validateFields(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty())
            _errorMsg.value = "Debe digitar todos los campos"
        else
            if (password.length < 6)
                _errorMsg.value = "La contraseña debe tener al menos 6 caracteres"
            else
                GlobalScope.launch(Dispatchers.IO) {
                    val result = userRepository.loginUser(email, password)
                    result.let { resourceRemote ->
                        when (resourceRemote){
                            is ResourceRemote.Success -> {
                                _loginSuccess.postValue(result.data)
                                _errorMsg.postValue("Inicio de Sesión Exitoso.")
                            }
                            is ResourceRemote.Error -> {
                                var msg = result.message
                                when (result.message) {
                                    "The email address is badly formatted." -> msg = "El email está mal escrito."
                                    "A network error (suck as timeout, interrupted connection or unreachable host) has occurred." -> msg = "Revise su conexión de Internet."
                                    "There is no user record corresponding to this identifier. The user may have been deleted." -> msg = "No existe una cuenta con ese correo electrónico."
                                    "The password is invalid or the user does not have a password." -> msg = "La contraseña es inválida."
                                }
                                _errorMsg.postValue(msg)
                            }
                        }
                    }
                }

    }
    // TODO: Implement the ViewModel
}