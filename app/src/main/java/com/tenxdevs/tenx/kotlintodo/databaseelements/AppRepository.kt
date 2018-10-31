package com.tenxdevs.tenx.kotlintodo.databaseelements

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.util.Log

class AppRepository constructor(application: Application){
    val mDb = AppDatabase.getInstance(application)
    val mDao = mDb.taskDao()
    companion object {
        const  val CMD_INSERT = 1
        const  val CMD_DELETE = 2
        const  val CMD_DELETEALL = 3
        const  val CMD_UPDATE = 4
        const  val CMD_UPDATE2 = 5
    }



    fun getAll() : LiveData<List<Task>>{
        return mDao.getAll()
    }

    fun insert(task: Task){
        DbWorkerAsync(mDao).execute(CMD_INSERT, task)
    }
    fun update(task: Task){
        DbWorkerAsync(mDao).execute(CMD_UPDATE2, task)
    }
    fun delete(id : Int){
        DbWorkerAsync(mDao).execute(CMD_DELETE, id)
    }
    fun update(id : Int, task : String, time: Long){
        DbWorkerAsync(mDao).execute(CMD_UPDATE,id, task,time)
    }
    fun deleteAll(){
        DbWorkerAsync(mDao).execute(CMD_DELETEALL)
    }





    class DbWorkerAsync(private val dao: TaskDao) : AsyncTask<Any, Void, String>(){


        override fun doInBackground(vararg params: Any?):String {
            if( params[0] as Int == CMD_INSERT){
                dao.insert(params[1] as Task)
            }
            if(params[0] == CMD_DELETEALL){
                dao.deleteAll()
            }
            if(params[0] == CMD_DELETE){
                dao.delete(params[1] as Int)
            }
            if(params[0] == CMD_UPDATE2){
                dao.update2(params[1] as Task)
            }
            if(params[0] == CMD_UPDATE){
                Log.d("test", "update called")
                dao.updateT(params[1] as Int, params[2] as String, params[3] as Long)
            }

            return ""
        }
    }
}