import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var editTituloNoticia: EditText
    private lateinit var editNoticia: EditText
    private lateinit var editDataNoticia: EditText
    private lateinit var editAutorNoticia: EditText
    private lateinit var btPublicarNoticia: Button
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTituloNoticia = findViewById(R.id.editTituloNoticia)
        editNoticia = findViewById(R.id.editNoticia)
        editDataNoticia = findViewById(R.id.editDataNoticia)
        editAutorNoticia = findViewById(R.id.editAutorNoticia)
        btPublicarNoticia = findViewById(R.id.btPublicarNoticia)

        btPublicarNoticia.setOnClickListener {
            publicarNoticia()
        }
    }

    private fun publicarNoticia() {
        val titulo = editTituloNoticia.text.toString()
        val noticia = editNoticia.text.toString()
        val data = editDataNoticia.text.toString()
        val autor = editAutorNoticia.text.toString()

        if (titulo.isEmpty() || noticia.isEmpty() || data.isEmpty() || autor.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val noticiaData = hashMapOf(
            "titulo" to titulo,
            "conteudo" to noticia,
            "data" to data,
            "autor" to autor
        )

        db.collection("noticias")
            .add(noticiaData)
            .addOnSuccessListener {
                Toast.makeText(this, "Notícia publicada com sucesso!", Toast.LENGTH_SHORT).show()
                limparCampos()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao publicar notícia: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun limparCampos() {
        editTituloNoticia.text.clear()
        editNoticia.text.clear()
        editDataNoticia.text.clear()
        editAutorNoticia.text.clear()
    }
}