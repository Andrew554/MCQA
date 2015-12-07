package cn.edu.cqut.mcqa;

import java.util.ArrayList;
import java.util.List;

import cn.edu.cqut.fragment.FellowRecommend;
import cn.edu.cqut.fragment.ProfessionRecommend;
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

public class RecommendActivity extends BaseFragmentActivity {
	
	private ViewPager vp_recommend;
	private FragmentStatePagerAdapter adapter;
	private List<Fragment> fragments;
	private TextView tv_profession, tv_fellow;
	private ImageView im_search;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommend);
		initView();
		initFragments();
		initAdapter();
		initViewPager();
	}

	private void initView() {
		tv_profession = (TextView) findViewById(R.id.tv_profession);
		tv_fellow = (TextView) findViewById(R.id.tv_fellow);
		im_search = (ImageView) findViewById(R.id.img_search);
		
		tv_profession.setOnClickListener(this);
		tv_fellow.setOnClickListener(this);
		im_search.setOnClickListener(this);
		
		// 初始默认为0
		changeTabs(0);
	}
	
	private void changeTabs(int index) {
		// 初始将两个TextView设置为未选中状态
		tv_profession.setBackgroundResource(R.drawable.guide_coner_bg_left_normal);
		tv_profession.setTextColor(getResources().getColor(R.color.white));
		tv_fellow.setBackgroundResource(R.drawable.guide_coner_bg_right_normal);
		tv_fellow.setTextColor(getResources().getColor(R.color.white));
		
		switch (index) {
		case 0:
			tv_profession.setBackgroundResource(R.drawable.guide_coner_bg_left);
			tv_profession.setTextColor(getResources().getColor(R.color.orange));
			break;
		case 1:
			tv_fellow.setBackgroundResource(R.drawable.guide_coner_bg_right);
			tv_fellow.setTextColor(getResources().getColor(R.color.orange));
			break;
		default:
			break;
		}
	}
	
	private void initFragments() {
		fragments = new ArrayList<Fragment>();
		
		ProfessionRecommend frg_profession = new ProfessionRecommend();
		FellowRecommend frg_fellow = new FellowRecommend();
		
		fragments.add(frg_profession);
		fragments.add(frg_fellow);
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
		vp_recommend = (ViewPager) findViewById(R.id.viewpager_recommend);
		vp_recommend.setOffscreenPageLimit(1);
		vp_recommend.setAdapter(adapter);
		vp_recommend.setCurrentItem(0);
		
		vp_recommend.setOnPageChangeListener(new OnPageChangeListener() {
			
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
		case R.id.tv_profession:
			vp_recommend.setCurrentItem(0, true);
			break;
		case R.id.tv_fellow:
			vp_recommend.setCurrentItem(1, true);
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
