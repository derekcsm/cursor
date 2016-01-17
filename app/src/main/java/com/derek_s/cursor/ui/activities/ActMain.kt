package com.derek_s.cursor.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.derek_s.cursor.R
import com.derek_s.cursor.ui.views.MainView

class ActMain: AppCompatActivity(), MainView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)
    }

}