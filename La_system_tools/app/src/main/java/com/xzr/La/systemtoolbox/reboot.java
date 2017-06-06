package com.xzr.La.systemtoolbox;
import android.app.*;
import android.os.*;
import android.view.*;
import java.io.*;

public class reboot extends Activity
{
	String cmd;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.reboot);
}
public void reboot(){
	try
	{
		java.lang.Process p=Runtime.getRuntime().exec("su");
		OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
		BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
		o.write(cmd+"\necho xzr467706992\n");
		o.flush();
		b.readLine();


	}
	catch (IOException e)
	{}
}
public void r9008(View v){
	cmd="reboot edl";
	reboot();
}
public void rec(View v){
	cmd="reboot recovery";
	reboot();
}
public void bl(View v){
	cmd="reboot bootloader";
	reboot();
}
}
