package com.tenxdevs.tenx.kotlintodo.databaseelements

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData

class AppViewModel constructor(application: Application): AndroidViewModel(application) {

    val mRepo : AppRepository = AppRepository(application)

    fun getAll() : LiveData<List<Task>> {
        return mRepo.getAll()
    }

    fun insert(task: Task){
        mRepo.insert(task)
    }
    fun update2(task: Task){
        mRepo.insert(task)
    }

    fun delete(id : Int){
        mRepo.delete(id)
    }
    fun deleteAll(){
        mRepo.deleteAll()
    }

    fun update(id : Int, task : String, time : Long){
        mRepo.update(id, task, time)
    }
}