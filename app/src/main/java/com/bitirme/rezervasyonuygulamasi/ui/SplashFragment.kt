package com.bitirme.rezervasyonuygulamasi.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bitirme.rezervasyonuygulamasi.R
import com.bitirme.rezervasyonuygulamasi.databinding.FragmentSplashBinding

class SplashFragment:Fragment() {
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().popBackStack(R.id.splash_fragment, true)
            findNavController().navigate(R.id.global_to_home)
        }, 2000)
    }
}