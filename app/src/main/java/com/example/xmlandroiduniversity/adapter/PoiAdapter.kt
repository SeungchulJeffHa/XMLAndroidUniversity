package com.example.xmlandroiduniversity.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.xmlandroiduniversity.databinding.ListitemExcelListBinding
import com.example.xmlandroiduniversity.db.ExcelFileEntity
import com.example.xmlandroiduniversity.db.RoomDb

class PoiAdapter(private val items: List<ExcelFileEntity>, private val roomDb: RoomDb, private val listener: POIAdapterListener) : RecyclerView.Adapter<PoiAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoiAdapter.ViewHolder {
        val binding = ListitemExcelListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        context = parent.context

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PoiAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            listener.onListItemPressed(item)
        }
    }

    override fun getItemCount(): Int {

        Log.d("아이템 개스", "=========================================${items.size}")

        return items.size
    }

    inner class ViewHolder(val binding: ListitemExcelListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExcelFileEntity) {
            binding.excelName.text = item.name
            binding.excelCreatedAt.text = item.createdAt

            binding.slctFile.setOnClickListener {
                listener.onListItemCheckboxPressed(item)
            }
        }
    }
}

