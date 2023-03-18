package com.kleber.beautyplanner.Funcionarios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kleber.beautyplanner.R

class FuncionarioInserirActivity : AppCompatActivity() {
    private lateinit var funcionarioNome: EditText
    private lateinit var funcionarioEmail: EditText
    private lateinit var funcionarioEndereco: EditText
    private lateinit var funcionarioCidade: EditText
    private lateinit var funcionarioEstado: EditText
    private lateinit var funcionarioTelefone: EditText
    private lateinit var btnSalvarFuncionario: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inserir_funcionario)

        funcionarioNome = findViewById(R.id.funcionarioNome)
        funcionarioEmail = findViewById(R.id.funcionarioEmail)
        funcionarioEndereco = findViewById(R.id.funcionarioEndereco)
        funcionarioCidade = findViewById(R.id.funcionarioCidade)
        funcionarioEstado = findViewById(R.id.funcionarioEstado)
        funcionarioTelefone = findViewById(R.id.funcionarioTelefone)
        btnSalvarFuncionario = findViewById(R.id.btnSalvarFuncionario)

        dbRef = FirebaseDatabase.getInstance().getReference("Funcionarios")

        btnSalvarFuncionario.setOnClickListener {
            salvarDadosFuncionario()
            return@setOnClickListener
        }
    }

    private fun salvarDadosFuncionario() {

        //getting values
        val vfuncionarioNome = funcionarioNome.text.toString()
        val vfuncionarioEmail = funcionarioEmail.text.toString()
        val vfuncionarioEndereco = funcionarioEndereco.text.toString()
        val vfuncionarioCidade = funcionarioCidade.text.toString()
        val vfuncionarioEstado = funcionarioEstado.text.toString()
        val vfuncionarioTelefone = funcionarioTelefone.text.toString()

        if (vfuncionarioNome.isEmpty()) {
            funcionarioNome.error = "Inserir Nome"
        }
        else if (vfuncionarioEmail.isEmpty()) {
            funcionarioEmail.error = "Inserir E-mail"
        }
        else if (vfuncionarioEndereco.isEmpty()) {
            funcionarioEndereco.error = "Inserir Endereço"
        }
        else if (vfuncionarioCidade.isEmpty()) {
            funcionarioCidade.error = "Inserir Cidade"
        }
        else if (vfuncionarioEstado.isEmpty()) {
            funcionarioEstado.error = "Inserir Estado"
        }
        else if (vfuncionarioTelefone.isEmpty()) {
            funcionarioTelefone.error = "Inserir Telefone"
        }

        else {
            val funcionarioId = dbRef.push().key!!

            val funcionario = FuncionarioModel(
                funcionarioId,
                vfuncionarioNome,
                vfuncionarioEmail,
                vfuncionarioEndereco,
                vfuncionarioCidade,
                vfuncionarioEstado,
                vfuncionarioTelefone
            )

            dbRef.child(funcionarioId).setValue(funcionario)
                .addOnCompleteListener {
                    Toast.makeText(this, "Dados inseridos com sucesso", Toast.LENGTH_LONG).show()

                    funcionarioNome.text.clear()
                    funcionarioEmail.text.clear()
                    funcionarioEndereco.text.clear()
                    funcionarioCidade.text.clear()
                    funcionarioEstado.text.clear()
                    funcionarioTelefone.text.clear()


                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
        }

    }
}