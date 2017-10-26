package imooc.com.smartbulter.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import imooc.com.smartbulter.R;
import imooc.com.smartbulter.entity.NewData;
import imooc.com.smartbulter.entity.WeChatData;
import imooc.com.smartbulter.utils.PicassoUtils;

import static imooc.com.smartbulter.R.id.iv_news_img;


/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.adapter
*文件名：NewAdapter
*创建者：pavilion15
*创建时间：2017/9/23 21:47
*描述： 新闻Adapter
*/
public class NewAdapter extends BaseAdapter {
    private NewData data;
    private List<NewData> mList=new ArrayList<>();
    private LayoutInflater inflater;
    private Context mContext;

    public NewAdapter(List<NewData> mList, Context mContext){
        this.mList=mList;
        this.mContext=mContext;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            view=inflater.inflate(R.layout.wechat_item,null);
            viewHolder.tv_news_title=(TextView)view.findViewById(R.id.tv_news_title);
            viewHolder.tv_news_source=(TextView)view.findViewById(R.id.tv_news_source);
            viewHolder.iv_news_img=(ImageView)view.findViewById(iv_news_img);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }

        data=mList.get(i);
        viewHolder.tv_news_title.setText(data.getTitle());
        viewHolder.tv_news_source.setText(data.getDescription());
        //加载图片
        if(!TextUtils.isEmpty(data.getPicUrl())){
            PicassoUtils.loadImageViewSize(mContext,data.getPicUrl(),200,100,viewHolder.iv_news_img);
        }else{
            String url="https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%9B%BE%E7%89%87%E6%97%A0%E6%B3%95%E6%98%BE%E7%A4%BA%E7%9A%84%E5%9B%BE%E7%89%87&step_word=&hs=0&pn=0&spn=0&di=92330078830&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=3406044585%2C178857541&os=246667576%2C3168897365&simid=3543964295%2C672566659&adpicid=0&lpn=0&ln=1980&fr=&fmq=1506862888598_R&fm=rs1&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=%E6%97%A0%E6%B3%95%E6%98%BE%E7%A4%BA%E8%A1%A8%E6%83%85%E5%8C%85&objurl=http%3A%2F%2Fwenwen.soso.com%2Fp%2F20090927%2F20090927180856-1184824162.jpg&fromurl=" +
                    "ippr_z2C%24qAzdH3FAzdH3Fojgojg_z%26e3Bf5f5_z%26e3Bv54AzdH3FzAzdH3Fq8c09a809m_z%26e3Bip4&gsm=0&rpstart=0&rpnum=0";
            PicassoUtils.loadImageViewSize(mContext,url,200,100,viewHolder.iv_news_img);
        }
        return view;
    }

    class ViewHolder{
        private TextView tv_news_title;
        private ImageView iv_news_img;
        private TextView tv_news_source;
    }
}
