package com.example.xmlandroiduniversity.view.retrofit

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.xmlandroiduniversity.MainActivity
import com.example.xmlandroiduniversity.R
import com.example.xmlandroiduniversity.databinding.FragmentPoiBinding
import com.example.xmlandroiduniversity.databinding.FragmentRetrofitBinding
import com.example.xmlandroiduniversity.db.ExcelFileEntity
import com.example.xmlandroiduniversity.db.RoomDb
import com.example.xmlandroiduniversity.viewmodels.ExcelViewModel
import com.example.xmlandroiduniversity.viewmodels.RetrofitViewModel

class RetrofitFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentRetrofitBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var roomDb: RoomDb
    private lateinit var mainActivity: MainActivity
    private val retrofitVM: RetrofitViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity
        roomDb = mainActivity.roomDb
        navController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentRetrofitBinding.inflate(inflater, container, false)

        with(binding) {
            sendBtn.setOnClickListener(this@RetrofitFragment)
        }

        return binding.root
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.sendBtn -> {
                retrofitVM.fetchData(binding.tickerEDT.text.toString())

                retrofitVM.yfResponseLiveData.observe(this) { yahooFinanceResponse ->
                    // YahooFinanceResponse 객체가 null이 아닌 경우에만 longname을 출력합니다.
                    yahooFinanceResponse?.let { response ->
                        val longName = response.quotes?.get(0)?.longname

                        Log.d("통신", "=============================== $longName")

                    }
                }


            }
        }
    }
}