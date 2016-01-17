package com.derek_s.cursor.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.ImageView
import butterknife.bindView
import com.derek_s.cursor.R
import com.derek_s.cursor.ui.views.MainView

class ActMain: AppCompatActivity(), MainView {

    val etMain: EditText by bindView(R.id.et_main)
    val ivMenu: ImageView by bindView(R.id.iv_menu)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        ivMenu.setOnClickListener {
            // TODO
        }
    }

}