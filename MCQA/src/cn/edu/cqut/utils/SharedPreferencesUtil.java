package cn.edu.cqut.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences处理工具类
 * 
 * @author chenliang
 * @version v1.0
 * @date 2014-2-20
 */
public class SharedPreferencesUtil
{
	private static SharedPreferences preferences = null;

	/**
	 * 在调用SharedPreferencesUtil里的方法之前必须进行初始化
	 * 
	 * @param context
	 * @param name
	 *            SharedPreferences文件名
	 */
	public static void init(Context context, String name)
	{
		preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
	}

	public static void putBoolean(String key, boolean value)
	{
		preferences.edit().putBoolean(key, value).commit();
	}

	/**
	 * 默认值false
	 * 
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(String key)
	{
		return preferences.getBoolean(key, false);
	}

	public static void putInt(String key, int value)
	{
		preferences.edit().putInt(key, value).commit();
	}

	/**
	 * 默认值0
	 * 
	 * @param key
	 * @return
	 */
	public static int getInt(String key)
	{
		return preferences.getInt(key, 0);
	}
	
	public static int getInt(String key,int def)
	{
		return preferences.getInt(key, def);
	}

	public static void putFloat(String key, Float value)
	{
		preferences.edit().putFloat(key, value).commit();
	}

	/**
	 * 默认值0f
	 * 
	 * @param key
	 * @return
	 */
	public static Float getFloat(String key)
	{
		return preferences.getFloat(key, 0f);
	}

	public static void putLong(String key, Long value)
	{
		preferences.edit().putLong(key, value).commit();
	}

	/**
	 * 默认值0L
	 * 
	 * @param key
	 * @return
	 */
	public static Long getLong(String key)
	{
		return preferences.getLong(key, 0L);
	}

	public static void putString(String key, String value)
	{
		preferences.edit().putString(key, value).commit();
	}

	/**
	 * 默认值null
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key)
	{
		return preferences.getString(key, null);
	}
	
	public static String getString(String key,String def)
	{
		return preferences.getString(key, def);
	}
	
	/*清空*/
	public static void clean(){
		preferences.edit().clear().commit();
	}
	
}
