package cn.edu.cqut.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONAndObject {
	//将JSON格式的数据转换为一个对象
	public static Object converSingleObject(Object obj,String json){
		if(obj == null || json == null){
			return obj;
		}
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			if(fields != null){
				JSONObject jsonObject = new JSONObject(json);
				for(Field field : fields){
					try{
						@SuppressWarnings("unused")
						String str = field.getName();
						field.setAccessible(true);
						Object objValue = jsonObject.get(field.getName()); 
						if(field.getType().getSimpleName().equals("String")){
							//字符串类型
							field.set(obj, String.valueOf(objValue));
						}else if(field.getType() == long.class){
							//Long类型
							field.set(obj, Long.valueOf(String.valueOf(objValue)));
						}else if(field.getType() == int.class){
							//Integer类型
							field.set(obj, Integer.valueOf(String.valueOf(objValue)));
						}else if(field.getType() == boolean.class){
							//Boolean类型
							field.set(obj,Boolean.valueOf(String.valueOf(objValue)));
						}else{
							//Objet类型
							Object fieldObject = field.getType().newInstance();
							converSingleObject(fieldObject, String.valueOf(objValue));
							field.set(obj, fieldObject);
						}
					}catch(Exception e){
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	//将JSON转换为List
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List convert(Class c,String json,String propertyName){
		List objs = null;
		if(c==null||json==null){
			return objs;
		}
		try{
			Field[] field = c.getDeclaredFields();
			if(field != null){
//				JSONObject jsonObject = new JSONObject(json);
//				String jsonStr = jsonObject.get(propertyName).toString();
				JSONArray jsonArray = new JSONArray(json);
				objs = new ArrayList();
				for(int i=0;i<jsonArray.length();i++){
					Object obj = c.newInstance();
					obj = converSingleObject(obj, jsonArray.getString(i));
					objs.add(obj);
				}
			}
		}catch(Exception e){
		}
		return objs;
	}
}
