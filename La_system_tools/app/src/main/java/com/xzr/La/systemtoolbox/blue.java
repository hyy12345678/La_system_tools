package com.xzr.La.systemtoolbox;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;
import java.util.*;
import android.util.*;
import java.io.*;
public class blue extends Activity
{
	SharedPreferences sp;
	SharedPreferences.Editor se;
	EditText e1;
	EditText e2;
	AlertDialog dia;
	String ppp;
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				break;

			default:
				break;
		}
    	return super.onOptionsItemSelected(item);
    }
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
		sp=getSharedPreferences("main",0);
		ppp=sp.getString("store_path",null);
		se=sp.edit();
        setContentView(R.layout.blue);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		e1=(EditText)findViewById(R.id.blueEditText1);
		e2=(EditText)findViewById(R.id.blueEditText2);
		}
		public class scf implements Runnable{
			public void run(){
				try{
					java.lang.Process p=Runtime.getRuntime().exec("su");
					BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
					OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
					o.write("dd if=/dev/zero of="+ppp+"/"+e1.getText().toString()+" bs=1024 count="+e2.getText().toString()+"\necho Success\n");
					o.flush();
					final String str=b.readLine();
					runOnUiThread(new Runnable(){
							public void run(){
								
									Toast.makeText(getApplicationContext(),str, Toast.LENGTH_SHORT).show();
									dia.dismiss();
								
							}
						});
				}
				catch(IOException e){

				}
			}
		}
		public void sc(View v){
			AlertDialog.Builder a;
			a= new AlertDialog.Builder(this);
			a.setTitle("生成文件中...")
				.setView(LayoutInflater.from(this).inflate(R.layout.circle,null))
				.setCancelable(false);
			dia=a.create();
			dia.show();
			new Thread(new scf()).start();
			
		}
		
		public void jz(View v){
			try
			{
				java.lang.Process p=Runtime.getRuntime().exec("su");
				OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
				BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
				o.write("rm -f /data/system/batterystats.bin\necho Success\n");
				o.flush();
				Toast.makeText(getApplicationContext(),b.readLine(),Toast.LENGTH_SHORT).show();



			}
			catch (IOException e)
			{

			}
			
		}
	public void wipepass(View v){
		try
		{
			java.lang.Process p=Runtime.getRuntime().exec("su");
			OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
			BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
			o.write("rm -f /data/system/gatekeeper.password.key\n");
			o.write("rm -f /data/system/gatekeeper.pattern.key\necho Success\n");
			o.flush();
			Toast.makeText(getApplicationContext(),b.readLine(),Toast.LENGTH_SHORT).show();



		}
		catch (IOException e)
		{

		}

	}
	public void fixfc(View v){
		try
		{
			java.lang.Process p=Runtime.getRuntime().exec("su");
			OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
			BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
			o.write("rm -f /data/system/locksettings.db\n");
			o.write("rm -f /data/system/locksettings.db-wal\n");
			o.write("rm -f /data/system/locksettings.db-shm\necho Success\n");
			o.flush();
			Toast.makeText(getApplicationContext(),b.readLine(),Toast.LENGTH_SHORT).show();



		}
		catch (IOException e)
		{

		}
	}
		
		}
