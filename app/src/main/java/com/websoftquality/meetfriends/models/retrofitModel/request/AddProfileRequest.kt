package com.websoftquality.meetfriends.models.retrofitModel.request

data class AddProfileRequest (val HomeTown: String,val Education: String,
                                    val UserWork: String,val RelationShipStatus: String,val Hobbies: String,
                                    val UserId: String)