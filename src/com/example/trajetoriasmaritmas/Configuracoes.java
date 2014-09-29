package com.example.trajetoriasmaritmas;


import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Configuracoes extends ItensActionBar{
	
	Button Salvar;
	EditText confEmb,confServ,confDelay;
	String embarcacao, servico,newEmb,newServ,newDelay;
	int delay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_configuracoes);
		
		//Cria button up ni icone da action-bar
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);

		
		Salvar = (Button) findViewById(R.id.btnSalvar);
		confEmb = (EditText) findViewById(R.id.confEmb);
		confServ = (EditText) findViewById(R.id.confServ);
		
		createDb(this);
		embarcacao = getEmbarcacao(this);
		servico = getServico(this);
		
		confEmb.setText(embarcacao);
		confServ.setText(servico);
		
		Salvar.setOnClickListener(new View.OnClickListener() { 
	    	public void onClick(View v){
	    		newEmb = confEmb.getText().toString();
	    		alterarEmbarcacao(newEmb);
	    		newServ = confServ.getText().toString();
	    		alterarServico(newServ);
	    		
	    		/*Intent Init = new Intent(Configuracoes.this, MainActivity.class);
	    		Configuracoes.this.startActivity(Init);*/
	    		
	    		finish();
	    		
	    	}});
			
	}
	
	public static String getServico(final Context ctx){
		SQLiteDatabase myDB = ctx.openOrCreateDatabase("configuracoes", MODE_PRIVATE, null);	
		
		String select = "SELECT * FROM SERVICOS";
		Cursor c = myDB.rawQuery(select, null);
		c.moveToNext();	
		String servico = c.getString(c.getColumnIndex("nome"));
		
		myDB.close();
		return servico;	
	}
	
	public static String getEmbarcacao(final Context ctx){
		SQLiteDatabase myDB = ctx.openOrCreateDatabase("configuracoes", MODE_PRIVATE, null);
		
		String select = "SELECT * FROM EMBARCACOES";
		Cursor c = myDB.rawQuery(select, null);
		c.moveToNext();	
		String embarcacao = c.getString(c.getColumnIndex("nome"));
		
		myDB.close();
		
		return embarcacao;
	}
	
	public static void createDb(final Context ctx){
		SQLiteDatabase myDB = ctx.openOrCreateDatabase("configuracoes", MODE_PRIVATE, null);
		
		myDB.execSQL("CREATE TABLE IF NOT EXISTS "
				+ "EMBARCACOES"
				+ " (id INTEGER PRIMARY KEY NOT NULL, nome VARCHAR(50));");
		
		myDB.execSQL("CREATE TABLE IF NOT EXISTS "
				+ "SERVICOS"
				+ " (id INTEGER PRIMARY KEY NOT NULL, nome VARCHAR(50));");

		myDB.execSQL("INSERT OR IGNORE INTO "
				+ "EMBARCACOES"
				+ " (id,nome)"
				+ " VALUES (1,'Embarcação 1');");
			
		myDB.execSQL("INSERT OR IGNORE INTO "
				+ "SERVICOS"
				+ " (id,nome)"
				+ " VALUES (1,'Serviço 1');");
		
		myDB.close();

	}
	
	public void alterarEmbarcacao(String embarc){
		SQLiteDatabase myDB = openOrCreateDatabase("configuracoes", MODE_PRIVATE, null);
		
		ContentValues cv = new ContentValues();
		cv.put("nome", embarc);
		myDB.update("EMBARCACOES", cv, "id=1", null);
		
		myDB.close();
	}
	
	public void alterarServico(String serv){
		SQLiteDatabase myDB = openOrCreateDatabase("configuracoes", MODE_PRIVATE, null);
		
		ContentValues cv = new ContentValues();
		cv.put("nome", serv);
		myDB.update("SERVICOS", cv, "id=1", null);
		
		myDB.close();
	}
}
