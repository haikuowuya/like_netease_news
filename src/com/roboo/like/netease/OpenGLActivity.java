package com.roboo.like.netease;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;

public class OpenGLActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_open_gl);
		// TODO setContentView Tag
		setContentView(new MyGLSurfaceView(this));

	}

	private class MyGLSurfaceView extends GLSurfaceView
	{

		public MyGLSurfaceView(Context context)
		{
			super(context);

			System.out.println("MyGLSurfaceView");
			// 当使用OpenGLES 2.0时，你必须在GLSurfaceView构造器中调用另外一个函数，它说明了你将要使用2.0版的API：
			// 创建一个OpenGL ES 2.0 context
			setEGLContextClientVersion(2);
			// 另一个可以添加的你的GLSurfaceView实现的可选的操作是设置render模式为只在绘制数据发生改变时才绘制view。使用GLSurfaceView.RENDERMODE_WHEN_DIRTY：

			// 只有在绘制数据改变时才绘制view
			setRenderer(new MyRenderer());
			setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
			// 此设置会阻止绘制GLSurfaceView的帧，直到你调用了requestRender()，这样会非常高效。

		}

	}

	private class MyRenderer implements Renderer
	{
		private Triangle mTriangle;
		private Square mSquare;
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{
			// 仅调用一次，用于设置view的OpenGLES环境。
			// 设置背景的颜色
			GLES20.glClearColor(0.5f, 0.5f, 0.5f, 0.0f);
			System.out.println("onSurfaceCreated");
			mTriangle =  new Triangle();
			mSquare = new Square();
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height)
		{
			// 如果view的几和形状发生变化了就调用，例如当竖屏变为横屏时。
			GLES20.glViewport(0, 0, width, height);
			System.out.println("onSurfaceChanged");
		}

		@Override
		public void onDrawFrame(GL10 gl)
		{
			// 每次View被重绘时被调用。
			GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
			System.out.println("onDrawFrame");
			
		}

	}

	private static class Triangle
	{
		private FloatBuffer vertexBuffer;

		// 数组中每个顶点的坐标数
		static final int COORDS_PER_VERTEX = 3;
		static float triangleCoords[] = { // 按逆时针方向顺序:
		0.0f, 0.622008459f, 0.0f, // top
				-0.5f, -0.311004243f, 0.0f, // bottom left
				0.5f, -0.311004243f, 0.0f // bottom right
		};

		// 设置颜色，分别为red, green, blue 和alpha (opacity)
		float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

		public Triangle()
		{
			// 为存放形状的坐标，初始化顶点字节缓冲
			ByteBuffer bb = ByteBuffer.allocateDirect(
			// (坐标数 * 4)float占四字节
					triangleCoords.length * 4);
			// 设用设备的本点字节序
			bb.order(ByteOrder.nativeOrder());

			// 从ByteBuffer创建一个浮点缓冲
			vertexBuffer = bb.asFloatBuffer();
			// 把坐标们加入FloatBuffer中
			vertexBuffer.put(triangleCoords);
			// 设置buffer，从第一个坐标开始读
			vertexBuffer.position(0);
		}
	}

	private static class Square
	{

		private FloatBuffer vertexBuffer;
		private ShortBuffer drawListBuffer;

		// 每个顶点的坐标数
		static final int COORDS_PER_VERTEX = 3;
		static float squareCoords[] = { -0.5f, 0.5f, 0.0f, // top left
				-0.5f, -0.5f, 0.0f, // bottom left
				0.5f, -0.5f, 0.0f, // bottom right
				0.5f, 0.5f, 0.0f }; // top right

		private short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // 顶点的绘制顺序

		public Square()
		{
			// initialize vertex byte buffer for shape coordinates
			ByteBuffer bb = ByteBuffer.allocateDirect(
			// (坐标数 * 4)
					squareCoords.length * 4);
			bb.order(ByteOrder.nativeOrder());
			vertexBuffer = bb.asFloatBuffer();
			vertexBuffer.put(squareCoords);
			vertexBuffer.position(0);

			// 为绘制列表初始化字节缓冲
			ByteBuffer dlb = ByteBuffer.allocateDirect(
			// (对应顺序的坐标数 * 2)short是2字节
					drawOrder.length * 2);
			dlb.order(ByteOrder.nativeOrder());
			drawListBuffer = dlb.asShortBuffer();
			drawListBuffer.put(drawOrder);
			drawListBuffer.position(0);
		}
	}
}
