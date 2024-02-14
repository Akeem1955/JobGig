package com.pioneers.jobgig.services


import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.pioneers.jobgig.MainActivity
import com.pioneers.jobgig.R
import com.pioneers.jobgig.dataobj.utils.User
import com.pioneers.jobgig.screens.onlineState
import com.pioneers.jobgig.viewmodels.OnBoardViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext

class JobSearch: Service() {
    private var staledAlert ="init"
    private var running = false
    //private var Ses
    private val db = Firebase.firestore
    private lateinit var managerCompat: NotificationManagerCompat
    private lateinit var channelCompat: NotificationChannelCompat
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val joblisterner = Firebase.firestore.collection("Users").document(OnBoardViewModel.currentUser.value.uid).addSnapshotListener { value, error ->
        println("Service Still Working")
        println(value?.getString("tester"))

        if(value != null && value.exists()){
            val user = value.toObject<User>()
            if (staledAlert == "init" && user != null){
                staledAlert = user.alert
            }
            else if(user != null && staledAlert != user.alert){
                println(user.alert)
                scope.launch {
                    try {
                        simulateJobrecieve(user.alert.replace("/","_"))
                        onlineState = false
                        db.collection("Users")
                            .document(Firebase.auth.currentUser?.uid ?: OnBoardViewModel.currentUser.value.uid).set(OnBoardViewModel.currentUser.value).await()
                        println(OnBoardViewModel.currentUser.value.online)
                        managerCompat.cancel(10023)
                        stopSelf()
                    }catch (e:Exception){
                        e.printStackTrace()
                        println(e.message)
                    }
                }

            }
        }
    }
    private val handler = CoroutineExceptionHandler { _, throwable ->
        println(throwable.message)
        throwable.printStackTrace()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && intent.action != null) {
           when(intent.action){
               "online_stop"->{
                   if(running){
                       scope.launch(handler) {
                           OnBoardViewModel.currentUser.value.online = false
                           onlineState = false
                           db.collection("Users")
                               .document(Firebase.auth.currentUser?.uid ?: OnBoardViewModel.currentUser.value.uid).set(OnBoardViewModel.currentUser.value).await()
                           println(OnBoardViewModel.currentUser.value.online)
                           managerCompat.cancel(10023)
                           stopSelf()
                       }
                   }
                   return START_NOT_STICKY
               }
               "reject_job"->{
                   managerCompat.cancel(1957)
                   return START_STICKY
               }
           }
        }
        startForeground(10023, createNotification())
        running = true
        return START_STICKY
    }

    @SuppressLint("MissingPermission")
    private fun simulateJobrecieve(path:String){

        val title = "You Got A Gig"
        val intent_stop = Intent(this, com.pioneers.jobgig.services.JobSearch::class.java)
        intent_stop.setAction("reject_job")
        val intent_accept = Intent(Intent.ACTION_VIEW,Uri.parse("jobgig://confirm-gig/$path"))
        //intent_accept.setData(Uri.parse("deep"))
        val intent =
            PendingIntent.getService(this, 1956, intent_stop, PendingIntent.FLAG_IMMUTABLE)
        val intentAccept =
            PendingIntent.getActivity(this,1958,intent_accept,PendingIntent.FLAG_IMMUTABLE)
        managerCompat.notify(1957,
            NotificationCompat.Builder(
            applicationContext,
            applicationContext.resources.getString(R.string.voc_find_chanel)
        )
            .setContentTitle(title)
            .setContentText("Note! if You cancel the notification Job Gig Won't be send to you!")
            .setTicker(title)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setOngoing(false) // Add the cancel action to the notification which can

            // be used to cancel the worker
            .addAction(R.drawable.round_done_24,"Accept",intentAccept)
            .setAutoCancel(true)
            .addAction(R.drawable.baseline_stop_24, "Reject", intent).build())
    }
    private fun createNotification(): Notification {
        val title = "Helping You Find A Gig"
        val intent_stop = Intent(this, com.pioneers.jobgig.services.JobSearch::class.java)
        intent_stop.setAction("online_stop")
        val intent =
            PendingIntent.getService(this, 1955, intent_stop, PendingIntent.FLAG_IMMUTABLE)
        return NotificationCompat.Builder(
            applicationContext,
            applicationContext.resources.getString(R.string.voc_find_chanel)
        )
            .setContentTitle(title)
            .setContentText("Note! if You cancel the notification Job Gig Won't be send to you!")
            .setTicker(title)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setOngoing(true) // Add the cancel action to the notification which can
            // be used to cancel the worker
            .addAction(R.drawable.baseline_stop_24, "Cancel", intent).build()
    }
    override fun onCreate() {
        super.onCreate()
        managerCompat = NotificationManagerCompat.from(applicationContext)
        channelCompat = NotificationChannelCompat.Builder(
            applicationContext
                .resources
                .getString(R.string.voc_find_chanel),
            NotificationManagerCompat.IMPORTANCE_HIGH
        )
            .setName("Finding A Gig...").build()
        managerCompat.createNotificationChannel(channelCompat)

    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        joblisterner.remove()
        scope.cancel(CancellationException("On Destroy not needed anymore!!!!"))
        println("Service is destroyed")
    }


}