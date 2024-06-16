package com.signify.app.data.auth

import com.signify.app.data.basemodel.ApiStatus
import com.signify.app.data.service.SignifyService
import com.signify.app.utils.PreferenceManager
import com.signify.app.utils.koinModules
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

interface AuthRepository {
    fun login(user: LoginRequest): Flow<ApiStatus<LoginResponse>>
    fun register(user: RegisterRequest): Flow<ApiStatus<RegisterResponse>>
    fun logout(): Boolean
}

class AuthRepositoryImpl(
    private val api: SignifyService, private val prefs: PreferenceManager
) : AuthRepository {

    override fun login(user: LoginRequest): Flow<ApiStatus<LoginResponse>> =
        flow {
            try {
                emit(ApiStatus.Loading)
                val response = api.login(user)
                prefs.setLoginPref(response)

                reloadKoinModules()
                emit(ApiStatus.Success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiStatus.Error(e.message.toString()))
            }
        }

    override fun register(user: RegisterRequest): Flow<ApiStatus<RegisterResponse>> =
        flow {
            try {
                emit(ApiStatus.Loading)
                val response = api.register(user)
                emit(ApiStatus.Success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiStatus.Error(e.message.toString()))
            }
        }

    override fun logout(): Boolean {
        return try {
            prefs.clearAllPreferences()
            reloadKoinModules()

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun reloadKoinModules() {
        unloadKoinModules(koinModules)
        loadKoinModules(koinModules)
    }

    companion object {
        const val TAG = "AuthRepo"
    }
}
