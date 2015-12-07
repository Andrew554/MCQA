/*package cn.edu.cqut.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.widget.Toast;

*//**
 * 
 * @author jackxu
 * @version v1.0
 * @date 2014-12-17
 * 
 *//*
public class BaseTools {
	static ProgressDialog progressDialog = null;
	static String result;
	static int resultInt;

	public final static int getWindowsWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	// 异步操作1
	public final static void asyncTask(final String message,
			final Map<String, String> dataMap, final String method,
			final String resultMethod, final Context context) {
		class MyAsyncTask extends AsyncTask<String, String, String> {
			@Override
			protected void onPreExecute() {
				progressDialog = new ProgressDialog(context);
				setProgressDialogMessage(message);
				showProgressDialog();
			}

			@Override
			protected String doInBackground(String... params) {
				try {
					result = HttpUtil.post(method, dataMap);
				} catch (Exception e) {
					System.out.println("e-->" + e);
				}
				return result;
			}

			@Override
			protected void onPostExecute(String result) {
				closeProgressDialog();
				final BaseActivity b = new BaseActivity();
				try {
					context.getClass().getMethod(resultMethod, String.class)
							.invoke(context, result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		MyAsyncTask myAsyncTask = new MyAsyncTask();
		myAsyncTask.execute();
	}

	// 异步操作2
	public final static void asyncTask(final String message,
			final Map<String, String> dataMap, final String method,
			final int resultMethod, final Context context) {
		class MyAsyncTask extends AsyncTask<String, String, String> {
			@Override
			protected void onPreExecute() {
				progressDialog = new ProgressDialog(context);
				setProgressDialogMessage(message);
				showProgressDialog();
			}

			@Override
			protected String doInBackground(String... params) {
				try {
					result = HttpUtil.post(method, dataMap);
					resultInt = resultMethod;
				} catch (Exception e) {
					System.out.println("e-->" + e);
				}
				return result;
			}

			@Override
			protected void onPostExecute(String result) {
				closeProgressDialog();
				final BaseActivity b = new BaseActivity();
				try {
					handleResult();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		MyAsyncTask myAsyncTask = new MyAsyncTask();
		myAsyncTask.execute();
	}


	public static void handleResult() {

	}

	*//**
	 * 设置进度对话框消息
	 * 
	 * @param message
	 *//*
	public static void setProgressDialogMessage(String message) {
		progressDialog.setMessage(message);
	}

	*//**
	 * 显示进度对话框
	 *//*
	public static void showProgressDialog() {
		progressDialog.show();
	}

	*//**
	 * 关闭进度对话框
	 *//*
	public static void closeProgressDialog() {
		if (progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	*//**
	 * 显示短提示的消息
	 * 
	 * @param message
	 *//*
	public static void showShortToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	*//**
	 * 提示对话框
	 * 
	 * @param message
	 *//*
	public static void resultDialog(Context context, String message) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage(message);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle("结果");
		builder.setPositiveButton("确定", null);
		builder.setCancelable(false);
		builder.create().show();
	}
	
	// 检测用户是否登陆
	public static boolean isLogin() {
		return SharedPreferencesUtil.getBoolean("account");
	}
}
*/