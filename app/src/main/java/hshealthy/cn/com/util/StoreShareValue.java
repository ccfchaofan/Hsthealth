package hshealthy.cn.com.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 *  SharedPreferences 辅助类
 */
public class StoreShareValue {

    private static SharedPreferences getDataShared(Context context, String sharedName) {
        		return context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
    }

    public static void clear(Context context,String sharedName){
        try {
            getDataShared(context, sharedName).edit().clear().apply();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void remove(String key,Context context,String sharedName){
        try {
           SharedPreferences sharedPreferences= getDataShared(context, sharedName);
            sharedPreferences.edit().remove(key).apply();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String getString(String key, String defaultStr, Context context,
                                   String sharedName) {
        
    	try {
    		SharedPreferences sharedPreferences = getDataShared(context, sharedName);
            return sharedPreferences.getString(key, defaultStr);
		} catch (Exception e) {
			return defaultStr;
		}
    }

    public static void putString(String key, String value, Context context,
                                 String sharedName) {
        SharedPreferences sharedPreferences = getDataShared(context, sharedName);
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static int getInt(String key, int defaultValue, Context context,
                             String sharedName) {
        
    	try {
    		SharedPreferences sharedPreferences = getDataShared(context, sharedName);
            return sharedPreferences.getInt(key, defaultValue);
		} catch (Exception e) {
			return defaultValue;
		}
    }

    public static void putInt(String key, int value, Context context,
                              String sharedName) {
        SharedPreferences sharedPreferences = getDataShared(context, sharedName);
        sharedPreferences.edit().putInt(key, value).apply();
    }
    public static void putBoolean(String key, Boolean value, Context context,
                                  String sharedName) {
        SharedPreferences sharedPreferences = getDataShared(context, sharedName);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }
    public static boolean getBoolean(String key, Boolean defaultValue, Context context,
                                     String sharedName) {
       
    	try {
    		 SharedPreferences sharedPreferences = getDataShared(context, sharedName);
    	        return sharedPreferences.getBoolean(key, defaultValue);
		} catch (Exception e) {
		        return defaultValue;
		}
    }
    public static void putFloat(String key, Float value, Context context,
                                String sharedName) {
        SharedPreferences sharedPreferences = getDataShared(context, sharedName);
        sharedPreferences.edit().putFloat(key, value).apply();
    }
    public static float getFloat(String key, Float defaultValue, Context context,
                                 String sharedName) {
      try {
    	  SharedPreferences sharedPreferences = getDataShared(context, sharedName);
          return sharedPreferences.getFloat(key, defaultValue);
	} catch (Exception e) {
		return defaultValue;
	}
    }
    public static void putLong(String key, Long value, Context context,
                               String sharedName) {
        SharedPreferences sharedPreferences = getDataShared(context, sharedName);
        sharedPreferences.edit().putLong(key, value).apply();
    }
    public static long getLong(String key, Long defaultValue, Context context,
                               String sharedName) {
       try {
    	   SharedPreferences sharedPreferences = getDataShared(context, sharedName);
           return sharedPreferences.getLong(key, defaultValue);
	} catch (Exception e) {
		return defaultValue;
	}
    }
    public static String getSerialNumber() {
        StringBuffer serialNumber = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int v = Math.abs(random.nextInt()) % 10;
            serialNumber.append(v);
        }
        try{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String time=sdf.format(new Date());
        serialNumber.append(time);
        }catch (Exception e) {
            e.printStackTrace();
		}
        return serialNumber.toString();
    }
    public static void putObject(String objectKeyName, Object object, Context context ,
                                 String sharedName ){
    	try {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedName,
                Context.MODE_PRIVATE);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
		oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        // 将对象放到OutputStream中
        // 将对象转换成byte数组，并将其进行base64编码
        String data = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // 将编码后的字符串写到base64.xml文件中
        editor.putString(objectKeyName,data).apply();
//        editor.apply();
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
    /**
     * 用SharedPreferences从本地读取你保存的对象
     * @param context
     * @param sharedName
     * @param objectKeyName
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object getObject(String objectKeyName, String sharedName, Context context)
             {
    	Object object = null;
    	ByteArrayInputStream bais=null;
    	ObjectInputStream ois = null;
    	 try {

        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedName,
                Context.MODE_PRIVATE);
        String data = sharedPreferences.getString(objectKeyName, "");
        // 对Base64格式的字符串进行解码
        byte[] base64Bytes = Base64.decode(data.getBytes(), Base64.DEFAULT);
        bais = new ByteArrayInputStream(base64Bytes);
			ois = new ObjectInputStream(bais);
			object = ois.readObject();
			 return object;
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
			return object;
		} catch (IOException e) {
			e.printStackTrace();
			return object;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return object;
		}finally{
			if(ois!=null){
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bais!=null){
				try {
					bais.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }
}
