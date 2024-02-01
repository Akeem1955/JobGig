package com.pioneers.jobgig.services.communicate

import com.google.firebase.Firebase
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import io.getstream.log.taggedLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID


data class SignalManager(var offer:HashMap<String,String> = hashMapOf(),
                         var answer:HashMap<String,String> = hashMapOf(),
                         var ice:HashMap<String,String> = hashMapOf(),
                         var state:String = WebRTCSessionState.Impossible.toString(),
                         var size:Int? = 0)




class SignalingClient {
    private var offerConsumed = ""
    private var answerConsumed = ""
    private var iceConsumed = ""
    private val db = Firebase.firestore
    private val uuid = UUID.randomUUID().toString()
    private val callSessions = db.collection("CallSessions").document("call_test")
    private val logger by taggedLogger("Call:SignalingClient")
    private val signalingScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val snapshotListener:ListenerRegistration = callSessions.addSnapshotListener { snapshot, error ->
        println("Snapshot listener")
        if (error != null) {
            error.printStackTrace()
            println(error.message)
            return@addSnapshotListener
        }
        if (snapshot != null && snapshot.exists()) {

            val signalManager = snapshot.toObject<SignalManager>() ?: return@addSnapshotListener
            if(signalManager.size != null){
                if(signalManager.size!! > 1 && signalManager.state != WebRTCSessionState.Ready.toString() && signalManager.state != WebRTCSessionState.Creating.toString() && signalManager.state != WebRTCSessionState.Active.toString()  ){
                    signalManager.state = WebRTCSessionState.Ready.toString()
                    callSessions.set(signalManager)
                }
            }
            println("Snapshot listener offer reached")
            if(!signalManager.offer.containsKey(uuid) && signalManager.offer.isNotEmpty()){
                println("Snapshot listener offer")
               for ((_,value) in signalManager.offer){
                   if(offerConsumed != value){
                       offerConsumed =value
                       signalManager.state = WebRTCSessionState.Creating.toString()
                       _sessionStateFlow.value = WebRTCSessionState.valueOf(signalManager.state)
                       callSessions.set(signalManager)
                      signalingScope.launch {
                          try {
                              callSessions.set(signalManager).await()
                          }catch (e:Exception){
                              e.printStackTrace()
                          }
                          _signalingCommandFlow.emit(SignalingCommand.OFFER to value) }
                   }
               }
           }
            println("Snapshot listener answer reached")
            if(!signalManager.answer.containsKey(uuid) && signalManager.answer.isNotEmpty()){
                println("Snapshot listener answer")
                for ((_,value)in signalManager.answer){
                    if (answerConsumed != value){
                        answerConsumed = value
                        signalManager.state = WebRTCSessionState.Active.toString()
                        _sessionStateFlow.value = WebRTCSessionState.valueOf(signalManager.state)
                        signalingScope.launch{
                           try {
                               callSessions.set(signalManager).await()
                           }catch (e:Exception){
                               e.printStackTrace()
                           }
                            _signalingCommandFlow.emit(SignalingCommand.ANSWER to value)}
                    }
                }
            }
            println("Snapshot listener ice reached")
            if(!signalManager.ice.containsKey(uuid) && signalManager.ice.isNotEmpty()){
                println("Snapshot listener ice")
                for ((_,value) in signalManager.ice){
                    if (iceConsumed != value){
                        iceConsumed = value
                        signalingScope.launch { _signalingCommandFlow.emit(SignalingCommand.ICE to value) }
                    }
                }
            }
            if (signalManager.state != _sessionStateFlow.value.name){
                _sessionStateFlow.value = WebRTCSessionState.valueOf(signalManager.state)
            }
        }
        else {
            println("Current data: null")
        }
    }
    init {
        signalingScope.launch {
            try {
                //callSessions.set(SignalManager(), SetOptions.merge()).await()
                db.runTransaction {transaction->
                    val snapshot =transaction.get(callSessions)
                    when(snapshot.getLong("size")){
                        0L ->{transaction.update(callSessions,"size",1)}
                        1L->{
                            transaction.update(callSessions,"size",2)
                        }

                        else -> Unit
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                println(e.message)
            }
        }
    }

    // session flow to send information about the session state to the subscribers
    private val _sessionStateFlow = MutableStateFlow(WebRTCSessionState.Offline)
    val sessionStateFlow: StateFlow<WebRTCSessionState> = _sessionStateFlow

    // signaling commands to send commands to value pairs to the subscribers
    private val _signalingCommandFlow = MutableSharedFlow<Pair<SignalingCommand, String>>()
    val signalingCommandFlow: SharedFlow<Pair<SignalingCommand, String>> = _signalingCommandFlow

    fun sendCommand(signalingCommand: SignalingCommand, message: String) {
        when(signalingCommand){
            SignalingCommand.STATE -> {
                handleState()
            }
            SignalingCommand.OFFER -> {
                offerConsumed = message
                handleOffer(message)
            }
            SignalingCommand.ANSWER -> {
                answerConsumed = message
                handleAnswer(message)
            }
            SignalingCommand.ICE -> {
                iceConsumed = message
                handleIce(message)
            }
        }
    }
    private fun handleState(){
        signalingScope.launch {
            try {
                val value = callSessions.get().await().getString("state")
                if (value != null){
                    _sessionStateFlow.value = WebRTCSessionState.valueOf(value)
                }
            }catch (e:Exception){
                e.printStackTrace()
                println(e.message)
            }
        }
    }
    private fun handleOffer(message: String){
        db.runTransaction {transaction->
            val snapshot =transaction.get(callSessions)
            if(snapshot.exists() && snapshot.getString("state") == "Ready"){
                transaction.update(callSessions,"offer", hashMapOf(uuid to message))
            }
        }

    }
    private fun handleAnswer(message: String){
        db.runTransaction {transaction->
            val snapshot =transaction.get(callSessions)
            if(snapshot.exists() && snapshot.getString("state") == "Creating"){
                transaction.update(callSessions,"answer", hashMapOf(uuid to message))
            }
        }
    }
    private fun handleIce(message: String){
        db.runTransaction {transaction->
            val snapshot =transaction.get(callSessions)
            transaction.update(callSessions,"ice", hashMapOf(uuid to message))
        }
    }




    fun dispose() {
        _sessionStateFlow.value = WebRTCSessionState.Offline
        signalingScope.cancel()
        snapshotListener.remove()
    }
}

enum class WebRTCSessionState {

    Active, // Offer and Answer messages has been sent
    Creating, // Creating session, offer has been sent
    Ready, // Both clients available and ready to initiate session
    Impossible, // We have less than two clients connected to the server
    Offline // unable to connect signaling server
}
enum class SignalingCommand {
    STATE, // Command for WebRTCSessionState
    OFFER, // to send or receive offer
    ANSWER, // to send or receive answer
    ICE // to send and receive ice candidates
}