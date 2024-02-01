package com.pioneers.jobgig.services.communicate.utils

import com.pioneers.jobgig.services.communicate.StreamPeerType
import org.webrtc.AddIceObserver
import org.webrtc.IceCandidate
import org.webrtc.IceCandidateErrorEvent
import org.webrtc.MediaStreamTrack
import org.webrtc.PeerConnection
import org.webrtc.SdpObserver
import org.webrtc.SessionDescription
import org.webrtc.audio.JavaAudioDeviceModule
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine







suspend inline fun createValue(
    crossinline call: (SdpObserver) -> Unit
): Result<SessionDescription> = suspendCoroutine {
    val observer = object : SdpObserver {

        /**
         * Handling of create values.
         */
        override fun onCreateSuccess(description: SessionDescription?) {
            if (description != null) {
                it.resume(Result.success(description))
            } else {
                it.resume(Result.failure(RuntimeException("SessionDescription is null!")))
            }
        }

        override fun onCreateFailure(message: String?) =
            it.resume(Result.failure(RuntimeException(message)))

        /**
         * We ignore set results.
         */
        override fun onSetSuccess() = Unit
        override fun onSetFailure(p0: String?) = Unit
    }

    call(observer)
}

suspend inline fun setValue(
    crossinline call: (SdpObserver) -> Unit
): Result<Unit> = suspendCoroutine {
    val observer = object : SdpObserver {
        /**
         * We ignore create results.
         */
        override fun onCreateFailure(p0: String?) = Unit
        override fun onCreateSuccess(p0: SessionDescription?) = Unit

        /**
         * Handling of set values.
         */
        override fun onSetSuccess() = it.resume(Result.success(Unit))
        override fun onSetFailure(message: String?) =
            it.resume(Result.failure(RuntimeException(message)))
    }

    call(observer)
}

//fun SessionDescription.stringify(): String =
//    "SessionDescription(type=$type, description=$description)"

//fun MediaStreamTrack.stringify(): String {
//    return "MediaStreamTrack(id=${id()}, kind=${kind()}, enabled: ${enabled()}, state=${state()})"
//}

//fun IceCandidateErrorEvent.stringify(): String {
//    return "IceCandidateErrorEvent(errorCode=$errorCode, $errorText, address=$address, port=$port, url=$url)"
//}

fun JavaAudioDeviceModule.AudioSamples.stringify(): String {
    return "AudioSamples(audioFormat=$audioFormat, channelCount=$channelCount" +
            ", sampleRate=$sampleRate, data.size=${data.size})"
}

fun StreamPeerType.stringify() = when (this) {
    StreamPeerType.PUBLISHER -> "publisher"
    StreamPeerType.SUBSCRIBER -> "subscriber"
}