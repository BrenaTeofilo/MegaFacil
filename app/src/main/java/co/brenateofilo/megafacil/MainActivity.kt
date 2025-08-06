package co.brenateofilo.megafacil

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtNumber: EditText = findViewById(R.id.editNumber)
        val txtResult: TextView = findViewById(R.id.txtRes)
        val btnRoll: Button = findViewById(R.id.btnSend)

        prefs = getSharedPreferences("db", MODE_PRIVATE)
        val result = prefs.getString("result", null)
//        if (result != null) {
//            txtResult.text = "Ultima Aposta: $result"
//        }

        //substituiçao do if
        result?.let {
            txtResult.text = "Ultima Aposta: $it"
        }


        btnRoll.setOnClickListener {
            val text = txtNumber.text.toString()
            nGenerator(text, txtResult)

        }
    }

    private fun nGenerator(text: String, txtResult: TextView) {
        if (text.isEmpty()) {
            Toast.makeText(this, "Formulario nao aceita vazio", Toast.LENGTH_LONG).show()
            return
        }

        val qtd = text.toInt()
        if (qtd < 6 || qtd > 15) {
            Toast.makeText(this, "Digite um numero entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        val numbers = mutableSetOf<Int>()
        val random = Random()
        while (true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)

            if (numbers.size == qtd) {
                break
            }
        }
        txtResult.text = numbers.joinToString (" - " )

//        val editor = prefs.edit()
//        editor.putString("result", txtResult.text.toString())
//        editor.apply()

        //forma de substituir o editor
        prefs.edit().apply() {
            putString("result", txtResult.text.toString())
            apply()
        }
    }
}
