package com.kleber.beautyplanner.Cliente.ui.main


import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.kleber.beautyplanner.R
import com.google.firebase.FirebaseApp
import com.kleber.beautyplanner.Cliente.Cliente
import java.util.*

class ClienteActivity : AppCompatActivity() {
    var edtNome: EditText? = null
    var edtEmail: EditText? = null
    var edtTelefone: EditText? = null
    var edtEndereco: EditText? = null
    var edtCidade: EditText? = null
    var edtBairro: EditText? = null
    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente)
        edtNome = findViewById<View>(R.id.editNome) as EditText
        edtEmail = findViewById<View>(R.id.editEmail) as EditText
        edtTelefone = findViewById<View>(R.id.editTelefone) as EditText
        edtEndereco = findViewById<View>(R.id.editEndereco) as EditText
        edtCidade = findViewById<View>(R.id.editCidade) as EditText
        edtBairro = findViewById<View>(R.id.editBairro) as EditText
        inicializarFirebase()
    }

    private fun inicializarFirebase() {
        FirebaseApp.initializeApp(this@ClienteActivity)
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase!!.reference
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cliente, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_add_cliente) {
            val p = Cliente()
            p.uid = UUID.randomUUID().toString()
            p.nome = edtNome!!.text.toString()
            p.email = edtEmail!!.text.toString()
            p.endereco = edtEndereco!!.text.toString()
            p.cidade = edtCidade!!.text.toString()
            p.bairro = edtBairro!!.text.toString()
            databaseReference!!.child("Cliente").child(p.email!!).setValue(p)
            limparCampos()
        }
        return true
    }

    private fun limparCampos() {
        edtNome!!.setText("")
        edtEmail!!.setText("")
        edtEndereco!!.setText("")
        edtCidade!!.setText("")
        edtBairro!!.setText("")
    }
}