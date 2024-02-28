package com.example.xmlandroiduniversity.view.poi

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.xmlandroiduniversity.R
import com.example.xmlandroiduniversity.adapter.ViewExcelAdapter
import com.example.xmlandroiduniversity.databinding.FragmentCreateExcelBinding
import com.example.xmlandroiduniversity.databinding.FragmentViewExcelBinding
import com.example.xmlandroiduniversity.databinding.ListitemExcelRowBinding
import com.example.xmlandroiduniversity.global.Constant
import com.example.xmlandroiduniversity.viewmodels.ExcelViewModel
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class ViewExcelFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentViewExcelBinding? = null
    private val binding get() = _binding!!
    protected lateinit var navController: NavController
    private val excelVM: ExcelViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentViewExcelBinding.inflate(inflater, container, false)

        with(binding) {
            editBtn.setOnClickListener(this@ViewExcelFragment)
        }
        if (excelVM.viewMode == Constant.EDITMODE) {
            binding.editBtn.visibility = View.VISIBLE
        } else {
            binding.editBtn.visibility = View.GONE
        }

        val fileName = excelVM.filename?.substring(0, excelVM.filename!!.length - 5)

        binding.excelTitle.setText(fileName)

        val adapter = ViewExcelAdapter(readExcelData(), excelVM)
        binding.excelTableView.layoutManager = LinearLayoutManager(context)
        binding.excelTableView.adapter = adapter

        return binding.root
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.editBtn -> {
                val externalFilesDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                val filePath = "${externalFilesDir}/${excelVM.filename}"

               try {
                   // Excel 파일 열기
                   val file = FileInputStream(filePath)
                   val workbook = XSSFWorkbook(file)

                   // 원하는 시트 선택 (예: 첫 번째 시트)
                   val sheet = workbook.getSheetAt(0)

                   for (i in 0 until rowCount) {
                       for (j in 0 until columnCount) {
                           Log.d("TAG", "r${i}c${j}")

                           val parentView = binding.root
                           val editTextWithTag = findEditTextByTag(parentView, "r${i}c${j}")

                           Log.d("VALUE", editTextWithTag?.text.toString())

                           // 원하는 행과 열 선택 (예: 첫 번째 행, 첫 번째 열)
//                           val rowIndex = 0
//                           val columnIndex = 0
                           val cell = sheet.getRow(i).getCell(j)
                           // 셀에 새 값을 설정 (예: "새로운 값")
                           cell.setCellValue(editTextWithTag?.text.toString())

                       }

                   }

                   // 변경된 내용을 파일에 쓰기
                   val fileOut = FileOutputStream(filePath)
                   workbook.write(fileOut)
                   fileOut.close()

                   // 사용 후에는 파일을 닫아주어야 합니다.
                   workbook.close()

                   // 변경 성공 로그
                   Log.d("ExcelModification", "셀 값이 성공적으로 변경되었습니다.")
               }  catch (e: IOException) {
                   e.printStackTrace()
                   // 변경 실패 로그
                   Log.e("ExcelModification", "셀 값 변경 중 오류 발생: ${e.message}")
               }

                navController.popBackStack()
            }
        }
    }

    fun findEditTextByTag(parentView: View, tag: String): EditText? {
        if (parentView is ViewGroup) {
            val childCount = parentView.childCount
            for (i in 0 until childCount) {
                val childView = parentView.getChildAt(i)
                if (childView is EditText && childView.tag == tag) {
                    // Found the EditText with the specified tag
                    return childView
                } else if (childView is ViewGroup) {
                    // Recursively search in nested view groups
                    val result = findEditTextByTag(childView, tag)
                    if (result != null) {
                        return result
                    }
                }
            }
        }
        // If not found, return null
        return null
    }

    var rowCount = 0
    var columnCount = 0

    private fun readExcelData(): List<List<String>> {

        val excelData: MutableList<MutableList<String>> = mutableListOf()
        val externalFilesDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val name = "${externalFilesDir}/${excelVM.filename}"

        try {
            val workbook = WorkbookFactory.create(FileInputStream(name))
            val sheet = workbook.getSheetAt(0)

            rowCount = sheet.physicalNumberOfRows

            columnCount = if (rowCount > 0) {
                sheet.getRow(0).physicalNumberOfCells
            } else {
                0
            }

            for (rowIndex in 0 until rowCount) {
                val row = sheet.getRow(rowIndex)
                val rowData: MutableList<String> = mutableListOf()
                for (columnIndex in 0 until columnCount) {
                    val cell = row.getCell(columnIndex)
                    rowData.add(cell.toString())
                }
                excelData.add(rowData)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return excelData
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

