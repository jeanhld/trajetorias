package com.example.trajetoriasmaritmas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Environment;

public class Txt {

	   public static void writeTxt(String text, String name){
	    	
	       File externalStorageDir = Environment.getExternalStorageDirectory();
		   File logFile = new File(externalStorageDir, name+".txt");
		   if (!logFile.exists())
		   {
		      try
		      {
		         logFile.createNewFile();
		      } 
		      catch (IOException e)
		      {
		         e.printStackTrace();
		      }
		   }
		   try
		   {
		      BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true)); 
		      buf.append(text);
		      buf.close();
		   }
		   
		   catch (IOException e)
		   {
		      e.printStackTrace();
		   }
	    }


}
