package com.kleber.beautyplanner.Servicos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kleber.beautyplanner.R

class ServicosInserirActivity : AppCompatActivity() {
    private lateinit var servicosCategoria: EditText
    private lateinit var servicosServico: EditText
    private lateinit var servicosTempo: EditText
    private lateinit var servicosPreco: EditText
    private lateinit var btnSalvarServicos: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inserir_servicos)

        servicosCategoria = findViewById(R.id.servicosCategoria)
        servicosServico = findViewById(R.id.servicosServico)
        servicosTempo = findViewById(R.id.servicosTempo)
        servicosPreco = findViewById(R.id.servicosPreco)
        btnSalvarServicos = findViewById(R.id.btnSalvarServicos)

        dbRef = FirebaseDatabase.getInstance().getReference("Servicos")

        btnSalvarServicos.setOnClickListener {
            salvarDadosServicos()
            return@setOnClickListener
        }
    }

    private fun salvarDadosServicos() {

        //getting values
        val vservicosCategoria = servicosCategoria.text.toString()
        val vservicosServico = servicosServico.text.toString()
        val vservicosTempo = servicosTempo.text.toString()
        val vservicosPreco= servicosPreco.text.toString()


        if (vservicosCategoria.isEmpty()) {
            servicosCategoria.error = "Inserir Categoria"
        }
        else if (vservicosServico.isEmpty()) {
            servicosServico.error = "Inserir Serviço"
        }
        else if (vservicosTempo.isEmpty()) {
            servicosTempo.error = "Inserir Tempo médio"
        }
        else if (vservicosPreco.isEmpty()) {
            servicosPreco.error = "Inserir Preço médio"
        }

        else {
            val servicosId = dbRef.push().key!!

            val servicos = ServicosModel(
                servicosId,
                vservicosCategoria,
                vservicosServico,
                vservicosTempo,
                vservicosPreco
            )

            dbRef.child(servicosId).setValue(servicos)
                .addOnCompleteListener {
                    Toast.makeText(this, "Dados inseridos com sucesso", Toast.LENGTH_LONG).show()

                    servicosCategoria.text.clear()
                    servicosServico.text.clear()
                    servicosTempo.text.clear()
                    servicosPreco.text.clear()


                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
        }

    }
}