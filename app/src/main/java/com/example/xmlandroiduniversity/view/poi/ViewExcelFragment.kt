package com.example.xmlandroiduniversity.view.poi

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.xmlandroiduniversity.R
import com.example.xmlandroiduniversity.adapter.ViewExcelAdapter
import com.example.xmlandroiduniversity.databinding.FragmentCreateExcelBinding
import com.example.xmlandroiduniversity.databinding.FragmentViewExcelBinding
import com.example.xmlandroiduniversity.databinding.ListitemExcelRowBinding
import com.example.xmlandroiduniversity.viewmodels.ExcelViewModel
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileInputStream

class ViewExcelFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentViewExcelBinding? = null
    private val binding get() = _binding!!
    protected lateinit var navController: NavController
    private val excelVM: ExcelViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentViewExcelBinding.inflate(inflater, container, false)

        with(binding) {

            backBtn.setOnClickListener(this@ViewExcelFragment)

        }

        val adapter = ViewExcelAdapter(readExcelData(), excelVM)
        binding.excelTableView.layoutManager = LinearLayoutManager(context)
        binding.excelTableView.adapter = adapter

        return binding.root
    }

    private fun createDummyData(): List<List<String>> {
        val dummyData = mutableListOf<List<String>>()
        for (i in 1..10) {
            val row = mutableListOf<String>()
            for (j in 1..10) {
                row.add("Row $i, Col $j")
            }
            dummyData.add(row)
        }
        return dummyData
    }

    private fun readExcelData(): List<List<String>> {

        val excelData: MutableList<MutableList<String>> = mutableListOf()
        val externalFilesDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val name = "${externalFilesDir}/test2.xlsx"

        try {
            val workbook = WorkbookFactory.create(FileInputStream(name))
            val sheet = workbook.getSheetAt(0)

            val rowCount = sheet.physicalNumberOfRows
            val columnCount = if (rowCount > 0) {
                sheet.getRow(0).physicalNumberOfCells
            } else {
                0
            }

            for (rowIndex in 0 until rowCount) {
                val row = sheet.getRow(rowIndex)
                val rowData: MutableList<String> = mutableListOf()
                for (columnIndex in 0 until columnCount) {
                    val cell = row.getCell(columnIndex)
                    Log.d("value", cell.toString())
                    rowData.add(cell.toString())
                }
                excelData.add(rowData)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return excelData
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.backBtn -> {
                excelVM.currentX()
            }
        }
    }


//    private fun countRowAndColumn() {
//
//        val externalFilesDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
////        val name = "${externalFilesDir}/${excelVM.filename}.xlsx"
//        val name = "${externalFilesDir}/test2.xlsx"
//
//        try {
//            val workbook = WorkbookFactory.create(FileInputStream(name))
//            val sheet = workbook.getSheetAt(0)  // 첫 번째 시트를 가져옴 (0부터 시작)
//
//            val rowCount = sheet.physicalNumberOfRows
//            val columnCount = if (rowCount > 0) {
//                sheet.getRow(0).physicalNumberOfCells
//            } else {
//                0
//            }
//
//            Log.d("행의 개수", "$rowCount")
//            Log.d("열의 개수", "$columnCount")
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun drawRow() {
//
//        val inflater = LayoutInflater.from(requireContext())
//
//        val externalFilesDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
////        val name = "${externalFilesDir}/${excelVM.filename}.xlsx"
//        val name = "${externalFilesDir}/test2.xlsx"
//
//        try {
//            val workbook = WorkbookFactory.create(FileInputStream(name))
//            val sheet = workbook.getSheetAt(0)
//
//            val rowCount = sheet.physicalNumberOfRows
//            val columnCount = if (rowCount > 0) {
//                sheet.getRow(0).physicalNumberOfCells
//            } else {
//                0
//            }
//
//            for (rowIndex in 0 until rowCount) {
//
//                val row = sheet.getRow(rowIndex)
//
//                for (columnIndex in 0 until columnCount) {
//
//                    val cell = row.getCell(columnIndex)
//                    Log.d("value", cell.toString())
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//    }
//
//    private fun createTextView(text: String): TextView {
//        val textView = TextView(requireContext())
//        textView.text = text
//        // 원하는 뷰 속성을 설정할 수 있음
//        return textView
//    }
}

