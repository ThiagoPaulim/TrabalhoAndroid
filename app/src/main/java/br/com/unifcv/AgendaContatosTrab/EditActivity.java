package br.com.unifcv.AgendaContatosTrab;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;

public class EditActivity extends AppCompatActivity {

    private AgendaInfo contato;

    protected View layout;

    protected ImageButton foto;
    private EditText nome;
    private EditText ref;
    private EditText email;
    private EditText fone;
    private EditText end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        contato = getIntent().getParcelableExtra("contato");

        layout = findViewById(R.id.mainLayout);

        nome = findViewById(R.id.nomeContato);
        ref = findViewById(R.id.refContato);
        email = findViewById(R.id.emailContato);
        fone = findViewById(R.id.foneContato);
        end = findViewById(R.id.endContato);

        nome.setText(contato.getNome());
        ref.setText(contato.getRef());
        email.setText(contato.getEmail());
        fone.setText(contato.getFone());
        end.setText(contato.getEnd());

        File imgFile = new File(contato.getFoto());
        if (imgFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            foto.setImageBitmap(bitmap);
        }

        Button salvar = findViewById(R.id.btnSalvar);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contato.setNome(nome.getText().toString());
                contato.setRef(ref.getText().toString());
                contato.setEmail(email.getText().toString());
                contato.setFone(fone.getText().toString());
                contato.setEnd(end.getText().toString());

                if (contato.getNome().equals("")) {
                    Toast.makeText(EditActivity.this, "Não é possivel salvar sem um contato sem nome! ", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent i = new Intent();
                i.putExtra("contato", contato);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}
