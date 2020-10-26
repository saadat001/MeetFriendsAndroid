package com.websoftquality.meetfriends.retrofit

import okhttp3.RequestBody

//const val BASE_URL = "http://staging.meetfreinds.com/api/"
const val BASE_URL = "https://websoftquality.com/meetfriends/public/api/"

fun createPartFromString(partString: String): RequestBody {
    return RequestBody.create(okhttp3.MultipartBody.FORM, partString)
}
const val REPORT_POST = "Post/ReportPost"
const val SAVE_POST = "Post/SavePost"
const val LIKE_POST = "Post/PostLike"
const val GET_POST= "user/GetUserPost"
const val GET_LOGIN = "loginnew"
const val GET_USER_VALIDATION = "usernamevalidation"
const val GET_CREATE_USER = "signupnew"
const val RESET_PASSWORD = "replacepassword"
const val TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRxYUBnbWFpbC5jb20iLCJ1bmlxdWVfbmFtZSI6ImEiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9zaWQiOiIxMDE0IiwibmJmIjoxNTk4MjAxNzY5LCJleHAiOjE1OTgyMDM1NjksImlhdCI6MTU5ODIwMTc2OX0.aNOqhLTxNDBJaq15rNYzLMIBI2pldWZTG-gGQnw4w4U"
const val SUBMIT_PROFILE = "User/UpdateUserProfile"
const val GET_FRIEND_LIST_SUGGESTION = "Post/GetFriendSuggesstion"
const val SEND_REQUEST = "Post/SaveUserFriendRequest"
const val SUBMIT_POST = "Post/CreatePost"
const val UPDATE_POST = "Post/UpdatePost"
const val UNLIKE_POST = "Post/PostDisLike"
const val GET_USER_POTS = "Post/GetUserPostList"
const val DELETE_POST = "Post/DeletePost"
const val GET_USER_PROFILE = "User/GetUserProfile"
const val FORGOT_PASSWORD = "forgotpassword"
const val POST_COMMENT = "Post/PostComment"