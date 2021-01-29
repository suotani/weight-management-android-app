package com.example.hellotestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_sub1.*

class SubActivity1 : AppCompatActivity() {

    lateinit var realm : Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub1)

        back.setOnClickListener {
            finish()
        }


    }
    override fun onResume() {
        super.onResume()
        realm = Realm.getDefaultInstance()
    }

    override fun onPause() {
        super.onPause()
        realm.close()
    }

}