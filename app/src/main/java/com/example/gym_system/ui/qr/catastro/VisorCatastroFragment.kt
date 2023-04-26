package com.example.gym_system.ui.qr.catastro

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import com.example.pruebadrawer.R
import com.example.pruebadrawer.databinding.FragmentHomeBinding


class VisorCatastroFragment(private val cUrlDocumento: String) :
    Fragment(R.layout.fragment_visor_catastro) {

    private var _binding: FragmentHomeBinding? = null
    lateinit var btnRegresa: Button

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val root = inflater.inflate(R.layout.fragment_visor_catastro, container, false)
        webView = root.findViewById(R.id.visorCatastro)
        btnRegresa = root.findViewById(R.id.btnRegresa)
        val fragmentManager = parentFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setReorderingAllowed(true)
        // Habilitar JavaScript
        val settings = webView.settings
        settings.javaScriptEnabled = true

        webView.loadUrl(cUrlDocumento)

        webView.settings.apply {
            javaScriptEnabled = true
            allowContentAccess = true
            domStorageEnabled = true
            useWideViewPort = true
        }

        webView.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(
                view: WebView,
                url: String
            ): Boolean {
                view.loadUrl(url)
                return true
            }
        }

        btnRegresa.setOnClickListener {
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.replace(

                R.id.nav_host_fragment_content_main, QrCatastroFragment()

            )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return root
    }

    override fun onDestroy() {
        // liberar los recursos y memoria aquí
        super.onDestroy()
    }

}