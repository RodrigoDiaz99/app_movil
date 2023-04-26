package com.example.gym_system

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.gym_system.databinding.ActivityMainBinding
import com.example.gym_system.interfaces.SweetAlert
import com.example.gym_system.ui.home.HomeFragment
import com.example.gym_system.ui.productos.ProductsFragment


import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (isInternetAvailable()) {
            setSupportActionBar(binding.appBarMain.toolbar)

            val fragmentManager = supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

            drawerLayout = binding.drawerLayout
            val navView: NavigationView = binding.navView
            val navController = findNavController(R.id.nav_host_fragment_content_main)
            fragmentTransaction.setReorderingAllowed(true);
            toggle = ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_home,
                    R.id.nav_qr_rpp,
                    R.id.nav_qr_catastro,
                    R.id.nav_productos,
                    R.id.nav_predio,
                    R.id.nav_tomo
                ), drawerLayout
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


            navView.setNavigationItemSelectedListener {

                it.isChecked = true

                when (it.itemId) {
                    R.id.nav_home -> replaceFragment(HomeFragment())
                    R.id.nav_productos -> replaceFragment(ProductsFragment())
//                    R.id.nav_qr_rpp -> replaceFragment(QrRppFragment())
//                    R.id.nav_qr_catastro -> replaceFragment(QrCatastroFragment())
//                    R.id.nav_tomo -> replaceFragment(DatosTomoFragment())
//                    R.id.nav_predio -> replaceFragment(ConsultarPredioFragment())


                }
                true
            }

            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
        } else {
            SweetAlert.showErrorDialog(this, "No cuenta su dispositivo con conexion a internet")
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
    }

    @SuppressLint("ServiceCast")
    fun Context.isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting == true
    }

    override fun onDestroy() {
        // liberar los recursos y memoria aquí
        super.onDestroy()
        finish()
    }
}