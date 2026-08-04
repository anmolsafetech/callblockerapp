package com.example.callblocker

import android.telecom.Call
import android.telecom.CallScreeningService
import android.util.Log

class CallBlockerService : CallScreeningService() {

    override fun onScreenCall(callDetails: Call.Details) {
        if (callDetails.callDirection == Call.Details.DIRECTION_INCOMING) {
            val phoneNumber = callDetails.handle?.schemeSpecificPart ?: ""
            Log.d("CallBlocker", "Incoming call from: $phoneNumber")

            val cleanNumber = phoneNumber.replace(" ", "").replace("-", "")
            
            // Checking +917313, 917313, or 0917313 just in case format varies
            val shouldBlock = cleanNumber.startsWith("+917313") || 
                              cleanNumber.startsWith("917313") || 
                              cleanNumber.startsWith("0917313")

            if (shouldBlock) {
                Log.d("CallBlocker", "Blocking call from: $phoneNumber")
                val response = CallResponse.Builder()
                    .setDisallowCall(true)
                    .setRejectCall(true)
                    .setSkipCallLog(false)
                    .setSkipNotification(true)
                    .build()
                respondToCall(callDetails, response)
            } else {
                val response = CallResponse.Builder().build()
                respondToCall(callDetails, response)
            }
        }
    }
}
