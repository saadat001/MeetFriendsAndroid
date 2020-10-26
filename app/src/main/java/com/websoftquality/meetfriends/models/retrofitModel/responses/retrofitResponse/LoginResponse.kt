package com.websoftquality.meetfriends.models.retrofitModel.responses.retrofitResponse

import android.os.Parcel
import android.os.Parcelable

data class LoginResponse(val status_code: String, val status_message: String,val user_id: String,val first_name: String,val last_name: String,val email_id: String,
                         val user_image_url: String,val age: String,val work: String, val access_token: String): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status_code)
        parcel.writeString(status_message)
        parcel.writeString(user_id)
        parcel.writeString(first_name)
        parcel.writeString(last_name)
        parcel.writeString(email_id)
        parcel.writeString(user_image_url)
        parcel.writeString(age)
        parcel.writeString(work)
        parcel.writeString(access_token)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginResponse> {
        override fun createFromParcel(parcel: Parcel): LoginResponse {
            return LoginResponse(parcel)
        }

        override fun newArray(size: Int): Array<LoginResponse?> {
            return arrayOfNulls(size)
        }
    }
}
