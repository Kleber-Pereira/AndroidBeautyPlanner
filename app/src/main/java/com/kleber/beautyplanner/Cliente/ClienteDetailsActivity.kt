package com.kleber.beautyplanner.Cliente

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

class ClienteDetailsActivity : AppCompatActivity() {
    private lateinit var tvclienteId: TextView
    private lateinit var tvclienteNome: TextView
    private lateinit var tvclienteEmail:  TextView
    private lateinit var tvclienteTelefone: TextView
    private lateinit var tvclienteEndereco: TextView
    private lateinit var tvclienteCidade: TextView
    private lateinit var tvclienteEstado: TextView
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

    private fun openUpdateDialog(
        clienteId: String,
        clienteNome: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_cliente_dialog, null)

        mDialog.setView(mDialogView)

        val etclienteNome = mDialogView.findViewById<EditText>(R.id.etclienteNome)
        val etclienteEmail = mDialogView.findViewById<EditText>(R.id.etclienteEmail)
        val etclienteTelefone = mDialogView.findViewById<EditText>(R.id.etclienteTelefone)
        val etclienteEndereco = mDialogView.findViewById<EditText>(R.id.etclienteEndereco)
        val etclienteCidade = mDialogView.findViewById<EditText>(R.id.etclienteCidade)
        val etclienteEstado = mDialogView.findViewById<EditText>(R.id.etclienteEstado)


        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etclienteNome.setText(intent.getStringExtra("clienteNome").toString())
        etclienteEmail.setText(intent.getStringExtra("clienteEmail").toString())
        etclienteTelefone.setText(intent.getStringExtra("clienteTelefone").toString())
        etclienteEndereco.setText(intent.getStringExtra("clienteEndereco").toString())
        etclienteCidade.setText(intent.getStringExtra("clienteCidade").toString())
        etclienteEstado.setText(intent.getStringExtra("clienteEstado").toString())


        mDialog.setTitle("Atualizando $clienteNome ")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateClienteData(
                clienteId,
                etclienteNome.text.toString(),
                etclienteEmail.text.toString(),
                etclienteTelefone.text.toString(),
                etclienteEndereco.text.toString(),
                etclienteCidade.text.toString(),
                etclienteEstado.text.toString()

            )

            Toast.makeText(applicationContext, "Dados do cliente atulizado", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvclienteNome.text = etclienteNome.text.toString()
            tvclienteEmail.text = etclienteEmail.text.toString()
            tvclienteTelefone.text = etclienteTelefone.text.toString()
            tvclienteEndereco.text = etclienteEndereco.text.toString()
            tvclienteCidade.text = etclienteCidade.text.toString()
            tvclienteEstado.text = etclienteEstado.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateClienteData(
        clienteId: String,
        clienteNome: String,
        clienteEmail: String,
        clienteEndereco: String,
        clienteCidade: String,
        clienteEstado: String,
        clienteTelefone: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Clientes").child(clienteId)
        val clienteInfo = ClienteModel(clienteId,clienteNome,clienteEmail,clienteEndereco,
            clienteCidade,clienteEstado,clienteTelefone)
        dbRef.setValue(clienteInfo)
    }

    private fun initView() {
        tvclienteId = findViewById(R.id.tvclienteId)
        tvclienteNome = findViewById(R.id.tvclienteNome)
        tvclienteEmail = findViewById(R.id.tvclienteEmail)
        tvclienteTelefone = findViewById(R.id.tvclienteTelefone)
        tvclienteEndereco = findViewById(R.id.tvclienteEnderco)
        tvclienteCidade = findViewById(R.id.tvclienteCidade)
        tvclienteEstado = findViewById(R.id.tvclienteEstado)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvclienteId.text = intent.getStringExtra("clienteId")
        tvclienteNome.text = intent.getStringExtra("clienteName")
        tvclienteEmail.text = intent.getStringExtra("clienteEmail")
        tvclienteTelefone.text = intent.getStringExtra("clienteTelefone")
        tvclienteEndereco.text = intent.getStringExtra("clienteEndereco")
        tvclienteCidade.text = intent.getStringExtra("clienteCidade")
        tvclienteEstado.text = intent.getStringExtra("clienteEstado")

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
