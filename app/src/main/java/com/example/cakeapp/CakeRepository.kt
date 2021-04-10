package com.example.cakeapp

import com.example.cakeapp.models.LoginModel
import com.example.cakeapp.models.LoginResponseModel
import com.example.cakeapp.models.SignUpModel
import com.example.cakeapp.models.SignUpResponseModel
import com.example.cakeapp.service.CakeService
import com.example.cakeapp.service.ServiceFactory
import java.lang.Exception

class CakeRepository {

    private var cakeService: CakeService

    init {
        val serviceFactory = ServiceFactory()
        cakeService = serviceFactory.getInstanceCakeService()
    }

    suspend fun login(loginModel: LoginModel): LoginResponseModel {
        val response = cakeService.login(loginModel)

        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception(response.message())
        }
    }

    suspend fun signUp(signUpModel: SignUpModel): SignUpResponseModel {
        val response = cakeService.signUp(signUpModel)

        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception(response.message())
        }
    }
}