package com.pioneers.jobgig.screens

import android.Manifest
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.pioneers.jobgig.R
import com.pioneers.jobgig.services.communicate.SignalingClient
import com.pioneers.jobgig.services.communicate.StreamPeerConnectionFactory
import com.pioneers.jobgig.services.communicate.session.LocalWebRtcSessionManager
import com.pioneers.jobgig.services.communicate.session.WebRtcSessionManager
import com.pioneers.jobgig.services.communicate.session.WebRtcSessionManagerImpl
import com.pioneers.jobgig.ui.stage.StageScreen
import com.pioneers.jobgig.ui.stage.VideoCallScreen
import com.pioneers.jobgig.ui.theme.JobGigTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable

fun CallScreenManager(){
    val context = LocalContext.current
    val permissions = rememberMultiplePermissionsState(permissions = listOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA,Manifest.permission.ACCESS_NETWORK_STATE) )

    val sessionManager: WebRtcSessionManager = remember{
        WebRtcSessionManagerImpl(
            context = context,
            signalingClient = SignalingClient(),
            peerConnectionFactory = StreamPeerConnectionFactory(context))
    }
    CompositionLocalProvider(LocalWebRtcSessionManager provides sessionManager) {
        if (permissions.allPermissionsGranted){
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
                color = MaterialTheme.colorScheme.background
            ) {
                var onCallScreen by remember { mutableStateOf(false) }
                val state by sessionManager.signalingClient.sessionStateFlow.collectAsStateWithLifecycle()

                if (!onCallScreen) {
                    StageScreen(state = state) { onCallScreen = true }
                } else {
                    VideoCallScreen()
                }
            }
        }else{
            DisplayRationale(permisionState = permissions, rationale = listOf(
                stringResource(id = R.string.perm),
                stringResource(id = R.string.perm),
                stringResource(id = R.string.perm)
            ))
        }

    }
}








@OptIn(ExperimentalPermissionsApi::class)
@Preview(showBackground = true)
@Composable
fun CallPreview(){
    JobGigTheme {
        CallScreenManager()
    }
}