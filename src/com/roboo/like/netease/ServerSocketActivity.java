package com.roboo.like.netease;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ServerSocketActivity extends BaseActivity
{
	private static final int LISTENING_PORT = 8888;
	private static final String IP_ADDRESS = "172.16.100.72";
	private static final int SERVER_PORT = 10000;
	private TextView mTVIP;
	private TextView mTVContent;
	private Button mBtnSend;
	private Handler mHandler;
	private String mIp;
	private String mContent;
	private Button mBtnCancle;
	private ServerSocket mServerSocket;
	private ServerSocketThread mServerSocketThread;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_server_socket);
		// TODO setContentView Tag
		setContentView(R.layout.activity_server_socket);
		initHandler();
		initView();
		this.mBtnCancle.setOnClickListener(this);
		this.mBtnSend.setOnClickListener(this);
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifiManager.getConnectionInfo();
		System.out.println("IP = " + intToIp(info.getIpAddress()));
		mServerSocketThread = new ServerSocketThread();
		mServerSocketThread.start();
	}

	private void initHandler()
	{
		this.mHandler = new Handler(new Callback()
		{
			public boolean handleMessage(Message msg)
			{
				mTVContent.setText(mContent);
				mTVIP.setText(mIp);
				return true;

			}
		});
	}

	private void initView()
	{
		this.mTVIP = (TextView) findViewById(R.id.tv_ip);
		this.mTVContent = (TextView) findViewById(R.id.tv_content);
		this.mBtnCancle = (Button) findViewById(R.id.btn_cancle);
		this.mBtnSend = (Button) findViewById(R.id.btn_send);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.ibtn_left_top:
			handleFinish();
			break;
		case R.id.btn_send:
			break;
		case R.id.btn_cancle:
			this.finish();
		default:
			break;
		}
		super.onClick(v);

	}
	private String intToIp(int i)
	{
		return (i & 0xFF) + "." +

			((i >> 8) & 0xFF) + "." +

			((i >> 16) & 0xFF) + "." +

			(i >> 24 & 0xFF);

	}
	private class ServerSocketThread extends Thread
	{
		public void run()
		{

			try
			{
				if (null == mServerSocket)
				{
					mServerSocket = new ServerSocket(LISTENING_PORT);

				}
				while (true)
				{
					Socket socket = mServerSocket.accept();
					BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					// 读取数据
					String msg = buffer.readLine();
					System.out.println("从PC客户端发送过来的信息  = " + msg);
					mIp = (null == socket.getInetAddress()) ? "Ip地址为空" : "Ip地址  = " + socket.getInetAddress().getHostAddress() + " 主机名 = " + socket.getInetAddress().getHostName();
					mContent = msg;
					mHandler.sendEmptyMessage(1);
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				if (null != mServerSocket)
				{
					try
					{
						mServerSocket.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}

		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			handleFinish();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
	private void handleFinish()
	{
		try
		{
			mServerSocketThread.interrupt();
			mServerSocket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		this.finish();

	}
}
