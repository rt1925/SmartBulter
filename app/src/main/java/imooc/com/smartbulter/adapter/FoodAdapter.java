package imooc.com.smartbulter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import imooc.com.smartbulter.R;
import imooc.com.smartbulter.entity.FoodData;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.adapter
*文件名：FoodAdapter
*创建者：pavilion15
*创建时间：2017/9/20 18:44
*描述： 菜谱Adapter
*/
public class FoodAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<FoodData> mlist=new ArrayList<>();

    public FoodAdapter (Context mContext,List<FoodData> mlist){
        this.mContext=mContext;
        this.mlist=mlist;
        inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view == null){
            viewHolder=new ViewHolder();
            view=inflater.inflate(R.layout.food_item,null);
            viewHolder.tv_cp_name=view.findViewById(R.id.tv_cp_name);
            viewHolder.tv_type_name=view.findViewById(R.id.tv_type_name);
            viewHolder.tv_tiaoliao=view.findViewById(R.id.tv_tiaoliao);
            viewHolder.tv_yuanliao=view.findViewById(R.id.tv_yuanliao);
            viewHolder.tv_texing=view.findViewById(R.id.tv_texing);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        FoodData data=mlist.get(i);
        viewHolder.tv_cp_name.setText(data.getCp_name());
        viewHolder.tv_type_name.setText(data.getType_name());
        viewHolder.tv_tiaoliao.setText(data.getTiaoliao());
        viewHolder.tv_yuanliao.setText(data.getYuanliao());
        viewHolder.tv_texing.setText(data.getTexing());
        return view;
    }

    class ViewHolder{
        private TextView tv_cp_name,tv_type_name,tv_tiaoliao,tv_yuanliao,tv_texing;
    }
}
