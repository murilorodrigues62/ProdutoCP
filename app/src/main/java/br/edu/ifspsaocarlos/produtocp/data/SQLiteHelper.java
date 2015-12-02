package br.edu.ifspsaocarlos.produtocp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.edu.ifspsaocarlos.produtocp.contentprovider.ProdutoProvider;

public class SQLiteHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "produtos.db";
    public static final String DATABASE_TABLE = "produtos";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_CREATE = "CREATE TABLE "+ DATABASE_TABLE +" (" +
            ProdutoProvider.Produtos.KEY_ID  +  " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ProdutoProvider.Produtos.KEY_DESC + " TEXT NOT NULL, " +
            ProdutoProvider.Produtos.KEY_PRECO + " TEXT);";
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int     newVersion) {
    }

}
