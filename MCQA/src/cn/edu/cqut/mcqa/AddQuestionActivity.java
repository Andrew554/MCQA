package cn.edu.cqut.mcqa;

import cn.edu.cqut.utils.BaseActivity;
import cn.edu.cqut.utils.HeadView;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddQuestionActivity extends BaseActivity {

	@SuppressWarnings("unused")
	private RelativeLayout layout_clear;
	private HeadView headView;
	@SuppressWarnings("unused")
	private TextView tv_nums;
	private ImageView im_content_clear;
	private EditText edit_title, edit_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_question);
		initView();
	}

	private void initView() {

		headView = (HeadView) findViewById(R.id.add_question_head);
		headView.setLeftImageSource(R.drawable.back);
		headView.setRightText("下一步");
		headView.setCenterTitle("问题描述");
		headView.getLeftImage().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		final Intent intent = new Intent(getApplicationContext(),
				AddQuestionTypes.class);
		headView.getRightText().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (edit_title.getText().length() < 5
						|| edit_content.getText().length() < 10) {
					showLongToast("问题标题字数不能少于5,内容不能小于10!!!");
				} else {
					intent.putExtra("title", edit_title.getText().toString());
					intent.putExtra("content", edit_content.getText().toString());
					startActivity(intent);
				}
			}
		});

		layout_clear = (RelativeLayout) findViewById(R.id.layout_clear);

		tv_nums = (TextView) findViewById(R.id.tv_nums);
		im_content_clear = (ImageView) findViewById(R.id.im_content_clear);

		edit_title = (EditText) findViewById(R.id.question_title);
		edit_content = (EditText) findViewById(R.id.question_content);

		im_content_clear.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.im_content_clear:
			if (edit_content.getText().length() > 0) {
				createDialog();
			}
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
		dialog_msg.setText("是否清空内容？");

		// 4. 确定按钮
		TextView tv_confirm = (TextView) layout.findViewById(R.id.tv_confirm);
		tv_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				edit_content.setText("");
				dialog.dismiss();
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
}
