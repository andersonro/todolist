package br.com.arodevsistemas.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import br.com.arodevsistemas.todolist.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val intent: Intent = Intent(this, MainActivity::class.java)

        Handler().postDelayed(Runnable {
            startActivity(intent)
            finish()
        }, 3000)


    }
}