package com.example.xmlandroiduniversity.view.poi

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.xmlandroiduniversity.databinding.FragmentExcelBinding
import com.example.xmlandroiduniversity.viewmodels.ExcelViewModel
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class ExcelFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentExcelBinding? = null
    private val binding get() = _binding!!
    protected lateinit var navController: NavController

    private val excelVM: ExcelViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.filenameField.setText(excelVM.filename + ".xlsx")
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
        _binding = FragmentExcelBinding.inflate(inflater, container, false)

        with(binding) {
            postiveBtn.setOnClickListener(this@ExcelFragment)
            negativeBtn.setOnClickListener(this@ExcelFragment)
        }

        return binding.root
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.postiveBtn -> {
                permissionCheck()
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
        val headers = arrayOf("name", "line", "order")
        for ((index, header) in headers.withIndex()) {
            val cell: Cell = headerRow.createCell(index)
            cell.setCellValue(header)
        }

        // 데이터 추가
        for ((index, data) in excelVM.subwayData.withIndex()) {
            val row = sheet.createRow(index + 1)
            row.createCell(0).setCellValue(data.name)
            row.createCell(1).setCellValue(data.line.toString())
            row.createCell(2).setCellValue(data.order.toString())
        }

        // 파일 저장
        val filename = binding.filenameField.text.toString()
        val externalFilesDir = requireContext().getExternalFilesDir(DIRECTORY_DOWNLOADS)
        if (externalFilesDir != null) {
            val filePath = File(externalFilesDir, "$filename")
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
}