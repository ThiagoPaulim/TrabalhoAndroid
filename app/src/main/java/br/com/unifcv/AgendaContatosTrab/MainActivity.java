package br.com.unifcv.AgendaContatosTrab;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AgendaBD helper;

    private RecyclerView contatosRecy;
    private AgendaAdapter adapter;

    private List<AgendaInfo> listaContatos;

    private final int REQUEST_NEW = 1;
    private final int REQUEST_ALTER = 2;

    private String order = "ASC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                i.putExtra("contato", new AgendaInfo());
                startActivityForResult(i, REQUEST_NEW);
            }
        });

        helper = new AgendaBD(this);
        listaContatos = helper.getList(order);

        contatosRecy = findViewById(R.id.contatosRecy);
        contatosRecy.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        contatosRecy.setLayoutManager(llm);

        adapter = new AgendaAdapter(listaContatos);
        contatosRecy.setAdapter(adapter);

        contatosRecy.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        abrirOpcoes(listaContatos.get(position));
                    }
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_NEW && resultCode == RESULT_OK){
            AgendaInfo agendaInfo = data.getParcelableExtra("contato");
            helper.inserirContato(agendaInfo);
            listaContatos = helper.getList(order);
            adapter = new AgendaAdapter(listaContatos);
            contatosRecy.setAdapter(adapter);
        } else if(requestCode == REQUEST_ALTER && resultCode == RESULT_OK){
            AgendaInfo agendaInfo = data.getParcelableExtra("contato");
            helper.alteraContato(agendaInfo);
            listaContatos = helper.getList(order);
            adapter = new AgendaAdapter(listaContatos);
            contatosRecy.setAdapter(adapter);
        }
    }

    private void abrirOpcoes(final AgendaInfo contato){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(contato.getNome());
        builder.setItems(new CharSequence[]{"Editar", "Excluir"},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent intent1 = new Intent(MainActivity.this, EditActivity.class);
                                intent1.putExtra("contato", contato);
                                startActivityForResult(intent1, REQUEST_ALTER);
                                break;
                            case 1:
                                listaContatos.remove(contato);
                                helper.apagarContato(contato);
                                adapter.notifyDataSetChanged();
                                break;
                        }
                    }
                });
        builder.create().show();
    }
}
