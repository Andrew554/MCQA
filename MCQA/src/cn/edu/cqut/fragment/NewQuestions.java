package cn.edu.cqut.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.apache.http.Header;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import cn.edu.cqut.adapter.QuestionAdapter;
import cn.edu.cqut.bean.Question;
import cn.edu.cqut.mcqa.QuestionDetail;
import cn.edu.cqut.mcqa.R;
import cn.edu.cqut.utils.BaseFragment;
import cn.edu.cqut.utils.JSONAndObject;
import cn.edu.cqut.utils.MyAlertLoadingDialog;
import cn.edu.cqut.utils.http.BaseJsonHttpResponseHandler;
import cn.edu.cqut.utils.http.HttpClient;
import cn.edu.cqut.utils.http.HttpUrl;

public class NewQuestions extends BaseFragment{
	private PullToRefreshListView listView;
	public final String REFRESH = "0";//表示下拉刷新
    public final String LOADMORE = "1";//表示上拉刷新
    private final String NORMAL = "2";//表示未刷新状态
    public String type = REFRESH;
    public RequestParams params = null;
    public QuestionAdapter adapter = null;
    public String page = "1";//当前的页数
    private Map<String, String> dataMap;
    private List<Question> data;
    private MyAlertLoadingDialog loadingDialog = null;
	
	//	Fragment对象关联到相对应的view时被调用
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.new_question, container, false);
		return view;
	}
	
	//	当Activity对象完成自己的onCreate时被调用
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		System.out.println("onActivityCreated");
		initView();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		loadingDialog = new MyAlertLoadingDialog(getActivity(), "");
		/**
		 * 第一次进入
		 */
		params = null;
	    type = REFRESH;
	    loadingDialog.showLoadingDialog();
	    refreshData();
		
	    data = new ArrayList<Question>();
		adapter = new QuestionAdapter(getActivity());
		
		initPullToRefresh();
	}

	/**
	 * 初始化下拉组件
	 */
	private void initPullToRefresh() {
		listView = (PullToRefreshListView) getActivity().findViewById(R.id.listview_new_question);
		listView.setMode(Mode.BOTH);
		ILoadingLayout startLabels = listView.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("向下拉进行刷新");
        startLabels.setRefreshingLabel("正在刷新...");
        startLabels.setReleaseLabel("放开进行刷新");

        ILoadingLayout endLabels = listView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("向上拉加载更多");
        endLabels.setRefreshingLabel("正在加载...");
        endLabels.setReleaseLabel("放开进行加载");		
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Question question = adapter.list.get(position - 1);
                params = new RequestParams();
                params.put("question_id", question.getId());
                addReadNum();
                Intent intent = new Intent(getActivity(), QuestionDetail.class);
                intent.putExtra("question", (Serializable)question);
                startActivity(intent);
            }
        });
		
		listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                params = null;
                type = REFRESH;
                refreshData();
            }

            //上拉加载
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
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
		dataMap = new HashMap<String, String>();
		asyHttp(HttpUrl.GET_NEW_QUESTION, dataMap, new HttpCallBack() {
			
			@Override
			public void Success(String respose) {
				// TODO Auto-generated method stub
				try {
					JSONArray jsonArray = new JSONArray(respose);
					HandlerJson(jsonArray);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void Fail(String error) {
				// TODO Auto-generated method stub
				showShortToast(getActivity(), "最新资源加载失败");
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	private void HandlerJson(JSONArray jsonArray) {
		if(type==REFRESH){
		}
		
		data = JSONAndObject.convert(Question.class, jsonArray.toString(), "questions");
		adapter.addNewQuestion(data);
		listView.setAdapter(adapter);
		refreshOrLoadMoreEnd();		
	}
	
	// 下拉刷新完成
	private void refreshOrLoadMoreEnd() {
		if(type == REFRESH){
			listView.onRefreshComplete();
			loadingDialog.closeLoadingDialog();
			type=NORMAL;
		}
	}
	
	// 增加阅读量
	private void addReadNum() {
		HttpClient.get(getActivity(), HttpUrl.POST_READ_NUM, params, new BaseJsonHttpResponseHandler( getActivity()){
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }
        });
	}
}
