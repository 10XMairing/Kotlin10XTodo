package com.tenxdevs.tenx.kotlintodo.adapters

import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tenxdevs.tenx.kotlintodo.MainActivity
import com.tenxdevs.tenx.kotlintodo.R
import com.tenxdevs.tenx.kotlintodo.databaseelements.AppViewModel
import com.tenxdevs.tenx.kotlintodo.databaseelements.Task
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_edit_task.view.*
import kotlinx.android.synthetic.main.vh_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(private val mCtx : Context) : RecyclerView.Adapter<TaskAdapter.CustomVH>() {
    private var mList : ArrayList<Task>? = null
    var  vm : AppViewModel? = null
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomVH {
        val view = LayoutInflater.from(mCtx).inflate(R.layout.vh_list_item, p0, false)
        vm = ViewModelProviders.of(mCtx as MainActivity).get(AppViewModel::class.java)
        return CustomVH(view)
    }

    override fun getItemCount(): Int {
        return if(mList != null){
            mList!!.size
        }else{
            0
        }
    }

    override fun onBindViewHolder(p0: CustomVH, p1: Int) {
        val sdf = SimpleDateFormat("h:mm:aa", Locale.ENGLISH)
        val time = Date(mList!!.get(p1).time!!)
        val task = mList!!.get(p1).task
        val id = mList!!.get(p1).uid

        p0.tvTime.text = sdf.format(time)
        p0.tvTask.setText(mList!!.get(p1).task)
        p0.tvTask.setOnClickListener{ it ->
            //create a dialog
            val builder = AlertDialog.Builder(mCtx, R.style.CustomDialog)
            val view =  LayoutInflater.from(mCtx).inflate(R.layout.dialog_edit_task, null, false);
            builder.setView(view)
            builder.setCancelable(true)
            val dialog =  builder.create()
            dialog.show()
            view.et_dialog_edit_task.setText(task)
            view.et_dialog_edit_task.isEnabled = false
            view.et_dialog_edit_task.maxLines = 9
            view.et_dialog_edit_task.isVerticalScrollBarEnabled = true
            view.et_dialog_edit_task.movementMethod = ScrollingMovementMethod()
            view.tog_dialog_save.setOnCheckedChangeListener{_,c->
                if(c){
                    view.et_dialog_edit_task.isEnabled = true
                }else{
                    //read text
                    val s = view.et_dialog_edit_task.text.toString()

                    vm!!.update(id, s, System.currentTimeMillis())
                    dialog.dismiss()
                }
            }

            view.iv_undo.setOnClickListener{_->
                view.et_dialog_edit_task.setText(task)
            }

            view.iv_dialog_delete.setOnClickListener{_->

                val s = Snackbar.make(view, "Confirm Delete", Snackbar.LENGTH_SHORT).setAction("OK") { _->
                    vm!!.delete(id)
                    dialog.dismiss()

                }.setDuration(2000).show()






            }



        }

    }

    class CustomVH(view : View) : RecyclerView.ViewHolder(view){
        val tvTask = view.et_task
        val tvTime = view.tv_task_time
    }

    fun insertAndRefresh(list : List<Task>){
        mList = list as ArrayList<Task>
        notifyDataSetChanged()
    }

}