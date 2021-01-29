package com.example.hellotestapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import io.realm.Realm
import io.realm.RealmResults
import java.util.*
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.activity_graph.*
import kotlinx.android.synthetic.main.activity_sub1.*
import java.text.SimpleDateFormat

class GraphActivity : AppCompatActivity() {

    lateinit var realm : Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        backBtn.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        realm = Realm.getDefaultInstance()
        setGraph(realm)
    }

    override fun onPause() {
        super.onPause()
    }

    private fun setGraph(db : Realm){
        val results : RealmResults<WeightDB> = db.where(WeightDB::class.java).findAll().sort("createdAt")
        if(results.size != 0) {
            var entryList = mutableListOf<Entry>()//1本目の線
            var dateDiff: Double = 0.0
            val format = SimpleDateFormat("yyyyMMddHHmm")
            var pre: Double = format.format(results[0]?.createdAt).toDouble()
            results.forEach {
                dateDiff = (format.format(it.createdAt).toDouble() - pre)
                pre = format.format(it.createdAt).toDouble()
                entryList.add(Entry(dateDiff.toFloat(), it.weight))
            }
            val lineDataSets = mutableListOf<ILineDataSet>()
            val lineDataSet = LineDataSet(entryList, "square")
            lineDataSet.color = Color.BLUE
            lineDataSets.add(lineDataSet)
            val lineData = LineData(lineDataSets)
            lineChartExample.data = lineData
            lineChartExample.xAxis.apply {
                isEnabled = true
                textColor = Color.BLACK
            }
            lineChartExample.invalidate()
        }

    }
}