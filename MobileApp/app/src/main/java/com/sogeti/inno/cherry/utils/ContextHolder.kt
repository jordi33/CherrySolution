package com.sogeti.inno.cherry.utils

import android.content.Context

data class ContextHolder(var context: Context?) {
    companion object {
        val instance = ContextHolder(null)
    }
}