import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var editTextTempo: EditText
    private lateinit var textViewContador: TextView
    private lateinit var buttonIniciar: Button
    private var timer: CountDownTimer? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        editTextTempo = findViewById(R.id.editTextNumero)
        textViewContador = findViewById(R.id.textViewContador)
        buttonIniciar = findViewById(R.id.buttonIniciar)
        
        buttonIniciar.setOnClickListener {
            iniciarPomodoro()
        }
    }
    
    private fun iniciarPomodoro() {
        val tempoTexto = editTextTempo.text.toString()
        val tempoMinutos = tempoTexto.toIntOrNull() ?: 1 // Padrão 1 min para testes
        val tempoMillis = (tempoMinutos * 60 * 1000).toLong()
        
        timer?.cancel()
        timer = object : CountDownTimer(tempoMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val segundosRestantes = millisUntilFinished / 1000
                textViewContador.text = "$segundosRestantes segundos restantes"
            }
            
            override fun onFinish() {
                textViewContador.text = "Tempo esgotado!"
                vibrar(500)
            }
        }.start()
    }
    
    private fun vibrar(tempo: Long) {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val efeito = VibrationEffect.createOneShot(tempo, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(efeito)
        } else {
            vibrator.vibrate(tempo)
        }
    }
}
