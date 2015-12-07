package cn.edu.cqut.mcqa;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.cqut.bean.Answer;
import cn.edu.cqut.db.DBUtil;
import cn.edu.cqut.utils.BaseActivity;
import cn.edu.cqut.utils.HeadView;
import cn.edu.cqut.utils.http.BaseJsonHttpResponseHandler;
import cn.edu.cqut.utils.http.HttpClient;
import cn.edu.cqut.utils.http.HttpUrl;
import org.apache.http.Header;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class AnswerDetail extends BaseActivity {
	private HeadView head;
	private ImageView img_prise;
	private TextView tv_title, tv_userName, tv_userSign, tv_priseNum, tv_content;
	private boolean message;
	
	Answer answer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_answer_detail);
		initView();
	}

	private void initView() {
		head = (HeadView) findViewById(R.id.head_answer);
		head.showLeftImage();
		head.setCenterTitle("回答详情");
		head.hideRightView();
		head.getLeftImage().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		img_prise = (ImageView) findViewById(R.id.priseImg);
		img_prise.setOnClickListener(this);
		tv_title = (TextView) findViewById(R.id.question_title);
		tv_userName = (TextView) findViewById(R.id.user_name);
		tv_userSign = (TextView) findViewById(R.id.user_sign);  
		tv_priseNum = (TextView) findViewById(R.id.priseNum);
		tv_content = (TextView) findViewById(R.id.content);
		
		Intent intent = this.getIntent();
		answer = (Answer) intent.getSerializableExtra("answer");
		tv_title.setText(intent.getStringExtra("question_title"));
		tv_userName.setText(answer.getuser_role());
		if(answer.getuser_sign() == null || answer.getuser_sign().equals("") || answer.getuser_sign().equals("null")) {
			tv_userSign.setText("该用户很懒，什么都没留下...");
		}else {
			tv_userSign.setText(answer.getuser_sign());
		}
		tv_content.setText(answer.getContext());
		tv_priseNum.setText(answer.getpraise_num());
		checkPrise();	// 判断是否可以点赞
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.priseImg:
			clickPrise();
			break;

		default:
			break;
		}
	}
	
	private void clickPrise() {
		// TODO Auto-generated method stub
		  if(DBUtil.checkLoginState()){
	            if(message){
	                sentPrise();
	            }
	        }else{
	           showShortToast("你没有登陆,无法进行点赞");
	        }
	}
	
	 /**
     * 判断是否可以进行点赞
     */
    public void checkPrise(){
        RequestParams params = new RequestParams();
        params.put("user_id", DBUtil.getUserID());
        params.put("answer_id", answer.getId());
        HttpClient.post(this, HttpUrl.POST_CHECK_PRISE, params, new BaseJsonHttpResponseHandler(this) {
            @SuppressWarnings("unchecked")
			@Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
            	Gson gson = new Gson();
                Map<String, Object> map = new HashMap<String, Object>();
                map = gson.fromJson(responseString, map.getClass());
                if(map.get("message").equals("yes")){
                    message = true;
                    img_prise.setImageResource(R.drawable.prise_normal);
                }else{
                    message = false;
                    img_prise.setImageResource(R.drawable.prise_select);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }
        });
    }
    /**
     * 点赞后发送到服务器
     */

    public void sentPrise(){
        RequestParams params = new RequestParams();
        params.put("user_id", DBUtil.getUserID());
        params.put("answer_id", answer.getId());
        HttpClient.post(this, HttpUrl.POST_PRISE, params, new BaseJsonHttpResponseHandler(this){
            @SuppressWarnings("unchecked")
			@Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
            	Gson gson = new Gson();
                Map<String, Object> map = new HashMap<String, Object>();
                map = gson.fromJson(responseString, map.getClass());
                if(map.containsValue("success")){
                    img_prise.setImageResource(R.drawable.prise_select);
                    tv_priseNum.setText((Integer.parseInt(tv_priseNum.getText().toString()) + 1) + "");
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showShortToast("未知错误--");
            }
        });
    }
}
