package cn.edu.cqut.mcqa;

import java.io.Serializable;
import org.apache.http.Header;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.loopj.android.http.RequestParams;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.cqut.adapter.AnswerAdapter;
import cn.edu.cqut.bean.Answer;
import cn.edu.cqut.bean.Question;
import cn.edu.cqut.bean.UserMessage;
import cn.edu.cqut.db.DBUtil;
import cn.edu.cqut.utils.BaseActivity;
import cn.edu.cqut.utils.HeadView;
import cn.edu.cqut.utils.JSONAndObject;
import cn.edu.cqut.utils.MyAlertLoadingDialog;
import cn.edu.cqut.utils.http.BaseJsonHttpResponseHandler;
import cn.edu.cqut.utils.http.HttpClient;
import cn.edu.cqut.utils.http.HttpUrl;

public class QuestionDetail extends BaseActivity {

	public final String REFRESH = "0";// 表示下拉刷新
	public final String LOADMORE = "1";// 表示上拉刷新
	private final String NORMAL = "2";// 表示未刷新状态
	public String type = REFRESH;
	public RequestParams params = null;
	public AnswerAdapter adapter = null;
	private List<Answer> data;
	private MyAlertLoadingDialog loadingDialog = null;
	public String page = "1";// 当前的页数

	private HeadView header;
	private PullToRefreshListView listView;
	private TextView tv_title, tv_content, tv_label;
	private EditText edit_answer;
	private RelativeLayout layout_commit;

	Question question;
	InputMethodManager imm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_detail);
		Intent intent = getIntent();
		adapter = new AnswerAdapter(this);
		question = (Question) intent.getSerializableExtra("question");
		initView();
	}

	private void initView() {
		imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
		layout_commit = (RelativeLayout) findViewById(R.id.layout_commit);
		layout_commit.setOnClickListener(this);
		edit_answer = (EditText) findViewById(R.id.fast_answer);

		loadingDialog = new MyAlertLoadingDialog(this, "");

		params = null;
		type = REFRESH;
		loadingDialog.showLoadingDialog();
		refreshData();

		header = (HeadView) findViewById(R.id.head_question);
		header.setCenterTitle("问题详情");
		header.setLeftImageSource(R.drawable.back);
		header.getLeftImage().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		header.hideRightView();
		initPullToRefresh();

		tv_title = (TextView) findViewById(R.id.question_title);
		tv_content = (TextView) findViewById(R.id.question_content);
		tv_label = (TextView) findViewById(R.id.question_label);

		String label = splitLabel(question);
		tv_label.setText(label);
		tv_title.setText(question.getTitle());
		tv_content.setText(question.getContent());

		initPullToRefresh();
	}

	/**
	 * 初始化下拉组件
	 */
	private void initPullToRefresh() {
		listView = (PullToRefreshListView) findViewById(R.id.listview_answers);
		listView.setMode(Mode.BOTH);
		ILoadingLayout startLabels = listView
				.getLoadingLayoutProxy(true, false);
		startLabels.setPullLabel("向下拉进行刷新");
		startLabels.setRefreshingLabel("正在刷新...");
		startLabels.setReleaseLabel("放开进行刷新");

		ILoadingLayout endLabels = listView.getLoadingLayoutProxy(false, true);
		endLabels.setPullLabel("向上拉加载更多");
		endLabels.setRefreshingLabel("正在加载...");
		endLabels.setReleaseLabel("放开进行加载");
		final Intent intent = new Intent(this, AnswerDetail.class);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Answer answer = adapter.list.get(position - 1);
				intent.putExtra("answer", (Serializable) answer);
				intent.putExtra("question_title", question.getTitle());
				startActivity(intent);
			}
		});

		listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
			// 下拉刷新
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				params = null;
				type = REFRESH;
				refreshData();
			}

			// 上拉加载
			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				type = LOADMORE;
				params = new RequestParams();
				params.put("page", Integer.parseInt(page) + 1);
				refreshData();
			}
		});
		listView.setAdapter(adapter);
	}

	// 刷新数据
	private void refreshData() {
		type = REFRESH;
		HttpClient.get(this, HttpUrl.GET_ALL_ANSWERS + question.getId(),
				params, new BaseJsonHttpResponseHandler(this) {
					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {
						showShortToast("错误" + statusCode);
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String responseString) {
						JSONArray jsonArray;
						try {
							System.out.println(responseString);
							jsonArray = new JSONArray(responseString);
							HandlerJson(jsonArray);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	};

	@SuppressWarnings("unchecked")
	private void HandlerJson(JSONArray jsonArray) {
		data = JSONAndObject.convert(Answer.class, jsonArray.toString(),
				"questions");
		if (type == REFRESH) {
			if (data.size() == 0) {
				showShortToast("暂无数据");
			} else {
				adapter.addNewAnswer(data);
			}
		} else {

		}
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		refreshOrLoadMoreEnd();
	}

	/**
	 * 提交回答
	 */
	public void answerCommit() {
		HttpClient.post(this, HttpUrl.POST_ONE_ANSWER, params,
				new BaseJsonHttpResponseHandler(this) {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String responseString) {
						showShortToast("回答成功");
						refreshData();
						edit_answer.setText("");
						edit_answer.clearFocus();
						getWindow()
								.setSoftInputMode(
										WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
						if (imm.isActive()) // 一直是true
							imm.toggleSoftInput(
									InputMethodManager.SHOW_IMPLICIT,
									InputMethodManager.HIDE_NOT_ALWAYS);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {
						showShortToast("回答失败");
					}
				});
	}

	// 下拉刷新完成
	private void refreshOrLoadMoreEnd() {
		if (type == REFRESH) {
			loadingDialog.closeLoadingDialog();
			listView.onRefreshComplete();
			type = NORMAL;
		}
	}

	public String splitLabel(Question question) {
		String[] strings = {};
		String str = "";
		if (question.getLabel() != null && !question.getLabel().equals("")) {
			strings = question.getLabel().split(",");
			for (int i = 0; i < strings.length; i++) {
				str += strings[i] + "  ";
			}
		}
		return str;
	}

	public void first() {
		params = null;
		type = REFRESH;
		refreshData();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		first();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.layout_commit:
			if (edit_answer.getText().length() == 0) {
				createDialog("请先输入回答内容!!!");
			} else {
				// 提交回答
				List<UserMessage> list = DBUtil.listAll(UserMessage.class);
				if (list.size() > 0) {
					UserMessage userInfo = list.get(0);
					params = new RequestParams();
					params.put("content", edit_answer.getText());
					params.put("praise_num", 0);
					params.put("user_id", userInfo.getUser_id() + "");
					params.put("user_role", userInfo.getName());
					params.put("question_id", question.getId());
					answerCommit();
				} else {
					Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(this, LoginActivity.class));
				}
			}
			break;

		default:
			break;
		}
	}

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
}
