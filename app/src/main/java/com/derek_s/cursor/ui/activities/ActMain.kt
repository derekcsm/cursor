package com.derek_s.cursor.ui.activities

import android.app.Activity
import android.os.Bundle
import com.derek_s.cursor.R
import com.derek_s.cursor.ui.views.MainView

class ActMain: Activity(), MainView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)
    }

}