package com.websoftquality.meetfriends.models.retrofitModel.request

data class HomeList(val image: Int,var isLiked: Boolean){
    fun setLikedValue(value: Boolean){
        isLiked = value
    }

    fun getLiked(): Boolean{
        return isLiked
    }
}