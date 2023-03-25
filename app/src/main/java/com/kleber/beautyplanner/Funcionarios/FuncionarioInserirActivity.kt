package com.kleber.beautyplanner.Funcionarios

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kleber.beautyplanner.R
import java.text.SimpleDateFormat
import java.util.*

class FuncionarioInserirActivity : AppCompatActivity() {
    private lateinit var funcionarioNome: EditText
    private lateinit var funcionarioSobrenome: EditText
    private lateinit var funcionarioCPF: EditText
    private lateinit var funcionarioNasc: EditText
    private lateinit var funcionarioEndereco: EditText
    private lateinit var funcionarioCidade: EditText
    private lateinit var funcionarioEstado: EditText
    private lateinit var funcionarioTelefone: EditText
    private lateinit var funcionarioEmail: EditText
    private lateinit var funcionarioFuncao: EditText
    private lateinit var funcionarioDtAd: EditText
    private lateinit var funcionarioStatus: EditText

    private lateinit var btnSalvarFuncionario: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inserir_funcionario)

        funcionarioNome = findViewById(R.id.funcionarioNome)
        funcionarioSobrenome = findViewById(R.id.funcionarioSobrenome)
        funcionarioCPF = findViewById(R.id.funcionarioCPF)
        funcionarioNasc = findViewById(R.id.funcionarioNasc)
        funcionarioEndereco = findViewById(R.id.funcionarioEndereco)
        funcionarioCidade = findViewById(R.id.funcionarioCidade)
        funcionarioEstado = findViewById(R.id.funcionarioEstado)
        funcionarioTelefone = findViewById(R.id.funcionarioTelefone)
        funcionarioEmail = findViewById(R.id.funcionarioEmail)
        funcionarioFuncao = findViewById(R.id.funcionarioFuncao)
        funcionarioDtAd = findViewById(R.id.funcionarioDtAd)
        funcionarioStatus = findViewById(R.id.funcionarioStatus)

        btnSalvarFuncionario = findViewById(R.id.btnSalvarFuncionario)

        dbRef = FirebaseDatabase.getInstance().getReference("Funcionarios")

        btnSalvarFuncionario.setOnClickListener {
            salvarDadosFuncionario()
            return@setOnClickListener
        }
        funcionarioNasc.transformIntoDatePicker(this, "dd/MM/yyyy")
        funcionarioNasc.transformIntoDatePicker(this, "dd/MM/yyyy", Date())
        funcionarioDtAd.transformIntoDatePicker(this, "dd/MM/yyyy")
        funcionarioDtAd.transformIntoDatePicker(this, "dd/MM/yyyy", Date())
    }

    private fun EditText.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

        val myCalendar = Calendar.getInstance()
        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat(format, Locale.UK)
                setText(sdf.format(myCalendar.time))
            }

        setOnClickListener {
            DatePickerDialog(
                context, datePickerOnDataSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).run {
                maxDate?.time?.also { datePicker.maxDate = it }
                show()
            }
        }
    }


    private fun salvarDadosFuncionario() {

        //getting values
        val vfuncionarioNome = funcionarioNome.text.toString()
        val vfuncionarioSobrenome = funcionarioSobrenome.text.toString()
        val vfuncionarioCPF = funcionarioCPF.text.toString()
        val vfuncionarioNasc = funcionarioNasc.text.toString()
        val vfuncionarioEndereco = funcionarioEndereco.text.toString()
        val vfuncionarioCidade = funcionarioCidade.text.toString()
        val vfuncionarioEstado = funcionarioEstado.text.toString()
        val vfuncionarioTelefone = funcionarioTelefone.text.toString()
        val vfuncionarioEmail = funcionarioEmail.text.toString()
        val vfuncionarioFuncao = funcionarioFuncao.text.toString()
        val vfuncionarioDtAd = funcionarioDtAd.text.toString()
        val vfuncionarioStatus = funcionarioStatus.text.toString()


        if (vfuncionarioNome.isEmpty()) {
            funcionarioNome.error = "Inserir Nome"
        }
        else if (vfuncionarioSobrenome.isEmpty()) {
            funcionarioSobrenome.error = "Inserir Sobrenome"
        }
        else if (vfuncionarioCPF.isEmpty()) {
            funcionarioCPF.error = "Inserir CPF"
        }
        else if (vfuncionarioNasc.isEmpty()) {
            funcionarioNasc.error = "Inserir Data de Nascimento"
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
        else if (vfuncionarioEmail.isEmpty()) {
            funcionarioEmail.error = "Inserir E-mail"
        }
        else if (vfuncionarioFuncao.isEmpty()) {
            funcionarioFuncao.error = "Inserir Função"
        }
        else if (vfuncionarioDtAd.isEmpty()) {
            funcionarioDtAd.error = "Inserir Data de Admissão"
        }
        else if (vfuncionarioStatus.isEmpty()) {
            funcionarioStatus.error = "Inserir Status"
        }

        else {
            val funcionarioId = dbRef.push().key!!

            val funcionario = FuncionarioModel(
                funcionarioId,
                vfuncionarioNome,
                vfuncionarioSobrenome,
                vfuncionarioCPF,
                vfuncionarioNasc,
                vfuncionarioEndereco,
                vfuncionarioCidade,
                vfuncionarioEstado,
                vfuncionarioTelefone,
                vfuncionarioEmail,
                vfuncionarioFuncao,
                vfuncionarioDtAd,
                vfuncionarioStatus

                )

            dbRef.child(funcionarioId).setValue(funcionario)
                .addOnCompleteListener {
                    Toast.makeText(this, "Dados inseridos com sucesso", Toast.LENGTH_LONG).show()

                    funcionarioNome.text.clear()
                    funcionarioSobrenome.text.clear()
                    funcionarioCPF.text.clear()
                    funcionarioNasc.text.clear()
                    funcionarioEndereco.text.clear()
                    funcionarioCidade.text.clear()
                    funcionarioEstado.text.clear()
                    funcionarioTelefone.text.clear()
                    funcionarioEmail.text.clear()
                    funcionarioFuncao.text.clear()
                    funcionarioDtAd.text.clear()
                    funcionarioStatus.text.clear()



                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
        }

    }
}