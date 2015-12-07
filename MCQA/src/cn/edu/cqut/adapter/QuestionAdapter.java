package cn.edu.cqut.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.cqut.bean.Question;
import cn.edu.cqut.mcqa.R;
import cn.edu.cqut.utils.comp.DateUtil;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * 问题的适配器
 */
public class QuestionAdapter extends BaseAdapter{

    static class ViewHolder{
        ImageView headImage;
        TextView username;
        TextView readNum;
        TextView questionTitle;
        TextView questionLabel;
        TextView createTime;
    }
    private Context context;
    public List<Question> list = new ArrayList<Question>();
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

    public QuestionAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public List<Question> getList() {
        return list;
    }

    public void setList(List<Question> list) {
        this.list = list;
    }

    /**
     * 得到最新数据覆盖原来数据
     */
    public void addNewQuestion(List<Question> questions){
        list.clear();
        list.addAll(questions);
    }

    /**
     * 加载更多的时候在list的最后加数据
     * @param questions
     */
    public void addOldQuestion(List<Question> questions){
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
            convertView = inflater.inflate(R.layout.question_item, null);
            holder.headImage = (ImageView) convertView.findViewById(R.id.userImage);
            holder.username = (TextView) convertView.findViewById(R.id.username);
            holder.readNum = (TextView) convertView.findViewById(R.id.read_num);
            holder.questionTitle = (TextView) convertView.findViewById(R.id.question_title);
            holder.questionLabel = (TextView) convertView.findViewById(R.id.question_label);
            holder.createTime = (TextView) convertView.findViewById(R.id.red_time);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.username.setText(list.get(position).getUserName());
        System.out.println("userName: " + holder.username.getText());
        holder.readNum.setText(list.get(position).getReadNum());
        holder.questionTitle.setText(list.get(position).getTitle());
        holder.createTime.setText(DateUtil.formatSimple(list.get(position).getTime()));
        System.out.println("createTime: " + list.get(position).getTime());
        holder.questionLabel.setText("");
        if( list.get(position).getLabel() != null && !list.get(position).getLabel().equals("")){
            String[] split = list.get(position).getLabel().split(",");
            for (int i = 0 ; i < split.length ; i++){
                String str = split[i] + "  ";
                int bstart = str.lastIndexOf("  ");
                int bend = bstart + "  ".length();
                SpannableStringBuilder style=new SpannableStringBuilder(str);
                style.setSpan(new BackgroundColorSpan(Color.rgb(238,238,238)),bstart,bend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.questionLabel.append(style);
            }
        }else{
            holder.questionLabel.setText("");
        }
        //使用imageLoader加载图像
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        imageLoader.displayImage(list.get(position).getHeadImage(), holder.headImage, options);
        return convertView;
    }
}
