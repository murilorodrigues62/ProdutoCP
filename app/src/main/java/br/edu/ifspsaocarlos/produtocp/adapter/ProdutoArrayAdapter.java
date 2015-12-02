package br.edu.ifspsaocarlos.produtocp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ifspsaocarlos.produtocp.R;
import br.edu.ifspsaocarlos.produtocp.model.Produto;

public class ProdutoArrayAdapter extends ArrayAdapter<Produto> {
    private LayoutInflater inflater;

    public ProdutoArrayAdapter(Activity activity, List<Produto> objects) {
        super(activity, R.layout.produto_celula, objects);
        this.inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.produto_celula, null);
            holder = new ViewHolder();
            holder.descricao = (TextView) convertView.findViewById(R.id.nomeProduto);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Produto p = getItem(position);
        holder.descricao.setText(p.getDescricao());

        return convertView;
    }

    static class ViewHolder {
        public TextView descricao;
    }
}