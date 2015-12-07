package cn.edu.cqut.mcqa;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.edu.cqut.utils.BaseActivity;
import cn.edu.cqut.utils.HeadView;
import cn.edu.cqut.utils.http.BaseJsonHttpResponseHandler;
import cn.edu.cqut.utils.http.HttpClient;
import cn.edu.cqut.utils.http.HttpUrl;

public class RegisterActivity extends BaseActivity {

	private Map<String, String> dataMap;
	private HeadView header;
	private RelativeLayout re_register;
	private EditText edit_nicheng, edit_pwd, edit_email, edit_confirmpwd;
	private ImageView clear_nicheng, clear_pwd, clear_email, clear_confirmpwd;
	private RequestParams params;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
	}

	/**
	 * 初始化组件
	 */
	@SuppressLint("CutPasteId")
	private void initView() {
		dataMap = new HashMap<String, String>();

		header = (HeadView) findViewById(R.id.register);
		header.showLeftImage();
		header.hideRightView();
		header.setCenterTitle("注册");
		header.getLeftImage().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		re_register = (RelativeLayout) findViewById(R.id.layout_register);
		re_register.setOnClickListener(this);

		edit_nicheng = (EditText) findViewById(R.id.nicheng);
		edit_nicheng.addTextChangedListener(this);

		clear_nicheng = (ImageView) findViewById(R.id.nicheng_clear);
		clear_nicheng.setOnClickListener(this);

		edit_pwd = (EditText) findViewById(R.id.passWd);
		edit_pwd.addTextChangedListener(this);
		clear_pwd = (ImageView) findViewById(R.id.pwd_clear);
		clear_pwd.setOnClickListener(this);

		edit_email = (EditText) findViewById(R.id.textMail);
		edit_email.addTextChangedListener(this);
		clear_email = (ImageView) findViewById(R.id.email_clear);
		clear_email.setOnClickListener(this);

		edit_confirmpwd = (EditText) findViewById(R.id.confirmpwd);
		edit_confirmpwd.addTextChangedListener(this);
		clear_confirmpwd = (ImageView) findViewById(R.id.confirmpwd_clear);
		clear_confirmpwd.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.nicheng_clear:
			edit_nicheng.setText("");
			break;
		case R.id.email_clear:
			edit_email.setText("");
			break;
		case R.id.pwd_clear:
			edit_pwd.setText("");
			break;
		case R.id.confirmpwd_clear:
			edit_confirmpwd.setText("");
			break;
		case R.id.layout_register:
			if (edit_nicheng.getText().length() == 0
					|| edit_email.getText().length() == 0
					|| edit_pwd.getText().length() == 0
					|| edit_confirmpwd.getText().length() == 0) {
				createDialog("注册信息中任何一项都不能为空!!!");
			} else if (!isEmail(edit_email.getText().toString())) {
				createDialog("邮箱格式错误!!!");
			} else if (edit_pwd.getText().length() < 8) {
				createDialog("密码长度太短");
			} else if (!edit_pwd.getText().toString()
					.equals(edit_confirmpwd.getText().toString())) {
				createDialog("两次密码不同！！！");
			} else {
				showShortToast("注册中");
				register();
			}
			break;
		default:
			break;
		}
	}

	// 注册的网络请求方法
	private void register() {
		/**
		 * 发送注册请求
		 */
		params = new RequestParams();
		params.put("username", edit_nicheng.getText().toString());
		params.put("email", edit_email.getText().toString());
		params.put("password", edit_pwd.getText().toString());
		params.put("fellow_name", "浙江省");

		HttpClient.post(this, HttpUrl.POST_SIGN_UP, params,
				new BaseJsonHttpResponseHandler(this) {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String responseString) {
						showShortToast("注册成功！！！");
						finished();
					}

					@SuppressWarnings("unchecked")
					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {
						Gson gson = new Gson();
						Map<String, Object> map = new HashMap<String, Object>();
						map = gson.fromJson(responseString, map.getClass());

						if (map.containsKey("email")) {
							showShortToast("email已被注册,请更换邮箱！");
						} else {
							showShortToast(statusCode + "---" + responseString);
						}
					}
				});
	};

	/**
	 * 注册成功，跳转到登陆页面
	 */
	public void finished() {
		Intent intent = new Intent(this, LoginActivity.class);
		intent.putExtra("email", edit_email.getText().toString());
		setResult(0, intent);
		this.finish();
	}

	// 检测邮箱的格式
	private boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	// 自定义的对话框
	private void createDialog(String message) {
		// 1. 布局文件转换为View对象
		LayoutInflater inflater = LayoutInflater.from(this);
		RelativeLayout layout = (RelativeLayout) inflater.inflate(
				R.layout.alert_dialog_confirm, null);

		// 2. 新建对话框对象
		final Dialog dialog = new AlertDialog.Builder(this).create();
		dialog.setCancelable(false);
		dialog.show();
		dialog.getWindow().setContentView(layout);

		// 3. 消息内容
		TextView dialog_msg = (TextView) layout
				.findViewById(R.id.dialog_message);
		dialog_msg.setText(message);

		// 4. 确定按钮
		TextView tv_confirm = (TextView) layout.findViewById(R.id.tv_confirm);
		tv_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		if (edit_nicheng.getText().length() > 0) {
			clear_nicheng.setVisibility(View.VISIBLE);
		} else {
			clear_nicheng.setVisibility(View.GONE);
		}
		if (edit_email.getText().length() > 0) {
			clear_email.setVisibility(View.VISIBLE);
		} else {
			clear_email.setVisibility(View.GONE);
		}
		if (edit_pwd.getText().length() > 0) {
			clear_pwd.setVisibility(View.VISIBLE);
		} else {
			clear_pwd.setVisibility(View.GONE);
		}
		if (edit_confirmpwd.getText().length() > 0) {
			clear_confirmpwd.setVisibility(View.VISIBLE);
		} else {
			clear_confirmpwd.setVisibility(View.GONE);
		}
	}
}
