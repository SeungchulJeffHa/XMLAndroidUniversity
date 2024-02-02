package com.example.xmlandroiduniversity.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.xmlandroiduniversity.databinding.ListitemExcelListBinding
import com.example.xmlandroiduniversity.db.ExcelFileEntity
import com.example.xmlandroiduniversity.db.RoomDb

class PoiAdapter(private var items: List<ExcelFileEntity>, private val roomDb: RoomDb, private val listener: POIAdapterListener) :
    RecyclerView.Adapter<PoiAdapter.ViewHolder>() {
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
        return items.size
    }

    fun updateData(newData: List<ExcelFileEntity>) {
        items = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ListitemExcelListBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            // 파일 목록 아이템 전체에 대한 클릭 이벤트 리스너 설정
            binding.fileList.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = items[position]
                    // 클릭 이벤트를 POIAdapterListener를 통해 전달
                    listener.onListItemPressed(item)
                }
            }
        }

        fun bind(item: ExcelFileEntity) {
            binding.excelName.text = item.name
            binding.excelCreatedAt.text = item.createdAt
        }
    }
}

