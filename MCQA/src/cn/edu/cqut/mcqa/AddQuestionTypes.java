package cn.edu.cqut.mcqa;

import com.loopj.android.http.RequestParams;

import cn.edu.cqut.db.DBUtil;
import cn.edu.cqut.utils.BaseActivity;
import cn.edu.cqut.utils.HeadView;
import cn.edu.cqut.utils.http.BaseJsonHttpResponseHandler;
import cn.edu.cqut.utils.http.HttpClient;
import cn.edu.cqut.utils.http.HttpUrl;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.apache.http.Header;

public class AddQuestionTypes extends BaseActivity {

	private HeadView headView;
	private CheckBox label_1, label_2, label_3, label_4, label_5, label_6;
	public String label = "";
	private String title = "", content = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_question_types);
		initView();
	}

	private void initView() {
		Intent intent = this.getIntent();
		content = intent.getStringExtra("content");
		title = intent.getStringExtra("title");
		
		showShortToast(title + ":"  + content);
		headView = (HeadView) findViewById(R.id.add_types_head);
		headView.setLeftImageSource(R.drawable.back);
		headView.setRightImageSource(R.drawable.send);
		headView.setCenterTitle("问题类型");
		headView.getLeftImage().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		headView.getRightImage().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				createDialog();
			}
		});

		label_1 = (CheckBox) findViewById(R.id.label_1);
		label_2 = (CheckBox) findViewById(R.id.label_2);
		label_3 = (CheckBox) findViewById(R.id.label_3);
		label_4 = (CheckBox) findViewById(R.id.label_4);
		label_5 = (CheckBox) findViewById(R.id.label_5);
		label_6 = (CheckBox) findViewById(R.id.label_6);

	}

	public void isCheckBoxChecked() {
		if (label_1.isChecked()) {
			label += label_1.getText().toString() + ",";
		}
		if (label_2.isChecked()) {
			label += label_2.getText().toString() + ",";
		}
		if (label_3.isChecked()) {
			label += label_3.getText().toString() + ",";
		}
		if (label_4.isChecked()) {
			label += label_4.getText().toString() + ",";
		}
		if (label_5.isChecked()) {
			label += label_5.getText().toString() + ",";
		}
		if (label_6.isChecked()) {
			label += label_6.getText().toString() + ",";
		}
		if (!label.equals("")) {
			label = label.substring(0, label.length() - 1);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_image:
			break;
		default:
			break;
		}
	}

	// 自定义的对话框
	private void createDialog() {
		// 1. 布局文件转换为View对象
		LayoutInflater inflater = LayoutInflater.from(this);
		RelativeLayout layout = (RelativeLayout) inflater.inflate(
				R.layout.alert_dialog_select, null);

		// 2. 新建对话框对象
		final Dialog dialog = new AlertDialog.Builder(this).create();
		dialog.setCancelable(false);
		dialog.show();
		dialog.getWindow().setContentView(layout);

		// 3. 消息内容
		TextView dialog_msg = (TextView) layout
				.findViewById(R.id.dialog_message);
		dialog_msg.setText("确认发表该问题？");

		// 4. 确定按钮
		TextView tv_confirm = (TextView) layout.findViewById(R.id.tv_confirm);
		tv_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				isCheckBoxChecked();
				commitQuestion();
			}
		});

		// 5. 取消按钮
		TextView tv_cancel = (TextView) layout.findViewById(R.id.tv_cancle);
		tv_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	// 提交问题
	private void commitQuestion() {
		RequestParams params = new RequestParams();
		Intent intent = getIntent();
		params.put("user_id", DBUtil.getUserID());
		params.put("content", intent.getStringExtra("content"));
		params.put("title", intent.getStringExtra("title"));
		params.put("label", label);

		HttpClient.post(this, HttpUrl.POST_ONE_QUESTION, params,
				new BaseJsonHttpResponseHandler(this) {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String responseString) {
						showShortToast("发表成功!!!");
						backActivity();
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {
						showShortToast("发表失败...");
					}
				});
	}

	// 发表成功之后返回到主页面
	private void backActivity() {
		this.finish();
		startActivity(new Intent(this, MainActivity.class));
	}
}
