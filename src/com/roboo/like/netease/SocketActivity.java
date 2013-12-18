package com.roboo.like.netease;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SocketActivity extends BaseActivity
{
	private static final String IP_ADDRESS = "172.16.100.72";
	private static final int SERVER_PORT = 10000;
	private EditText mETText;
	private Button mBtnSend;
	private Button mBtnCancle;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_client_socket);
		// TODO setContentView Tag
		setContentView(R.layout.activity_socket);
		initView();
		this.mBtnCancle.setOnClickListener(this);
		this.mBtnSend.setOnClickListener(this);
	}

	private void initView()
	{
		this.mETText = (EditText) findViewById(R.id.et_text);
		this.mBtnCancle = (Button) findViewById(R.id.btn_cancle);
		this.mBtnSend = (Button) findViewById(R.id.btn_send);
	}

	// 发送信息
	public boolean sendMsg()
	{
		Socket socket = null;
		boolean flag = false;
		try
		{
			// 创建socket对象，指定服务器端地址和端口号
			socket = new Socket(IP_ADDRESS, SERVER_PORT);
			// 获取 Client 端的输出流
			PrintWriter out = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(socket.getOutputStream())), true);
			// 填充信息
			out.println(mETText.getText());
			System.out.println("msg=" + mETText.getText());
			flag = true;
		}
		catch (UnknownHostException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		finally
		{
			// 关闭
			try
			{
				if (null != socket)
				{
					socket.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}	 
		}
		return flag;	
	}
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_send:
			new AsyncTask<Void, Void, Boolean>()
			{

				@Override
				protected Boolean doInBackground(Void... params)
				{
					return sendMsg();
				}
				@Override
				protected void onPostExecute(Boolean result)
				{
					if(result.booleanValue())
					{
						Toast.makeText(SocketActivity.this, "发送成功",Toast.LENGTH_SHORT).show();
					mETText.setText("");
					}
					else
					{
						Toast.makeText(SocketActivity.this, "发送失败",Toast.LENGTH_SHORT).show();
					}
					
				}
			}.execute();
			break;
		case R.id.btn_cancle:
			this.finish();
		default:
			break;
		}
		super.onClick(v);

	}
}
