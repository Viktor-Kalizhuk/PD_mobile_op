package com.example.app_spisho_dolgi.VPAdapter

import android.content.res.Resources
import androidx.fragment.app.*
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.antikolektor.VpContent.ContentFragment1
import com.example.antikolektor.VpContent.ContentFragment2
import com.example.antikolektor.VpContent.ContentFragment3


class VpAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
               ContentFragment1()
            }
            1 -> {
                ContentFragment2()
            }
            2 -> {
                ContentFragment3()
            }
            else -> {
                throw Resources.NotFoundException("Position Not Found")
            }
        }
    }


}