package com.example.antikolektor.Autarization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentEntryStorisBinding
import com.example.app_spisho_dolgi.VPAdapter.VpAdapter


class EntryStorisFragment : Fragment() {
    lateinit var binding: FragmentEntryStorisBinding
    var selectedSort: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEntryStorisBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewpager.adapter = VpAdapter(requireActivity())
        requireActivity().supportFragmentManager
            .setFragmentResultListener("request", requireActivity()) { key, bundle ->
                selectedSort = bundle.getString("position")
                when (selectedSort) {
                    "nextTwo" -> {
                        binding.viewpager.setCurrentItem(1)
                    }
                    "next3" -> {
                        binding.viewpager.setCurrentItem(2)
                    }
                    "nextMain" -> {
                        findNavController().navigate(R.id.action_entryStorisFragment_to_personalAreaFragment)
                    }

                }
            }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)


//        val storiesList = listOf<StoryItem>(
//            StoryItem(url = "https://fakeimg.pl/100x100/"),
//            StoryItem(url = "https://fakeimg.pl/100x100/"),
//            StoryItem(url = "https://fakeimg.pl/100x100/"),
//            StoryItem(url = "https://fakeimg.pl/100x100/")
//        )
//
//        val storiesView = binding.stories
//        storiesView.setStoriesList(storiesList)

    }


//    override fun onStoriesEnd() {
//        println("Stories end callback")
//    }
}