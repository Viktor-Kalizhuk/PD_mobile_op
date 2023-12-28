package com.example.antikolektor.VPAdapter

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.antikolektor.More.AntiKolektor.BlackListFragment
import com.example.antikolektor.More.AntiKolektor.LogCallFragment
import com.example.antikolektor.More.AntiKolektor.LogCallSortFragment
import com.example.antikolektor.VpContent.ContentFragment1
import com.example.antikolektor.VpContent.ContentFragment2
import com.example.antikolektor.VpContent.ContentFragment3

class VpAdapterKolektor(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                BlackListFragment()
            }
            1 -> {
                LogCallSortFragment()
            }


            else -> {
                throw Resources.NotFoundException("Position Not Found")
            }
        }
    }


}