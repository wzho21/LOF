package com.test.myapplication.ui.gp

import android.app.AlertDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.test.myapplication.R
import com.test.myapplication.logic.model.chooseList
import com.test.myapplication.logic.model.date
import com.test.myapplication.ui.MyApplication

class allAdapter( private val dateList:List<date.Rows>):RecyclerView.Adapter<allAdapter.ViewHolder>() {
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val name: TextView =view.findViewById(R.id.nameTextView)
        val date:TextView=view.findViewById(R.id.dateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.lof_item,parent,false)
        val holder=ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = dateList[position]
        if(date.cell.coverprice!="-") {
            holder.name.text = date.cell.name
            holder.date.text = date.id + "       " + date.cell.price + "       " + date.cell.coverprice + "%"
            if (date.cell.sg != "开放" && date.cell.sg != "限大额" || date.cell.sh != "开放" && date.cell.sh != "限大额") {
                holder.name.setTextColor(Color.GREEN)
            }

            if (date.cell.coverprice.toFloat() <= -0.8 || date.cell.coverprice.toFloat() >= 0.5) {
                holder.date.setTextColor(Color.RED)
            }
        }
    }
    override fun getItemCount()=dateList.size
}