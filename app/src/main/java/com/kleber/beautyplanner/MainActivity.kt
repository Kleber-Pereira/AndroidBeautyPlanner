package com.kleber.beautyplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kleber.beautyplanner.Agenda.AgendaFragment
import com.kleber.beautyplanner.Cliente.ClienteFragment

import com.kleber.beautyplanner.Funcionarios.FuncionarioFragment
import com.kleber.beautyplanner.Home.HomeFragment
import com.kleber.beautyplanner.Servicos.ServicosFragment


class MainActivity : AppCompatActivity() {
    private lateinit var currentFragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BeautyPlanner)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.frame_layout,HomeFragment()).commit()
        val bottomNav : BottomNavigationView=findViewById(R.id.main_menu)
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
            R.id.menu_Agenda-> {
                currentFragment = AgendaFragment()
            }

            R.id.menu_cliente -> {
                currentFragment = ClienteFragment()
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout,currentFragment).commit()
        true
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (it.itemId){
            R.id.menu_home -> {

                println("Home clicado")
                true
            }
            R.id.menu_Agenda-> {
                println("Agenda clicado")
                true
            }
            R.id.menu_Funcionario -> {
                println("Funcionário clicado")
                true
            }
            R.id.menu_Servicos -> {
                println("Serviços clicado")
                true
            }
            R.id.menu_cliente -> {
                replaceFragment(ClienteFragment())
                true
            }
            else ->{
                return super.onOptionsItemSelected(item)
                true
            }*/

        }


/*
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_menu,fragment)
        fragmentTransaction.commit()


    }*/





