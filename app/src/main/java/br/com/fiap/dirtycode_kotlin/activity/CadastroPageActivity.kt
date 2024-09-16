package br.com.fiap.dirtycode_kotlin.activity

import Usuario
import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import br.com.fiap.dirtycode_kotlin.databinding.CadastroPageLayoutBinding
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class CadastroPageActivity : Activity() {
    val BASE_URL = "http://192.168.0.100:8080"
    val gson = Gson()
    private val client = OkHttpClient()
    private lateinit var binding: CadastroPageLayoutBinding

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        binding = CadastroPageLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnCadastro.setOnClickListener {
                val nome = edtNomeCadastro.text.toString()
                val email = edtEmailCadastro.text.toString()
                val senha = edtSenhaCadastro.text.toString()

                if (nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()) {
                    val usuario = Usuario(nome = nome, email = email, senha = senha)

                    cadastrarUsuario(usuario)
                } else {
                    Toast.makeText(this@CadastroPageActivity, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun cadastrarUsuario(usuario: Usuario) {
        val json = gson.toJson(usuario)
        val requestBody = json.toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("$BASE_URL/user")
            .post(requestBody)
            .build()

        val callback = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@CadastroPageActivity, "Erro de rede: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@CadastroPageActivity, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@CadastroPageActivity, "Falha ao cadastrar usuário: ${response.code}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        client.newCall(request).enqueue(callback)
    }
}
