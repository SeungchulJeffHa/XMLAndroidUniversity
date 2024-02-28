package com.example.xmlandroiduniversity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.xmlandroiduniversity.databinding.ActivityMainBinding
import com.example.xmlandroiduniversity.db.RoomDb

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    private val navController by lazy { findNavController(R.id.mainNavHostFragment)}

    internal lateinit var roomDb: RoomDb

    override fun onCreate(savedInstanceState: Bundle?) {

        roomDb = RoomDb.invoke(this)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}