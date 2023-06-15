package com.bitirme.rezervasyonuygulamasi.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bitirme.rezervasyonuygulamasi.R
import com.bitirme.rezervasyonuygulamasi.databinding.FragmentHomeBinding

class HomeFragment:Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnStart.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_home_to_cafeFragment)
        }
    }


}