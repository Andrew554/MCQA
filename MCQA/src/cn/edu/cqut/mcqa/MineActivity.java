package cn.edu.cqut.mcqa;

import cn.edu.cqut.bean.UserMessage;
import cn.edu.cqut.db.DBUtil;
import cn.edu.cqut.utils.ActivityManager;
import cn.edu.cqut.utils.BaseFragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MineActivity extends BaseFragmentActivity {
	
	private TextView tv_name, tv_email, tv_sign;
	private RelativeLayout exit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine);
		initView();
	}
	
	private void initView() {
		tv_email = (TextView) findViewById(R.id.userEmail);
		tv_sign = (TextView) findViewById(R.id.userSign);
		tv_name = (TextView) findViewById(R.id.name);
		exit = (RelativeLayout) findViewById(R.id.exitLogin);
		
		exit.setOnClickListener(this);
		
		UserMessage user = (UserMessage) DBUtil.listAll(UserMessage.class).get(0);
		System.out.println(user.toString());
		tv_name.setText(user.getName());
		tv_email.setText(user.getEmail());
//		if(user.getS().equals("null") || user.getRealName().equals("") || user.getRealName() == null) {
			tv_sign.setText("该用户很懒,没有个性签名...");
//		}else {
//			tv_sign.setText(user.getRealName());
//		}
	}
	
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.exitLogin:
			DBUtil.clear(UserMessage.class);
			ActivityManager.getInstance().exit();
			break;
		default:
			break;
		}
	}
}
