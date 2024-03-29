package com.test.myapplication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.test.myapplication.databinding.ActivityChooseBinding
import com.test.myapplication.logic.model.chooseList
import com.test.myapplication.logic.model.date
import com.test.myapplication.logic.model.myTextMap
import com.test.myapplication.ui.MyApplication
import com.test.myapplication.ui.gp.gpViewModel
import com.test.myapplication.ui.jj.jjViewModel
import java.util.*
import kotlin.concurrent.thread

class ChooseActivity : AppCompatActivity() {
    val jjviewModel by lazy{ ViewModelProviders.of(this).get(jjViewModel::class.java)}
    val gpviewModel by lazy { ViewModelProviders.of(this).get(gpViewModel::class.java) }
    private lateinit var binding: ActivityChooseBinding
    val timer =Timer()
    val timer2=Timer()
    val handler= object :Handler(){
        override fun handleMessage(msg: Message) {
            when(msg.what){
                1->{
                    Log.d("111","refresh")
                    refreshdate()
                    StartTimer()
                }
                2->{
                    Log.d("111","saved")
                    gpviewModel.saveList()
                    gpviewModel.saveMap()
                    SaveList()

                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        if(gpviewModel.isSaved()){
            chooseList.clear()
            val list=gpviewModel.getSavedList()
            chooseList.addAll(list)
        }
        if(gpviewModel.isMapSaved()){
            myTextMap.clear()
            val map = gpviewModel.getSavedMap()
            myTextMap.putAll(map)
        }
        StartTimer()
        SaveList()
        super.onCreate(savedInstanceState)
        binding= ActivityChooseBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar2)
        setContentView(binding.root)
        binding.addBtn.setOnClickListener {
            chooseList.add(binding.addEdit.text.toString())
            refreshdate()
            gpviewModel.saveList()
            gpviewModel.saveMap()
            binding.addEdit.text.clear()
            Log.d("111", chooseList.toString())
        }
        binding.deleteBtn.setOnClickListener {
            chooseList.remove(binding.addEdit.text.toString())
            refreshdate()
            gpviewModel.saveList()
            gpviewModel.saveMap()
            binding.addEdit.text.clear()
            Log.d("111", chooseList.toString())
        }
            jjviewModel.lofLiveDate.observe(this, Observer { result->
                val date=result.getOrNull()
                if(date!=null){
                   showDate(date)
                }else{
                    Toast.makeText(this,"无法获取数据", Toast.LENGTH_SHORT).show()
                    result.exceptionOrNull()?.printStackTrace()
                }
            })
            gpviewModel.lofLiveDate.observe(this, Observer { result->
                val date1=result.getOrNull()
                if(date1!=null){
                  showDate1(date1)
                }else{
                    Toast.makeText(this,"无法获取数据", Toast.LENGTH_SHORT).show()
                    result.exceptionOrNull()?.printStackTrace()
                }
            })
            refreshdate()
        }
        private fun showDate(date: List<date.Rows>){
            binding.jjlofchoose.removeAllViews()
            val count=date.size
            for(i in 0 until count){
                if(date[i].id in chooseList){
                    val view=LayoutInflater.from(this).inflate(R.layout.lof_item,binding.jjlofchoose,false)
                    val name=view.findViewById(R.id.nameTextView) as TextView
                    val message=view.findViewById(R.id.dateTextView) as TextView
                    val editText = view.findViewById(R.id.editText) as MyTextView
                    val editBtn = view.findViewById(R.id.editBtn) as TextView
                    if (date[i].cell.coverprice=="-"){date[i].cell.coverprice = "-0.0"}
                    name.text=date[i].cell.name
                    editText.text = myTextMap.get(date[i].id)
                    message.text=date[i].id + "    " + date[i].cell.price + "    " + date[i].cell.coverprice.toFloat() + "%"
                    if (date[i].cell.coverprice.toFloat() >= 0.5) {
                       message.setTextColor(Color.RED)
                    }
                    if(date[i].cell.coverprice.toFloat() <=-0.8){
                        message.setTextColor(Color.GREEN)
                    }
                    editBtn.setOnClickListener {
                        val dialog = CustomDialog(
                            this,
                            "编辑备注",
                            "最多支持40字",
                            object : CustomDialog.clickCallBack {
                                override fun onYesClick(dialog: CustomDialog) {
                                    val input: String = dialog.mEditText?.text.toString()
                                    editText.setText(input)
                                    myTextMap.put(date[i].id,input)
                                    gpviewModel.saveMap()
                                    dialog?.dismiss()
                                }
                            },
                            true,
                            editText.text.toString()
                        )
                        dialog.setOnDismissListener {
                            //关闭监听逻辑
                        }
                        //显示的位置居中提高100dp-------------
                        val w = dialog.window
                        val lp = w!!.attributes
                        lp.x = 0
                        lp.y = -100
                        //-----------------------------------

                        dialog.show();
                    }
                    binding.jjlofchoose.addView(view)
                }
            }
        }
        private fun showDate1(date: List<date.Rows>) {
            binding.gplofchoose.removeAllViews()
            val count = date.size
            for (i in 0 until count) {
                if (date[i].id in chooseList) {
                    val view = LayoutInflater.from(this)
                        .inflate(R.layout.lof_item, binding.gplofchoose, false)
                    val name = view.findViewById(R.id.nameTextView) as TextView
                    val message = view.findViewById(R.id.dateTextView) as TextView
                    val editText = view.findViewById(R.id.editText) as MyTextView
                    val editBtn = view.findViewById(R.id.editBtn) as TextView
                    if (date[i].cell.coverprice == "-") {
                        date[i].cell.coverprice = "-0.0"
                    }
                    name.text = date[i].cell.name
                    editText.text = myTextMap.get(date[i].id)
                    message.text =
                        date[i].id + "    " + date[i].cell.price + "    " + date[i].cell.coverprice.toFloat() + "%"
                    if (date[i].cell.coverprice.toFloat() >= 0.5) {
                        message.setTextColor(Color.RED)
                    }
                    if (date[i].cell.coverprice.toFloat() <= -0.8) {
                        message.setTextColor(Color.GREEN)
                    }
                    editBtn.setOnClickListener {
                        val dialog = CustomDialog(
                            this,
                            "编辑备注",
                            "最多支持40字",
                            object : CustomDialog.clickCallBack {
                                override fun onYesClick(dialog: CustomDialog) {
                                    val input: String = dialog.mEditText?.text.toString()
                                    editText.setText(input)
                                    myTextMap.put(date[i].id,input)
                                    gpviewModel.saveMap()
                                    dialog?.dismiss()
                                }
                            },
                            true,
                            editText.text.toString()
                        )
                        dialog.setOnDismissListener {
                            //关闭监听逻辑
                        }
                        //显示的位置居中提高100dp-------------
                        val w = dialog.window
                        val lp = w!!.attributes
                        lp.x = 0
                        lp.y = -100
                        //-----------------------------------

                        dialog.show();
                    }
                    binding.gplofchoose.addView(view)
                }
            }
        }
           private fun refreshdate(){
            jjviewModel.refreshdate("111")
            gpviewModel.refreshdate("121")
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.showChoose->{
                val intent= Intent(MyApplication.context, ChooseActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.showAll->{
                val intent= Intent(MyApplication.context, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.react->{
                refreshdate()
            }
        }
        return true
    }
      override fun onDestroy() {
          gpviewModel.saveList()
          gpviewModel.saveMap()
          Log.d("111","saved")
          timer2.cancel()
          timer.cancel()
        super.onDestroy()
    }
    fun StartTimer(){
        thread {
            val msg = Message()
            msg.what = 1
            val task= object :TimerTask(){
                override fun run() {
                    handler.sendMessage(msg)
                }
            }
          timer.schedule(task,30000)
        }

    }
    fun SaveList(){
       thread{
           val msg=Message()
           msg.what=2
           val task= object:TimerTask(){
               override fun run() {
                   handler.sendMessage(msg)
               }
           }
           timer2.schedule(task,3*60*1000)
       }
    }
}