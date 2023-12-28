package com.example.antikolektor.More.AntiKolektor

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.CallLog
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.antikolektor.Adapter.MultiAdapter.ElementOfList
import com.example.antikolektor.Adapter.MultiAdapter.MultiAdapterCall
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentLogCallSortBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class LogCallSortFragment : Fragment() {

    private val adapterCall = MultiAdapterCall { callback1(it) }
    lateinit var binding: FragmentLogCallSortBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogCallSortBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCall()
        if (binding.nameSort.text == "Все") {
            initCallContact()
        }
        binding.popap.setOnClickListener {
            showMenu()

        }
    }

    fun callback1(it: ElementOfList.Item) {
        if (!TextUtils.isEmpty(it.number)) {
            val dial = "tel:${it.number}"
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(dial)))
        } else {
            Toast.makeText(requireContext(), "Enter a phone number", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initCall() {
        with(binding) {
            rcCallVew.layoutManager = LinearLayoutManager(requireContext())
            rcCallVew.adapter = adapterCall
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("Range", "SimpleDateFormat")
    fun initCallContact() {
        val arrayCallContatct = arrayListOf<ElementOfList.Item>()
        val arraycallTwo = arrayListOf<ElementOfList.Item>()
        val arraycallyesterday = arrayListOf<ElementOfList.Item>()
        val cursor = requireActivity().contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            null, null, null, CallLog.Calls.DATE + " DESC"
        )
        cursor?.let {
            while (it.moveToNext()) {
                val number = it.getString(it.getColumnIndex(CallLog.Calls.NUMBER))
                val type = it.getString(it.getColumnIndex(CallLog.Calls.TYPE))
                val date = it.getString(it.getColumnIndex(CallLog.Calls.DATE))

                var duration = it.getString(it.getColumnIndex(CallLog.Calls.CACHED_NAME))
                if (duration.isNullOrEmpty()) {
                    duration = "name"
                }
                val dateTime = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd MMM yyyy")).toString()
                val dateBuff = SimpleDateFormat("dd MMM yyyy").format(Date(date.toLong()))
                    .toString()
                val dateFormat = SimpleDateFormat("dd MMM yyyy").format(yesterday())
                when (binding.nameSort.text) {
                    "Входящие" -> {
                        if (type.toInt() == 1) {
                            when (dateBuff) {
                                dateTime -> {
                                    val result = filling(number, type, date, duration)
                                    arrayCallContatct.add(result)
                                }
                                dateFormat -> {
                                    val result = filling(number, type, date, duration)
                                    arraycallyesterday.add(result)
                                }
                                else -> {
                                    val result = filling(number, type, date, duration)
                                    arraycallTwo.add(result)
                                }
                            }
                        }
                    }
                    "Все" -> {
                        when (dateBuff) {
                            dateTime -> {
                                val result = filling(number, type, date, duration)
                                arrayCallContatct.add(result)
                            }
                            dateFormat -> {
                                val result = filling(number, type, date, duration)
                                arraycallyesterday.add(result)
                            }
                            else -> {
                                val result = filling(number, type, date, duration)
                                arraycallTwo.add(result)
                            }
                        }
                    }
                    "Исходящие" -> {
                        if (type.toInt() == 2) {
                            when (dateBuff) {
                                dateTime -> {
                                    val result = filling(number, type, date, duration)
                                    arrayCallContatct.add(result)
                                }
                                dateFormat -> {
                                    val result = filling(number, type, date, duration)
                                    arraycallyesterday.add(result)
                                }
                                else -> {
                                    val result = filling(number, type, date, duration)
                                    arraycallTwo.add(result)
                                }
                            }
                        }
                    }
                    "Пропущенные" -> {
                        if (type.toInt() == 3) {
                            when (dateBuff) {
                                dateTime -> {
                                    val result = filling(number, type, date, duration)
                                    arrayCallContatct.add(result)
                                }
                                dateFormat -> {
                                    val result = filling(number, type, date, duration)
                                    arraycallyesterday.add(result)
                                }
                                else -> {
                                    val result = filling(number, type, date, duration)
                                    arraycallTwo.add(result)
                                }
                            }
                        }
                    }
                    "Заблокированные" -> {
                        if (type.toInt() == 6) {
                            when (dateBuff) {
                                dateTime -> {
                                    val result = filling(number, type, date, duration)
                                    arrayCallContatct.add(result)
                                }
                                dateFormat -> {
                                    val result = filling(number, type, date, duration)
                                    arraycallyesterday.add(result)
                                }
                                else -> {
                                    val result = filling(number, type, date, duration)
                                    arraycallTwo.add(result)
                                }
                            }
                        }
                    }
                    "Отклоненные" -> {
                        if (type.toInt() == 5) {
                            when (dateBuff) {
                                dateTime -> {
                                    val result = filling(number, type, date, duration)
                                    arrayCallContatct.add(result)
                                }
                                dateFormat -> {
                                    val result = filling(number, type, date, duration)
                                    arraycallyesterday.add(result)
                                }
                                else -> {
                                    val result = filling(number, type, date, duration)
                                    arraycallTwo.add(result)
                                }
                            }
                        }
                    }
                }
            }
        }
        val param = mutableListOf<ElementOfList.DataDate>()
        param.add(ElementOfList.DataDate("СЕГОДНЯ"))
        param.add(ElementOfList.DataDate("ВЧЕРА"))
        param.add(ElementOfList.DataDate("РАНЕЕ"))

        val newModel = arrayListOf<ElementOfList>(
        )
        if (arrayCallContatct.isNotEmpty()) {
            newModel.add(param[0])
            newModel.addAll(arrayCallContatct)
        }
        if (arraycallyesterday.isNotEmpty()) {
            newModel.add(param[1])
            newModel.addAll(arraycallyesterday)
        }
        if (arraycallTwo.isNotEmpty()) {
            newModel.add(param[2])
            newModel.addAll(arraycallTwo)
        }
        adapterCall.addRepoz(newModel)
        cursor?.close()
    }

    private fun filling(
        number: String,
        type: String,
        date: String,
        duration: String?
    ): ElementOfList.Item {
        val newModel = ElementOfList.Item("", "", "", "")
        newModel.number = number.replace(
            regex = Regex("[\\s,-]"),
            replacement = ""
        )
        newModel.type = type
        newModel.date = date
        newModel.duration = duration!!
        return newModel
    }

    private fun yesterday(): Date {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        return cal.time
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showMenu() {
        val popup = PopupMenu(context, binding.popap)

        popup.apply {
            menuInflater.inflate(R.menu.menu_sort, menu)

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.allItem -> {
                        binding.nameSort.text = "Все"
                        initCallContact()
                    }
                    R.id.WelcomItem -> {
                        binding.nameSort.text = "Входящие"
                        initCallContact()
                    }
                    R.id.outgoingItem -> {
                        binding.nameSort.text = "Исходящие"
                        initCallContact()
                    }
                    R.id.MissedItem -> {
                        binding.nameSort.text = "Пропущенные"
                        initCallContact()
                    }
                    R.id.BlockedItem -> {
                        binding.nameSort.text = "Заблокированные"
                        initCallContact()
                    }
                    R.id.ReceivItem -> {
                        binding.nameSort.text = "Отклоненные"
                        initCallContact()
                    }
                }
                false
            }
        }
        popup.show()
    }
}