package com.example.gym_system.ui.qr.catastro

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.pruebadrawer.R
import com.example.pruebadrawer.databinding.FragmentQrCatastroBinding
import com.example.pruebadrawer.databinding.FragmentQrRppBinding
import com.example.pruebadrawer.ui.home.HomeFragment
import com.example.pruebadrawer.ui.qr.rpp.ResultadoQrCatastroFragment
import com.example.pruebadrawer.ui.qr.rpp.ResultadoQrRPPFragment
import com.google.zxing.integration.android.IntentIntegrator


class QrCatastroFragment : Fragment(R.layout.fragment_qr_catastro) {

    private lateinit var binding: FragmentQrCatastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentQrCatastroBinding.inflate(layoutInflater)
        val root: View = binding.root

        val integrator = IntentIntegrator.forSupportFragment(this)



        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Escanea QR de Catastro")
        integrator.setTorchEnabled(false)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragmentManager = parentFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(context, "Cancelado", Toast.LENGTH_LONG).show();
                fragmentTransaction.setReorderingAllowed(true)
                fragmentTransaction.add(
                    R.id.nav_host_fragment_content_main,
                    HomeFragment()
                )
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            } else {
                Toast.makeText(context, "Resultado " + result.contents, Toast.LENGTH_LONG).show();
                fragmentTransaction.setReorderingAllowed(true)
                fragmentTransaction.add(
                    R.id.nav_host_fragment_content_main,
                    ResultadoQrCatastroFragment(result.contents)
                )
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }
    }

    override fun onDestroy() {
        // liberar los recursos y memoria aquí
        super.onDestroy()
    }
}