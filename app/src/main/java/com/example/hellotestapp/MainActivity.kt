package com.example.hellotestapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {

    lateinit var realm : Realm
    var weight by Delegates.notNull<Float>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toGraph.setOnClickListener{
            val intent = Intent(this@MainActivity, GraphActivity::class.java)
            startActivity(intent)
        }
        toList.setOnClickListener{
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

        val lastData = realm.where(WeightDB::class.java).sort("createdAt", Sort.DESCENDING).findFirst()
        if (lastData != null) {
            weight = lastData.weight
        }else{
            weight = 0.0F
        }
        setCurrentWeight(weight)
        preWeight.text = (String.format("%.01f", weight))

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