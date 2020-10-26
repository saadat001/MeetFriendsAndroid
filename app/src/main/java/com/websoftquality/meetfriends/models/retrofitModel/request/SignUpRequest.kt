package com.websoftquality.meetfriends.models.retrofitModel.request

import android.os.Parcel
import android.os.Parcelable

data class SignUpRequest(val auth_type: String,val user_name: String,val first_name: String,val last_name: String,
                         val password: String,val gender: String,val dob: String):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(auth_type)
        parcel.writeString(user_name)
        parcel.writeString(first_name)
        parcel.writeString(last_name)
        parcel.writeString(password)
        parcel.writeString(gender)
        parcel.writeString(dob)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SignUpRequest> {
        override fun createFromParcel(parcel: Parcel): SignUpRequest {
            return SignUpRequest(parcel)
        }

        override fun newArray(size: Int): Array<SignUpRequest?> {
            return arrayOfNulls(size)
        }
    }
}

data class ValidationDataRequest(val user_name : String, val country_code : String, val first_name: String)

