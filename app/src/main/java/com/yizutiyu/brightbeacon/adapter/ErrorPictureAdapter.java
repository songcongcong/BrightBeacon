package com.yizutiyu.brightbeacon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yizutiyu.brightbeacon.R;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * @author
 * @data 2019/9/22
 */
public class ErrorPictureAdapter extends RecyclerView.Adapter<ErrorPictureAdapter.MyViewHolder> {

    private Context mContext;
    private List<Integer> mList;

    public ErrorPictureAdapter(Context mContext, List<Integer> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    //点击条目监听
    private onItemChilkLisener onItemChilkLisener;

    @NonNull
    @Override
    public ErrorPictureAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(mContext, R.layout.picture_layout_item, null);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ErrorPictureAdapter.MyViewHolder holder, int position) {
        Glide.with(mContext).load(mList.get(position)).into(holder.mImg);
        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemChilkLisener != null) {
                    onItemChilkLisener.OnLisener(holder.mImg, holder.mVideoView);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface onItemChilkLisener {
        void OnLisener(ImageView imageView, JZVideoPlayerStandard videoView);
    }

    public void setOnItemChilkLisener(onItemChilkLisener onItemChilkLisener) {
        this.onItemChilkLisener = onItemChilkLisener;
    }

    /**
     * viewHolder
     */
    class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImg;
        private final JZVideoPlayerStandard mVideoView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImg = itemView.findViewById(R.id.im_picture_item);
            mVideoView = itemView.findViewById(R.id.jd_view);

        }
    }
}
