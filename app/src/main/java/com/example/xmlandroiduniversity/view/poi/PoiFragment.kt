package com.example.xmlandroiduniversity.view.poi

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xmlandroiduniversity.MainActivity
import com.example.xmlandroiduniversity.R.id.action_poiFragment_to_creatExcelFragment
import com.example.xmlandroiduniversity.R.id.action_poiFragment_to_viewExcelFragment
import com.example.xmlandroiduniversity.adapter.POIAdapterListener
import com.example.xmlandroiduniversity.adapter.PoiAdapter
import com.example.xmlandroiduniversity.adapter.SwipeHelperCallback
import com.example.xmlandroiduniversity.databinding.DialogCreateExcelBinding
import com.example.xmlandroiduniversity.databinding.FragmentPoiBinding
import com.example.xmlandroiduniversity.db.ExcelFileEntity
import com.example.xmlandroiduniversity.db.RoomDb
import com.example.xmlandroiduniversity.global.Constant
import com.example.xmlandroiduniversity.viewmodels.ExcelViewModel
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PoiFragment : Fragment(), View.OnClickListener{
    private var _binding: FragmentPoiBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var roomDb: RoomDb
    private lateinit var mainActivity: MainActivity
    private val excelVM: ExcelViewModel by activityViewModels()
    private var items = mutableListOf<ExcelFileEntity>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity
        roomDb = mainActivity.roomDb
        navController = findNavController()
    }

    private val adapterListener = object : POIAdapterListener {
        override fun onListItemPressed(data: ExcelFileEntity) {
            excelVM.viewMode = Constant.READMODE
            excelVM.moveToReadPage(data, navController)
        }
    }

    override fun onResume() {
        super.onResume()
        runBlocking {
            roomDb.excelFileDao().delete()
        }

        loadExcelFile()

        setupRecyclerView(runBlocking {
            roomDb.excelFileDao().select()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBlocking {
            roomDb.excelFileDao().delete()
        }
        loadExcelFile()
    }

    @SuppressLint("NotifyDataSetChanged", "ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentPoiBinding.inflate(inflater, container, false)

        with(binding) {
            createBtn.setOnClickListener(this@PoiFragment)
        }

        val recyclerViewAdapter = PoiAdapter(items, roomDb, adapterListener, excelVM, navController)
        binding.recyclerView.adapter = recyclerViewAdapter

        // 리사이클러뷰에 스와이프, 드래그 기능 달기
        val swipeHelperCallback = SwipeHelperCallback(recyclerViewAdapter).apply {
            // 스와이프한 뒤 고정시킬 위치 지정
            setClamp((resources.displayMetrics.widthPixels.toFloat() / 3.5).toFloat())    // 1080 / 4 = 270
        }

        ItemTouchHelper(swipeHelperCallback).attachToRecyclerView(binding.recyclerView)
        // 구분선 추가
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        // 다른 곳 터치 시 기존 선택했던 뷰 닫기
        binding.recyclerView.setOnTouchListener { _, _ ->
            swipeHelperCallback.removePreviousClamp(binding.recyclerView)
            false
        }

        return binding.root
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.createBtn -> {
                createExcel()
            }
        }
    }

    private fun loadExcelFile() {
        val externalFilesDir = requireContext().getExternalFilesDir(DIRECTORY_DOWNLOADS)
        val files = externalFilesDir?.listFiles { file ->
            file.isFile && file.extension == "xlsx"
        }

        if (files != null) {
            val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())

            for (file in files) {
                val fileLastModified = System.currentTimeMillis() // 예시로 현재 시간을 사용했지만, 실제로는 file.lastModified()로 사용해야 합니다.
                val creationDate = Date(fileLastModified)
                val dateFormat = SimpleDateFormat("yyyyMMdd")
                val formattedDate = dateFormat.format(creationDate)

                runBlocking {
                    roomDb.excelFileDao().insert(ExcelFileEntity(file.name, formattedDate))
                }
            }
        }

        runBlocking {
            items = roomDb.excelFileDao().select().toMutableList()
        }
    }

    private fun setupRecyclerView(dataList: MutableList<ExcelFileEntity>) {
        val adapter = PoiAdapter(items, roomDb, adapterListener, excelVM, navController)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        adapter.updateData(items)
    }

    private fun createExcel() {
        Log.d("POI", "===========================================createExcel")

        var filename = ""

        DialogCreateExcelBinding.inflate(LayoutInflater.from(requireContext())).apply {
            val alertDialog = AlertDialog.Builder(requireContext()).setView(root).create()
            alertDialog.show()

            cancelBtn.setOnClickListener {
                alertDialog.dismiss()
            }

            createBtn.setOnClickListener {
                excelVM.filename = filenameField.text.toString()
                alertDialog.dismiss()
                navController.navigate(action_poiFragment_to_creatExcelFragment)
            }
        }

    }

    private fun showFileDetailsDialog(data: ExcelFileEntity) {
        // 파일의 상세 정보를 다이얼로그로 표시하는 로직을 여기에 추가
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("File Details")
        alertDialogBuilder.setMessage("File Name: ${data.name}\nCreated At: ${data.createdAt}")
        alertDialogBuilder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        alertDialogBuilder.show()
    }
}