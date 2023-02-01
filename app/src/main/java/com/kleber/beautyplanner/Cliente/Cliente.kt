package com.kleber.beautyplanner.Cliente

class Cliente {
    var uid: String? = null
    var nome: String? = null
    var email: String? = null
    var telefone: String? = null
    var endereco: String? = null
    var cidade: String? = null
    var bairro: String? = null
    override fun toString(): String {
        return nome!!
    }
}