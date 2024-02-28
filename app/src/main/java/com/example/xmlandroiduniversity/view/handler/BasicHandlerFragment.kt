package com.example.xmlandroiduniversity.view.handler

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.xmlandroiduniversity.MainActivity
import com.example.xmlandroiduniversity.R
import com.example.xmlandroiduniversity.databinding.FragmentBasicHandlerBinding
import com.example.xmlandroiduniversity.databinding.FragmentHomeBinding
import com.example.xmlandroiduniversity.db.RoomDb

class BasicHandlerFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentBasicHandlerBinding? = null
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
        _binding = FragmentBasicHandlerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onClick(v: View?) {
        when(v) {

        }
    }

}