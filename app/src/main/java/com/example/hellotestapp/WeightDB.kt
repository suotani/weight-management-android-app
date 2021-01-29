package com.example.hellotestapp

import io.realm.RealmObject
import java.util.*

open class WeightDB : RealmObject() {
    var weight : Float = 0.0F
    lateinit var createdAt : Date
}