package com.example.hellotestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_sub1.*
import java.text.SimpleDateFormat

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
        val results : RealmResults<WeightDB> = realm.where(WeightDB::class.java).findAll().sort("createdAt")
        val weightList = ArrayList<String>()
        val format = SimpleDateFormat("yyyy/MM/dd HH:mm")
//        for(i in 0 .. results.size-1){
//            weightList.add(format.format(results[i]?.createdAt) + "　" +  String.format("%.01f", results[i]?.weight))
//        }
        results.forEach{
            weightList.add(format.format(it.createdAt) + "　" +  String.format("%.01f", it.weight))
            // 全件削除
//            realm.beginTransaction()
//            it.deleteFromRealm()
//            realm.commitTransaction()
        }

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, weightList)
        listView.adapter = adapter
    }

    override fun onPause() {
        super.onPause()
        realm.close()
    }

}