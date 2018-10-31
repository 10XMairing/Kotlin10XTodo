package com.tenxdevs.tenx.kotlintodo.databaseelements

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*


@Dao
interface TaskDao {
    @Query("SELECT * FROM task_table")
    fun getAll() : LiveData<List<Task>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(t : Task)
    @Query("DELETE FROM task_table WHERE uid = :id")
    fun delete(id : Int)
    @Query("DELETE FROM task_table")
    fun deleteAll()
    @Query("UPDATE task_table SET task = :t, time = :time WHERE uid =:id")
    fun updateT(id : Int, t : String, time : Long)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update2(t : Task)
}