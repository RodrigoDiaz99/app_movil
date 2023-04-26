package com.example.gym_system.ui.datospredio

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.pruebadrawer.ui.datospredio.detallespredio.CedulaFragment
import com.example.pruebadrawer.ui.datospredio.detallespredio.InscripcionFragment
//import com.example.pruebadrawer.ui.datospredio.detallespredio.InscripcionFragment
import com.example.pruebadrawer.ui.datospredio.detallespredio.NomenclaturaFragment

internal class ViewPagerAdapater(
    var context: DetallesPredioFragment,
    fm: FragmentManager,
    var totaltabs: Int,
    iIDPredio: Int
) : FragmentPagerAdapter(fm) {

    private val iIDPredio = iIDPredio
    //  private val cMunicipio = cMunicipio


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                CedulaFragment(iIDPredio)
            }

            1 -> {
                InscripcionFragment(iIDPredio)
            }

            2 -> {

                NomenclaturaFragment(iIDPredio)
            }
            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totaltabs
    }


}