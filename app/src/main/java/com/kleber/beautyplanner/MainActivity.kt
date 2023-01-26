package com.kleber.beautyplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BeautyPlanner)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
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
                println("Clientes clicado")
                true
            }
            else ->{
                return super.onOptionsItemSelected(item)
            }
        }

    }



}

