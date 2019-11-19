package br.com.unifcv.AgendaContatosTrab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.ContactViewHolder>{

    private List<AgendaInfo> listaContatos;

    AgendaAdapter(List<AgendaInfo> lista){
        listaContatos = lista;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.agenda_contato, parent, false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        AgendaInfo c = listaContatos.get(position);
        holder.nome.setText(c.getNome());
        holder.ref.setText(c.getRef());
        holder.fone.setText(c.getFone());

        File imgFile = new File(c.getFoto());
        if(imgFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.foto.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return listaContatos.size();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {

        ImageView foto;
        TextView nome;
        TextView ref;
        TextView fone;

        ContactViewHolder(View v){
            super(v);
            nome = v.findViewById(R.id.textoNome);
            ref = v.findViewById(R.id.textoReferencia);
            fone = v.findViewById(R.id.textoTelefone);
        }

    }

}
