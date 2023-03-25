package com.kleber.beautyplanner.Cliente

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

class ClienteInserirActivity : AppCompatActivity() {
    private lateinit var clienteNome: EditText
    private lateinit var clienteSobrenome: EditText
    private lateinit var clienteCPF: EditText
    private lateinit var clienteNasc: EditText
    private lateinit var clienteEndereco: EditText
    private lateinit var clienteCidade: EditText
    private lateinit var clienteEstado: EditText
    private lateinit var clienteTelefone: EditText
    private lateinit var clienteEmail: EditText
    private lateinit var btnSalvarCliente: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inserir_cliente)

        clienteNome = findViewById(R.id.clienteNome)
        clienteSobrenome = findViewById(R.id.clienteSobrenome)
        clienteCPF = findViewById(R.id.clienteCPF)
        clienteNasc = findViewById(R.id.clienteNasc)
        clienteEndereco = findViewById(R.id.clienteEndereco)
        clienteCidade = findViewById(R.id.clienteCidade)
        clienteEstado = findViewById(R.id.clienteEstado)
        clienteTelefone = findViewById(R.id.clienteTelefone)
        clienteEmail = findViewById(R.id.clienteEmail)
        btnSalvarCliente = findViewById(R.id.btnSalvarCliente)

        dbRef = FirebaseDatabase.getInstance().getReference("Clientes")

        btnSalvarCliente.setOnClickListener {
            salvarDadosCliente()
            return@setOnClickListener
        }

        clienteNasc.transformIntoDatePicker(this, "dd/MM/yyyy")
        clienteNasc.transformIntoDatePicker(this, "dd/MM/yyyy", Date())


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


    private fun salvarDadosCliente() {

        //getting values
        val vclienteNome = clienteNome.text.toString()
        val vclienteSobrenome = clienteSobrenome.text.toString()
        val vclienteCPF = clienteCPF.text.toString()
        val vclienteNasc = clienteNasc.text.toString()
        val vclienteEndereco = clienteEndereco.text.toString()
        val vclienteCidade = clienteCidade.text.toString()
        val vclienteEstado = clienteEstado.text.toString()
        val vclienteTelefone = clienteTelefone.text.toString()
        val vclienteEmail = clienteEmail.text.toString()


        if (vclienteNome.isEmpty()) {
            clienteNome.error = "Inserir Nome"
        }
        else if (vclienteSobrenome.isEmpty()) {
            clienteSobrenome.error = "Inserir Sobrenome"
        }
        else if (vclienteCPF.isEmpty()) {
            clienteCPF.error = "Inserir CPF"
        }
        else if (vclienteEmail.isEmpty()) {
            clienteNasc.error = "Inserir Data de Nascimento"
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
        else if (vclienteEmail.isEmpty()) {
            clienteEmail.error = "Inserir E-mail"
        }

        else {
            val clienteId = dbRef.push().key!!

            val cliente = ClienteModel(
                clienteId,
                vclienteNome,
                vclienteSobrenome,
                vclienteCPF,
                vclienteNasc,
                vclienteEndereco,
                vclienteCidade,
                vclienteEstado,
                vclienteTelefone,
                vclienteEmail
            )

            dbRef.child(clienteId).setValue(cliente)
                .addOnCompleteListener {
                    Toast.makeText(this, "Dados inseridos com sucesso", Toast.LENGTH_LONG).show()

                    clienteNome.text.clear()
                    clienteSobrenome.text.clear()
                    clienteCPF.text.clear()
                    clienteNasc.text.clear()
                    clienteEndereco.text.clear()
                    clienteCidade.text.clear()
                    clienteEstado.text.clear()
                    clienteTelefone.text.clear()
                    clienteEmail.text.clear()


                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
        }

    }
}