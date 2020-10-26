package com.websoftquality.meetfriends.models.retrofitModel.responses.retrofitResponse

data class SignUpResponse(val status_code: String,val status_message: String,val access_token: String)

data class SignUpDataList(val DateOfBirth: String,val Education: String,val EmailAddress: String,val FirstName: String
,val Gender: String,val Hobbies: String,val HomeTown: String,val LastName: String,val LoginByGoogle: String,
                          val LoginFirstTime: String
                          ,val PhoneNumber: String
                          ,val RelationShipStatus: String
                          ,val token: String,val TokenURL: String,val UserId: String,val UserImage: String
                          ,val UserWork: String,val ConfirmUserID: String,val ConfirmGuid: String)

data class ValidationResponse(val status_message: String,val status_code: String,val otp: String,val type: String)