package br.edu.ifspsaocarlos.produtocp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import br.edu.ifspsaocarlos.produtocp.R;

public class MainActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DetalheActivity.class);
                startActivityForResult(i, 0);
            }
        });

        buildListView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        buildListView();
        searchView.clearFocus();
    }
}
