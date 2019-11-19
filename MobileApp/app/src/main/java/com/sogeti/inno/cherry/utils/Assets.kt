package com.sogeti.inno.cherry.utils

import android.content.res.AssetManager

object Assets {

    var CherryAssets: AssetManager? = null

    fun setAssets(assets: AssetManager?) {
        this.CherryAssets = assets
    }

}