package com.example.trajetoriasmaritmas;

import java.text.DateFormat;
import java.util.Date;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class NovaColeta extends ItensActionBar {
	
	Button Iniciar;
	EditText editEmb,editServ;
	String embarcacao,servico,newEmb, newServ, nomeArq;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_nova_coleta);
		
		//Cria button up ni icone da action-bar
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		embarcacao = Configuracoes.getEmbarcacao(this);
		servico = Configuracoes.getServico(this);
		
		Iniciar = (Button) findViewById(R.id.btnIniciarNova);
		editEmb = (EditText) findViewById(R.id.editEmb);
		editServ = (EditText) findViewById(R.id.editServ);
		
		editEmb.setText(embarcacao);
		editServ.setText(servico);
		
		Iniciar.setOnClickListener(new View.OnClickListener() { 
	    	public void onClick(View v){	    		
	    		newEmb = editEmb.getText().toString();
	    		newServ = editServ.getText().toString();
	    		String dataInicioColeta = DateFormat.getDateTimeInstance().format(new Date());
	    		dataInicioColeta = dataInicioColeta.replace("/", "-");
	    		dataInicioColeta = dataInicioColeta.replace(" ", "-");
	    	
	    		nomeArq = newServ+";"+newEmb+";"+dataInicioColeta;
	    		MainActivity.IniciarColeta(nomeArq);
	    		
	    		finish();
	    	}});
		

	}

}
