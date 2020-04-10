package com.gerka.okhttpcachelib.util

fun parseInt(s: String?, defaultValue: Int = 0): Int {
    var i = defaultValue
    if (s == null) return i
    i = try {
        s.trim { it <= ' ' }.toInt()
    } catch (e: Exception) {
        return defaultValue
    }
    return i
}

fun parseLong(s: String?, defaultValue: Long = 0): Long {
    var i = defaultValue
    if (s == null) return i
    i = try {
        s.trim { it <= ' ' }.toLong()
    } catch (e: Exception) {
        return defaultValue
    }
    return i
}