package com.example.trajetoriasmaritmas;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends ItensActionBar implements SensorEventListener{ 
	
	/*Faz conexão com os itens da action-bar na activity ItensActionBar 
	 *e verifica o ciclo de vida de uma activity
	 */
	private final String TAG = "MainActivity";
	
	static Button btnGps,btnGpsSair; 
	static TextView txtLatitude,txtLongitude,txtSpeed,txtAltitude,txtObs,txtObsTrue;
	TextView txtAcelX,txtAcelY,txtAcelZ;
	static String deviceName,txtSalvar,nomeArquivo,currentDate;
	static boolean salvarCc = false;
	static float sensorX,sensorY,sensorZ;
	static final int TIPO_SENSOR = Sensor.TYPE_ACCELEROMETER;
	SensorManager sensorManager;
	Sensor sensor;
	boolean coletaInfo = false;
	static int DELAY_CAPTURA = 5;
     
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.fragment_main);
        
        //CRIAR DATABASE SE PRIMEIRO USO
        Configuracoes.createDb(this);
        
        //FORÇA A CRIAÇÃO DE UM MENU OVERFLOW EM DISPOSITIVOS QUE NÃO RECONHECEM
        try{
        	ViewConfiguration config = ViewConfiguration.get(this);
        	Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
        	if (menuKeyField != null){
        		menuKeyField.setAccessible(true);
        		menuKeyField.setBoolean(config, false);
        	}
        }
        catch (Exception e){
        	
        }
        
       
		// INICIAR O SERVIÇO DO ACELERÔMETRO
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(TIPO_SENSOR);
		sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL); 
		
		// DADOS DO SMARTPHONE
		deviceName = android.os.Build.MANUFACTURER + "-" + android.os.Build.MODEL;
		
        txtLatitude = (TextView) findViewById(R.id.txtLatitude); 
        txtObs = (TextView) findViewById(R.id.txtObs);
        txtObsTrue = (TextView) findViewById(R.id.txtObsTrue); 
        txtLongitude = (TextView) findViewById(R.id.txtLongitude);
        txtSpeed = (TextView) findViewById(R.id.txtSpeed); 
        txtAltitude = (TextView) findViewById(R.id.txtAltitude);
        txtAcelX = (TextView) findViewById(R.id.txtAcelX);
        txtAcelY = (TextView) findViewById(R.id.txtAcelY); 
        txtAcelZ = (TextView) findViewById(R.id.txtAcelZ); 
         
        btnGps = (Button) findViewById(R.id.btnGps); 
        btnGps.setOnClickListener(new View.OnClickListener() { 
		public void onClick(View v){
        	  	Intent Init = new Intent(MainActivity.this, NovaColeta.class);
      	  		MainActivity.this.startActivity(Init);
        }});
        
        btnGpsSair = (Button) findViewById(R.id.btnGpsSair); 
        btnGpsSair.setOnClickListener(new Button.OnClickListener() { 
          public void onClick(View v){
        	  StopColeta(); 
        }});
       
        txtLatitude.setText("Procurando...");
		txtLongitude.setText("Procurando...");
		txtSpeed.setText("Procurando...");
		txtAltitude.setText("Procurando...");
        
        // INICIA O SERVIÇO DE GPS
		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				Atualizar(location);
		    }
		    public void onStatusChanged(String provider, int status, Bundle extras) {}
		    public void onProviderEnabled(String provider) {}
		    public void onProviderDisabled(String provider) {}
		};
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MainActivity.DELAY_CAPTURA*1000, 0, locationListener);
		        
    } 
     
    // METODOS
    
    // COMEÇAR SALVAR OS DADOS COLETADOS NO TXT
	public static void IniciarColeta(String nomeArq) {
		salvarCc = true;
		btnGps.setVisibility(View.INVISIBLE);
		btnGpsSair.setVisibility(View.VISIBLE);
		txtObs.setVisibility(View.INVISIBLE);
		txtObsTrue.setVisibility(View.VISIBLE);
        
		MainActivity.nomeArquivo = nomeArq;
        currentDate = DateFormat.getDateTimeInstance().format(new Date());
        txtSalvar = "Início de Coleta"+";"+nomeArquivo+";"+deviceName+";"+currentDate+"\n";
		Txt.writeTxt(txtSalvar, nomeArq);
	}
    
	// PARA DE SALVAR OS DADOS NO TXT
	public void StopColeta(){
		salvarCc = false;
		btnGps.setVisibility(View.VISIBLE);
		btnGpsSair.setVisibility(View.INVISIBLE);
		txtObs.setVisibility(View.VISIBLE);
		txtObsTrue.setVisibility(View.INVISIBLE);
	}
	
	//ESCREVER COORDENADAS NO ARQUIVO
    public static void Atualizar(Location location) {
        Double latPoint = location.getLatitude();
        Double lngPoint = location.getLongitude();
        float speedPoint = location.getSpeed();
        Double altPoint = location.getAltitude();
        
        // Converter m/s para km/h
        speedPoint = (float) (speedPoint*3.6);
        
        // Formato para numero float
        DecimalFormat df = new DecimalFormat();  
        df.applyPattern("0.0");
        
        //COORDENADAS
        txtLatitude.setText(latPoint.toString());
        txtLongitude.setText(lngPoint.toString());
        txtSpeed.setText(df.format(speedPoint).replace(",",".")+" km/h");
        txtAltitude.setText(df.format(altPoint).replace(",",".")+" m");
        
        //DATA
        currentDate = DateFormat.getDateTimeInstance().format(new Date());
        
        //ESCREVER NO TXT SE VERDADEIRO
        if (salvarCc == true){
        	txtSalvar = currentDate+";"+latPoint+";"+lngPoint+";"+sensorX+";"+sensorY+";"+sensorZ+df.format(speedPoint)+"\n";
        	Txt.writeTxt(txtSalvar, nomeArquivo);
        }
    } 

    // DADOS ACELERÔMETRO
	@Override
	public void onSensorChanged(SensorEvent event) {
		
		// Formato para numero float
        DecimalFormat df = new DecimalFormat();  
        df.applyPattern("0.0");
        
		sensorX = event.values[0];
		sensorY = event.values[1];
		sensorZ = event.values[2];
		
        txtAcelX.setText(df.format(sensorX).replace(",","."));
        txtAcelY.setText(df.format(sensorY).replace(",","."));
        txtAcelZ.setText(df.format(sensorZ).replace(",","."));
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}
	
	//VERIFICA SE ESTÁ CONECTADO NA INTERNET
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	    	new AlertDialog.Builder(this).setTitle("Conexão").setMessage("Conectado.").setNeutralButton("Sair", null).show();
	    	return true;
	    }
	    new AlertDialog.Builder(this).setTitle("Conexão").setMessage("Serviço indisponível. Conecte-se à Internet.").setNeutralButton("Sair", null).show();
	    return false;
	}
	
	/*Retorna a TAG de conexão com os itens da action-bar na activity ItensActionBar 
	 *e verifica o ciclo de vida de uma activity
	 */
	public String getTAG(){
		return TAG;
	}
} 