package cn.edu.cqut.mcqa;

import java.util.ArrayList;
import java.util.List;

import cn.edu.cqut.fragment.HotQuestions;
import cn.edu.cqut.fragment.NewQuestions;
import cn.edu.cqut.utils.BaseFragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class QuestionActivity extends BaseFragmentActivity {
	
	private ViewPager vp_question;
	private FragmentStatePagerAdapter adapter;
	private List<Fragment> fragments;
	private TextView tv_new, tv_hot;
	private ImageView im_search;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		initView();
		initFragments();
		initAdapter();
		initViewPager();
	}

	private void initView() {
		tv_new = (TextView) findViewById(R.id.tv_new);
		tv_hot = (TextView) findViewById(R.id.tv_hot);
		im_search = (ImageView) findViewById(R.id.img_search);
		
		tv_new.setOnClickListener(this);
		tv_hot.setOnClickListener(this);
		im_search.setOnClickListener(this);
		
		// 初始默认为0
		changeTabs(0);
	}
	
	private void changeTabs(int index) {
		// 初始将两个TextView设置为未选中状态
		tv_new.setBackgroundResource(R.drawable.guide_coner_bg_left_normal);
		tv_new.setTextColor(getResources().getColor(R.color.white));
		tv_hot.setBackgroundResource(R.drawable.guide_coner_bg_right_normal);
		tv_hot.setTextColor(getResources().getColor(R.color.white));
		
		switch (index) {
		case 0:
			tv_new.setBackgroundResource(R.drawable.guide_coner_bg_left);
			tv_new.setTextColor(getResources().getColor(R.color.orange));
			break;
		case 1:
			tv_hot.setBackgroundResource(R.drawable.guide_coner_bg_right);
			tv_hot.setTextColor(getResources().getColor(R.color.orange));
			break;
		default:
			break;
		}
	}
	
	private void initFragments() {
		fragments = new ArrayList<Fragment>();
		
		NewQuestions frg_new = new NewQuestions();
		HotQuestions frg_hot = new HotQuestions();
		
		fragments.add(frg_new);
		fragments.add(frg_hot);
	}

	private void initAdapter() {
		adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return fragments.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return fragments.get(arg0);
			}
		};
	}
	
	private void initViewPager() {
		vp_question = (ViewPager) findViewById(R.id.viewpager_question);
		vp_question.setOffscreenPageLimit(1);
		vp_question.setAdapter(adapter);
		vp_question.setCurrentItem(0);
		
		vp_question.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				changeTabs(arg0);	// 改变导航文字状态
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				System.out.println("arg0:" + arg0 + " arg1:" + arg1);
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_new:
			vp_question.setCurrentItem(0, true);
			break;
		case R.id.tv_hot:
			vp_question.setCurrentItem(1, true);
			break;
		case R.id.img_search: {
			Intent intent = new Intent(this, SearchActivity.class);
			startActivity(intent);
		}
			break;
		default:
			break;
		}
	}
}
