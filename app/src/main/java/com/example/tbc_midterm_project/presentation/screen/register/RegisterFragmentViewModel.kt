package com.example.tbc_midterm_project.presentation.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_midterm_project.data.common.Resource
import com.example.tbc_midterm_project.domain.use_case.authentication.RegisterUserUseCase
import com.example.tbc_midterm_project.presentation.event.login.LoginEvents
import com.example.tbc_midterm_project.presentation.event.register.RegisterEvents
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
class RegisterFragmentViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _registerState = MutableStateFlow(AuthenticationState())
    val registerState: SharedFlow<AuthenticationState> = _registerState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<NavigationEvents>()
    val uiEvent: SharedFlow<NavigationEvents> get() = _uiEvent

    fun onEvent(event: RegisterEvents) {
        when (event) {
            is RegisterEvents.RegisterPressed -> {
                _registerState.update { currentState ->
                    currentState.copy(
                        user = UserPres(
                            event.email,
                            event.password
                        )
                    )
                }
                register(
                    event.email,
                    event.password,
                    event.repeatPassword
                )
            }

            is RegisterEvents.AlreadyAccountPressed -> handleAlreadyAccount()
            is RegisterEvents.ResetError -> updateError(null)
        }
    }

    private fun handleAlreadyAccount() {
        viewModelScope.launch {
            _uiEvent.emit(NavigationEvents.NavigateToLogin(null, null))
        }
    }

    private fun register(email: String, password: String, repeatPassword: String) {
        viewModelScope.launch {
            registerUserUseCase(UserPres(email, password).toDomain(), repeatPassword).collect {
                handleResult(it)
            }
        }
    }

    private fun handleResult(result: Resource<Boolean>) {
        viewModelScope.launch {
            when (result) {
                is Resource.Success -> _uiEvent.emit(
                    NavigationEvents.NavigateToLogin(
                        _registerState.value.user.email,
                        _registerState.value.user.password
                    )
                )

                is Resource.Loading -> _registerState.update { currentState ->
                    currentState.copy(
                        isLoading = result.loading
                    )
                }

                is Resource.Error -> updateError(result.error)
            }
        }
    }

    private fun updateError(error: String?) {
        _registerState.update { currentState -> currentState.copy(errorMessage = error) }
    }

    sealed class NavigationEvents {
        data class NavigateToLogin(val email: String?, val password: String?) : NavigationEvents()
    }
}