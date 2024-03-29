package com.example.tbc_midterm_project.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_midterm_project.data.common.Resource
import com.example.tbc_midterm_project.domain.use_case.authentication.LoginUserUseCase
import com.example.tbc_midterm_project.domain.use_case.datastore.SaveStateUseCase
import com.example.tbc_midterm_project.presentation.event.login.LoginEvents
import com.example.tbc_midterm_project.presentation.mapper.toDomain
import com.example.tbc_midterm_project.presentation.model.UserPres
import com.example.tbc_midterm_project.presentation.state.authentication.AuthenticationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val saveStateUseCase: SaveStateUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow(AuthenticationState())
    val loginState: SharedFlow<AuthenticationState> = _loginState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<NavigationEvents>()
    val uiEvent: SharedFlow<NavigationEvents> get() = _uiEvent

    fun onEvent(event: LoginEvents) {
        viewModelScope.launch {
            when (event) {
                is LoginEvents.LoginPressed -> login(
                    email = event.email,
                    password = event.password,
                    remember = event.remember
                )

                is LoginEvents.NoAccountPressed -> _uiEvent.emit(NavigationEvents.NavigateToRegister)
                is LoginEvents.ContinueOfflinePressed -> _uiEvent.emit(NavigationEvents.NavigateToOfflineHome)
                is LoginEvents.ResetError -> updateError(null)
            }
        }
    }

    private fun login(email: String, password: String, remember: Boolean) {
        viewModelScope.launch {
            loginUserUseCase(UserPres(email, password).toDomain()).collect {
                handleResult(it, remember)
            }
        }
    }

    private fun handleResult(result: Resource<Boolean>, remember: Boolean) {
        viewModelScope.launch {
            when (result) {
                is Resource.Success -> logIn(remember)
                is Resource.Loading -> _loginState.update { currentState ->
                    currentState.copy(
                        isLoading = result.loading
                    )
                }
                is Resource.Error -> updateError(result.error)
            }
        }
    }

    private fun logIn(remember: Boolean){
        viewModelScope.launch {
            if (remember){
                saveStateUseCase(remember)
            }
            _uiEvent.emit(NavigationEvents.NavigateToOnlineHome)
        }
    }

    private fun updateError(error: String?) {
        _loginState.update { currentState -> currentState.copy(errorMessage = error) }
    }

    sealed class NavigationEvents {
        data object NavigateToOfflineHome : NavigationEvents()
        data object NavigateToOnlineHome : NavigationEvents()
        data object NavigateToRegister : NavigationEvents()
    }
}