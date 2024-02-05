package com.example.xmlandroiduniversity.adapter

import android.content.Context
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.xmlandroiduniversity.R
import com.example.xmlandroiduniversity.databinding.ListitemExcelListBinding
import com.example.xmlandroiduniversity.db.ExcelFileEntity
import com.example.xmlandroiduniversity.db.RoomDb
import com.example.xmlandroiduniversity.global.Constant
import com.example.xmlandroiduniversity.viewmodels.ExcelViewModel
import java.util.Collections

class PoiAdapter(
    private var items: MutableList<ExcelFileEntity>,
    private val roomDb: RoomDb,
    private val listener: POIAdapterListener,
    private val excelViewModel: ExcelViewModel,
    private val navController: NavController
) :
    RecyclerView.Adapter<PoiAdapter.MyViewHolder>() {
    private lateinit var context: Context

    // 뷰 레이아웃 (listitem_excel_list.xml)  연결 후 뷰 홀더 만들어 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoiAdapter.MyViewHolder {
        val binding = ListitemExcelListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        context = parent.context

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PoiAdapter.MyViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            listener.onListItemPressed(item)
        }
    }

    override fun getItemCount() = items.size

    // ----------- 데이터 조작 함수 추가 -----------

    // position 위치의 데이터를 삭제 후 어댑터 갱신
    fun removeData(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    // 현재 선택된 데이터와 드래그한 위치에 있는 데이터를 교환
    fun swapData(fromPos: Int, toPos: Int) {
        Collections.swap(items, fromPos, toPos)
        notifyItemMoved(fromPos, toPos)
    }

    fun updateData(newData: MutableList<ExcelFileEntity>) {
        items = newData
        notifyDataSetChanged()
    }

    inner class MyViewHolder(val binding: ListitemExcelListBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // 파일 목록 아이템 전체에 대한 클릭 이벤트 리스너 설정
            binding.swipeView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = items[position]
                    // 클릭 이벤트를 POIAdapterListener를 통해 전달
                    listener.onListItemPressed(item)
                }
            }
        }

        fun bind(data: ExcelFileEntity) {
            // 제목 달기
            binding.textView.text = data.name

            // 서브 메뉴 달기(...모양)
            binding.textViewOptions.setOnClickListener {
                val popup = PopupMenu(binding.textViewOptions.context, binding.textViewOptions)
                popup.inflate(R.menu.recyclerview_item_menu)
                popup.setOnMenuItemClickListener { item ->
                    val str = when (item.itemId) {
                        R.id.itemSaveLater -> "나중에 볼 동영상에 저장"
                        R.id.itemSavePalyList -> "재생목록에 저장"
                        R.id.itemSvaeOffline -> "동영상 오프라인 저장"
                        R.id.itemShare -> "공유"
                        R.id.itemRemove -> "좋아요 표시한 동영상에서 삭제"
                        else -> "오류"
                    }
                    Toast.makeText(binding.textViewOptions.context, str, Toast.LENGTH_SHORT).show()
                    true
                }
                popup.show()
            }


            binding.tvEdit.setOnClickListener {
                excelViewModel.viewMode = Constant.EDITMODE
                excelViewModel.moveToReadPage(data, navController)
            }

            // 삭제 텍스트뷰 클릭시 토스트 표시
            binding.tvRemove.setOnClickListener {
                removeData(this.layoutPosition)

                val externalFilesDir = context.getExternalFilesDir(DIRECTORY_DOWNLOADS)

                // 외부 다운로드 디렉토리에 있는 파일 목록 가져오기
                val files = externalFilesDir?.listFiles { file ->
                    file.isFile && file.name == data.name
                }

                // 파일이 존재하면 삭제
                files?.forEach { file ->
                    if (file.exists()) {
                        val deleted = file.delete()
                        if (deleted) {
                            // 파일이 성공적으로 삭제되었음
                            // 추가적인 작업을 수행할 수 있습니다.
                            println("File ${data.name} deleted successfully.")
                        } else {
                            // 파일 삭제 실패
                            println("Failed to delete file ${data.name}.")
                        }
                    }
                }

                Toast.makeText(binding.root.context, "${data.name}을 삭제했습니다.", Toast.LENGTH_SHORT).show()
            }

        }
    }
}

