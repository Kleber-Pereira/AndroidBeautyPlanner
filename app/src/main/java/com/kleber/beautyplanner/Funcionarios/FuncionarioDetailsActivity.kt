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
    private lateinit var tvfuncionarioSobrenome: TextView
    private lateinit var tvfuncionarioCPF: TextView
    private lateinit var tvfuncionarioNasc: TextView
    private lateinit var tvfuncionarioEndereco: TextView
    private lateinit var tvfuncionarioCidade: TextView
    private lateinit var tvfuncionarioEstado: TextView
    private lateinit var tvfuncionarioTelefone: TextView
    private lateinit var tvfuncionarioEmail:  TextView
    private lateinit var tvfuncionarioFuncao:  TextView
    private lateinit var tvfuncionarioDtAd:  TextView
    private lateinit var tvfuncionarioStatus:  TextView
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
        val etfuncionarioSobrenome = mDialogView.findViewById<EditText>(R.id.etfuncionarioSobrenome)
        val etfuncionarioCPF = mDialogView.findViewById<EditText>(R.id.etfuncionarioCPF)
        val etfuncionarioNasc = mDialogView.findViewById<EditText>(R.id.etfuncionarioNasc)
        val etfuncionarioEndereco = mDialogView.findViewById<EditText>(R.id.etfuncionarioEndereco)
        val etfuncionarioCidade = mDialogView.findViewById<EditText>(R.id.etfuncionarioCidade)
        val etfuncionarioEstado = mDialogView.findViewById<EditText>(R.id.etfuncionarioEstado)
        val etfuncionarioTelefone = mDialogView.findViewById<EditText>(R.id.etfuncionarioTelefone)
        val etfuncionarioEmail = mDialogView.findViewById<EditText>(R.id.etfuncionarioEmail)
        val etfuncionarioFuncao = mDialogView.findViewById<EditText>(R.id.etfuncionarioFuncao)
        val etfuncionarioDtAd = mDialogView.findViewById<EditText>(R.id.etfuncionarioDtAd)
        val etfuncionarioStatus = mDialogView.findViewById<EditText>(R.id.etfuncionarioStatus)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etfuncionarioNome.setText(intent.getStringExtra("funcionarioNome").toString())
        etfuncionarioSobrenome.setText(intent.getStringExtra("funcionarioSobrenome").toString())
        etfuncionarioCPF.setText(intent.getStringExtra("funcionarioCPF").toString())
        etfuncionarioNasc.setText(intent.getStringExtra("funcionarioNasc").toString())
        etfuncionarioEndereco.setText(intent.getStringExtra("funcionarioEndereco").toString())
        etfuncionarioCidade.setText(intent.getStringExtra("funcionarioCidade").toString())
        etfuncionarioEstado.setText(intent.getStringExtra("funcionarioEstado").toString())
        etfuncionarioTelefone.setText(intent.getStringExtra("funcionarioTelefone").toString())
        etfuncionarioEmail.setText(intent.getStringExtra("funcionarioEmail").toString())
        etfuncionarioFuncao.setText(intent.getStringExtra("funcionarioFuncao").toString())
        etfuncionarioDtAd.setText(intent.getStringExtra("funcionarioDtAd").toString())
        etfuncionarioStatus.setText(intent.getStringExtra("funcionarioStatus").toString())

        mDialog.setTitle("Atualizando $funcionarioNome ")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateFuncionarioData(
                funcionarioId,
                etfuncionarioNome.text.toString(),
                etfuncionarioSobrenome.text.toString(),
                etfuncionarioCPF.text.toString(),
                etfuncionarioNasc.text.toString(),
                etfuncionarioEndereco.text.toString(),
                etfuncionarioCidade.text.toString(),
                etfuncionarioEstado.text.toString(),
                etfuncionarioTelefone.text.toString(),
                etfuncionarioEmail.text.toString(),
                etfuncionarioFuncao.text.toString(),
                etfuncionarioDtAd.text.toString(),
                etfuncionarioStatus.text.toString()

            )

            Toast.makeText(applicationContext, "Dados do funcionario atulizado", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvfuncionarioNome.text = etfuncionarioNome.text.toString()
            tvfuncionarioSobrenome.text = etfuncionarioSobrenome.text.toString()
            tvfuncionarioCPF.text = etfuncionarioCPF.text.toString()
            tvfuncionarioNasc.text = etfuncionarioNasc.text.toString()
            tvfuncionarioEndereco.text = etfuncionarioEndereco.text.toString()
            tvfuncionarioCidade.text = etfuncionarioCidade.text.toString()
            tvfuncionarioEstado.text = etfuncionarioEstado.text.toString()
            tvfuncionarioTelefone.text = etfuncionarioTelefone.text.toString()
            tvfuncionarioEmail.text = etfuncionarioEmail.text.toString()
            tvfuncionarioFuncao.text = etfuncionarioFuncao.text.toString()
            tvfuncionarioDtAd.text = etfuncionarioDtAd.text.toString()
            tvfuncionarioStatus.text = etfuncionarioStatus.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateFuncionarioData(
        funcionarioId: String,
        funcionarioNome: String,
        funcionarioSobrenome: String,
        funcionarioCPF: String,
        funcionarioNasc: String,
        funcionarioEndereco: String,
        funcionarioCidade: String,
        funcionarioEstado: String,
        funcionarioTelefone: String,
        funcionarioEmail: String,
        funcionarioFuncao: String,
        funcionarioDtAd: String,
        funcionarioStatus: String


        ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Funcionarios").child(funcionarioId)
        val funcionarioInfo = FuncionarioModel(funcionarioId,funcionarioNome, funcionarioSobrenome,
            funcionarioCPF, funcionarioNasc,funcionarioEndereco, funcionarioCidade,
            funcionarioEstado,funcionarioTelefone,funcionarioEmail,funcionarioFuncao,
            funcionarioDtAd,funcionarioStatus)
        dbRef.setValue(funcionarioInfo)
    }

    private fun initView() {
        tvfuncionarioId = findViewById(R.id.tvfuncionarioId)
        tvfuncionarioNome = findViewById(R.id.tvfuncionarioNome)
        tvfuncionarioSobrenome = findViewById(R.id.tvfuncionarioSobrenome)
        tvfuncionarioCPF = findViewById(R.id.tvfuncionarioCPF)
        tvfuncionarioNasc = findViewById(R.id.tvfuncionarioNasc)
        tvfuncionarioEndereco = findViewById(R.id.tvfuncionarioEnderco)
        tvfuncionarioCidade = findViewById(R.id.tvfuncionarioCidade)
        tvfuncionarioEstado = findViewById(R.id.tvfuncionarioEstado)
        tvfuncionarioTelefone = findViewById(R.id.tvfuncionarioTelefone)
        tvfuncionarioEmail = findViewById(R.id.tvfuncionarioEmail)
        tvfuncionarioFuncao = findViewById(R.id.tvfuncionarioFuncao)
        tvfuncionarioDtAd = findViewById(R.id.tvfuncionarioDtAd)
        tvfuncionarioStatus = findViewById(R.id.tvfuncionarioStatus)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvfuncionarioId.text = intent.getStringExtra("funcionarioId")
        tvfuncionarioNome.text = intent.getStringExtra("funcionarioNome")
        tvfuncionarioSobrenome.text = intent.getStringExtra("funcionarioSobrenome")
        tvfuncionarioCPF.text = intent.getStringExtra("funcionarioCPF")
        tvfuncionarioNasc.text = intent.getStringExtra("funcionarioNasc")
        tvfuncionarioEndereco.text = intent.getStringExtra("funcionarioEndereco")
        tvfuncionarioCidade.text = intent.getStringExtra("funcionarioCidade")
        tvfuncionarioEstado.text = intent.getStringExtra("funcionarioEstado")
        tvfuncionarioTelefone.text = intent.getStringExtra("funcionarioTelefone")
        tvfuncionarioEmail.text = intent.getStringExtra("funcionarioEmail")
        tvfuncionarioFuncao.text = intent.getStringExtra("funcionarioFuncao")
        tvfuncionarioDtAd.text = intent.getStringExtra("funcionarioDtAd")
        tvfuncionarioStatus.text = intent.getStringExtra("funcionarioStatus")




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
