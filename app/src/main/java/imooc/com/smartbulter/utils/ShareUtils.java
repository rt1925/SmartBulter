package imooc.com.smartbulter.utils;

import android.content.Context;
import android.content.SharedPreferences;

//SharedPreferences的封装
public class ShareUtils {

	public static final String NAME="config";
	//put 键-值
	public static void putString(Context mcontext,String key,String value){
		SharedPreferences sp=mcontext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}
	public static void putInt(Context mcontext,String key,int value){
		SharedPreferences sp=mcontext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		sp.edit().putInt(key, value).commit();
	}
	public static void putBoolean(Context mcontext,String key,boolean value){
		SharedPreferences sp=mcontext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}


	//get 键-默认值
	public static int getInt(Context mcontext,String key,int defValue){
		SharedPreferences sp=mcontext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		return sp.getInt(key, defValue);
	}
	public static String getString(Context mcontext,String key,String defValue){
		SharedPreferences sp=mcontext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		return sp.getString(key, defValue);
	}
	public static boolean getBoolean(Context mcontext,String key,boolean defValue){
		SharedPreferences sp=mcontext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		return sp.getBoolean(key, defValue);
	}

	//删除单个
	public static void deleShare(Context mcontext,String key){
		SharedPreferences sp=mcontext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		sp.edit().remove(key).commit();
	}

	//删除全部
	public static void deleAll(Context mcontext){
		SharedPreferences sp=mcontext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		sp.edit().clear().commit();
	}
}
