package com.example.xmlandroiduniversity.adapter

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.xmlandroiduniversity.R
import com.example.xmlandroiduniversity.databinding.ListitemExcelRowBinding
import com.example.xmlandroiduniversity.viewmodels.ExcelViewModel

class ViewExcelAdapter(private val items: List<List<String>>, private val viewModel: ExcelViewModel) : RecyclerView.Adapter<ViewExcelAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewExcelAdapter.ViewHolder {
        val binding = ListitemExcelRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewExcelAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(val binding: ListitemExcelRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: List<String>) {
            binding.root.removeAllViews()

            val horizontalScrollView = HorizontalScrollView(context)

            val id = View.generateViewId()
            viewModel.horizontalScrollID.add(id)
            horizontalScrollView.id = id

            horizontalScrollView.isHorizontalScrollBarEnabled = false

            val linearLayout = LinearLayout(context)
            linearLayout.orientation = LinearLayout.HORIZONTAL

            for (value in item) {
                val textView = TextView(context)
                textView.text = value

                val layoutParams = LinearLayout.LayoutParams(
                    80.dpToPx(),
                    50.dpToPx()
                )

                val shapeDrawable = ContextCompat.getDrawable(context, R.drawable.border_background)
                textView.background = shapeDrawable
                textView.gravity = Gravity.CENTER

                textView.setTextColor(ContextCompat.getColor(context, R.color.black))
                textView.setPadding(16, 16, 16, 16)

                textView.layoutParams = layoutParams

                linearLayout.addView(textView)
            }

            horizontalScrollView.addView(linearLayout)
            binding.root.addView(horizontalScrollView)


        }
    }
}

fun Int.dpToPx(): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return (this * scale + 0.5f).toInt()
}