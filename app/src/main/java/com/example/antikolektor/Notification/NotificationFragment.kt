package com.example.antikolektor.Notification

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.MenuProvider
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.antikolektor.Adapter.AdapterNotif
import com.example.antikolektor.DataNotifCallback
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentNotificationBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class NotificationFragment : Fragment() {
    lateinit var binding: FragmentNotificationBinding
    lateinit var dataStore: DataStore<Preferences>
    val adapter = AdapterNotif { callback1(it) }
    var enabled = false
    val viewModelServer: NotificationViewModel by lazy {
        ViewModelProvider(this)[NotificationViewModel::class.java]
    }

    fun callback1(item: DataNotifCallback) {
        lifecycleScope.launch {
            val value = readToken("qwe")
            viewModelServer.readNotif(item.id, "Bearer $value")
        }
        adapter.removeItem(item.position)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(layoutInflater)
        return binding.root
    }

    fun init() {
        with(binding) {
            rcView.layoutManager = LinearLayoutManager(requireContext())
            rcView.adapter = adapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        dataStore = requireContext().createDataStore(name = "settings")
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(NotificationFragmentDirections.actionNotificationFragmentToPersonalAreaFragment())
        }
        lifecycleScope.launch(Dispatchers.IO) {
            val value = readToken("qwe")
            viewModelServer.getNotif("Bearer $value")
        }
        viewModelServer.getNotification.observe(viewLifecycleOwner) { notif ->
            if (notif?.data.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "error", Toast.LENGTH_LONG).show()
                binding.nullNotif.visibility = View.VISIBLE
                enabled = false

            } else {
                binding.nullNotif.visibility = View.GONE
                adapter.addRepoz(notif!!.data)
                enabled = true

            }
        }
        viewModelServer.readAllNotification.observe(viewLifecycleOwner) { notif ->
            if (notif == "OK") {
                lifecycleScope.launch(Dispatchers.IO) {
                    val value = readToken("qwe")
                    viewModelServer.getNotif("Bearer $value")
                }
            }
        }
        binding.toolbar.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                if (enabled != false){
                    menuInflater.inflate(R.menu.menu_notif, menu)
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                return when (menuItem.itemId) {
                    R.id.read_all -> {

                        lifecycleScope.launch(Dispatchers.IO) {
                            val value = readToken("qwe")
                            viewModelServer.readAllNotification("Bearer $value")
                        }
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(NotificationFragmentDirections.actionNotificationFragmentToPersonalAreaFragment())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)


    }

    private suspend fun readToken(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }
}