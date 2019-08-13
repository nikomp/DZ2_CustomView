package com.example.dz2_customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btn=findViewById<Button>(R.id.idBtn)
        btn.setOnClickListener(View.OnClickListener {
            Log.d("myLogs","OnClick")
            /*val customView=findViewById<LogoCustomView>(R.id.logoCustomView)
            customView.startAnimation()*/
        })

        val customView=findViewById<LogoCustomView>(R.id.logoCustomView)
        customView.startAnimation()

    }


}
