package com.example.trajetoriasmaritmas;

import android.app.ActionBar;
import android.os.Bundle;

public class Sobre extends ItensActionBar {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sobre);
		
		//Cria button up ni icone da action-bar
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	
	}



}
