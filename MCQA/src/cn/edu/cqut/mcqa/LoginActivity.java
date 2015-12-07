package cn.edu.cqut.mcqa;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.edu.cqut.bean.User;
import cn.edu.cqut.bean.UserMessage;
import cn.edu.cqut.db.DBHelper;
import cn.edu.cqut.db.DBUtil;
import cn.edu.cqut.utils.BaseActivity;
import cn.edu.cqut.utils.HeadView;
import cn.edu.cqut.utils.JSONAndObject;
import cn.edu.cqut.utils.http.BaseJsonHttpResponseHandler;
import cn.edu.cqut.utils.http.HttpClient;
import cn.edu.cqut.utils.http.HttpUrl;

public class LoginActivity extends BaseActivity {

	private Map<String, String> dataMap;
	private HeadView header;
	private RelativeLayout re_login;
	private EditText edit_account, edit_pwd;
	private TextView losePwd;
	private ImageView clear_account, clear_pwd;
	private List<User> userList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
	}

	/**
	 * 初始化组件
	 */
	@SuppressLint("CutPasteId")
	private void initView() {
		dataMap = new HashMap<String, String>();

		header = (HeadView) findViewById(R.id.login);
		header.showLeftImage();
		header.setCenterTitle("登录");
		header.setRightText("注册");

		final Intent intent = new Intent(this, RegisterActivity.class);

		header.getLeftImage().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		header.getRightText().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivityForResult(intent, 0);
			}
		});

		re_login = (RelativeLayout) findViewById(R.id.layout_login);
		re_login.setOnClickListener(this);
		edit_account = (EditText) findViewById(R.id.account);
		edit_account.addTextChangedListener(this);
		edit_pwd = (EditText) findViewById(R.id.pwd);
		edit_pwd.addTextChangedListener(this);
		clear_account = (ImageView) findViewById(R.id.account_clear);
		clear_account.setOnClickListener(this);
		clear_pwd = (ImageView) findViewById(R.id.pwd_clear);
		clear_pwd.setOnClickListener(this);
		losePwd = (TextView) findViewById(R.id.tv_losepwd);
		losePwd.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.account_clear:
			edit_account.setText("");
			break;
		case R.id.pwd_clear:
			edit_pwd.setText("");
			break;
		case R.id.layout_login:
			if (edit_account.getText().length() == 0
					|| edit_pwd.getText().length() == 0) {
				createDialog("账号或密码不能为空!!!");
			} else {
				login();
			}
			break;
		case R.id.tv_losepwd:
			showShortToast("跳转忘记密码");
			break;
		default:
			break;
		}
	}

	// 登陆网络请求方法
	private void login() {
		dataMap.put("login", edit_account.getText().toString());
		dataMap.put("password", edit_pwd.getText().toString());

		HttpClient.post(this, HttpUrl.POST_LOGIN, dataMap,
				new BaseJsonHttpResponseHandler(this) {
					@SuppressWarnings("unchecked")
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String responseString) {
						System.out.println(responseString);
						userList = new ArrayList<User>();
						User user = null;
						try {
							userList = JSONAndObject.convert(User.class,
									new JSONArray("[" + responseString + "]")
											.toString(), "user");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						user = userList.get(0);

						UserMessage userMessage = new UserMessage();

						if (user.getApprove() != null
								&& !user.getApprove().equals("")) {
							userMessage.setApprove(user.getApprove());
						} else {
							userMessage.setApprove("");
						}
						showShortToast(userMessage.getApprove());
						if (user.getRealname() != null
								&& !user.getRealname().equals("")) {
							userMessage.setRealName(user.getRealname());
						} else {
							userMessage.setRealName("");
						}
						if (user.getStuNum() != null
								&& !user.getStuNum().equals("")) {
							userMessage.setStuNumber(user.getStuNum());
						} else {
							userMessage.setStuNumber("");
						}
						userMessage.setUser_id(user.getId() + "");
						userMessage.setName(user.getUsername());
						userMessage.setEmail(user.getEmail());
						userMessage.setToken(user.getToken());

						showLongToast(userMessage.toString());

						HttpClient.resetAuth(user.getToken());

						DBUtil.clear(UserMessage.class); // 删除以前的数据
						DBUtil.save(userMessage); // 存储用户数据

						showLongToast("登陆成功");
						finish();
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {
						showShortToast("登陆失败,请核对用户名和密码");
					}
				});
	}

	// 自定义的对话框
	private void createDialog(String str) {
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
		dialog_msg.setText(str);

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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 注册成功跳转会登陆页面 自动填充用户名(邮箱)
		if (requestCode == 0) {
			if (data != null) {
				String email = data.getStringExtra("email");
				edit_account.setText(email);
			}
		}
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		if (edit_account.getText().length() > 0) {
			clear_account.setVisibility(View.VISIBLE);
		} else {
			clear_account.setVisibility(View.GONE);
		}
		if (edit_pwd.getText().length() > 0) {
			clear_pwd.setVisibility(View.VISIBLE);
		} else {
			clear_pwd.setVisibility(View.GONE);
		}
	}
}
