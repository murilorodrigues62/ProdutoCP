package br.edu.ifspsaocarlos.produtocp.activity;

import android.app.SearchManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.produtocp.R;
import br.edu.ifspsaocarlos.produtocp.adapter.ProdutoArrayAdapter;
import br.edu.ifspsaocarlos.produtocp.contentprovider.ProdutoProvider;
import br.edu.ifspsaocarlos.produtocp.model.Produto;

public class BaseActivity extends AppCompatActivity {


    protected ListView list;
    protected ProdutoArrayAdapter adapter;
    protected SearchView searchView;

    protected Uri uriProd=ProdutoProvider.Produtos.CONTENT_URI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2,
                                    long arg3) {
                Produto p = (Produto) adapterView.getAdapter().getItem(arg2);
                Intent inte = new Intent(getApplicationContext(), DetalheActivity.class);
                inte.putExtra("produto", p);
                startActivityForResult(inte, 0);
            }
        });

        registerForContextMenu(list);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.pesqProduto).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ProdutoArrayAdapter adapter = (ProdutoArrayAdapter)list.getAdapter();
        Produto p = adapter.getItem(info.position);
        switch(item.getItemId()){
            case R.id.delete_item:


                getContentResolver().delete(ContentUris.withAppendedId(uriProd,p.getId()), null,null);
                //getContentResolver().delete(uriProd, ProdutoProvider.Produtos.KEY_ID + "=?",new String[] {Long.toString(p.getId())});

                Toast.makeText(getApplicationContext(), "Removido com sucesso", Toast.LENGTH_SHORT).show();
                buildListView();
                return true;
        }
        return super.onContextItemSelected(item);
    }


    public List<Produto> cursorTolist(Cursor cursor)
    {
        List<Produto> prods=new ArrayList<Produto>();
        while (cursor.moveToNext()) {
            Produto p = new Produto();
            p.setId(cursor.getInt(0));
            p.setDescricao(cursor.getString(1));
            p.setPreco(cursor.getString(2));
            prods.add(p);
        }
        return prods;
    }


    protected void buildListView() {
       List<Produto> prods=new ArrayList<Produto>();
        Cursor  cursor = getContentResolver().query(uriProd,null,null,null,null);

        if (cursor!=null)
            prods=cursorTolist(cursor);

        adapter = new ProdutoArrayAdapter(this, prods);
        list.setAdapter(adapter);
    }


    protected void buildSearchListView(String query) {
        List<Produto> prods=new ArrayList<Produto>();

        String where= ProdutoProvider.Produtos.KEY_DESC + " like ?";
        String[] whereargs = new String[] {query + "%"};

        Cursor  cursor = getContentResolver().query(uriProd,null,where,whereargs,null,null);

        if (cursor!=null)
            prods=cursorTolist(cursor);

        adapter = new ProdutoArrayAdapter(this, prods);
        list.setAdapter(adapter);
    }
}
