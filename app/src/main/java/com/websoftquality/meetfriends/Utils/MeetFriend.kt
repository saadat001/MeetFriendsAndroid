//package com.websoftquality.meetfriends.utils
//
//import android.app.Application
//import com.facebook.drawee.backends.pipeline.Fresco
//import com.websoftq.meetfriends.modules.appModule
//import com.websoftq.meetfriends.modules.networkModule
//import com.websoftq.meetfriends.modules.viewModelModule
//import org.koin.android.ext.android.startKoin
//
//class MeetFriend : Application() {
//
//    override fun onCreate() {
//        super.onCreate()
//
//        meetFriend = this
//
//        Fresco.initialize(this)
//        val modules = listOf(appModule, networkModule, viewModelModule)
//        startKoin(this, modules)
//
//    }
//
//
//    companion object {
//        private var meetFriend: MeetFriend? = null
//        fun get () = MeetFriend()
//    }
//}