package com.example.antikolektor.Payment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentGoPaymentBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.tinkoff.acquiring.sdk.TinkoffAcquiring
import ru.tinkoff.acquiring.sdk.localization.AsdkSource
import ru.tinkoff.acquiring.sdk.localization.Language
import ru.tinkoff.acquiring.sdk.models.enums.CheckType
import ru.tinkoff.acquiring.sdk.models.options.screen.PaymentOptions
import ru.tinkoff.acquiring.sdk.utils.Money


class GoPaymentFragment : Fragment() {
    lateinit var dataStore: DataStore<Preferences>
    val viewModelServer: TinkoffViewModel by lazy {
        ViewModelProvider(this).get(TinkoffViewModel::class.java)
    }
    val PAYMENT_REQUEST_CODE =1
    lateinit var binding: FragmentGoPaymentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoPaymentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")
//        AcquiringSdk.isDeveloperMode = true
//        AcquiringSdk.isDebug = true
        binding.button15000.setOnClickListener {
            binding.edText.setText(binding.button15000.text)
        }
        binding.button10000.setOnClickListener {
            binding.edText.setText(binding.button10000.text)
        }
        binding.button7500.setOnClickListener {
            binding.edText.setText(binding.button7500.text)
        }
        binding.button4.setOnClickListener {
//            findNavController().navigate(GoPaymentFragmentDirections.actionGoPaymentFragmentToTinkoffPayFragment(binding.edText.text.toString()))
          if(binding.edText.text.isNullOrEmpty()){}else{
            lifecycleScope.launch {
                val value = readToken("qwe")
                viewModelServer.makeRemittance(binding.edText.toString(), "Bearer ${value}")
            }}
        }
        binding.toolbar2.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_goPaymentFragment_to_personalAreaFragment)
        }

        viewModelServer.idRemittance.observe(viewLifecycleOwner) { response ->
            if (response!!.id.isNotEmpty()) {
                Toast.makeText(requireContext(), response.id, Toast.LENGTH_SHORT).show()
                val tinkoffAcquiring = TinkoffAcquiring(requireContext(),
                    "1652349716955DEMO",
                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv5yse9ka3ZQE0feuGtemYv3IqOlLck8zHUM7lTr0za6lXTszRSXfUO7jMb+L5C7e2QNFs+7sIX2OQJ6a+HG8kr+jwJ4tS3cVsWtd9NXpsU40PE4MeNr5RqiNXjcDxA+L4OsEm/BlyFOEOh2epGyYUd5/iO3OiQFRNicomT2saQYAeqIwuELPs1XpLk9HLx5qPbm8fRrQhjeUD5TLO8b+4yCnObe8vy/BMUwBfq+ieWADIjwWCMp2KTpMGLz48qnaD9kdrYJ0iyHqzb2mkDhdIzkim24A3lWoYitJCBrrB2xM05sm9+OdCI1f7nPNJbl5URHobSwR94IRGT7CJcUjvwIDAQAB") // создание объекта для взаимодействия с SDK и передача данных продавца

                val paymentOptions =
                    PaymentOptions().setOptions {
                        orderOptions { // данные заказа
                            orderId = "V2-${response.id}" // ID заказа в вашей системе
                            amount = Money.ofRubles(binding.edText.text.toString().toLong()) // сумма для оплаты

                        }
                        customerOptions { // данные покупателя
                            checkType = CheckType.NO.toString() // тип привязки карты
                            customerKey =
                                "CUSTOMER_KEY" // уникальный ID пользователя для сохранения данных его карты
                            email =
                                "" // E-mail клиента для отправки уведомления об оплате
                        }
                        featuresOptions { // настройки визуального отображения и функций экрана оплаты
                            useSecureKeyboard = true // флаг использования безопасной клавиатуры [2]
                            fpsEnabled = true
                            localizationSource = AsdkSource(Language.RU)
                        }
                    }

                tinkoffAcquiring.openPaymentScreen(
                    requireActivity(),
                    paymentOptions,
                    PAYMENT_REQUEST_CODE
                )

            }
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                findNavController().navigate(R.id.action_goPaymentFragment_to_personalAreaFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            PAYMENT_REQUEST_CODE-> if (resultCode == AppCompatActivity.RESULT_OK) {
                var data = data!!.data
                Toast.makeText(requireContext(), "успешно", Toast.LENGTH_SHORT).show()}
            else->{
                Toast.makeText(requireContext(), "false", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private suspend fun readToken(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }
}