package com.example.antikolektor.More.AntiKolektor

import android.Manifest
import android.app.role.RoleManager
import android.content.Context.ROLE_SERVICE
import android.content.Context.TELECOM_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.telecom.TelecomManager
import android.telecom.TelecomManager.ACTION_CHANGE_DEFAULT_DIALER
import android.telecom.TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.antikolektor.R
import com.example.antikolektor.VPAdapter.VpAdapterKolektor
import com.example.antikolektor.databinding.FragmentAntiKolektorMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.skydoves.balloon.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class AntiKolektorMainFragment : Fragment() {

    lateinit var dataStore: DataStore<Preferences>
    private val REQUEST_ID = 1
    val InfoBotomSheet = InfoFragment()
    private lateinit var plauncher: ActivityResultLauncher<Array<String>>
    lateinit var binding: FragmentAntiKolektorMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAntiKolektorMainBinding.inflate(layoutInflater)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")

        registerPermishnListener()
        lifecycleScope.launch {
            val value = readToken("switch")

            CheckPermishn(value.toString())

        }
        requestScreeningRole()
        binding.switch1.setOnClickListener {
            Toast.makeText(
                requireContext(),
                binding.switch1.isChecked.toString(),
                Toast.LENGTH_SHORT
            ).show()
            if (binding.switch1.isChecked == true) {
                lifecycleScope.launch(Dispatchers.IO) {
                    save(
                        "switch", "true"
                    )
                }
                plauncher.launch(
                    arrayOf(
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_CONTACTS
                    )
                )
            } else {
                if (binding.switch1.isChecked == false) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        save(
                            "switch", "false"
                        )
                    }
                }
            }
        }

        binding.imageView3.setOnClickListener {
            InfoBotomSheet.setStyle(
                BottomSheetDialogFragment.STYLE_NORMAL,
                R.style.BottomSheetTheme
            )
            InfoBotomSheet.show(requireActivity().supportFragmentManager, "TAgg")
        }
        binding.viewpagerKolector.adapter = VpAdapterKolektor(requireActivity())

        TabLayoutMediator(binding.tablayout, binding.viewpagerKolector) { tab, index ->
            tab.text = when (index) {
                0 -> {
                    "Черный список"
                }
                1 -> {
                    "Журнал звонков"
                }

                else -> {
                    throw Resources.NotFoundException("Position Not Found")
                }
            }
        }.attach()

        binding.toolbar2.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_antiKolektorMainFragment_to_moreFragment)
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_antiKolektorMainFragment_to_moreFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun CheckPermishn(value: String) {

        if (value == "true" && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_PHONE_STATE
            )
            == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CALL_LOG
            )
            == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(requireContext(), "Permishn  Ok", Toast.LENGTH_LONG).show()
            binding.switch1.isChecked = true
        } else {
            val balloon = createBalloon(requireContext()) {
                setArrowSize(10)
                setHeight(65)
//                    setArrowPosition(0.7f)
                setCornerRadius(4f)
                setAlpha(0.9f)
                setText("Включите \nантиколектор")
                setTextColorResource(R.color.white)
                setTextSize(12.0f)
                setTextGravity(0)
                paddingBottom = 12
                paddingTop = 12
                paddingRight = 40
                paddingLeft = 12
                setBackgroundColorResource(R.color.accent_blue)
                setBalloonAnimation(BalloonAnimation.FADE)
                setLifecycleOwner(lifecycleOwner)
            }
//               binding.switch1.tooltipText="Включите антиколектор"
            balloon.showAlignBottom(binding.switch1, 0, 0)
        }

    }

    private fun registerPermishnListener() {
        plauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                if (it[Manifest.permission.READ_PHONE_STATE] == true) {
                    Toast.makeText(requireContext(), "Permishn Ok", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Permishn denied", Toast.LENGTH_LONG).show()
                }
                if (it[Manifest.permission.READ_CALL_LOG] == true) {
                    Toast.makeText(requireContext(), "Permishn Ok", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Permishn denied", Toast.LENGTH_LONG).show()
                }
                if (it[Manifest.permission.READ_CONTACTS] == true) {
                    Toast.makeText(requireContext(), "Permishn Ok", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Permishn denied", Toast.LENGTH_LONG).show()
                }
            }
    }



    private fun requestScreeningRole() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = requireContext().getSystemService(ROLE_SERVICE) as RoleManager
            val isHeld = roleManager.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)
            if (!isHeld) {
                val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING)
                startActivityForResult(intent, 123)
            }
        } else {
            val telecomManager: TelecomManager =
                requireContext().getSystemService(TELECOM_SERVICE) as TelecomManager
            if (!activity?.packageName.equals(telecomManager.defaultDialerPackage)) {
                val intent: Intent = Intent(ACTION_CHANGE_DEFAULT_DIALER)
                    .putExtra(EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, activity?.packageName)
                startActivity(intent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ID) {
            if (resultCode == AppCompatActivity.RESULT_OK) {

            } else {

            }
        }
    }

    private suspend fun save(key: String, value: String) {
        val dataStorekey = preferencesKey<String>(key)
        dataStore.edit { settings ->
            settings[dataStorekey] = value
        }
    }

    private suspend fun readToken(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }

}