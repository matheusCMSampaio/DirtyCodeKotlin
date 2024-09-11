package br.com.fiap.dirtycode_kotlin.activity

import android.app.Activity
import android.os.Bundle

import br.com.fiap.dirtycode_kotlin.databinding.ProfilePageLayoutBinding

class ProfilePageActivity : Activity() {

    private lateinit var binding: ProfilePageLayoutBinding
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        binding = ProfilePageLayoutBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.apply {
            btnSettings.setOnClickListener {

            }
        }

    }
}