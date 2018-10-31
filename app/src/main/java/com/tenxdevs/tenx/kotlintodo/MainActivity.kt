package com.tenxdevs.tenx.kotlintodo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.text.Editable
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.messaging.FirebaseMessaging
import com.tenxdevs.tenx.kotlintodo.adapters.TaskAdapter
import com.tenxdevs.tenx.kotlintodo.databaseelements.AppViewModel
import com.tenxdevs.tenx.kotlintodo.databaseelements.Task
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var adapter : TaskAdapter? = null
    var vm : AppViewModel? = null
    private var animShrink : Animation ? = null
    private var animGrow : Animation ? = null
    lateinit var mAdview : AdView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vm = ViewModelProviders.of(this).get(AppViewModel::class.java)


        MobileAds.initialize(this, getString(R.string.appID))

        mAdview = findViewById(R.id.adView)
        val adreq = AdRequest.Builder().build()
        mAdview.loadAd(adreq)

        FirebaseMessaging.getInstance().isAutoInitEnabled = true


        animShrink = AnimationUtils.loadAnimation(this, R.anim.anim_shrink)
        animGrow = AnimationUtils.loadAnimation(this, R.anim.anim_grow)

        adapter = TaskAdapter(this)
        recycler_task.layoutManager = LinearLayoutManager(this)
        recycler_task.adapter = adapter

        //setting up the initial state of buttons
        btn_home_save.visibility = View.GONE
        btn_home_cancel.visibility = View.GONE

        iv_pop.setOnClickListener{
            val popup = PopupMenu(this, it)
            popup.inflate(R.menu.menu)
            popup.show()
            popup.setOnMenuItemClickListener{v->

                when(v.itemId){
                    R.id.menu_deleteAll->{
                        if(adapter!!.itemCount != 0){
                            Snackbar.make(parentLayout, "Confirm Delete", Snackbar.LENGTH_SHORT).setAction("OK") { _->
                                vm!!.deleteAll()


                            }.setDuration(6000).show()
                            true
                        }else{
                            Snackbar.make(parentLayout, "Nothing to delete", Snackbar.LENGTH_SHORT).setDuration(3000).show()
                            true
                        }

                    }
                    R.id.menu_about->{
                        Toast.makeText(this, "App Developed by TenX", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> {
                         false
                    }
                }

            }
        }
        //setting observer on task data
        vm!!.getAll().observe(this, Observer {
            adapter!!.insertAndRefresh(it!!)
        })

        et_input.maxLines = 4
        et_input.isVerticalScrollBarEnabled = true
        et_input.movementMethod = ScrollingMovementMethod()
        et_input.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                Log.d("check", "after")
                if(p0.isNullOrBlank()){
                    btn_home_save.visibility = View.GONE
                    btn_home_cancel.visibility = View.GONE
                }else{
                    btn_home_save.visibility = View.VISIBLE
                    btn_home_cancel.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                Log.d("check", "Before")
                if(p0.isNullOrBlank()){
                    btn_home_save.visibility = View.GONE
                    btn_home_cancel.visibility = View.GONE
                }else{
                    btn_home_save.visibility = View.VISIBLE
                    btn_home_cancel.visibility = View.VISIBLE
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("check", "onCurrent")
                if(p0.isNullOrBlank()){
                    btn_home_save.visibility = View.GONE
                    btn_home_cancel.visibility = View.GONE
                }else{
                    btn_home_save.visibility = View.VISIBLE
                    btn_home_cancel.visibility = View.VISIBLE

                }
            }
        })

        btn_home_save.setOnClickListener{
            val task = et_input.text.toString()
            if(!et_input.text.isEmpty()){
                val time = System.currentTimeMillis()
                vm!!.insert(Task(task, time))
                et_input.text.clear()
            }
        }

        btn_home_cancel.setOnClickListener{
            et_input.text.clear()
        }

    }

}
