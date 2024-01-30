package com.example.xmlandroiduniversity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.xmlandroiduniversity.databinding.ListitemExcelListBinding
import com.example.xmlandroiduniversity.db.ExcelFileEntity
import com.example.xmlandroiduniversity.db.RoomDb

class PoiAdapter(private val items: List<ExcelFileEntity>, private val roomDb: RoomDb) : RecyclerView.Adapter<PoiAdapter.ViewHolder>() {
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

    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ListitemExcelListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExcelFileEntity) {
            binding.excelName.text = item.name
            binding.excelCreatedAt.text = item.createdAt
        }
    }
}

