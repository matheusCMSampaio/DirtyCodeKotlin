package br.com.fiap.dirtycode_kotlin.activity

import Usuario
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import br.com.fiap.dirtycode_kotlin.databinding.LoginPageLayoutBinding
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class LoginPageActivity : Activity() {

    private lateinit var binding: LoginPageLayoutBinding
    val BASE_URL = "https://webappdirtycodeapi.azurewebsites.net"
    val gson = Gson()
    private val client = OkHttpClient()


    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        // Inflate o layout com o binding
        binding = LoginPageLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root) // Define o layout da activity

        binding.apply {
            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString()
                val senha = edtSenha.text.toString()

                if (email.isNotEmpty() && senha.isNotEmpty()){
                    val usuario = Usuario(nome = "", cpf = "", email = email, senha = senha, telefone = "")
                    fazerLogin(usuario)

                }


            }
            btnCadastro.setOnClickListener {
                val intent = Intent(this@LoginPageActivity, CadastroPageActivity::class.java)
                startActivity(intent)
            }
        }

    }
    private fun fazerLogin(usuario: Usuario){
        val json = gson.toJson(usuario)

        val request = Request.Builder()
            .url("$BASE_URL/user/usuario")
            .post(json.toRequestBody("application/json".toMediaType()))
            .build()

        Log.v("TESTES", request.toString())
    }
}
