package com.aisouji.redis.utils;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author jwang
 * properties文件读写工具类
 *
 */
public abstract class PropertiesUtils 
{
	private static final Logger LOG = Logger.getLogger(PropertiesUtils.class);
	
	public static Properties getProperties(String path)
	{
		Properties prop = new Properties();
		
		loadProp(prop, path);
		
		return prop;
	}
	
	private static void loadProp(Properties p, String conf)
	{
		InputStream is = getInputStream(conf);
		
		if(null != is)
		{
			try 
			{
				p.load(is);
			} 
			catch (IOException e)
			{
				LOG.info("file not found!");
			}
			finally
			{
				if(is != null)
				{
					try 
					{
						is.close();
					} 
					catch (IOException e)
					{
						LOG.info("stream close fail!");
					}
				}
			}
		}
	}
	
	//获取输入流
	private static InputStream getInputStream(String conf)
	{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		
		InputStream is = null;
		
		if(null != classLoader)
		{
			is = classLoader.getResourceAsStream(conf);
		}
		return is;
	}
	
	//获取输出流
	private static OutputStream getOutPutStream(String conf)
	{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		
		OutputStream out = null;
		
		if(null != classLoader)
		{
			String filePath = classLoader.getResource(conf).getFile();
			try 
			{
				out = new FileOutputStream(filePath);
			} 
			catch (FileNotFoundException e) 
			{
				LOG.info("file not found!!!");
			}
		}
		return out;
	}
	
	//读取key=value
	public static String getValue(Properties p, String key)
	{
		String value = p.getProperty(key);
		
		return value == null?"":value;
	} 
	
	//设置key=value
	public static void setValue(String conf, String key, String value)
	{
		Properties p = getProperties(conf);
		
		OutputStream out = getOutPutStream(conf);
		
		p.setProperty(key, value);
		
		try 
		{
			p.store(out, "set:"+key+"="+value);
		} 
		catch (IOException e)
		{
			LOG.info("set properties fail!!!");
		}
		finally
		{
			if(out != null)
			{
				try 
				{
					out.close();
				} 
				catch (IOException e)
				{
					LOG.info("stream close fail!");
				}
			}
		}
	}
}
