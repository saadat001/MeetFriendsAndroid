package com.websoftquality.meetfriends.models.retrofitModel.responses.retrofitResponse

import android.os.Parcel
import android.os.Parcelable

data class HomePostResponse(val StatusCode:String,val Message:String,val Data: ArrayList<DataList>)

data class DataList(val PostID: String, val UserID: String, val UserName: String, val UserProfilePhoto: String, val PostTitle: String,
                    val PostDescription: String, val PostUploadedTime: String, var PostLikeStatus: String,
                    val PostComment: ArrayList<PostCommentList>, val PostAttachment: ArrayList<PostAttachmentList> ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        TODO("PostComment"),
        TODO("PostAttachment")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(PostID)
        parcel.writeString(UserID)
        parcel.writeString(UserName)
        parcel.writeString(UserProfilePhoto)
        parcel.writeString(PostTitle)
        parcel.writeString(PostDescription)
        parcel.writeString(PostUploadedTime)
        parcel.writeString(PostLikeStatus)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataList> {
        override fun createFromParcel(parcel: Parcel): DataList {
            return DataList(parcel)
        }

        override fun newArray(size: Int): Array<DataList?> {
            return arrayOfNulls(size)
        }
    }
}

data class PostCommentList(val PostCommentID: String,val PostCommentUserID: String,val PostCommentUserName: String
                           ,val PostCommentUserImage: String, val PostComment: String,val PostCommentDateTime:String): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(PostCommentID)
        parcel.writeString(PostCommentUserID)
        parcel.writeString(PostCommentUserName)
        parcel.writeString(PostCommentUserImage)
        parcel.writeString(PostComment)
        parcel.writeString(PostCommentDateTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostCommentList> {
        override fun createFromParcel(parcel: Parcel): PostCommentList {
            return PostCommentList(parcel)
        }

        override fun newArray(size: Int): Array<PostCommentList?> {
            return arrayOfNulls(size)
        }
    }

}

data class PostAttachmentList(val PostUrl: String)