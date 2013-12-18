package com.roboo.like.netease.utils;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.roboo.like.netease.NewsApplication;

public class FileCopyUtils
{
	public static boolean copyDB(InputStream in)
	{
		File dbFile = new File(NewsApplication.DB_PATH + NewsApplication.DB_NAME);
		if(!dbFile.getParentFile().exists())
		{
			dbFile.getParentFile().mkdirs();
		}
		if(!dbFile.exists())
		{			
			OutputStream out = null;
			try
			{
				out = new FileOutputStream(dbFile);
				byte[] buffer = new byte[1024];
				int len = 0;
				while((len = in.read(buffer)) != -1)
				{
					out.write(buffer, 0, len);
				}
				return true;
			}	
			catch (Exception e)
			{
				e.printStackTrace();
				return false;
			}
			finally
			{
				try
				{
					if(in != null )
					{
						in.close();
					}
					if(out != null)
					{
						out.close();
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
						
		}
		return false;
		
	}
}
