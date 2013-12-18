package com.roboo.like.netease;

import java.net.ServerSocket;

import com.roboo.like.netease.aidl.ISocketService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class SocketService extends Service
{

	@Override
	public IBinder onBind(Intent intent)
	{
		//TODO 
		return new SocketServiceStub();
		
	}
	private class SocketServiceStub extends ISocketService.Stub
	{

		@Override
		public void printMsg() throws RemoteException
		{
			System.out.println("服务器Android");	
		}
		
	}
}
