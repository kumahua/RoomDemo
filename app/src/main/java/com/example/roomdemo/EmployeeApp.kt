package com.example.roomdemo

import android.app.Application

class EmployeeApp: Application() {
    val db by lazy {
        EmployeeDatabase.getInstance(this)
    } //這裡使用lazy委托，表示只有使用到db才會執行該段程式
}
