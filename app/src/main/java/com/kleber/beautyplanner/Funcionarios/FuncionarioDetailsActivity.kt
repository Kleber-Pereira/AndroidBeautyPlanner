package com.kleber.beautyplanner.Funcionarios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase
import com.kleber.beautyplanner.R

class FuncionarioDetailsActivity : AppCompatActivity() {
    private lateinit var tvfuncionarioId: TextView
    private lateinit var tvfuncionarioNome: TextView
    private lateinit var tvfuncionarioEmail:  TextView
    private lateinit var tvfuncionarioTelefone: TextView
    private lateinit var tvfuncionarioEndereco: TextView
    private lateinit var tvfuncionarioCidade: TextView
    private lateinit var tvfuncionarioEstado: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_funcionario_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("funcionarioId").toString(),
                intent.getStringExtra("funcionarioNome").toString()
            )
           // return@setOnClickListener


        }
        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("funcionarioId").toString()
            )
            //return@setOnClickListener
        }


    }

    private fun openUpdateDialog(
        funcionarioId: String,
        funcionarioNome: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_funcionario_dialog, null)

        mDialog.setView(mDialogView)

        val etfuncionarioNome = mDialogView.findViewById<EditText>(R.id.etfuncionarioNome)
        val etfuncionarioEmail = mDialogView.findViewById<EditText>(R.id.etfuncionarioEmail)
        val etfuncionarioTelefone = mDialogView.findViewById<EditText>(R.id.etfuncionarioTelefone)
        val etfuncionarioEndereco = mDialogView.findViewById<EditText>(R.id.etfuncionarioEndereco)
        val etfuncionarioCidade = mDialogView.findViewById<EditText>(R.id.etfuncionarioCidade)
        val etfuncionarioEstado = mDialogView.findViewById<EditText>(R.id.etfuncionarioEstado)


        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etfuncionarioNome.setText(intent.getStringExtra("funcionarioNome").toString())
        etfuncionarioEmail.setText(intent.getStringExtra("funcionarioEmail").toString())
        etfuncionarioTelefone.setText(intent.getStringExtra("funcionarioTelefone").toString())
        etfuncionarioEndereco.setText(intent.getStringExtra("funcionarioEndereco").toString())
        etfuncionarioCidade.setText(intent.getStringExtra("funcionarioCidade").toString())
        etfuncionarioEstado.setText(intent.getStringExtra("funcionarioEstado").toString())


        mDialog.setTitle("Atualizando $funcionarioNome ")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateFuncionarioData(
                funcionarioId,
                etfuncionarioNome.text.toString(),
                etfuncionarioEmail.text.toString(),
                etfuncionarioTelefone.text.toString(),
                etfuncionarioEndereco.text.toString(),
                etfuncionarioCidade.text.toString(),
                etfuncionarioEstado.text.toString()

            )

            Toast.makeText(applicationContext, "Dados do funcionario atulizado", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvfuncionarioNome.text = etfuncionarioNome.text.toString()
            tvfuncionarioEmail.text = etfuncionarioEmail.text.toString()
            tvfuncionarioTelefone.text = etfuncionarioTelefone.text.toString()
            tvfuncionarioEndereco.text = etfuncionarioEndereco.text.toString()
            tvfuncionarioCidade.text = etfuncionarioCidade.text.toString()
            tvfuncionarioEstado.text = etfuncionarioEstado.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateFuncionarioData(
        funcionarioId: String,
        funcionarioNome: String,
        funcionarioEmail: String,
        funcionarioEndereco: String,
        funcionarioCidade: String,
        funcionarioEstado: String,
        funcionarioTelefone: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Funcionarios").child(funcionarioId)
        val funcionarioInfo = FuncionarioModel(funcionarioId,funcionarioNome,funcionarioEmail,funcionarioEndereco,
            funcionarioCidade,funcionarioEstado,funcionarioTelefone)
        dbRef.setValue(funcionarioInfo)
    }

    private fun initView() {
        tvfuncionarioId = findViewById(R.id.tvfuncionarioId)
        tvfuncionarioNome = findViewById(R.id.tvfuncionarioNome)
        tvfuncionarioEmail = findViewById(R.id.tvfuncionarioEmail)
        tvfuncionarioTelefone = findViewById(R.id.tvfuncionarioTelefone)
        tvfuncionarioEndereco = findViewById(R.id.tvfuncionarioEnderco)
        tvfuncionarioCidade = findViewById(R.id.tvfuncionarioCidade)
        tvfuncionarioEstado = findViewById(R.id.tvfuncionarioEstado)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvfuncionarioId.text = intent.getStringExtra("funcionarioId")
        tvfuncionarioNome.text = intent.getStringExtra("funcionarioName")
        tvfuncionarioEmail.text = intent.getStringExtra("funcionarioEmail")
        tvfuncionarioTelefone.text = intent.getStringExtra("funcionarioTelefone")
        tvfuncionarioEndereco.text = intent.getStringExtra("funcionarioEndereco")
        tvfuncionarioCidade.text = intent.getStringExtra("funcionarioCidade")
        tvfuncionarioEstado.text = intent.getStringExtra("funcionarioEstado")

    }
    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Funcionarios").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Dados do funcionario exluídos", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FuncionarioBuscarActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Erro ao Excluir ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

}
