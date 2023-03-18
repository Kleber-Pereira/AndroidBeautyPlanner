package com.kleber.beautyplanner.Cliente

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kleber.beautyplanner.R

class ClienteInserirActivity : AppCompatActivity() {
    private lateinit var clienteNome: EditText
    private lateinit var clienteEmail: EditText
    private lateinit var clienteEndereco: EditText
    private lateinit var clienteCidade: EditText
    private lateinit var clienteEstado: EditText
    private lateinit var clienteTelefone: EditText
    private lateinit var btnSalvarCliente: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inserir_cliente)

        clienteNome = findViewById(R.id.clienteNome)
        clienteEmail = findViewById(R.id.clienteEmail)
        clienteEndereco = findViewById(R.id.clienteEndereco)
        clienteCidade = findViewById(R.id.clienteCidade)
        clienteEstado = findViewById(R.id.clienteEstado)
        clienteTelefone = findViewById(R.id.clienteTelefone)
        btnSalvarCliente = findViewById(R.id.btnSalvarCliente)

        dbRef = FirebaseDatabase.getInstance().getReference("Clientes")

        btnSalvarCliente.setOnClickListener {
            salvarDadosCliente()
            return@setOnClickListener
        }
    }

    private fun salvarDadosCliente() {

        //getting values
        val vclienteNome = clienteNome.text.toString()
        val vclienteEmail = clienteEmail.text.toString()
        val vclienteEndereco = clienteEndereco.text.toString()
        val vclienteCidade = clienteCidade.text.toString()
        val vclienteEstado = clienteEstado.text.toString()
        val vclienteTelefone = clienteTelefone.text.toString()

        if (vclienteNome.isEmpty()) {
            clienteNome.error = "Inserir Nome"
        }
        else if (vclienteEmail.isEmpty()) {
            clienteEmail.error = "Inserir E-mail"
        }
        else if (vclienteEndereco.isEmpty()) {
            clienteEndereco.error = "Inserir Endereço"
        }
        else if (vclienteCidade.isEmpty()) {
            clienteCidade.error = "Inserir Cidade"
        }
        else if (vclienteEstado.isEmpty()) {
            clienteEstado.error = "Inserir Estado"
        }
        else if (vclienteTelefone.isEmpty()) {
            clienteTelefone.error = "Inserir Telefone"
        }

        else {
            val clienteId = dbRef.push().key!!

            val cliente = ClienteModel(
                clienteId,
                vclienteNome,
                vclienteEmail,
                vclienteEndereco,
                vclienteCidade,
                vclienteEstado,
                vclienteTelefone
            )

            dbRef.child(clienteId).setValue(cliente)
                .addOnCompleteListener {
                    Toast.makeText(this, "Dados inseridos com sucesso", Toast.LENGTH_LONG).show()

                    clienteNome.text.clear()
                    clienteEmail.text.clear()
                    clienteEndereco.text.clear()
                    clienteCidade.text.clear()
                    clienteEstado.text.clear()
                    clienteTelefone.text.clear()


                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
        }

    }
}