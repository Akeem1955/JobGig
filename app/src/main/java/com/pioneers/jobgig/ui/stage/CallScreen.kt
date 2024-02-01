package com.pioneers.jobgig.ui.stage

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pioneers.jobgig.R
import com.pioneers.jobgig.services.communicate.WebRTCSessionState
import com.pioneers.jobgig.services.communicate.session.LocalWebRtcSessionManager
import com.pioneers.jobgig.ui.component.VideoRenderer




@Composable
fun VideoCallScreen() {
    val sessionManager = LocalWebRtcSessionManager.current

    LaunchedEffect(key1 = Unit) {
        println("We Got Here Launched effect")
        sessionManager.onSessionScreenReady()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        var parentSize: IntSize by remember { mutableStateOf(IntSize(0, 0)) }

        val remoteVideoTrackState by sessionManager.remoteVideoTrackFlow.collectAsState(null)
        val remoteVideoTrack = remoteVideoTrackState

        val localVideoTrackState by sessionManager.localVideoTrackFlow.collectAsState(null)
        val localVideoTrack = localVideoTrackState

        var callMediaState by remember { mutableStateOf(CallMediaState()) }

        if (remoteVideoTrack != null) {
            println("Remote track not null")
            VideoRenderer(
                videoTrack = remoteVideoTrack,
                modifier = Modifier
                    .fillMaxSize()
                    .onSizeChanged { parentSize = it }
            )
        }else{
            println("Null remote track")
        }

        if (localVideoTrack != null && callMediaState.isCameraEnabled) {
            println("Local video is not null now haa....")
            FloatingVideoRenderer(
                modifier = Modifier
                    .size(width = 150.dp, height = 250.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .align(Alignment.BottomEnd).padding(end = 8.dp, bottom = 80.dp),
                videoTrack = localVideoTrack,
                parentBounds = parentSize,
                paddingValues = PaddingValues(0.dp)
            )
        }
        else{

            println("${callMediaState.isCameraEnabled} Local video is null ooooooo now haa....")
        }

        val activity = (LocalContext.current as? Activity)

        VideoCallControls(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            callMediaState = callMediaState,
            onCallAction = {
                when (it) {
                    is CallAction.ToggleMicroPhone -> {
                        val enabled = callMediaState.isMicrophoneEnabled.not()
                        callMediaState = callMediaState.copy(isMicrophoneEnabled = enabled)
                        sessionManager.enableMicrophone(enabled)
                    }
                    is CallAction.ToggleCamera -> {
                        val enabled = callMediaState.isCameraEnabled.not()
                        callMediaState = callMediaState.copy(isCameraEnabled = enabled)
                        sessionManager.enableCamera(enabled)
                    }
                    CallAction.FlipCamera -> sessionManager.flipCamera()
                    CallAction.LeaveCall -> {
                        sessionManager.disconnect()
                        activity?.finish()
                    }
                }
            }
        )
    }
}





@Composable
fun StageScreen(
    state: WebRTCSessionState,
    onJoinCall: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        var enabledCall by remember { mutableStateOf(false) }

        val text = when (state) {
            WebRTCSessionState.Offline -> {
                enabledCall = false
                stringResource(id = R.string.button_start_session)
            }
            WebRTCSessionState.Impossible -> {
                enabledCall = false
                stringResource(id = R.string.session_impossible)
            }
            WebRTCSessionState.Ready -> {
                enabledCall = true
                stringResource(id = R.string.session_ready)
            }
            WebRTCSessionState.Creating -> {
                enabledCall = true
                stringResource(id = R.string.session_creating)
            }
            WebRTCSessionState.Active -> {
                enabledCall = false
                stringResource(id = R.string.session_active)
            }
        }

        Button(
            modifier = Modifier.align(Alignment.Center),
            enabled = enabledCall,
            onClick = { onJoinCall.invoke() }
        ) {
            Text(
                text = text,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}