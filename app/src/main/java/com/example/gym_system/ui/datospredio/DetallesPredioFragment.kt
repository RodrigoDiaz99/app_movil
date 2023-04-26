package com.example.gym_system.ui.datospredio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.example.pruebadrawer.R
import com.example.pruebadrawer.databinding.FragmentDetallesPredioBinding
import com.example.gym_system.interfaces.APIServices
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient


class DetallesPredioFragment(
    iIDPredio: Int,
    cCalle: Int,
    cNumero: Int,
    cManzana: Int,
    cTablaje: Int,
    cSeccion: Int,
    cMunicipio: String,
    opcion: Int
) : Fragment(R.layout.fragment_detalles_predio) {

    private val iIDPredio = iIDPredio
    private val cMunicipio = cMunicipio
    private val cCalle = cCalle
    private val cManzana = cManzana
    private val cNumero = cNumero
    private val cTablaje = cTablaje
    private val cSeccion = cSeccion
    private val opcion = opcion

    lateinit var title: TextView
    lateinit var llcontenedor: LinearLayout
    val gson2 = Gson()
    lateinit var btnRegresa: Button
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager


    private var _binding: FragmentDetallesPredioBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetallesPredioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val fragmentManager = parentFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        title = binding.txtDatosPredio
        title.append("FOLIO: " + iIDPredio)
        //llcontenedor = binding.llcontenedor
        btnRegresa = binding.btnRegresar

        btnRegresa.setOnClickListener {
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.replace(

                R.id.nav_host_fragment_content_main,
                ResultadosPredioFragment(
                    iIDPredio,
                    cCalle,
                    cNumero,
                    cManzana,
                    cTablaje,
                    cSeccion,
                    cMunicipio,
                    opcion
                )

            )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }
        // val btn: BottomNavigationView = binding.bottomNavigationView
        tabLayout = binding.tabLayaout
        viewPager = binding.viewPager

        tabLayout.addTab(tabLayout.newTab().setText("Cedula"))
        tabLayout.addTab(tabLayout.newTab().setText("Inscripcion"))
        tabLayout.addTab(tabLayout.newTab().setText("Nomenclatura"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = ViewPagerAdapater(this, parentFragmentManager, tabLayout.tabCount, iIDPredio)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
//    private fun openFragment(fragment: Fragment) {
//        val fragmentManager = parentFragmentManager
//        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fragment_content_detalles_predio2, fragment)
//        fragmentTransaction.commit()
//    }

}