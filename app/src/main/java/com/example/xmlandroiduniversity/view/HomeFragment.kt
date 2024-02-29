package com.example.xmlandroiduniversity.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.xmlandroiduniversity.MainActivity
import com.example.xmlandroiduniversity.R
import com.example.xmlandroiduniversity.adapter.HomeGridAdapter
import com.example.xmlandroiduniversity.adapter.HomeGridAdapterListener
import com.example.xmlandroiduniversity.adapter.POIAdapterListener
import com.example.xmlandroiduniversity.data.home.MenuItem
import com.example.xmlandroiduniversity.databinding.FragmentHomeBinding
import com.example.xmlandroiduniversity.databinding.FragmentPoiBinding
import com.example.xmlandroiduniversity.db.ExcelFileEntity
import com.example.xmlandroiduniversity.db.RoomDb
import com.example.xmlandroiduniversity.global.Constant

class HomeFragment : Fragment(), View.OnClickListener, HomeGridAdapterListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var roomDb: RoomDb
    private lateinit var mainActivity: MainActivity

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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val menuItems = listOf(MenuItem(1, "POI"), MenuItem(2, "Handler"), MenuItem(3, "Retrofit2"))
        val adapter = HomeGridAdapter(requireContext(), menuItems, adapterListener)

        with(binding) {
            homeGrid.adapter = adapter
        }

        return (binding.root)
    }

    override fun onClick(v: View?) {

    }

    private val adapterListener = object : HomeGridAdapterListener {
        override fun onListItemPressed(data: MenuItem) {
            when (data.id) {
                1 -> {
                    navController.navigate(R.id.action_homeFragment_to_poiFragment)
                }
                2 -> {
                    navController.navigate(R.id.action_homeFragment_to_basicHandlerFragment)
                }
                3 -> {
                    navController.navigate(R.id.action_homeFragment_to_retrofitFragment)
                }
            }
        }
    }

}