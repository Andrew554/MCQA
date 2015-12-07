package cn.edu.cqut.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.cqut.bean.Answer;
import cn.edu.cqut.mcqa.R;
import cn.edu.cqut.utils.comp.DateUtil;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import java.util.ArrayList;
import java.util.List;

public class AnswerAdapter extends BaseAdapter{

    static class ViewHolder{
        ImageView headImage;
        TextView priseNum;
        TextView roleName;
        TextView answerContent;
        TextView showTime;
    }

    private Context context;
    public List<Answer> list = new ArrayList<Answer>();
    private LayoutInflater inflater;

 // DisplayImageOptions的初始化
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.cool)
            .showImageOnLoading(R.drawable.cool)
            .showImageOnFail(R.drawable.cool)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    public AnswerAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }


    /**
     * 得到最新数据往list里面添加
     */
    public void addNewAnswer(List<Answer> answers){
        list.clear();
        list.addAll(answers);
    }

    /**
     * 加载更多的时候往list的最后加数据
     * @param questions
     */
    public void addOldAnswer(List<Answer> questions){
        list.addAll(list.size(), questions);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_item_answer_model, null);
            holder.headImage = (ImageView) convertView.findViewById(R.id.head_image);
            holder.priseNum = (TextView) convertView.findViewById(R.id.prise_num);
            holder.roleName = (TextView) convertView.findViewById(R.id.role_name);
            holder.answerContent = (TextView) convertView.findViewById(R.id.answer_content);
            holder.showTime = (TextView) convertView.findViewById(R.id.show_time);
            
            holder.priseNum.setText(list.get(position).getpraise_num() + "");
            holder.roleName.setText(list.get(position).getuser_role());
            holder.answerContent.setText(list.get(position).getContext());
            holder.showTime.setText(DateUtil.formatSimple(list.get(position).getcreated_at()));
            //imageLoader加载图像
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            imageLoader.displayImage(list.get(position).gethead_image(), holder.headImage, options);
            
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}
