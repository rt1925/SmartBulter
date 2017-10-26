package imooc.com.smartbulter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import imooc.com.smartbulter.R;
import imooc.com.smartbulter.entity.GirlData;
import imooc.com.smartbulter.utils.L;
import imooc.com.smartbulter.utils.PicassoUtils;


/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.adapter
*文件名：GirlAdapter
*创建者：pavilion15
*创建时间：2017/9/4 20:31
*描述： 美女adapter
*/
public class GirlAdapter extends BaseAdapter {
    private Context mContext;
    private List<GirlData> mList=new ArrayList<>();
    private LayoutInflater inflater;
    //屏幕宽度
    private int width;
    private WindowManager windowManager;
    private GirlData data;

    public GirlAdapter(Context mContext,List<GirlData> mList){
        this.mContext=mContext;
        this.mList=mList;
        inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        windowManager= (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width=windowManager.getDefaultDisplay().getWidth();
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            view=inflater.inflate(R.layout.girl_item,null);
            viewHolder.iv_girl=(ImageView) view.findViewById(R.id.iv_girl);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        data=mList.get(i);
        //加载图片
        //L.i("url:"+data.getUrl());
        PicassoUtils.loadImageViewSize(mContext,data.getUrl(),width/2,200,viewHolder.iv_girl);
        return view;
    }

    class ViewHolder{
        private ImageView iv_girl;
    }
}
