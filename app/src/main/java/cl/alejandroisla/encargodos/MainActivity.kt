package cl.alejandroisla.encargodos

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin.setOnClickListener {
            login()
        }

    }

    private fun login(){

       val intent = Intent(this@MainActivity, PrincipalActivity::class.java)
/*
        startActivity(intent)
*/
        if(txtUser.text.toString().equals("duoc") && txtPass.text.toString().equals("duoc")){

            Toast.makeText(this@MainActivity, "Acceso concedido", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()

        }else{
            Toast.makeText(this@MainActivity, "Usuario o contraseña incorrecta.", Toast.LENGTH_SHORT).show()
        }
    }
}
