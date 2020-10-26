package com.websoftquality.meetfriends.models.retrofitModel.responses.retrofitResponse

data class AddProfileResponse(val StatusCode: Int, val Message: String, val Data: DataLoginEdit)
data class DataLoginEdit(val UserId: String,val FirstName: String,val LastName: String,val EmailAddress: String,
                     val PhoneNumber: String,val DateOfBirth: String,val Gender: String,
                     val LoginByGoogle: String,
                     val HomeTown: String,val RelationShipStatus: String,val UserWork: String,
                     val Education: String,val Hobbies: String,val LoginFirstTime: String,val UserImage: String,
                     val TokenURL: String, val IsProfileUpdated: String, val CoverImage: String)