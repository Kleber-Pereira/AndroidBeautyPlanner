package com.kleber.beautyplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kleber.beautyplanner.Agendas.AgendaFragment
import com.kleber.beautyplanner.Cliente.ClienteFragment

import com.kleber.beautyplanner.Funcionarios.FuncionarioFragment
import com.kleber.beautyplanner.Home.HomeFragment
import com.kleber.beautyplanner.Servicos.ServicosFragment


class MainActivity : AppCompatActivity() {
    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BeautyPlanner)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, HomeFragment())
            .commit()
        val bottomNav: BottomNavigationView = findViewById(R.id.main_menu)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

    }

    val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.menu_home -> {
                currentFragment = HomeFragment()
            }
            R.id.menu_Servicos -> {
                currentFragment = ServicosFragment()
            }
            R.id.menu_Funcionario -> {
                currentFragment = FuncionarioFragment()
            }
            R.id.menu_Agenda -> {
                currentFragment = AgendaFragment()
            }

            R.id.menu_cliente -> {
                currentFragment = ClienteFragment()
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, currentFragment)
            .commit()
        true
    }

}





