package com.websoftquality.meetfriends.retrofit

import com.websoftquality.meetfriends.models.retrofitModel.request.*
import com.websoftquality.meetfriends.models.retrofitModel.responses.retrofitResponse.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface API {

    @Multipart
    @POST(SUBMIT_PROFILE)
    fun addProfileData(@Part image: MultipartBody.Part,@Part coverImage: MultipartBody.Part, @PartMap data:
    Map<String, @JvmSuppressWildcards RequestBody>): Call<AddProfileResponse>

    @Multipart
    @POST(SUBMIT_POST)
    fun submitPost( @PartMap data:
                     Map<String, @JvmSuppressWildcards RequestBody>,@Part image: MultipartBody.Part): Call<PostDataResponse>
    @Multipart
    @POST(UPDATE_POST)
    fun upDatePost(@PartMap data:
                   Map<String, @JvmSuppressWildcards RequestBody>,@Part image: MultipartBody.Part): Call<PostDataResponse>
    @POST(REPORT_POST)
    fun reportData(@Body request: ReportRequest): Call<PostDataResponse>

    @POST(SAVE_POST)
    fun savePost(@Body request: SaveRequest): Call<PostDataResponse>

    @POST(LIKE_POST)
    fun likePost(@Body request: LikeRequest): Call<PostDataResponse>

    @POST(UNLIKE_POST)
    fun unlikePost(@Body request: LikeRequest): Call<PostDataResponse>

    //@Headers("Accept:Authorization", "Bearer: $TOKEN")
    @POST(GET_POST)
    fun getPosts(@Body homeRequest: HomeRequest): Call<HomePostResponse>

    @POST(GET_LOGIN)
    fun getLogin(@Body request: LoginRequest):  Call<LoginResponse>

    @POST(GET_CREATE_USER)
    fun createUser(@Body signUpRequest: SignUpRequest): Call<SignUpResponse>

    @POST(GET_FRIEND_LIST_SUGGESTION)
    fun getUserLIst(@Body homeRequest: UserListRequest): Call<SuggestFriendListData>

    @POST(SEND_REQUEST)
    fun SentRequest(@Body request: FriendRequest): Call<PostDataResponse>

    @POST(GET_USER_POTS)
    fun getUserPost(@Body homeRequest: HomeRequest):Call<HomePostResponse>

    @POST(DELETE_POST)
    fun DeletPost(@Body request: LikeRequest): Call<PostDataResponse>

    @Multipart
    @POST(SUBMIT_POST)
    fun updateProfileData(
        @Part imageBodayCover: MultipartBody.Part,
        @Part imageBody: MultipartBody.Part,
        @PartMap data:
        Map<String, @JvmSuppressWildcards RequestBody>
    ): Call<AddProfileResponse>

    @POST(GET_USER_PROFILE)
    fun getUserData(@Body homeRequest: GetPostRequest): Call<LoginResponse>

    @POST(FORGOT_PASSWORD)
    fun forgetPassword(@Body request: ForgotRequest): Call<ForgetResponse>

    @POST(POST_COMMENT)
    fun sendComment(@Body request: CommentRequest): Call<PostDataResponse>

    @Multipart
    @POST(SUBMIT_PROFILE)
    fun updateNewProfileData(
        @Part("HomeTown")homeTown: RequestBody,
        @Part("Education")education: RequestBody,
        @Part("UserWork")userWork: RequestBody,
        @Part("RelationShipStatus")relationShipStatus: RequestBody,
        @Part("Hobbies")hobbies: RequestBody,
        @Part("Gender")gender: RequestBody,
        @Part("FirstName")firstName: RequestBody,
        @Part("LastName")lastName: RequestBody,
        @Part("DateOfBirth")dateOfBirth: RequestBody,
        @Part("UserId")userId: RequestBody,
        @Part("File")requestFile1: RequestBody,
        @Part("CoverImage")requestFile2: RequestBody
    ): Call<AddProfileResponse>

    @POST(GET_USER_VALIDATION)
    fun validateUSer(@Body validate: ValidationDataRequest): Call<ValidationResponse>

    @POST(RESET_PASSWORD)
    fun resetPassword(@Body request: LoginRequest): Call<ResetPasswordResponse>

}