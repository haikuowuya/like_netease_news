package com.roboo.like.netease;

import android.R.anim;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.Keyframe;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.LinearGradient;
import android.graphics.BitmapFactory.Options;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.roboo.like.netease.animation.Rotate3dAnimation;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class RotateActivity extends BaseActivity implements OnItemClickListener
{
	private static final int DURATION_TIME = 400;
	private LayoutTransition mTransitioner;
	private static final float DISTANCE_Z = 0.0f;
	private ListView mListView;
	private ImageView mImageView;
	private boolean flag = false;
	private LinearLayout mParentView;
	private int[] mImgIds = { R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6, R.drawable.image3, R.drawable.image5 };
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_3d_rotate);
		// TODO setContentView Tag
		setContentView(R.layout.activity_3d_rotate);
		initView();
		this.mListView.setAdapter(getAdapter());
		setListener();
		setLayoutTransition();

	}
	private void setListener()
	{
		this.mListView.setOnItemClickListener(this);
		this.mImageView.setOnClickListener(this);
	}
	private void initView()
	{
		this.mListView = (ListView) findViewById(android.R.id.list);
		this.mImageView = (ImageView) findViewById(R.id.iv_image);
		this.mParentView = (LinearLayout) findViewById(R.id.linear_container);
	}
	private ListAdapter getAdapter()
	{
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, getResources().getStringArray(R.array.titles));
		return adapter;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(R.string.tv_layout_change_animation).setCheckable(true).setChecked(true);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		item.setCheckable(true);
		item.setChecked(!item.isChecked());
		flag = item.isChecked();

		return true;

	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{

		this.mImageView.setImageBitmap(handlerImg(mImgIds[position]));
		if (!flag)
		{
			// 获取View中心坐标点
			float centerX = this.mParentView.getWidth() / 2f;
			float centerY = this.mParentView.getHeight() / 2f;
			System.out.println("centerX = " + centerX + " centerY = " + centerY);
			// 构建旋转动画实例
			Rotate3dAnimation animation = new Rotate3dAnimation(0, 90, centerX, centerY, DISTANCE_Z, true);
			animation.setDuration(DURATION_TIME);
			animation.setInterpolator(new AccelerateDecelerateInterpolator());
			animation.setFillAfter(true);
			animation.setAnimationListener(new AnimationListenerImpl(0));
			mParentView.startAnimation(animation);
		}
		else
		{
			mImageView.setVisibility(View.VISIBLE);
			mListView.setVisibility(View.GONE);
		}

	}
	private Bitmap handlerImg(int imgId)
	{
		Options opts = new Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(getResources(), imgId, opts);
		int oldWidth = opts.outWidth;
		int oldHeight = opts.outHeight;
		System.out.println("oldwidth = " + oldWidth + " oldHeight = " + oldHeight);
		int widthScale = Math.round((float) oldWidth / (float) (getResources().getDisplayMetrics().widthPixels));
		int heightScale = Math.round((float) oldHeight / (float) (getResources().getDisplayMetrics().heightPixels));
		System.out.println("widthScale = " + widthScale + " heightScale = " + heightScale);
		opts.inSampleSize = widthScale < heightScale ? widthScale : heightScale;
		opts.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgId, opts);
		int newWidth = opts.outWidth;
		int newHeight = opts.outHeight;
		System.out.println("newWidth = " + newWidth + " newHeight = " + newHeight);
		return bitmap;

	}
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.iv_image:
			if (!flag)
			{
				// 获取布局的中心点位置，作为旋转的中心点
				float centerX = mParentView.getWidth() / 2f;
				float centerY = mParentView.getHeight() / 2f;
				// 构建3D旋转动画对象，旋转角度为360到270度，这使得ImageView将会从可见变为不可见，并且旋转的方向是相反的
				final Rotate3dAnimation rotation = new Rotate3dAnimation(360, 270, centerX, centerY, DISTANCE_Z, true);
				// 动画持续时间500毫秒
				rotation.setDuration(DURATION_TIME);
				// 动画完成后保持完成的状态
				rotation.setFillAfter(true);
				rotation.setInterpolator(new AccelerateDecelerateInterpolator());
				// 设置动画的监听器
				rotation.setAnimationListener(new AnimationListenerImpl(1));
				mParentView.startAnimation(rotation);
			}
			else
			{
				mImageView.setVisibility(View.GONE);
				mListView.setVisibility(View.VISIBLE);
			}
			break;
		default:
			super.onClick(v);
			break;
		}

	}
	private class AnimationListenerImpl implements AnimationListener
	{
		private int mode;
		float centerX;
		float centerY;

		public AnimationListenerImpl(int mode)
		{

			this.mode = mode;
			centerX = mParentView.getWidth() / 2f;
			centerX = mParentView.getHeight() / 2f;
		}

		@Override
		public void onAnimationStart(Animation animation)
		{

		}

		@Override
		public void onAnimationEnd(Animation animation)
		{
			switch (mode)
			{
			case 0:
				mListView.setVisibility(View.GONE);
				mImageView.setVisibility(View.VISIBLE);
				final Rotate3dAnimation rotation = new Rotate3dAnimation(270, 360, centerX, centerY, DISTANCE_Z, false);
				// 动画持续时间500毫秒
				rotation.setDuration(DURATION_TIME);
				// 动画完成后保持完成的状态
				rotation.setFillAfter(true);
				rotation.setInterpolator(new AccelerateDecelerateInterpolator());
				mParentView.startAnimation(rotation);
				break;
			case 1:

				mListView.setVisibility(View.VISIBLE);
				mImageView.setVisibility(View.GONE);
				final Rotate3dAnimation rotation2 = new Rotate3dAnimation(90, 0, centerX, centerY, DISTANCE_Z, false);
				// 动画持续时间500毫秒
				rotation2.setDuration(DURATION_TIME);
				// 动画完成后保持完成的状态
				rotation2.setFillAfter(true);
				rotation2.setInterpolator(new AccelerateDecelerateInterpolator());
				mParentView.startAnimation(rotation2);
				break;
			default:
				break;
			}

		}

		@Override
		public void onAnimationRepeat(Animation animation)
		{

		}
	}
	@SuppressLint("NewApi")
	private void setLayoutTransition()
	{
		mTransitioner = new LayoutTransition();

		mTransitioner.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
		mTransitioner.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);
		setupCustomAnimations();
		this.mParentView.setLayoutTransition(mTransitioner);
	}

	@SuppressLint("NewApi")
	private void setupCustomAnimations()
	{
		// Changing while Adding
		PropertyValuesHolder pvhLeft =
			PropertyValuesHolder.ofInt("left", 0, 1);
		PropertyValuesHolder pvhTop =
			PropertyValuesHolder.ofInt("top", 0, 1);
		PropertyValuesHolder pvhRight =
			PropertyValuesHolder.ofInt("right", 0, 1);
		PropertyValuesHolder pvhBottom =
			PropertyValuesHolder.ofInt("bottom", 0, 1);
		PropertyValuesHolder pvhScaleX =
			PropertyValuesHolder.ofFloat("scaleX", 1f, 0f, 1f);
		PropertyValuesHolder pvhScaleY =
			PropertyValuesHolder.ofFloat("scaleY", 1f, 0f, 1f);
		final ObjectAnimator changeIn = ObjectAnimator.ofPropertyValuesHolder(
			this, pvhLeft, pvhTop, pvhRight, pvhBottom, pvhScaleX, pvhScaleY).
			setDuration(mTransitioner.getDuration(LayoutTransition.CHANGE_APPEARING));
		mTransitioner.setAnimator(LayoutTransition.CHANGE_APPEARING, changeIn);
		changeIn.addListener(new AnimatorListenerAdapter()
		{
			public void onAnimationEnd(Animator anim)
			{
				View view = (View) ((ObjectAnimator) anim).getTarget();
				view.setScaleX(1f);
				view.setScaleY(1f);
			}
		});

		// Changing while Removing
		Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
		Keyframe kf1 = Keyframe.ofFloat(.9999f, 360f);
		Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
		PropertyValuesHolder pvhRotation =
			PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);
		final ObjectAnimator changeOut = ObjectAnimator.ofPropertyValuesHolder(
			this, pvhLeft, pvhTop, pvhRight, pvhBottom, pvhRotation).
			setDuration(mTransitioner.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
		mTransitioner.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changeOut);
		changeOut.addListener(new AnimatorListenerAdapter()
		{
			public void onAnimationEnd(Animator anim)
			{
				View view = (View) ((ObjectAnimator) anim).getTarget();
				view.setRotation(0f);
			}
		});

		// Adding
		ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "rotationY", 90f, 0f).
			setDuration(mTransitioner.getDuration(LayoutTransition.APPEARING));
		mTransitioner.setAnimator(LayoutTransition.APPEARING, animIn);
		animIn.addListener(new AnimatorListenerAdapter()
		{
			public void onAnimationEnd(Animator anim)
			{
				View view = (View) ((ObjectAnimator) anim).getTarget();
				view.setRotationY(0f);
			}
		});

		// Removing
		ObjectAnimator animOut = ObjectAnimator.ofFloat(null, "rotationY", 0f, 90f).
			setDuration(mTransitioner.getDuration(LayoutTransition.DISAPPEARING));
		mTransitioner.setAnimator(LayoutTransition.DISAPPEARING, animOut);
		animOut.addListener(new AnimatorListenerAdapter()
		{
			public void onAnimationEnd(Animator anim)
			{
				View view = (View) ((ObjectAnimator) anim).getTarget();
				view.setRotationY(0f);
			}
		});

	}
}
