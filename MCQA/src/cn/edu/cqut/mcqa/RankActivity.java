package cn.edu.cqut.mcqa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.loopj.android.http.RequestParams;

import cn.edu.cqut.adapter.QuestionAdapter;
import cn.edu.cqut.adapter.RankAdapter;
import cn.edu.cqut.bean.Question;
import cn.edu.cqut.bean.User;
import cn.edu.cqut.utils.BaseFragmentActivity;
import cn.edu.cqut.utils.JSONAndObject;
import cn.edu.cqut.utils.MyAlertLoadingDialog;
import cn.edu.cqut.utils.BaseFragment.HttpCallBack;
import cn.edu.cqut.utils.http.HttpUrl;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class RankActivity extends BaseFragmentActivity {
	private PullToRefreshListView listView;
	public final String REFRESH = "0";//表示下拉刷新
    public final String LOADMORE = "1";//表示上拉刷新
    private final String NORMAL = "2";//表示未刷新状态
    public String type = REFRESH;
    public RequestParams params = null;
    public RankAdapter adapter = null;
    public String page = "1";//当前的页数
    private Map<String, String> dataMap;
    private List<User> data;
    private MyAlertLoadingDialog loadingDialog = null;
    
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rank);
		initView();
	}
	/**
	 * 初始化组件
	 */
	private void initView() {
		loadingDialog = new MyAlertLoadingDialog(this, "");
		/**
		 * 第一次进入
		 */
		params = null;
	    type = REFRESH;
	    loadingDialog.showLoadingDialog();
	    refreshData();
	    
	    data = new ArrayList<User>();
		adapter = new RankAdapter(this);
		
		initPullToRefresh();
	}
	
	/**
	 * 初始化下拉组件
	 */
	private void initPullToRefresh() {
		listView = (PullToRefreshListView)findViewById(R.id.listview_rank);
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
                User user = adapter.list.get(position - 1);
                params = new RequestParams();
                params.put("question_id", user.getId());
                /*addReadNum();
                Intent intent = new Intent(getActivity(), AllAnswerActivity_.class);
                intent.putExtra("question", (Serializable)question);
                startActivity(intent);*/
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
                refreshData();
            }
        });
        listView.setAdapter(adapter);
	}

	// 刷新数据
	private void refreshData() {
		type = REFRESH;
		dataMap = new HashMap<String, String>();
		asyHttp(HttpUrl.GET_RANK, dataMap, new HttpCallBack() {
			
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
				showShortToast("资源加载失败");
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	private void HandlerJson(JSONArray jsonArray) {
		if(type==REFRESH){
		}
		
		data = JSONAndObject.convert(User.class, jsonArray.toString(), "rankUsers");
		
		adapter.addNewUser(data);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		refreshOrLoadMoreEnd();		
	}
	
	// 下拉刷新完成
	private void refreshOrLoadMoreEnd() {
		if(type == REFRESH){
			loadingDialog.closeLoadingDialog();
			showShortToast("加载完成");
			listView.onRefreshComplete();
			type = NORMAL;
		}
	}
}