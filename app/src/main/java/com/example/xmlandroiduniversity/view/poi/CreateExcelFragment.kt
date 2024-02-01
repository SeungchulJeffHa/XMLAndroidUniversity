package com.example.xmlandroiduniversity.view.poi

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xmlandroiduniversity.R
import com.example.xmlandroiduniversity.databinding.FragmentCreateExcelBinding
import com.example.xmlandroiduniversity.viewmodels.ExcelViewModel
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CreateExcelFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentCreateExcelBinding? = null
    private val binding get() = _binding!!
    protected lateinit var navController: NavController

    private val excelVM: ExcelViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filenameField.setText(excelVM.filename)
        binding.columnCount.setText(excelVM.colCount.toString())
        binding.rowCount.setText(excelVM.rowCount.toString())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentCreateExcelBinding.inflate(inflater, container, false)

        with(binding) {
            postiveBtn.setOnClickListener(this@CreateExcelFragment)
            negativeBtn.setOnClickListener(this@CreateExcelFragment)
            addColBtn.setOnClickListener(this@CreateExcelFragment)
            addRowBtn.setOnClickListener(this@CreateExcelFragment)
        }

        return binding.root
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.addColBtn -> {
                excelVM.colCount += 1
                binding.columnCount.setText(excelVM.colCount.toString())

                val allboxLayout = binding.root.findViewById<LinearLayout>(R.id.allbox)
                allboxLayout.removeAllViews()

                for (i in 1..excelVM.rowCount) {
                    val tagname = "row${i}"
                    val row = binding.root.findViewWithTag<LinearLayout>(tagname)

                    if (row != null) {
                        val newEditText = EditText(requireContext())
                        newEditText.layoutParams = LinearLayout.LayoutParams(
                            80.dpToPx(),
                            50.dpToPx()
                        )
                        newEditText.setBackgroundResource(R.drawable.border_background)
                        newEditText.tag = "row${i}col${excelVM.colCount}"
                        row.addView(newEditText)
                    } else {
                        Toast.makeText(context, "널이야", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            binding.addRowBtn -> {
                excelVM.rowCount += 1
                binding.columnCount.setText(excelVM.rowCount.toString())

                for (i in 1..excelVM.rowCount) {
                    val newLinearLayout = LinearLayout(requireContext())
                    newLinearLayout.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    newLinearLayout.orientation = LinearLayout.HORIZONTAL
                    newLinearLayout.tag = "row${excelVM.rowCount}"

                    val boxLayout = binding.root.findViewById<LinearLayout>(R.id.allbox)
                    boxLayout.addView(newLinearLayout)

                    for (j in 1..excelVM.colCount) {
                        val tagname = "row${i}"
                        val row = binding.root.findViewWithTag<LinearLayout>(tagname)

                        if (row != null) {
                            val newEditText = EditText(requireContext())
                            newEditText.layoutParams = LinearLayout.LayoutParams(
                                80.dpToPx(),
                                50.dpToPx()
                            )
                            newEditText.setBackgroundResource(R.drawable.border_background)
                            newEditText.tag = "row${i}col${excelVM.colCount}"
                            row.addView(newEditText)
                        } else {
                            Toast.makeText(context, "널이야", Toast.LENGTH_SHORT).show()
                        }
                    }

//                    for (j in 1..excelVM.colCount) {
//                        val newEditText = EditText(requireContext())
//                        newEditText.layoutParams = LinearLayout.LayoutParams(
//                            80.dpToPx(),
//                            50.dpToPx()
//                        )
//                        newEditText.setBackgroundResource(R.drawable.border_background)
//                        newEditText.tag = "row${excelVM.rowCount}col${excelVM.colCount}"
//                        newLinearLayout.addView(newEditText)
//                        val boxLayout = binding.root.findViewById<LinearLayout>(R.id.allbox)
//                        boxLayout.addView(newLinearLayout)
//                    }
                }






            }

            binding.postiveBtn -> {
                if (binding.filenameField.text.toString().isNotEmpty()) {
                    permissionCheck()
                } else {
                    Toast.makeText(context, "파일명을 입력해주셔야합니다", Toast.LENGTH_SHORT).show();
                }
            }

            binding.negativeBtn -> {
                navController.popBackStack()
            }
        }
    }

    private fun permissionCheck() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                123
            )
        } else {
            createExcelFile()
        }
    }

    private fun createExcelFile() {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("subway")

        // 헤더 추가
        val headerRow = sheet.createRow(0)
        val headers = arrayOf("name", "line", "order", "name", "name", "line", "order", "name", "name", "line", "order", "name")
        for ((index, header) in headers.withIndex()) {
            val cell: Cell = headerRow.createCell(index)
            cell.setCellValue(header)
        }

        // 데이터 추가
        for (i in 0 until 20) {
            val row = sheet.createRow(i + 1)
            row.createCell(0).setCellValue("${i + 1}")
            row.createCell(1).setCellValue("${i + 1}")
            row.createCell(2).setCellValue("${i + 1}")
            row.createCell(3).setCellValue("${i + 1}")
            row.createCell(4).setCellValue("${i + 1}")
            row.createCell(5).setCellValue("${i + 1}")
            row.createCell(6).setCellValue("${i + 1}")
            row.createCell(7).setCellValue("${i + 1}")
            row.createCell(8).setCellValue("${i + 1}")
            row.createCell(9).setCellValue("${i + 1}")
            row.createCell(10).setCellValue("${i + 1}")
            row.createCell(11).setCellValue("${i + 1}")
        }
//        for ((index, data) in excelVM.subwayData.withIndex()) {
//            val row = sheet.createRow(index + 1)
//            row.createCell(0).setCellValue(data.name)
//            row.createCell(1).setCellValue(data.line.toString())
//            row.createCell(2).setCellValue(data.order.toString())
//        }

        // 파일 저장
        val filename = binding.filenameField.text.toString()
        val externalFilesDir = requireContext().getExternalFilesDir(DIRECTORY_DOWNLOADS)
        if (externalFilesDir != null) {
            val filePath = File(externalFilesDir, "$filename.xlsx")
            Log.d("파일 경로", filePath.absolutePath)

            try {
                // 파일 작업 수행
                val fileOut = FileOutputStream(filePath)
                workbook.write(fileOut)
                fileOut.close()
                workbook.close()

                Log.d("생성 성공", "==================================================== 생성성공")

                navController.popBackStack()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            Log.e("에러", "External files directory is null")
        }

    }

    fun Int.dpToPx(): Int {
        val scale = resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }
}