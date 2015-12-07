package cn.edu.cqut.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.cqut.bean.User;
import cn.edu.cqut.mcqa.R;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * 排行榜适配器
 */
public class RankAdapter extends BaseAdapter {

    static class ViewHolder{
        ImageView sortImage;
        ImageView headImage;
        TextView sortNum;
        TextView username;
        TextView sex;
        TextView sign;
    }

    private Context context;
    public List<User> list = new ArrayList<User>();
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

    public RankAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    /**
     * 得到最新数据往list里面添加
     */
    public void addNewUser(List<User> users){
        list.clear();
        list.addAll( users);
    }

    /**
     * 加载更多的时候往list的最后加数据
     */
    public void addOldUser(List<User> users){
        list.addAll(list.size(), users);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
            convertView = inflater.inflate(R.layout.rank_item, null);
            holder.sortImage = (ImageView) convertView.findViewById(R.id.sort_bg);
            holder.headImage = (ImageView) convertView.findViewById(R.id.rank_cover);
            holder.sortNum = (TextView) convertView.findViewById(R.id.rank_num);
            holder.username = (TextView) convertView.findViewById(R.id.rank_name);
            holder.sex = (TextView) convertView.findViewById(R.id.rank_sex);
            holder.sign = (TextView) convertView.findViewById(R.id.sign);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        imageLoader.displayImage(list.get(position).getHeadImage(), holder.headImage, options);

        holder.sortNum.setText(position + 1 + "");
        holder.username.setText(list.get(position).getUsername());
        if(list.get(position).getUserSign() == null || list.get(position).getUserSign().equals("")) {
            holder.sign.setText("该用户很懒，什么也没留下...");
        } else {
            holder.sign.setText(list.get(position).getUserSign());
        }

//        holder.sex.setText("男");
       /* if(list.get(position).getSex()==null){
            holder.sex.setText("");
        } else {*/
            if (list.get(position).getSex() == 1) {
                holder.sex.setText("男");
            } else {
                holder.sex.setText("女");
            }
//        }

        return convertView;
    }
}
