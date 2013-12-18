package com.roboo.like.netease;

 
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.roboo.like.netease.pinterest.ZoomImageView;

/**
 * 查看大图的Activity界面。
 * 
 * @author guolin
 */
public class ImageDetailsActivity extends BaseActivity {

	/**
	 * 自定义的ImageView控制，可对图片进行多点触控缩放和拖动
	 */
	private ZoomImageView zoomImageView;

	/**
	 * 待展示的图片
	 */
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTVTitle("展示图片"	);
		setContentView(R.layout.activity_image_detail);
		zoomImageView = (ZoomImageView) findViewById(R.id.zoom_image_view);
		// 取出图片路径，并解析成Bitmap对象，然后在ZoomImageView中显示
		String imagePath = getIntent().getStringExtra("image_path");
		bitmap = BitmapFactory.decodeFile(imagePath);
		zoomImageView.setImageBitmap(bitmap);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 记得将Bitmap对象回收掉
		if (bitmap != null) {
			bitmap.recycle();
		}
	}

}