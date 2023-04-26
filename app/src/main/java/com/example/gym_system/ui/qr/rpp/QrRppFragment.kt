package com.example.gym_system.ui.qr.rpp

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.pruebadrawer.R
import com.example.pruebadrawer.databinding.FragmentQrRppBinding
import com.example.pruebadrawer.interfaces.SweetAlert
import com.example.pruebadrawer.ui.home.HomeFragment
import com.google.zxing.integration.android.IntentIntegrator

open class QrRppFragment : Fragment(R.layout.fragment_qr_rpp) {
    private lateinit var binding: FragmentQrRppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentQrRppBinding.inflate(layoutInflater)
        val root: View = binding.root

        @Suppress("DEPRECATION")
        val integrator = IntentIntegrator.forSupportFragment(this)


        @Suppress("DEPRECATION")
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Escanea QR de RPP")
        integrator.setTorchEnabled(false)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragmentManager = parentFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                SweetAlert.showErrorDialog(requireContext(), "Cancelado")

                fragmentTransaction.setReorderingAllowed(true)
                    .replace(
                        R.id.nav_host_fragment_content_main,
                        HomeFragment()
                    )
                    .addToBackStack(null)
                    .commit()
            } else {
                SweetAlert.showSuccessDialog(requireContext(), "Resultado")
                fragmentTransaction.setReorderingAllowed(true)
                    .replace(
                        R.id.nav_host_fragment_content_main,
                        ResultadoQrRPPFragment(result.contents)
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}


