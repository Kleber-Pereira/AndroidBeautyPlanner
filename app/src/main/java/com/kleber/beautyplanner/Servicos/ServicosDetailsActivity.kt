package com.kleber.beautyplanner.Servicos

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

class ServicosDetailsActivity : AppCompatActivity() {
    private lateinit var tvservicosId: TextView
    private lateinit var tvservicosCategoria: TextView
    private lateinit var tvservicosServico: TextView
    private lateinit var tvservicosTempo: TextView
    private lateinit var tvservicosPreco: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicos_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("servicosId").toString(),
                intent.getStringExtra("servicosServico").toString()
            )
           // return@setOnClickListener


        }
        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("servicosId").toString()
            )
            //return@setOnClickListener
        }


    }

    private fun openUpdateDialog(
        servicosId: String,
        servicosServico: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_servicos_dialog, null)

        mDialog.setView(mDialogView)

        val etservicosCategoria = mDialogView.findViewById<EditText>(R.id.etservicosCategoria)
        val etservicosServico = mDialogView.findViewById<EditText>(R.id.etservicosServico)
        val etservicosTempo = mDialogView.findViewById<EditText>(R.id.etservicosTempo)
        val etservicosPreco = mDialogView.findViewById<EditText>(R.id.etservicosPreco)


        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etservicosCategoria.setText(intent.getStringExtra("servicosCategoria").toString())
        etservicosServico.setText(intent.getStringExtra("servicosServico").toString())
        etservicosTempo.setText(intent.getStringExtra("servicosTempo").toString())
        etservicosPreco.setText(intent.getStringExtra("servicosPreco").toString())


        mDialog.setTitle("Atualizando $servicosServico ")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateServicosData(
                servicosId,
                etservicosCategoria.text.toString(),
                etservicosServico.text.toString(),
                etservicosTempo.text.toString(),
                etservicosPreco.text.toString()

            )

            Toast.makeText(applicationContext, "Dados do servico atulizado", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews


            tvservicosCategoria.text = etservicosCategoria.text.toString()
            tvservicosServico.text = etservicosServico.text.toString()
            tvservicosTempo.text = etservicosTempo.text.toString()
            tvservicosPreco.text = etservicosPreco.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateServicosData(
        servicosId: String,
        servicosCategoria: String,
        servicosServico: String,
        servicosTempo: String,
        servicosPreco: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Servicos").child(servicosId)
        val servicosInfo = ServicosModel(servicosId,servicosCategoria,servicosServico,  servicosTempo,
            servicosPreco)
        dbRef.setValue(servicosInfo)
    }

    private fun initView() {
        tvservicosId = findViewById(R.id.tvservicosId)
        tvservicosCategoria = findViewById(R.id.tvservicosCategoria)
        tvservicosServico = findViewById(R.id.tvservicosServico)
        tvservicosTempo = findViewById(R.id.tvservicosTempo)
        tvservicosPreco = findViewById(R.id.tvservicosPreco)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvservicosId.text = intent.getStringExtra("servicosId")
        tvservicosCategoria.text = intent.getStringExtra("servicosCategoria")
        tvservicosServico.text = intent.getStringExtra("servicosServico")
        tvservicosTempo.text = intent.getStringExtra("servicosTempo")
        tvservicosPreco.text = intent.getStringExtra("servicosPreco")
    }
    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Servicos").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Dados do servico exluídos", Toast.LENGTH_LONG).show()

            val intent = Intent(this, ServicosBuscarActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Erro ao Excluir ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

}
