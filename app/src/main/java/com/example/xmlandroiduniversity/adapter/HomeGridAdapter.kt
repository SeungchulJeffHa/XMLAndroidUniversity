package com.example.xmlandroiduniversity.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.xmlandroiduniversity.data.home.MenuItem
import android.view.LayoutInflater
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import com.example.xmlandroiduniversity.R
import com.example.xmlandroiduniversity.databinding.ListitemHomeGridBinding

class HomeGridAdapter(private val context: Context, private val items: List<MenuItem>, private val listener: HomeGridAdapterListener): BaseAdapter() {
    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        if (convertView == null) convertView = LayoutInflater.from(parent?.context).inflate(R.layout.listitem_home_grid, parent, false)

        val item : MenuItem = items[position]

        val imgView = convertView?.findViewById<ImageView>(R.id.menuImg)
        val nameView = convertView?.findViewById<TextView>(R.id.menuName)

        nameView!!.text = item.name

        convertView!!.setOnClickListener {
            listener.onListItemPressed(item)
        }

        return convertView!!
    }
}