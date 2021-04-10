package com.example.cakeapp.service

import com.example.cakeapp.models.LoginModel
import com.example.cakeapp.models.LoginResponseModel
import com.example.cakeapp.models.SignUpModel
import com.example.cakeapp.models.SignUpResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CakeService {

    @POST("users/login")
    suspend fun login(@Body loginModel: LoginModel) : Response<LoginResponseModel>

    @POST("users/signup")
    suspend fun signUp(@Body signUpModel: SignUpModel) : Response<SignUpResponseModel>
}