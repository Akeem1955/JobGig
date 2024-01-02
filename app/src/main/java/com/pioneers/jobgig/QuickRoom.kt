package com.pioneers.jobgig

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date
import java.util.Locale

class QuickRoom {
    init {
        var t = Timestamp.now()
        println(SimpleDateFormat("hh:mma", Locale.getDefault()).format(t.toDate()))
    }
}
