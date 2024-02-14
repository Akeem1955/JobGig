package com.pioneers.jobgig

import com.google.firebase.Timestamp
import com.pioneers.jobgig.dataobj.utils.Comment
import com.pioneers.jobgig.dataobj.utils.LatLngs
import com.pioneers.jobgig.screens.Agreement
import com.pioneers.jobgig.screens.ClientType
import com.pioneers.jobgig.screens.TransactionStage
import org.checkerframework.checker.units.qual.s
import java.util.UUID
import javax.crypto.KeyAgreement

data class UserMessage(var profilePic:String = "",var msg:String = "", var timestamp: Timestamp = Timestamp.now(), var uuid:String ="")
data class QuickSession(var profilePic:String="",
                        var initID:String = "",
                        var initComments:List<Comment> = emptyList(),
                        var initRating:Double = 0.0,
                        var fullname:String="",
                        var completed:Boolean = false,
                        var payed:Boolean = false,
                        var initiated:Boolean = false,
                        var transactionMsg:List<UserMessage> = emptyList(),
                        var prices:HashMap<String,Double> = hashMapOf(ClientType.Client.name to 0.0, ClientType.Worker.name to 0.0),
                        var agreement: HashMap<String,String> = hashMapOf(ClientType.Worker.name to Agreement.Init.name, ClientType.Client.name to Agreement.Init.name))
enum class PaymentType{
    TRANSFER,
    CASH,
    DYNAMIC,
    MOBILE
}