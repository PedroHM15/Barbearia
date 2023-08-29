package com.example.barbeariasuper;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends BaseAdapter {
    Context context;
    List<String> nomes;
    List<String> data;
    List<String> hora;
    List<String> imagem;
    LayoutInflater inflater;

    // Construtor da classe, recebe os dados e o contexto da atividade que irá utilizá-lo
    public Adapter(Context context, List<String> nomes, List<String> data, List<String> hora, List<String> imagem) {
        this.context = context;
        this.nomes = nomes;
        this.data = data;
        this.hora = hora;
        this.imagem = imagem;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // Retorna a quantidade de itens na lista de nomes, que será igual à quantidade de itens na lista de idades
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // Retorna o item na posição fornecida, mas não será utilizado neste exemplo
        return null;
    }

    @Override
    public long getItemId(int position) {
        // Retorna o ID do item na posição fornecida, mas não será utilizado neste exemplo
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Infla o layout personalizado para cada item da lista
        convertView = inflater.inflate(R.layout.adapter_lista_cortes, null);

        // Obtém as referências para os TextViews do layout personalizado
        TextView tvNome = convertView.findViewById(R.id.nome_adapter);
        TextView tvData = convertView.findViewById(R.id.data_adapter);
        TextView tvHora = convertView.findViewById(R.id.hora_adapter);

        ImageView imagens = convertView.findViewById(R.id.image_adapter);

        // Define os valores do nome e idade para a posição atual da lista
        Picasso.get().load(imagem.get(position)).into(imagens);

        tvNome.setText(nomes.get(position));

        tvData.setText(data.get(position));

        tvHora.setText(hora.get(position));

        return convertView;
    }
}