package com.pioneers.jobgig.services.preference

import android.content.Context
import androidx.datastore.dataStore
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable


@Serializable
data class AppPreference(val searches: List<String> = mutableListOf())
sealed class Apps(var searches: PersistentList<String> = persistentListOf())



val Context.datastore by dataStore("preferences",PreferenceSerializer)