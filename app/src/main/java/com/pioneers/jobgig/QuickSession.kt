package com.pioneers.jobgig

import com.google.firebase.Timestamp

data class UserMessage(var profilePic:String, var msg:String, var timestamp: Timestamp, var accept:Boolean?)
data class QuickSession(var consumer:UserMessage, var producer:UserMessage, var price:Int?, var paymentType:PaymentType?)
enum class PaymentType{
    TRANSFER,
    CASH,
    DYNAMIC,
    MOBILE
}
