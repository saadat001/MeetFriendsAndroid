package com.websoftquality.meetfriends.models.retrofitModel.responses.retrofitResponse

data class SuggestFriendListData(val StatusCode: String,val Message: String, val Data: ArrayList<DataListFriend>)
data class DataListFriend(val FullName: String,val UserImage: String, val UserId: String, val TotalFriends: String)