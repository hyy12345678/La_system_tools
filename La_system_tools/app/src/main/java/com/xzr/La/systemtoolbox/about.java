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
import com.avos.avoscloud.*;
import android.net.*;
public class about extends Activity
{
	ListView l1;
	EditText e1;
	SharedPreferences sp;
	SharedPreferences.Editor se;
	ProgressBar pr;
	AlertDialog dia;
	AlertDialog dia2;
	String ppp;
	View edit;
	View edit2;
	long code=0;
	EditText e2;
	EditText e3;
	String url;
	boolean error=false;
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
	public long getVersionCode(){
        PackageManager manager = getPackageManager();//获取包管理器
        try {
            //通过当前的包名获取包的信息
            PackageInfo info = manager.getPackageInfo(getPackageName(),0);//获取包对象信息
            return  info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
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
		 public class checkcode extends Thread{
			 public void run(){
				 while(true){

					 if (error)
					 {
						 runOnUiThread(new Runnable(){
								 public void run(){
									 Toast.makeText(getApplicationContext(),"网络连接错误",Toast.LENGTH_SHORT).show();
									 refresh();
									 pr.setVisibility(View.GONE);
								 }
							 });
						 break;
					 }
					 if(code!=0&&url!=null){
						 runOnUiThread(new Runnable(){
							 public void run(){
								 refresh();
								 pr.setVisibility(View.GONE);
							 }
						 });
						 break;
					 }
					 try
					 {
						 Thread.sleep(100);
					 }
					 catch (InterruptedException e)
					 {}
					
				 }
				 
			 }
		 }
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
		pr=(ProgressBar)findViewById(R.id.aboutProgressBar1);
		l1=(ListView)findViewById(R.id.aboutListView1);
		AVObject todo = AVObject.createWithoutData("info_push", "593817d9fe88c20061f4ffb6");
        todo.fetchInBackground(new GetCallback<AVObject>() {
				@Override
				public void done(AVObject avObject,AVException ge) {
					if(ge==null){
					
						code=Long.parseLong(avObject.getString("ver_code"));
					
						url=avObject.getString("update_url");
					}
					else{
						error=true;
					}
				}
			});
			
		new checkcode().start();
		
		edit=LayoutInflater.from(about.this).inflate(R.layout.input_dia,null);
		edit2=LayoutInflater.from(about.this).inflate(R.layout.feedback,null);
		e1=(EditText)edit.findViewById(R.id.inputdiaEditText1);
		e2=(EditText)edit2.findViewById(R.id.feedbackEditText1);
		e3=(EditText)edit2.findViewById(R.id.feedbackEditText2);
		sp=getSharedPreferences("main",0);
		
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
		dia2=new AlertDialog.Builder(about.this).setTitle("反馈")
			.setView(edit2)

			.setPositiveButton("确认",new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface p1,int p2){
					AVObject a=new AVObject("feedback");
					a.put("words",e2.getText().toString());
					a.put("user",e3.getText().toString());
					a.saveInBackground(new SaveCallback() {
							@Override
							public void done(AVException e) {
								if(e == null){
									Toast.makeText(getApplicationContext(),"反馈成功",Toast.LENGTH_SHORT).show();
								}
								else{
									Toast.makeText(getApplicationContext(),"出错了",Toast.LENGTH_SHORT).show();
								}
							}
						});
				}
			})
			.create();
		se=sp.edit();
		getActionBar().setDisplayHomeAsUpEnabled(true);

		
		
		
		}
	 public void refresh(){
		 String up;
		 if(code<=getVersionCode()){
			up= "当前版本：V"+getVersion()+"  已是最新版本";
		 }
		 else{
			 up= "当前版本：V"+getVersion()+"  有更新版本";
		 }
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
		 map0.put("title","更新");
		 map0.put("subtitle",up);
		
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
		 map2.put("title","反馈");
		 map2.put("subtitle","帮助改进应用");
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
				 if(arg2==1){
					 Intent intent = new Intent();        
					 intent.setAction("android.intent.action.VIEW");    
					 Uri content_url = Uri.parse(url);   
					 intent.setData(content_url);  
					 startActivity(intent);
				 }
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
				 if(arg2==4){
					 

					 dia2.show();

				 }
			 }
		 };

		 l1.setOnItemClickListener(mItemClickListener);
		 
	 }
		
		}
