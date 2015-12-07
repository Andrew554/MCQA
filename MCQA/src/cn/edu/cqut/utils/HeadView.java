package cn.edu.cqut.utils;

import cn.edu.cqut.mcqa.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 自定义的头部组件
 * @author Administrator
 * by luojianhua 2015/08/11
 */
public class HeadView extends RelativeLayout{
	
	private Context context = null;
	private TextView centerTitle, leftText, rightText;
	private ImageView leftImage, rightImage;
	
	public HeadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	/**
	 * 组件初始化
	 */
	private void initView() {
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.head_view, this);
		centerTitle = (TextView) findViewById(R.id.center_title);
		leftText = (TextView) findViewById(R.id.left_text);
		rightText = (TextView) findViewById(R.id.right_text);
		leftImage = (ImageView) findViewById(R.id.left_image);
		rightImage = (ImageView) findViewById(R.id.right_image);
	}
	
	/**
	 * 设置中间标题内容
	 */
	public void setCenterTitle(int resid) {
		showCenterTitle();
		centerTitle.setText(resid);
	} 
	
	/**
	 * 设置中间标题内容
	 */
	public void setCenterTitle(String title) {
		showCenterTitle();
		centerTitle.setText(title);
	}
	
	/**
	 * 设置左边文字内容
	 */
	public void setLeftText(int resid) {
		showLeftText();
		leftText.setText(resid);
	}
	
	/**
	 * 设置左边文字内容
	 */
	public void setLeftText(String text) {
		showLeftText();
		leftText.setText(text);
	}
	
	/**
	 * 设置右边文字内容
	 */
	public void setRightText(int resid) {
		showRightText();
		rightText.setText(resid);
	}
	
	/**
	 * 设置右边文字内容
	 */
	public void setRightText(String text) {
		showRightText();
		rightText.setText(text);
	}
	
	/**
	 * 设置左边图片资源
	 */
	public void setLeftImageSource(int resid) {
		showLeftImage();
		leftImage.setImageResource(resid);
	}
	
	/**
	 * 设置右边图片资源
	 */
	public void setRightImageSource(int resid) {
		showRightImage();
		rightImage.setImageResource(resid);
	}
	
	/**
	 * 隐藏中间标题
	 */
	public void hideCenterTitle() {
		centerTitle.setVisibility(View.GONE);
	}
	
	/**
	 * 设置显示中间标题
	 */
	public void showCenterTitle() {
		centerTitle.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 设置显示左边文字
	 */
	public void showLeftText() {
		leftText.setVisibility(View.VISIBLE);
		leftImage.setVisibility(View.GONE);
	}
	
	/**
	 * 设置显示左边图片
	 */
	public void showLeftImage() {
		leftText.setVisibility(View.GONE);
		leftImage.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 设置显示右边文字
	 */
	public void showRightText() {
		rightText.setVisibility(View.VISIBLE);
		rightImage.setVisibility(View.GONE);
	}
	
	/**
	 * 设置显示右边图片
	 */
	public void showRightImage() {
		rightText.setVisibility(View.GONE);
		rightImage.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 设置不显示右边布局
	 */
	public void hideRightView() {
		rightImage.setVisibility(View.GONE);
		rightText.setVisibility(View.GONE);
	}
	
	/**
	 * 返回左边文字
	 */
	public TextView getLeftText() {
		return leftText;
	}
	
	/**
	 * 返回右边文字
	 */
	public TextView getRightText() {
		return rightText;
	}
	
	/**
	 * 返回左边图片
	 */
	public ImageView getLeftImage() {
		return leftImage;
	}
	
	/**
	 * 返回右边图片
	 */
	public ImageView getRightImage() {
		return rightImage;
	}
	
	/**
	 * 返回中间标题
	 */
	public TextView getCenterTitle() {
		return centerTitle;
	}
}

