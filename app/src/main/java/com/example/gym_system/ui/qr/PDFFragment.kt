package com.example.gym_system.ui.qr


import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.pruebadrawer.R
import com.example.pruebadrawer.databinding.FragmentPdfRppBinding
import com.github.barteksc.pdfviewer.PDFView
import java.net.URL

import kotlinx.coroutines.*


open class PDFFragment(cURL: String) : Fragment(R.layout.fragment_pdf_rpp) {

    private val baseurl = cURL

    private lateinit var pdfView: PDFView


    lateinit var btnRegresa: Button

    private var _binding: FragmentPdfRppBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_pdf_rpp, container, false)

        val fragmentManager = parentFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()


//        btnRegresa = binding.btnRegresar
        fragmentTransaction.setReorderingAllowed(true)
//        btnRegresa.setOnClickListener {
//            fragmentTransaction.replace(
//
//                R.id.nav_host_fragment_content_main,
//                QrRppFragment()
//
//            )
//            fragmentTransaction.addToBackStack(null)
//            fragmentTransaction.commit()
//        }

        VerPDF(baseurl)
        // Inflate the layout for this fragment
        return view
    }

    private fun VerPDF(baseurl: String) {

        Thread(Runnable {
            val pdfView = view?.findViewById<PDFView>(R.id.pdfView)
            // Agregar botón de descarga en el diseño
            val downloadButton = view?.findViewById<Button>(R.id.downloadButton)
            val url = baseurl
            val pdfStream = URL(url).openStream()
            pdfView?.fromStream(pdfStream)?.load();
            pdfView?.minZoom = 1.0f
            pdfView?.maxZoom = 3.0f


// Agregar OnClickListener para descargar el archivo PDF
            downloadButton?.setOnClickListener {

                val request = DownloadManager.Request(Uri.parse(url))
                    .setTitle("PDF Descargado")
                    .setDescription("Descarga de PDF")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS,
                        "boleta_general.pdf"
                    )

                // Obtener el servicio de descarga del sistema y agregar la solicitud
                val downloadManager =
                    activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                downloadManager.enqueue(request)
            }


        }).start()
    }

    override fun onDestroy() {
        // liberar los recursos y memoria aquí
        super.onDestroy()
    }


}


