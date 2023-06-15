package com.bitirme.rezervasyonuygulamasi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bitirme.rezervasyonuygulamasi.R
import com.bitirme.rezervasyonuygulamasi.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val stringArray =
        arrayOf("- Doğum Günü", "- Evlilik Teklifi", "- İş Yemeği", "- Standart Oturum")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemsText = stringArray.joinToString("\n")
        binding.textView.text = itemsText

        binding.tvClick.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_detailFragment_to_reservationFragment)
        }
    }
}