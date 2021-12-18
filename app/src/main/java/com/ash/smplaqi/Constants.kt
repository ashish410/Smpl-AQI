package com.ash.smplaqi

import java.util.*

const val WS_URL = "wss://city-ws.herokuapp.com/"

fun Number.roundTo(
    numFractionDigits: Int
) = "%.${numFractionDigits}f".format(this, Locale.ENGLISH)