package com.test.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.myapplication.databinding.ActivityAllBinding
import com.test.myapplication.ui.gp.allAdapter
import com.test.myapplication.ui.gp.gpViewModel
import com.test.myapplication.ui.jj.jjViewModel

class MainActivity : AppCompatActivity() {
    val jjviewModel by lazy{ ViewModelProviders.of(this).get(jjViewModel::class.java)}
    val gpviewModel by lazy { ViewModelProviders.of(this).get(gpViewModel::class.java) }
    private lateinit var binding: ActivityAllBinding
    private lateinit var  jjadapter: allAdapter
    private lateinit var gpadapter: allAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAllBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        setContentView(binding.root)
        val layoutManager1= LinearLayoutManager(this)
        val layoutManager2= LinearLayoutManager(this)
        binding.gplofall.layoutManager=layoutManager1
        binding.jjlofall.layoutManager=layoutManager2
        gpadapter= allAdapter(gpviewModel.dateList)
        jjadapter=allAdapter(jjviewModel.dateList)
        binding.jjlofall.adapter=jjadapter
        binding.gplofall.adapter=gpadapter
        jjviewModel.lofLiveDate.observe(this, Observer { result->
            val date=result.getOrNull()
            if(date!=null){
                jjviewModel.dateList.clear()
                jjviewModel.dateList.addAll(date)
                jjadapter.notifyDataSetChanged()
            }else{
                Toast.makeText(this,"无法获取数据", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
            binding.swipeRefresh.isRefreshing=false
        })
        gpviewModel.lofLiveDate.observe(this, Observer { result->
            val date1=result.getOrNull()
            if(date1!=null){
                gpviewModel.dateList.clear()
                gpviewModel.dateList.addAll(date1)
                gpadapter.notifyDataSetChanged()
            }else{
                Toast.makeText(this,"无法获取数据", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
            binding.swipeRefresh.isRefreshing=false
        })
       binding.swipeRefresh.setColorSchemeResources(R.color.purple_200)
        refreshdate()
        binding.swipeRefresh.setOnRefreshListener {
            refreshdate()
        }
    }
    private fun refreshdate(){
        jjviewModel.refreshdate("111")
        gpviewModel.refreshdate("121")
        binding.swipeRefresh.isRefreshing=true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.showChoose->{
                val intent= Intent(this,ChooseActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.showAll->{
                val intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.react->{
                refreshdate()
            }
        }
        return true
    }
}