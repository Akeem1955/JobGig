package com.pioneers.jobgig.ui.stage


import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.Disabled
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.pioneers.jobgig.R
import com.pioneers.jobgig.screens.items
import com.pioneers.jobgig.ui.component.VideoRenderer
import org.webrtc.VideoTrack

/**
 * Represents a floating item used to feature a participant video, usually the local participant.
 *
 * @param parentBounds Bounds of the parent, used to constrain the component to the parent bounds,
 * when dragging the floating UI around the screen.
 * @param modifier Modifier for styling.
 */




data class CallMediaState(
    val isMicrophoneEnabled: Boolean = true,
    val isCameraEnabled: Boolean = true
)




@Composable
fun FloatingVideoRenderer(
    videoTrack: VideoTrack,
    parentBounds: IntSize,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        modifier = modifier,
        shape = RoundedCornerShape(16.dp)
    ) {
        VideoRenderer(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            videoTrack = videoTrack
        )
    }
}

private fun calculateHorizontalOffsetBounds(
    parentBounds: IntSize,
    paddingValues: PaddingValues,
    floatingVideoSize: IntSize,
    density: Density,
    offset: Float
): Float {
    val rightPadding =
        density.run { paddingValues.calculateRightPadding(LayoutDirection.Ltr).toPx() }

    return parentBounds.width - rightPadding - floatingVideoSize.width - offset
}

private fun calculateVerticalOffsetBounds(
    parentBounds: IntSize,
    paddingValues: PaddingValues,
    floatingVideoSize: IntSize,
    density: Density,
    offset: Float
): Float {
    val bottomPadding =
        density.run { paddingValues.calculateBottomPadding().toPx() }

    return parentBounds.height - bottomPadding - floatingVideoSize.height - offset
}









sealed class CallAction {
    data class ToggleMicroPhone(
        val isEnabled: Boolean
    ) : CallAction()

    data class ToggleCamera(
        val isEnabled: Boolean
    ) : CallAction()

    object FlipCamera : CallAction()

    object LeaveCall : CallAction()
}

data class VideoCallControlAction(
    val icon: Painter,
    val iconTint: Color,
    val background: Color,
    val callAction: CallAction
)

@Composable
fun buildDefaultCallControlActions(
    callMediaState: CallMediaState
): List<VideoCallControlAction> {
    val microphoneIcon =
        painterResource(
            id = if (callMediaState.isMicrophoneEnabled) {
                R.drawable.baseline_mic_24

            } else {
                R.drawable.baseline_mic_off_24
            }
        )

    val cameraIcon = painterResource(
        id = if (callMediaState.isCameraEnabled) {
            R.drawable.baseline_videocam_24
        } else {
            R.drawable.baseline_videocam_off_24
        }
    )

    return listOf(
        VideoCallControlAction(
            icon = microphoneIcon,
            iconTint = Color.White,
            background = MaterialTheme.colorScheme.primary,
            callAction = CallAction.ToggleMicroPhone(callMediaState.isMicrophoneEnabled)
        ),
        VideoCallControlAction(
            icon = cameraIcon,
            iconTint = Color.White,
            background = MaterialTheme.colorScheme.primary,
            callAction = CallAction.ToggleCamera(callMediaState.isCameraEnabled)
        ),
        VideoCallControlAction(
            icon = painterResource(id = R.drawable.baseline_flip_camera_ios_24),
            iconTint = Color.White,
            background = MaterialTheme.colorScheme.primary,
            callAction = CallAction.FlipCamera
        ),
        VideoCallControlAction(
            icon = painterResource(id = R.drawable.baseline_call_end_24),
            iconTint = Color.White,
            background = MaterialTheme.colorScheme.error,
            callAction = CallAction.LeaveCall
        )
    )
}





@Composable
fun VideoCallControls(
    modifier: Modifier,
    callMediaState: CallMediaState,
    actions: List<VideoCallControlAction> = buildDefaultCallControlActions(callMediaState = callMediaState),
    onCallAction: (CallAction) -> Unit
) {
    LazyRow(
        modifier = modifier.padding(bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(actions) { action ->
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(action.background)
            ) {
                Icon(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.Center)
                        .clickable { onCallAction(action.callAction) },
                    tint = action.iconTint,
                    painter = action.icon,
                    contentDescription = null
                )
            }
        }
    }
}