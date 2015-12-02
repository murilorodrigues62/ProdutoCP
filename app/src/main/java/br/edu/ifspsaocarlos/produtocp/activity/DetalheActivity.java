package br.edu.ifspsaocarlos.produtocp.activity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifspsaocarlos.produtocp.R;
import br.edu.ifspsaocarlos.produtocp.contentprovider.ProdutoProvider;
import br.edu.ifspsaocarlos.produtocp.model.Produto;

public class DetalheActivity extends AppCompatActivity {
    Produto p;
    Uri uriProd= ProdutoProvider.Produtos.CONTENT_URI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().hasExtra("produto"))
        {
            this.p = (Produto) getIntent().getSerializableExtra("produto");
            EditText descText = (EditText)findViewById(R.id.editText1);
            descText.setText(p.getDescricao());
            EditText precoText = (EditText)findViewById(R.id.editText2);
            precoText.setText(p.getPreco());

            int pos =p.getDescricao().indexOf(" ");
            if (pos==-1)
                pos=p.getDescricao().length();
            setTitle(p.getDescricao().substring(0, pos));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhe, menu);
        if (!getIntent().hasExtra("produto"))
        {
            MenuItem item = menu.findItem(R.id.delProduto);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salvarProduto:
                salvar();
                return true;
            case R.id.delProduto:

                getContentResolver().delete(ContentUris.withAppendedId(uriProd, p.getId()), null,null);
                //getContentResolver().delete(uriProd, ProdutoProvider.Produtos.KEY_ID + "=?",new String[] {Long.toString(p.getId())});

                Toast.makeText(getApplicationContext(), "Removido com sucesso", Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                setResult(RESULT_OK,resultIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void salvar()
    {
        String desc = ((EditText) findViewById(R.id.editText1)).getText().toString();
        String preco = ((EditText) findViewById(R.id.editText2)).getText().toString();



        ContentValues valores=new ContentValues();
        valores.put(ProdutoProvider.Produtos.KEY_DESC,desc);
        valores.put(ProdutoProvider.Produtos.KEY_PRECO,preco);

        if (p==null)
        {
            getContentResolver().insert(uriProd,valores);
            Toast.makeText(this, "Incluído com sucesso", Toast.LENGTH_SHORT).show();
        }
        else
        {
            getContentResolver().update(ContentUris.withAppendedId(uriProd, p.getId()), valores, null, null);
            //getContentResolver().update(uriProd, valores, ProdutoProvider.Produtos.KEY_ID + "=?",new String[] {Long.toString(p.getId())});

            Toast.makeText(this, "Alterado com sucesso", Toast.LENGTH_SHORT).show();
        }
        Intent resultIntent = new Intent();
        setResult(RESULT_OK,resultIntent);
        finish();
    }

}
