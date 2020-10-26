package com.websoftquality.meetfriends.models.retrofitModel.responses.retrofitResponse

data class ForgetResponse(val status_message:String, val status_code:String,val otp:String,val type:String)
data class ResetPasswordResponse(val status_message:String, val status_code:String)