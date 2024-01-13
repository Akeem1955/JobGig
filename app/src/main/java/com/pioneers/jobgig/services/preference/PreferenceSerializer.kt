package com.pioneers.jobgig.services.preference

import androidx.datastore.core.Serializer
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object PreferenceSerializer:Serializer<AppPreference> {
    override val defaultValue: AppPreference
        get() = AppPreference()

    override suspend fun readFrom(input: InputStream): AppPreference {
        return try {
            Json.decodeFromString(AppPreference.serializer(),input.readBytes().decodeToString())
        }catch (e:Exception){
            println(e.printStackTrace())
            defaultValue
        }
    }

    override suspend fun writeTo(t: AppPreference, output: OutputStream) {
        output.write(
            Json.encodeToString(
                AppPreference.serializer(),
                t
            ).encodeToByteArray()
        )
    }
}