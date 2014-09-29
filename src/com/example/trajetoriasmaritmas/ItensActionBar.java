package com.example.trajetoriasmaritmas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class ItensActionBar extends Activity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.itens_action_bar, menu);
		return true;	
	}
	

	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_coleta:
				openColeta();
				return true;
			case R.id.action_configuracoes:
				openConfiguracoes();
				return true;
/*			case R.id.action_visualizar:
				openVisualizar();
				return true;*/
			case R.id.action_sobre:
				openSobre();
				return  true;
			case R.id.action_sair:
				/*openSair();*/
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);		
		}
	}
	
	@SuppressWarnings("deprecation")
	public boolean openColeta(){
		if(MainActivity.salvarCc == true)
  	  	{    	
  		  AlertDialog errorDialog = new AlertDialog.Builder(ItensActionBar.this).create();  
  		  errorDialog.setCancelable(false);
  		  errorDialog.setTitle("Erro");
  		  errorDialog.setMessage("Existe uma coleta em andamento. Para iniciar uma nova, termine a atual.");  
  		  errorDialog.setButton("Ok", new DialogInterface.OnClickListener() {
  		      public void onClick(final DialogInterface dialog, final int which) {  
  		          dialog.dismiss();                      
  		      }  
  		  });  
  		  errorDialog.show();  
  	  	}
	  	  else{
	  		  	Intent Init = new Intent(ItensActionBar.this, NovaColeta.class);
	  		  ItensActionBar.this.startActivity(Init);
	  	  }
			return true;
		
	}
	
	public boolean openConfiguracoes(){
		Intent configuracoes = new Intent(ItensActionBar.this, Configuracoes.class);
		startActivity(configuracoes);
		return true;
	}
	
	public boolean openVisualizar(){
		Intent visualizar = new Intent(ItensActionBar.this, Visualizar.class);
		startActivity(visualizar);
		return true;
	}
	
	public boolean openSobre(){
		Intent sobre = new Intent(ItensActionBar.this, Sobre.class);
		startActivity(sobre);
		return true;
	}
	
	public boolean openSair(){
		Intent sair = new Intent(ItensActionBar.this, Sair.class);
		startActivity(sair);
		return true;
	}
}
