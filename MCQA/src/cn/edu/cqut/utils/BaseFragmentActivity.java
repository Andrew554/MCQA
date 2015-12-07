package cn.edu.cqut.utils;

import java.util.Map;

import cn.edu.cqut.bean.UserMessage;
import cn.edu.cqut.db.DBUtil;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class BaseFragmentActivity extends FragmentActivity implements OnClickListener, TextWatcher{
	public HttpCallBack httpCallBack = null;
	private ProgressDialog progressDialog = null;
	// 用于监听是否退出
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setCanceledOnTouchOutside(false);
	}

	/**
	 * 监听点击两次返回键退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
		if ((keyCode == KeyEvent.KEYCODE_BACK)
				&& (event.getAction() == KeyEvent.ACTION_DOWN)) {
			if (System.currentTimeMillis() - CurrentTime.temptime > 2000){ // 2s内再次选择back键有效 
				Toast.makeText(this, "再按一次退出校园问答", Toast.LENGTH_SHORT).show();
				CurrentTime.temptime = System.currentTimeMillis();
			} else {
				ActivityManager.getInstance().exit();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	

	/**
	 * 初始化共同的按钮
	 * 
	 * @param id
	 *            不居中layout的id
	 * @param text
	 *            button显示文本
	 * @param listener
	 *            button监听器
	 * @return 返回初始化的button
	 */
	public ImageView initCommonBack(int id)
	{
		ImageView button = (ImageView) findViewById(id);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		return button;
	}
	
	/**
	 * 设置进度对话框消息
	 * 
	 * @param message
	 */
	public void setProgressDialogMessage(String message)
	{
		progressDialog.setMessage(message);
	}

	/**
	 * 显示进度对话框
	 */
	public void showProgressDialog()
	{
		progressDialog.show();
	}

	/**
	 * 关闭进度对话框
	 */
	public void closeProgressDialog()
	{
		if (progressDialog.isShowing())
		{
			progressDialog.dismiss();
		}
	}

	/**
	 * 显示短提示的消息
	 * 
	 * @param message
	 */
	public void showShortToast(String message)
	{
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * 显示长提示的消息
	 * 
	 * @param message
	 */
	public void showLongToast(String message)
	{
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 服务器数据异步请求
	 * @param httpRequest
	 * @param map
	 * @param callBack
	 */
	public void asyHttp(String httpRequest,final Map<String,String> map,HttpCallBack callBack){
		
		this.httpCallBack = callBack;
		StringRequest request = new StringRequest(Method.GET, httpRequest, new Response.Listener<String>(){
			@Override
			public void onResponse(String response) {
				httpCallBack.Success(response);
				System.out.println("success:"+response);
			}
		}, new ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError error) {
				httpCallBack.Fail(error.toString());
				System.out.println("error:"+error);
			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return map;
			}
		};
		
		RequestQueue mQueue = Volley.newRequestQueue(this);  
		mQueue.add(request); 
	}
	
	//异步网络操作方法的接口
	public interface HttpCallBack{
		public void Success(String respose);
		public void Fail(String error);
	}
}

