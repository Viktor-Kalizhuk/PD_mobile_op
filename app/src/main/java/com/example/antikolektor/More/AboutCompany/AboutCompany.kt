package com.example.antikolektor.More.AboutCompany

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.antikolektor.Adapter.Expandble.Language
import com.example.antikolektor.Adapter.Expandble.RvAdapter
import com.example.antikolektor.databinding.FragmentAboutCompanyBinding


class AboutCompany : Fragment() {
    private var _binding: FragmentAboutCompanyBinding? = null
    private val binding get() = _binding!!
    private var languageList = ArrayList<Language>()
    private lateinit var rvAdapter: RvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutCompanyBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // define layout manager for the Recycler view
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())

        // attach adapter to the recycler view
        rvAdapter = RvAdapter(languageList)
        binding.rvList.adapter = rvAdapter

        // create new objects
        // add some row data
        val language1 = Language(
            "Списание долгов по 127-ФЗ",
            "В случае, если Ваши платежи по кредитам и займам практически равны или превышают Ваш официальный доход, Вы в праве обратиться в арбитражный суд для списания долгов по 127-ФЗ «О несостоятельности (Банкротстве). Вы можете списать долги:\n" +
                    " 1.  По кредитам\n" +
                    "\n" +
                    " 2.  По займам\n" +
                    "\n" +
                    " 3.  По коммунальным платежам\n" +
                    "\n" +
                    " 4.  Долги в ФССП (На приставах)\n" +
                    "\n" +
                    " 5.  Расчеты сумм в соответствии со ставкой банковского процента (последняя страница в файле с исковым заявлением)\n" +
                    "\n" +
                    " 6.  Большую часть налогов\n" +
                    "Для этого необходимо собрать пакет документов на банкротство, подготовить заявление на банкротство, выбрать правильно финансового управляющего и подать в арбитражный суд. После этого в течении 6-8-ми месяцев суд выносит решение о списании долгов. Наши юристы готовы полностью сопроводить Вас по всей процедуре\n" +
                    "Когда Вы начинаете работать с нами, Вы на законных основаниях прекращаете все выплаты по кредитам и займам. Эти деньги Вы можете потратить на себя и свою семью!\n",
            false
        )
        val language2 = Language(
            "Защита от звонков кредиторов и коллекторов по 230-ФЗ",
            "Согласно 230-ФЗ «О защите прав заемщика…» Вы можете оградить себя и своих близких от беспокойства и звонков коллекторов и служб взыскания банков и МФО. Согласно этому закону кредиторам и коллекторам запрещено звонить третьим лицам и на работу заемщика. Так же заемщик имеет право полностью отказаться от взаимодействия с кредиторами и коллекторами, если у Вас более 4-х месяцев просрочки. \n" +
                    "\n" +
                    "Наши юристы помогут правильно составить все необходимые документы и оградят Вас от негативных звонков в первые 1-3 месяца работы!\n",
            false
        )
        val language3 = Language(
            "Минимизация долгов",
            "Если Вам по каким-то причинам не подходит списание долгов, то у нас есть альтернатива – фиксация суммы долга и снижение выплат по кредитам и займам до половины официального дохода (а в отдельных случаях и до 3000-5000 рублей). Так как согласно ст. 138 Трудового кодекса РФ, и 229-ФЗ «Об исполнительном производстве» судебные приставы не имеют права удерживать более 50% от Вашего официального дохода, а также обязаны оставить Вам прожиточный минимум, если у Вас низкий официальный доход. \n" +
                    "\n" +
                    "Наши юристы помогут Вам снизить долг и ежемесячные выплаты в рамках программы минимизации",
            false
        )


        // add items to list
        languageList.add(language1)
        languageList.add(language2)
        languageList.add(language3)


        rvAdapter.notifyDataSetChanged()

        binding.ButtonVk.setOnClickListener {
            val browserIntent: Intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/pravoedelo_llc"))
            startActivity(browserIntent)
        }
        binding.ButtonYouTube.setOnClickListener {
            val browserIntent: Intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UC4KKLKXnwX3CUod2bMw4OyQ"))
            startActivity(browserIntent)
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(com.example.antikolektor.R.id.action_aboutCompany_to_moreFragment)
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(com.example.antikolektor.R.id.action_aboutCompany_to_moreFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}