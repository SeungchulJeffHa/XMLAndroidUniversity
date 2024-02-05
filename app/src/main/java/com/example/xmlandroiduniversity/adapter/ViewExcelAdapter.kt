package com.example.xmlandroiduniversity.adapter

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.EditText
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.xmlandroiduniversity.R
import com.example.xmlandroiduniversity.databinding.ListitemExcelRowBinding
import com.example.xmlandroiduniversity.global.Constant
import com.example.xmlandroiduniversity.viewmodels.ExcelViewModel

class ViewExcelAdapter(private val items: List<List<String>>, private val viewModel: ExcelViewModel) :
    RecyclerView.Adapter<ViewExcelAdapter.ViewHolder>() {

    private lateinit var context: Context

    private var row: Int = 0
    private var col: Int = 0

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

            viewModel.horizontalScrollID.add(row)
            horizontalScrollView.tag = row
//            val id = View.generateViewId()
//            viewModel.horizontalScrollID.add(id)
//            horizontalScrollView.id = id
            horizontalScrollView.isHorizontalScrollBarEnabled = false

            val linearLayout = LinearLayout(context)
            linearLayout.orientation = LinearLayout.HORIZONTAL

            for (value in item) {
                val editText = EditText(context)

                val layoutParams = LinearLayout.LayoutParams(
                    80.dpToPx(),
                    50.dpToPx()
                )

                val shapeDrawable = ContextCompat.getDrawable(context, R.drawable.border_background)

                with(editText) {
                    editText.setText(value)
                    editText.tag = "r${row}c${col}"
                    Log.d("값", "${value} ROW: $row // COLUMN: $col")

                    if (viewModel.viewMode == Constant.READMODE) {
                        editText.isEnabled = false;
                    }

                    editText.background = shapeDrawable
                    editText.gravity = Gravity.CENTER
                    editText.setTextColor(ContextCompat.getColor(context, R.color.black))
                    editText.setPadding(16, 16, 16, 16)
                    editText.maxLines = 1;
                    editText.layoutParams = layoutParams
                    linearLayout.addView(editText)

                    col += 1
                }
            }

            horizontalScrollView.addView(linearLayout)
            binding.root.addView(horizontalScrollView)

            row +=1
            col = 0
        }

    }
}

fun Int.dpToPx(): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return (this * scale + 0.5f).toInt()
}