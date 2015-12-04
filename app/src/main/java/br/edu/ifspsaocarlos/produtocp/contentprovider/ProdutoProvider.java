package br.edu.ifspsaocarlos.produtocp.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import br.edu.ifspsaocarlos.produtocp.data.SQLiteHelper;

public class ProdutoProvider extends ContentProvider {

    public static final int PRODUTOS = 1;
    public static final int PRODUTOS_ID = 2;

    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(Produtos.AUTHORITY, "produtos", PRODUTOS);
        sURIMatcher.addURI(Produtos.AUTHORITY, "produtos/#", PRODUTOS_ID);
    }

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    @Override
    public boolean onCreate()
    {
        dbHelper = new SQLiteHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        database = dbHelper.getWritableDatabase();
        Cursor cursor;
        switch(sURIMatcher.match(uri))
        {
            case PRODUTOS:
                cursor = database.query(SQLiteHelper.DATABASE_TABLE, projection, selection, selectionArgs, null,null,sortOrder);
                break;
            case PRODUTOS_ID:
                cursor = database.query(SQLiteHelper.DATABASE_TABLE, projection, Produtos.KEY_ID + "=" + uri.getLastPathSegment(), null,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("URI desconhecida");
        }
        return cursor;
    }


    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        database = dbHelper.getWritableDatabase();
        int uriType = sURIMatcher.match(uri);
        int count;
        switch(uriType)
        {
            case PRODUTOS:
                count=database.delete(SQLiteHelper.DATABASE_TABLE, where,whereArgs);
                break;
            case PRODUTOS_ID:
                count=database.delete(SQLiteHelper.DATABASE_TABLE, Produtos.KEY_ID + "=" + uri.getPathSegments().get(1), null);
                break;

            default:
                throw new IllegalArgumentException("URI desconhecida");

        }
        database.close();
        return count;
    }



    @Override
    public String getType(Uri arg0) {
        switch(sURIMatcher.match(arg0))
        {
            case PRODUTOS:
                return Produtos.CONTENT_TYPE;
            case PRODUTOS_ID:
                return Produtos.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("URI desconhecida");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        database = dbHelper.getWritableDatabase();
        int uriType = sURIMatcher.match(uri);
        long id;
        switch(uriType)
        {
            case PRODUTOS:
                id=database.insert(SQLiteHelper.DATABASE_TABLE, null, values);
                break;
            default:
                throw new IllegalArgumentException("URI desconhecida");

        }
        uri = ContentUris.withAppendedId(uri, id);
        return uri;
    }


    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        database = dbHelper.getWritableDatabase();
        int uriType = sURIMatcher.match(uri);
        int count;
        switch(uriType)
        {
            case PRODUTOS:
                count=database.update(SQLiteHelper.DATABASE_TABLE, values,where,whereArgs);
                break;
            case PRODUTOS_ID:
                count=database.update(SQLiteHelper.DATABASE_TABLE, values, Produtos.KEY_ID + "=" + uri.getPathSegments().get(1), null);
                break;

            default:
                throw new IllegalArgumentException("URI desconhecida");
        }
        database.close();
        return count;
    }

    public static final class Produtos {

        public static final String AUTHORITY = "br.edu.ifspsaocarlos.produtocp.provider";


            public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY  + "/produtos");

            public static final String CONTENT_TYPE= "vnd.android.cursor.dir/vnd.br.edu.ifspsaocarlos.produtocp.produtos";
            public static final String CONTENT_ITEM_TYPE= "vnd.android.cursor.item/vnd.br.edu.ifspsaocarlos.produtocp.produtos";

            public static final String KEY_ID = "id";
            public static final String KEY_DESC = "descricao";
            public static final String KEY_PRECO = "preco";
    }
}