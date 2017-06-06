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
import android.content.pm.*;
public class about extends Activity
{
	ListView l1;
	EditText e1;
	SharedPreferences sp;
	SharedPreferences.Editor se;
	AlertDialog dia;
	String ppp;
	View edit;
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
	public String getVersion() {
		String version = null;
		try {
			         PackageManager manager = this.getPackageManager();
			         PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			         version = info.versionName;
			       
			     } 
		catch (Exception e) {
			e.printStackTrace();
		}
		return version;
		 }
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
		sp=getSharedPreferences("main",0);
		edit=LayoutInflater.from(about.this).inflate(R.layout.input_dia,null);
		dia=new AlertDialog.Builder(about.this).setTitle("修改存储路径")
			.setView(edit)
			.setPositiveButton("确认",new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface p1,int p2){
					se.putString("store_path",e1.getText().toString());
					se.commit();
					refresh();
				}
			})
			.create();
		se=sp.edit();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		e1=(EditText)edit.findViewById(R.id.inputdiaEditText1);
		l1=(ListView)findViewById(R.id.aboutListView1);
		refresh();
		}
	 public void refresh(){
		 
	     ppp=sp.getString("store_path",null);
		 if(ppp==null||ppp.equals("")){
			 ppp="/mnt/sdcard/Lanthanum";
			 se.putString("store_path",ppp);
			 se.commit();
		 }
		 File f=new File(sp.getString("store_path",null));
		 f.mkdirs();
		 List<HashMap<String,String>> builder=new ArrayList<HashMap<String,String>>();
		 HashMap<String,String> map3=new HashMap<String,String>();
		 map3.put("title","捐赠");
		 map3.put("subtitle","帮助并支持开发");
		 builder.add(map3);
		 HashMap<String,String> map0=new HashMap<String,String>();
		 map0.put("title","版本");
		 map0.put("subtitle","V"+getVersion());
		 builder.add(map0);

		 HashMap<String,String> map1=new HashMap<String,String>();
		 map1.put("title","开发者");
		 map1.put("subtitle","酷安@xzr467706992");
		 builder.add(map1);
		 HashMap<String,String> map001=new HashMap<String,String>();
		 map001.put("title","图标");
		 map001.put("subtitle","酷安@pandecheng");
		 builder.add(map001);
		 HashMap<String,String> map2=new HashMap<String,String>();
		 map2.put("title","版本类型");
		 map2.put("subtitle","实验版");
		 builder.add(map2);

		 HashMap<String,String> map4=new HashMap<String,String>();
		 map4.put("title","存储路径");
		 map4.put("subtitle",ppp);
		 builder.add(map4);
		 SimpleAdapter a=new SimpleAdapter(this,builder,R.layout.list,new String[]{"title","subtitle"},new int[]{R.id.title,R.id.subtitle});
		 l1.setAdapter(a);
		 OnItemClickListener mItemClickListener = new OnItemClickListener() {
			 @Override
			 public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
				 if(arg2==0){
					 try{
						 new File("/mnt/sdcard/Pictures").mkdirs();
						 File file=new File("/mnt/sdcard/Pictures/捐赠.png");
						 InputStream ins = getResources().openRawResource(R.drawable.jz);// 通过raw得到数据资源  
						 //  开始读入
						 FileOutputStream fos = new FileOutputStream(file);  
						 // 开始写出
						 byte[] buffer = new byte[8192];  
						 int count = 0;// 循环写出  
						 while ((count = ins.read(buffer)) > 0) {  
							 fos.write(buffer, 0, count);  
						 }  
						 Toast.makeText(getApplicationContext(),"二维码已输出到/sdcard/Pictures 捐赠方式:微信扫一扫>从相册选取二维码 ",Toast.LENGTH_LONG).show();
						 fos.close();// 关闭流  
						 ins.close();  
					 }
					 catch(Exception e){
						 Toast.makeText(getApplicationContext(),"你可能干了一些坏事",Toast.LENGTH_SHORT).show();
					 }

				 }
				 if(arg2==5){
					 e1.setText(ppp);
					 dia.show();
					 
					 
				 }
			 }
		 };

		 l1.setOnItemClickListener(mItemClickListener);
		 
	 }
		
		}
