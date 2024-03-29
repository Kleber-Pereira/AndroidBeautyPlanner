package com.kleber.beautyplanner.Cliente

import android.app.DatePickerDialog
import android.content.Context
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
import java.util.*
import java.text.SimpleDateFormat

class ClienteDetailsActivity : AppCompatActivity() {
    private lateinit var tvclienteId: TextView
    private lateinit var tvclienteNome: TextView
    private lateinit var tvclienteSobrenome: TextView
    private lateinit var tvclienteCPF: TextView
    private lateinit var tvclienteNasc: TextView
    private lateinit var tvclienteEndereco: TextView
    private lateinit var tvclienteCidade: TextView
    private lateinit var tvclienteEstado: TextView
    private lateinit var tvclienteTelefone: TextView
    private lateinit var tvclienteEmail: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("clienteId").toString(),
                intent.getStringExtra("clienteNome").toString()
            )
           // return@setOnClickListener


        }
        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("clienteId").toString()
            )
            //return@setOnClickListener
        }


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


    private fun openUpdateDialog(
        clienteId: String,
        clienteNome: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_cliente_dialog, null)

        mDialog.setView(mDialogView)

        val etclienteNome = mDialogView.findViewById<EditText>(R.id.etclienteNome)
        val etclienteSobrenome = mDialogView.findViewById<EditText>(R.id.etclienteSobrenome)
        val etclienteCPF = mDialogView.findViewById<EditText>(R.id.etclienteCPF)
        val etclienteNasc = mDialogView.findViewById<EditText>(R.id.etclienteNasc)
        val etclienteEndereco = mDialogView.findViewById<EditText>(R.id.etclienteEndereco)
        val etclienteCidade = mDialogView.findViewById<EditText>(R.id.etclienteCidade)
        val etclienteEstado = mDialogView.findViewById<EditText>(R.id.etclienteEstado)
        val etclienteTelefone = mDialogView.findViewById<EditText>(R.id.etclienteTelefone)
        val etclienteEmail = mDialogView.findViewById<EditText>(R.id.etclienteEmail)

        etclienteNasc.transformIntoDatePicker(this, "dd/MM/yyyy")
        etclienteNasc.transformIntoDatePicker(this, "dd/MM/yyyy", Date())


        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etclienteNome.setText(intent.getStringExtra("clienteNome").toString())
        etclienteSobrenome.setText(intent.getStringExtra("clienteSobrenome").toString())
        etclienteCPF.setText(intent.getStringExtra("clienteCPF").toString())
        etclienteNasc.setText(intent.getStringExtra("clienteNasc").toString())
        etclienteEndereco.setText(intent.getStringExtra("clienteEndereco").toString())
        etclienteCidade.setText(intent.getStringExtra("clienteCidade").toString())
        etclienteEstado.setText(intent.getStringExtra("clienteEstado").toString())
        etclienteTelefone.setText(intent.getStringExtra("clienteTelefone").toString())
        etclienteEmail.setText(intent.getStringExtra("clienteEmail").toString())


        mDialog.setTitle("Atualizando $clienteNome ")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateClienteData(
                clienteId,
                etclienteNome.text.toString(),
                etclienteSobrenome.text.toString(),
                etclienteCPF.text.toString(),
                etclienteNasc.text.toString(),
                etclienteEndereco.text.toString(),
                etclienteCidade.text.toString(),
                etclienteEstado.text.toString(),
                etclienteEmail.text.toString(),
                etclienteTelefone.text.toString()

            )

            Toast.makeText(applicationContext, "Dados do cliente atulizado", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews


            tvclienteNome.text = etclienteNome.text.toString()
            tvclienteSobrenome.text = etclienteSobrenome.text.toString()
            tvclienteCPF.text = etclienteCPF.text.toString()
            tvclienteNasc.text = etclienteNasc.text.toString()
            tvclienteEndereco.text = etclienteEndereco.text.toString()
            tvclienteCidade.text = etclienteCidade.text.toString()
            tvclienteEstado.text = etclienteEstado.text.toString()
            tvclienteEmail.text = etclienteEmail.text.toString()
            tvclienteTelefone.text = etclienteTelefone.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateClienteData(
        clienteId: String,
        clienteNome: String,
        clienteSobrenome: String,
        clienteCPF: String,
        clienteNasc: String,
        clienteEndereco: String,
        clienteCidade: String,
        clienteEstado: String,
        clienteEmail: String,
        clienteTelefone: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Clientes").child(clienteId)
        val clienteInfo = ClienteModel(clienteId,clienteNome,clienteSobrenome,  clienteCPF, clienteNasc,
            clienteEndereco, clienteCidade,clienteEstado,clienteEmail,clienteTelefone)
        dbRef.setValue(clienteInfo)
    }

    private fun initView() {
        tvclienteId = findViewById(R.id.tvclienteId)
        tvclienteNome = findViewById(R.id.tvclienteNome)
        tvclienteSobrenome = findViewById(R.id.tvclienteSobrenome)
        tvclienteCPF = findViewById(R.id.tvclienteCPF)
        tvclienteNasc = findViewById(R.id.tvclienteNasc)
        tvclienteEndereco = findViewById(R.id.tvclienteEndereco)
        tvclienteCidade = findViewById(R.id.tvclienteCidade)
        tvclienteEstado = findViewById(R.id.tvclienteEstado)
        tvclienteTelefone = findViewById(R.id.tvclienteTelefone)
        tvclienteEmail = findViewById(R.id.tvclienteEmail)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvclienteId.text = intent.getStringExtra("clienteId")
        tvclienteNome.text = intent.getStringExtra("clienteNome")
        tvclienteSobrenome.text = intent.getStringExtra("clienteSobrenome")
        tvclienteCPF.text = intent.getStringExtra("clienteCPF")
        tvclienteNasc.text = intent.getStringExtra("clienteNasc")
        tvclienteEndereco.text = intent.getStringExtra("clienteEndereco")
        tvclienteCidade.text = intent.getStringExtra("clienteCidade")
        tvclienteEstado.text = intent.getStringExtra("clienteEstado")
        tvclienteTelefone.text = intent.getStringExtra("clienteTelefone")
        tvclienteEmail.text = intent.getStringExtra("clienteEmail")

    }
    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Clientes").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Dados do cliente exluídos", Toast.LENGTH_LONG).show()

            val intent = Intent(this, ClienteBuscarActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Erro ao Excluir ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

}
