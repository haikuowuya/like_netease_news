package com.roboo.like.netease;

import java.util.Hashtable;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class TwoCodeActivity extends BaseActivity
{
	private EditText mETText;
	private Button mBtnCreate;
	// 图片宽度的一般
	private static final int IMAGE_HALFWIDTH = 20;
	private ImageView mIVImage;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_two_code);
		// TODO setContentView Tag
		setContentView(R.layout.activity_two_code);
		initView();
		this.mBtnCreate.setOnClickListener(this);
	}
	private void initView()
	{
		this.mETText = (EditText) findViewById(R.id.et_text);
		this.mBtnCreate = (Button) findViewById(R.id.btn_create);
		this.mIVImage = (ImageView) findViewById(R.id.iv_image);
	}
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_create:
			CharSequence text = this.mETText.getText();
			Bitmap bitmap = createBitmap();
			if (!TextUtils.isEmpty(text))
			{
				try
				{
					this.mIVImage.setImageBitmap(createTwoCode(text.toString(), bitmap));
				}
				catch (WriterException e)
				{
					this.mIVImage.setImageResource(R.drawable.ic_launcher);
					e.printStackTrace();
				}
			}
			else
			{
				TranslateAnimation translateAnimation = new TranslateAnimation(0, 20, 0, 0);
				translateAnimation.setInterpolator( new CycleInterpolator(10));
				translateAnimation.setDuration(1000);
				mETText.startAnimation(translateAnimation);
				Toast.makeText(this, "内容不可以为空", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			super.onClick(v);
			break;
		}

	}

	private Bitmap createBitmap()
	{
		// 构造需要插入的图片对象
		Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(
			R.drawable.ic_matrix)).getBitmap();
		// 缩放图片
		Matrix m = new Matrix(); 
		float sx = (float) 2 * IMAGE_HALFWIDTH / bitmap.getWidth();
		float sy = (float) 2 * IMAGE_HALFWIDTH / bitmap.getHeight();
		m.setScale(sx, sy);
		// 重新构造一个40*40的图片
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
			bitmap.getHeight(), m, false);
		return bitmap;
	}
	private Bitmap createTwoCode(String text) throws WriterException
	{
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

		BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE,
			300, 300, hints);

		int width = matrix.getWidth();
		int hight = matrix.getHeight();

		int[] pixels = new int[width * hight];
		for (int y = 0; y < hight; y++)
		{
			for (int x = 0; x < width; x++)
			{
				if (matrix.get(x, y))
				{
					pixels[y * width + x] = 0xff000000;
				}
			}
		}
		Bitmap map = Bitmap.createBitmap(width, hight, Bitmap.Config.ARGB_8888);
		map.setPixels(pixels, 0, width, 0, 0, width, hight);

		return map;

	}
	private Bitmap createTwoCode(String text, Bitmap bitmap) throws WriterException
	{
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

		BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE,
			300, 300, hints);

		int width = matrix.getWidth();
		int height = matrix.getHeight();
		int halfW = width / 2;
		int halfH = height / 2;
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH && y > halfH - IMAGE_HALFWIDTH
					&& y < halfH + IMAGE_HALFWIDTH)
				{
					pixels[y * width + x] = bitmap.getPixel(x - halfW + IMAGE_HALFWIDTH, y
						- halfH + IMAGE_HALFWIDTH);
				}
				else
				{
					if (matrix.get(x, y))
					{
						pixels[y * width + x] = 0xff000000;
					}
				}

			}
		}
		Bitmap map = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		map.setPixels(pixels, 0, width, 0, 0, width, height);

		return map;

	}
}
