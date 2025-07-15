package com.ismailtaspinar.movieAppKmp.utils.extension

fun Double.formatToOneDecimal(): String {
    return ((this * 10).toInt() / 10.0).toString()
}