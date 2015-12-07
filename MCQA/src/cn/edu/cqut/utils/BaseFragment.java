package cn.edu.cqut.utils;

import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.widget.Toast;

public class BaseFragment extends Fragment{
	public HttpCallBack httpCallBack = null;
	ProgressDialog progressDialog = null;
	
	public final static int getWindowsWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
	
	/**
	 * 设置进度对话框消息
	 * 
	 * @param message
	 */
	public void setProgressDialogMessage(String message) {
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage(message);
	}

	/**
	 * 显示进度对话框
	 */
	public void showProgressDialog() {
		progressDialog.show();
	}

	/**
	 * 关闭进度对话框
	 */
	public void closeProgressDialog() {
		if (progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	/**
	 * 显示短提示的消息
	 * 
	 * @param message
	 */
	public void showShortToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 显示长提示的消息
	 * 
	 * @param message
	 */
	public void showLongToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
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
		
		RequestQueue mQueue = Volley.newRequestQueue(getActivity());  
		mQueue.add(request); 
	}
	
	//异步网络操作方法的接口
	public interface HttpCallBack{
		public void Success(String respose);
		public void Fail(String error);
	}
}
