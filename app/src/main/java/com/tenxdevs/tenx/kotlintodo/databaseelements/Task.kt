package com.tenxdevs.tenx.kotlintodo.databaseelements

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(var task : String?, var time : Long?){
    @PrimaryKey(autoGenerate = true)
    var uid : Int = 0
}