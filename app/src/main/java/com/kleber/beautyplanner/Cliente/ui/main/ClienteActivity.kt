package com.kleber.beautyplanner.Cliente.ui.main



import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.kleber.beautyplanner.R
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.kleber.beautyplanner.Cliente.Cliente

import java.util.*

class ClienteActivity : AppCompatActivity() {
    var edtNome: EditText? = null
    var edtEmail: EditText? = null
    var edtTelefone: EditText? = null
    var edtEndereco: EditText? = null
    var edtCidade: EditText? = null
    var edtBairro: EditText? = null
    var listV_dados: ListView? = null
    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null

    private val listCliente: MutableList<Cliente?> = ArrayList()
    private var arrayAdapterCliente: ArrayAdapter<Cliente?>? = null

    var clienteSelecionado: Cliente? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente)
        edtNome = findViewById<View>(R.id.editNome) as EditText
        edtEmail = findViewById<View>(R.id.editEmail) as EditText
        edtTelefone = findViewById<View>(R.id.editTelefone) as EditText
        edtEndereco = findViewById<View>(R.id.editEndereco) as EditText
        edtCidade = findViewById<View>(R.id.editCidade) as EditText
        edtBairro = findViewById<View>(R.id.editBairro) as EditText
        listV_dados = findViewById<View>(R.id.listV_dados) as ListView
        inicializarFirebase()
        eventoDatabase()

        listV_dados!!.setOnClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
            clienteSelecionado = adapterView.getItemAtPosition(i) as Cliente
            edtNome!!.setText(clienteSelecionado!!.nome)
        })



    }

private fun ListView.setOnClickListener(onItemClickListener: AdapterView.OnItemClickListener) {

}

private fun eventoDatabase() {
        databaseReference!!.child("Cliente").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listCliente.clear()
                for (objSnapshot in dataSnapshot.children) {
                    val p = objSnapshot.getValue(Cliente::class.java)
                    listCliente.add(p)
                }
                arrayAdapterCliente =
                    ArrayAdapter(this@ClienteActivity, android.R.layout.simple_list_item_1, listCliente)
                listV_dados!!.setAdapter(arrayAdapterCliente)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        firebaseDatabase!!.setPersistenceEnabled(true)
    }

    private fun inicializarFirebase() {
        FirebaseApp.initializeApp(this@ClienteActivity)
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase!!.reference
        firebaseDatabase!!.setPersistenceEnabled(true)
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
            databaseReference!!.child("Cliente").child(p.uid!!).setValue(p)
            limparCampos()
        }
        else if (id == R.id.menu_edit_cliente) {
            val p = Cliente()
            p.uid = clienteSelecionado!!.uid.toString()
            p.nome = edtNome!!.text.toString().trim()
            p.email = edtEmail!!.text.toString().trim()
            p.endereco = edtEndereco!!.text.toString().trim()
            p.cidade = edtCidade!!.text.toString().trim()
            p.bairro = edtBairro!!.text.toString().trim()
            databaseReference!!.child("Cliente").child(p.uid!!).setValue(p)
            limparCampos()
        }
        else if (id == R.id.menu_delete_cliente) {
            val p = Cliente()
            p.uid = clienteSelecionado!!.uid.toString()
            databaseReference!!.child("Cliente").child(p.uid!!).removeValue()
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