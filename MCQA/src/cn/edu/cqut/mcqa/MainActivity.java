package cn.edu.cqut.mcqa;

import cn.edu.cqut.bean.UserMessage;
import cn.edu.cqut.db.DBHelper;
import cn.edu.cqut.db.DBUtil;
import cn.edu.cqut.utils.CurrentTime;
import cn.edu.cqut.utils.SharedPreferencesUtil;
import android.os.Bundle;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity implements OnClickListener{

	private TabHost tabhost;
	private LocalActivityManager lam;

	public static Context context;

	private RelativeLayout re_question, re_recommend, re_add, re_rank, re_mine, common_title;
	private TextView tv_question, tv_recommend, tv_rank, tv_mine, common_head_title;
	@SuppressWarnings("unused")
	private TextView im_question, im_recommend, im_add, im_rank, im_mine;

	public static final int ORANGE = Color.parseColor("#F17A31");
	public static final int WHITE = Color.parseColor("#999999");

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		CurrentTime.temptime = System.currentTimeMillis();
		context = this;

		initDB();
		init(savedInstanceState);

		re_question.setOnClickListener(this);
		re_recommend.setOnClickListener(this);
		re_add.setOnClickListener(this);
		re_rank.setOnClickListener(this);
		re_mine.setOnClickListener(this);
	}

	/**
	 * 初始化数据库
	 */
	private void initDB() {
		//  在调用SharedPreferencesUtil里的方法之前必须进行初始化
		SharedPreferencesUtil.init(getApplicationContext(), "setting");		
		
		//  创建数据库
		DBHelper.init(getApplicationContext(), "mcqa", 1,null);
		// 创建数据库以及用户数据表  登陆成功之后将用户信息存入本地数据库中
		DBUtil.createTableWithName(DBHelper.getSQLiteDatabase(),UserMessage.class,"usermessage");
	}

	public void init(Bundle savedInstanceState) {
		
		// 初始化Tab栏
		lam = new LocalActivityManager(this, false);
		lam.dispatchCreate(savedInstanceState);

		tabhost = (TabHost) findViewById(R.id.tabhost);
		tabhost.setup(lam);

		tabhost.addTab(tabhost
				.newTabSpec("question")
				.setIndicator("question",
						getResources().getDrawable(R.drawable.ic_launcher))
				.setContent(new Intent(this, QuestionActivity.class)));
		tabhost.addTab(tabhost
				.newTabSpec("recommend")
				.setIndicator("recommend",
						getResources().getDrawable(R.drawable.ic_launcher))
				.setContent(new Intent(this, RecommendActivity.class)));
		tabhost.addTab(tabhost
				.newTabSpec("rank")
				.setIndicator("rank",
						getResources().getDrawable(R.drawable.ic_launcher))
				.setContent(new Intent(this, RankActivity.class)));
		tabhost.addTab(tabhost
				.newTabSpec("mine")
				.setIndicator("mine",
						getResources().getDrawable(R.drawable.ic_launcher))
				.setContent(new Intent(this, MineActivity.class)));

		tv_question = (TextView) findViewById(R.id.tab_layout_text_question);
		tv_recommend = (TextView) findViewById(R.id.tab_layout_text_recommend);
		tv_rank = (TextView) findViewById(R.id.tab_layout_text_rank);
		tv_mine = (TextView) findViewById(R.id.tab_layout_text_mine);
		common_head_title = (TextView) findViewById(R.id.common_head_title);
		
		im_question = (TextView) findViewById(R.id.tab_layout_textimg_question);
		im_recommend = (TextView) findViewById(R.id.tab_layout_textimg_recommend);
		im_add = (TextView) findViewById(R.id.tab_layout_textimg_add);
		im_rank = (TextView) findViewById(R.id.tab_layout_textimg_rank);
		im_mine = (TextView) findViewById(R.id.tab_layout_textimg_mine);

		re_question = (RelativeLayout) findViewById(R.id.tab_layout_question);
		re_recommend = (RelativeLayout) findViewById(R.id.tab_layout_recommend);
		re_add = (RelativeLayout) findViewById(R.id.tab_layout_add);
		re_rank = (RelativeLayout) findViewById(R.id.tab_layout_rank);
		re_mine = (RelativeLayout) findViewById(R.id.tab_layout_mine);
		
		common_title = (RelativeLayout) findViewById(R.id.common_title);
		
		//	默认为第一板块
		changeTabs(0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tab_layout_question: {
			changeTabs(0);
			break;
		}
		case R.id.tab_layout_recommend: {
			changeTabs(1);
			break;
		}
		case R.id.tab_layout_add: {
			// 首先判断是否登陆  如果已经登陆则可以提问
			if(DBUtil.checkLoginState()) {
				Intent intent = new Intent(this, AddQuestionActivity.class);
				startActivity(intent);
			}else {
				Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(this, LoginActivity.class));
			}
			
			break;
		}
		case R.id.tab_layout_rank: {
			changeTabs(2);
			break;
			}
		
		case R.id.tab_layout_mine: {
			if(DBUtil.checkLoginState()) {
				changeTabs(3);
			}else {
				Toast.makeText(this, "您还未登录", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(this, LoginActivity.class));
			}
			break;
			}
		
		default:
			break;
		}
	}
	
	/**
	 * 改变导航栏中选中图片和文字的状态
	 * @param index
	 */
	private void changeTabs(int index) {
		//  全部设置为未选中状态
		tv_question.setTextColor(WHITE);
		tv_recommend.setTextColor(WHITE);
		tv_rank.setTextColor(WHITE);
		tv_mine.setTextColor(WHITE);

		im_question.setBackgroundResource(R.drawable.question_normal);
		im_recommend.setBackgroundResource(R.drawable.recommend_normal);
		im_rank.setBackgroundResource(R.drawable.rank_normal);
		im_mine.setBackgroundResource(R.drawable.mine_normal);
		
		switch (index) {
			case 0:
				common_title.setVisibility(View.GONE);
				tabhost.setCurrentTabByTag("question");
				
				tv_question.setTextColor(ORANGE);
				tv_recommend.setTextColor(WHITE);
				tv_rank.setTextColor(WHITE);
				tv_mine.setTextColor(WHITE);
	
				im_question.setBackgroundResource(R.drawable.question1);
				im_recommend.setBackgroundResource(R.drawable.recommend_normal);
				im_rank.setBackgroundResource(R.drawable.rank_normal);
				im_mine.setBackgroundResource(R.drawable.mine_normal);
				break;
			case 1:
				common_title.setVisibility(View.GONE);
				tabhost.setCurrentTabByTag("recommend");
				
				tv_question.setTextColor(WHITE);
				tv_recommend.setTextColor(ORANGE);
				tv_rank.setTextColor(WHITE);
				tv_mine.setTextColor(WHITE);
	
				im_question.setBackgroundResource(R.drawable.question_normal);
				im_recommend.setBackgroundResource(R.drawable.recommend);
				im_rank.setBackgroundResource(R.drawable.rank_normal);
				im_mine.setBackgroundResource(R.drawable.mine_normal);
				break;
			case 2:
				common_title.setVisibility(View.VISIBLE);
				common_head_title.setText("排行榜");
				
				tabhost.setCurrentTabByTag("rank");
				
				tv_question.setTextColor(WHITE);
				tv_recommend.setTextColor(WHITE);
				tv_rank.setTextColor(ORANGE);
				tv_mine.setTextColor(WHITE);
	
				im_question.setBackgroundResource(R.drawable.question_normal);
				im_recommend.setBackgroundResource(R.drawable.recommend_normal);
				im_rank.setBackgroundResource(R.drawable.rank);
				im_mine.setBackgroundResource(R.drawable.mine_normal);
				break;
			case 3:
				common_title.setVisibility(View.VISIBLE);
				common_head_title.setText("个人中心");
				
				tabhost.setCurrentTabByTag("mine");
				
				tv_question.setTextColor(WHITE);
				tv_recommend.setTextColor(WHITE);
				tv_rank.setTextColor(WHITE);
				tv_mine.setTextColor(ORANGE);
	
				im_question.setBackgroundResource(R.drawable.question_normal);
				im_recommend.setBackgroundResource(R.drawable.recommend_normal);
				im_rank.setBackgroundResource(R.drawable.rank_normal);
				im_mine.setBackgroundResource(R.drawable.mine);
				break;
			default:
				break;
		}
	}

	@Override
	protected void onPause() {
		lam.dispatchPause(isFinishing());
		super.onPause();
	}

	@Override
	protected void onResume() {
		lam.dispatchResume();
		super.onResume();
	}
}
