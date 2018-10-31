package com.tenxdevs.tenx.kotlintodo.databaseelements

import android.nfc.Tag
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

val TAG = "MessagingService"
class MessagingService : FirebaseMessagingService(){
    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
        if(p0?.data != null){
            Log.d(TAG, "Data : "+p0.data.toString())
        }
        if(p0?.notification != null){
            Log.d(TAG, "notification : "+p0.notification.toString())
        }

    }

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
        if(p0 != null){
            Log.d(TAG, "Token id : "+p0.toString())
        }
    }
}