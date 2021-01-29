package com.example.hellotestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Float.valueOf
import java.time.LocalDateTime
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var realm : Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var weight = 80.0F
        var pWeight = weight
        setCurrentWeight(weight)
        preWeight.text = (String.format("%.01f", weight))

        toGraph.setOnClickListener{
            val intent = Intent(this@MainActivity, SubActivity1::class.java)
            startActivity(intent)
        }

        // 登録アクション
        submit.setOnClickListener{
            saveWeight()
            weight = currentWeight.getText().toString().toFloat()
            setCurrentWeight(weight)
            preWeight.text = (String.format("%.01f", weight))
            //Toast.makeText(出す場所, メッセージ, 表示時間)
            Toast.makeText(this@MainActivity, "登録が完了しました", Toast.LENGTH_SHORT).show()
        }

        // 体重減少アクション
        reduce.setOnClickListener{
            weight -= 0.1F
            setCurrentWeight(weight)
        }
        // 体重増加アクション
        add.setOnClickListener{
            weight += 0.1F
            setCurrentWeight(weight)
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

    private fun setCurrentWeight(w : Float){
        currentWeight.setText(String.format("%.01f", w))
    }

    private fun saveWeight(){
        realm.beginTransaction()
        var weightdb = realm.createObject(WeightDB::class.java)
        weightdb.weight = currentWeight.getText().toString().toFloat()
        weightdb.createdAt = Date()
        realm.commitTransaction()

    }
}