package cn.edu.cqut.mcqa;

import java.io.Serializable;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;

import cn.edu.cqut.adapter.QuestionAdapter;
import cn.edu.cqut.bean.Question;
import cn.edu.cqut.utils.BaseActivity;
import cn.edu.cqut.utils.JSONAndObject;
import cn.edu.cqut.utils.http.BaseJsonHttpResponseHandler;
import cn.edu.cqut.utils.http.HttpClient;
import cn.edu.cqut.utils.http.HttpUrl;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SearchActivity extends BaseActivity {
	public final String REFRESH = "0";// 表示下拉刷新
	public final String LOADMORE = "1";// 表示上拉刷新
	private final String NORMAL = "2";// 表示未刷新状态
	public String type = REFRESH;
	private RequestParams params;
	private PullToRefreshListView search_list;
	@SuppressWarnings("unused")
	private ImageView btn_back, im_clear, im_send;
	private TextView tv_none;
	private EditText search_content;
	public QuestionAdapter adapter = null;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initView();
	}

	private void initView() {
		initCommonBack(R.id.btn_back);
		adapter = new QuestionAdapter(this);
		initPullToRefresh();
		im_clear = (ImageView) findViewById(R.id.im_clear);
		im_send = (ImageView) findViewById(R.id.commit_search);
		search_content = (EditText) findViewById(R.id.search_content);
		im_clear.setOnClickListener(this);
		im_send.setOnClickListener(this);
		search_content.addTextChangedListener(this);
		tv_none = (TextView) findViewById(R.id.tv_null);
	}

	/**
	 * 初始化下拉组件
	 */
	private void initPullToRefresh() {
		search_list = (PullToRefreshListView) findViewById(R.id.listview_search);

		ILoadingLayout startLabels = search_list.getLoadingLayoutProxy(true,
				false);
		startLabels.setPullLabel("向下拉进行刷新");
		startLabels.setRefreshingLabel("正在刷新...");
		startLabels.setReleaseLabel("放开进行刷新");

		ILoadingLayout endLabels = search_list.getLoadingLayoutProxy(false,
				true);
		endLabels.setPullLabel("向上拉加载更多");
		endLabels.setRefreshingLabel("正在加载...");
		endLabels.setReleaseLabel("放开进行加载");

		final Intent intent = new Intent(this, QuestionDetail.class);
		search_list
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Question question = adapter.list.get(position - 1);
						params = new RequestParams();
						params.put("question_id", question.getId());
						addReadNum();
						intent.putExtra("question", (Serializable) question);
						startActivity(intent);
					}
				});

		search_list
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
					// 下拉刷新
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						type = REFRESH;
						refreshData();
					}

					// 上拉加载
					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						type = LOADMORE;
						/*
						 * params = new RequestParams(); params.put("page",
						 * Integer.parseInt(page) + 1);
						 */
						refreshData();
					}
				});
		search_list.setAdapter(adapter);
	}

	// 刷新数据
	private void refreshData() {
		type = REFRESH;
		HttpClient.post(this, HttpUrl.POST_SEARCH_QUESTION, params,
				new BaseJsonHttpResponseHandler(this) {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String responseString) {

						try {
							JSONArray jsonArray = new JSONArray(responseString);
							HandlerJson(jsonArray);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	@SuppressWarnings("unchecked")
	private void HandlerJson(JSONArray jsonArray) {
		List<Question> questions = JSONAndObject.convert(Question.class,
				jsonArray.toString(), "questions");
		if (type == REFRESH) {
			if (questions.size() == 0) {
				showShortToast("暂未搜到与关键相关的问题");
			} else {
				tv_none.setVisibility(View.GONE);
				search_list.setVisibility(View.VISIBLE);
				adapter.addNewQuestion(questions);
			}

		} else {
			if (questions.size() == 0) {
				showShortToast("没有更多数据！");
			}
			tv_none.setVisibility(View.GONE);
			search_list.setVisibility(View.VISIBLE);
			adapter.addOldQuestion(questions);
		}

		search_list.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		refreshOrLoadMoreEnd();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.im_clear: {
			search_content.setText("");
		}
			break;
		case R.id.commit_search: {
			if (search_content.getText().length() == 0
					|| search_content.getText() == null) {
				showConfirmDialog("请先输入要搜索的关键字...");
			} else {
				params = new RequestParams();
				params.put("title", search_content.getText());
				searchQuestions();
			}
		}
		default:
			break;
		}
	}

	// 数据请求
	private void searchQuestions() {
		refreshData();
	}

	// 创建自定义布局的对话框
	private void showConfirmDialog(String str) {
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
				// Toast.makeText(getApplicationContext(), "ok",
				// Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});

		/*
		 * // 5. 取消按钮 TextView tv_cancel = (TextView)
		 * layout.findViewById(R.id.tv_cancle); tv_cancel.setOnClickListener(new
		 * OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * Toast.makeText(getApplicationContext(), "cancel",
		 * Toast.LENGTH_SHORT).show(); dialog.dismiss(); } });
		 */
	}

	@Override
	public void afterTextChanged(Editable edit) {
		if (edit.length() > 0) {
			im_clear.setVisibility(View.VISIBLE);
		} else {
			im_clear.setVisibility(View.GONE);
		}
	}

	// 下拉刷新完成
	private void refreshOrLoadMoreEnd() {
		if (type == REFRESH) {
			search_list.onRefreshComplete();
			type = NORMAL;
		}
	}

	// 增加阅读量
	private void addReadNum() {
		HttpClient.get(this, HttpUrl.POST_READ_NUM, params,
				new BaseJsonHttpResponseHandler(this) {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String responseString) {
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {
					}
				});
	}

}
