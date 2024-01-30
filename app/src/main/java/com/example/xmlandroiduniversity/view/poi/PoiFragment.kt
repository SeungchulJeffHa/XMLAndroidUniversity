package com.example.xmlandroiduniversity.view.poi

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
import com.example.xmlandroiduniversity.R.id.action_poiFragment_to_excelFragment
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


class PoiFragment : Fragment(), View.OnClickListener {


    private var _binding: FragmentPoiBinding? = null
    private val binding get() = _binding!!
    protected lateinit var navController: NavController

    protected lateinit var roomDb: RoomDb
    private lateinit var mainActivity: MainActivity

    private val excelVM: ExcelViewModel by activityViewModels()

    private val items = arrayListOf<ExcelFileEntity>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity
        roomDb = mainActivity.roomDb
        navController = findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val externalFilesDir = requireContext().getExternalFilesDir(DIRECTORY_DOWNLOADS)
        val files = externalFilesDir?.listFiles { file ->
            file.isFile && file.extension == "xlsx"
        }


        if (files != null) {
            val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())

            for (file in files) {
                println("File name: ${file.name}")
                println("File path: ${file.absolutePath}")

                val creationDate = Date(file.lastModified())
                val formattedDate = dateFormat.format(creationDate)

                runBlocking {
                    roomDb.excelFileDao().insert(ExcelFileEntity(file.name, creationDate.toString(), false))
                }

            }
        }


    }

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

            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = PoiAdapter(items, roomDb)
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
                navController.navigate(action_poiFragment_to_excelFragment)
            }
        }

    }

    private fun readExcel() {
        Log.d("POI", "===========================================readExcel")
    }

    private fun updateExcel() {
        Log.d("POI", "===========================================updateExcel")
    }

    private fun deleteExcel() {
        Log.d("POI", "===========================================deleteExcel")
    }
}