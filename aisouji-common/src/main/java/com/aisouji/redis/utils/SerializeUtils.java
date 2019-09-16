package com.aisouji.redis.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;
/**
 * 序列化：把对象转换为字节序列的过程称为对象的序列化
 * 反序列化：把字节序列恢复为对象的过程称为对象的反序列化
 * 
 * 序列化和反序列化的工具类
 * @author jwang
 *
 */
public class SerializeUtils
{
    private static final Logger LOGGER = Logger.getLogger(SerializeUtils.class);
    /** 
     * 序列化 
     *  
     * @param object 
     * @return 
     */  
    public static byte[] serialize(Object object) 
    {  
        ObjectOutputStream objectOutputStream = null;  
        ByteArrayOutputStream byteArrayOutputStream = null;  
        try
        {  
            // 序列化  
            byteArrayOutputStream = new ByteArrayOutputStream();  
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);  
            objectOutputStream.writeObject(object);  
            byte[] bytes = byteArrayOutputStream.toByteArray();  
            return bytes;  
        }
        catch (Exception e)
        {  
            LOGGER.error("对象序列化失败！！！");
            e.printStackTrace();
        }  
        return null;  
    }  
  
    /** 
     * 反序列化 
     *  
     * @param bytes 
     * @return 
     */  
    public static Object unserialize(byte[] bytes) 
    {  
        ByteArrayInputStream byteArrayInputStream = null;  
        try 
        {  
            // 反序列化  
            byteArrayInputStream = new ByteArrayInputStream(bytes);  
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);  
            return objectInputStream.readObject();  
        } 
        catch (Exception e)
        {  
            LOGGER.error("对象反序列化失败！！！");
            e.printStackTrace();
        }  
        return null;  
    }     
}