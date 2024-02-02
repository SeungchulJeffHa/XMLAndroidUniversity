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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xmlandroiduniversity.MainActivity
import com.example.xmlandroiduniversity.R.id.action_poiFragment_to_creatExcelFragment
import com.example.xmlandroiduniversity.R.id.action_poiFragment_to_viewExcelFragment
import com.example.xmlandroiduniversity.adapter.POIAdapterListener
import com.example.xmlandroiduniversity.adapter.PoiAdapter
import com.example.xmlandroiduniversity.databinding.DialogCreateExcelBinding
import com.example.xmlandroiduniversity.databinding.FragmentPoiBinding
import com.example.xmlandroiduniversity.db.ExcelFileEntity
import com.example.xmlandroiduniversity.db.RoomDb
import com.example.xmlandroiduniversity.viewmodels.ExcelViewModel
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class PoiFragment : Fragment(), View.OnClickListener{
    private var _binding: FragmentPoiBinding? = null
    private val binding get() = _binding!!
    protected lateinit var navController: NavController
    protected lateinit var roomDb: RoomDb
    private lateinit var mainActivity: MainActivity
    private val excelVM: ExcelViewModel by activityViewModels()
    private var items = mutableListOf<ExcelFileEntity>()

    private val adapterListener = object : POIAdapterListener {
        override fun onListItemPressed(data: ExcelFileEntity) {
//            showFileDetailsDialog(data)
            moveToReadPage(data)
        }

        private fun moveToReadPage(data: ExcelFileEntity) {
            excelVM.filename = data.name
            navController.navigate(action_poiFragment_to_viewExcelFragment)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(items)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity
        roomDb = mainActivity.roomDb
        navController = findNavController()
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

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentPoiBinding.inflate(inflater, container, false)

        with(binding) {
            createBtn.setOnClickListener(this@PoiFragment)
            readBtn.setOnClickListener(this@PoiFragment)
            updateBtn.setOnClickListener(this@PoiFragment)
            deleteBtn.setOnClickListener(this@PoiFragment)
        }

        return binding.root
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.createBtn -> {
                createExcel()
            }

            binding.readBtn -> {
                readExcel()
            }

            binding.updateBtn -> {
                updateExcel()
            }

            binding.deleteBtn -> {
                deleteExcel()
            }
        }
    }

    private fun setupRecyclerView(dataList: List<ExcelFileEntity>) {
        val adapter = PoiAdapter(items, roomDb, adapterListener)
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

    private fun readExcel() {
        Log.d("POI", "===========================================readExcel")

        navController.navigate(action_poiFragment_to_viewExcelFragment)
    }

    private fun updateExcel() {
        Log.d("POI", "===========================================updateExcel")
    }

    private fun deleteExcel() {
        Log.d("POI", "===========================================deleteExcel")
    }
}