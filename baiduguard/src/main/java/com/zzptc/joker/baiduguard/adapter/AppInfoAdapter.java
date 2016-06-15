package com.zzptc.joker.baiduguard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzptc.joker.baiduguard.R;
import com.zzptc.joker.baiduguard.bean.AppInfo;

import java.util.List;

/**
 * Created by joker on 2016/5/30/030.
 *显示应用详细的recyclerview的适配器
 *
 */
public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.MyViewHolder>{

    private List<AppInfo> appInfoList;

    private LayoutInflater layoutInflater;

    public AppInfoAdapter(List<AppInfo> appInfoList, Context context) {
        this.appInfoList = appInfoList;
        this.layoutInflater = layoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.appinfo_item,null,false);

        MyViewHolder myViewHolder = new MyViewHolder(itemView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AppInfo appInfo = appInfoList.get(position);

        holder.iv_icon.setImageDrawable(appInfo.getAppIcon());
        holder.tv_appName.setText(appInfo.getAppName());
        holder.tv_memorySize.setText(appInfo.getAppMemory());

    }

    @Override
    public int getItemCount() {
        return appInfoList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_icon;
        TextView tv_appName,tv_memorySize;
        CheckBox checkBox ;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_appName = (TextView) itemView.findViewById(R.id.tv_appName);
            tv_memorySize = (TextView) itemView.findViewById(R.id.tv_memorySize);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }
}
