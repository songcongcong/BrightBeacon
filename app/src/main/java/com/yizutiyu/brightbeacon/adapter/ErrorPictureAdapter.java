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
 * @author scc
 * @data 2019/9/22
 */
public class ErrorPictureAdapter extends RecyclerView.Adapter<ErrorPictureAdapter.MyViewHolder> {
    /**
     * mContext
     */
    private Context mContext;
    /**
     * mList
     */
    private List<Integer> mList;

    /**
     * ErrorPictureAdapter
     * @param mContext mContext
     * @param mList mList
     */
    public ErrorPictureAdapter(Context mContext, List<Integer> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    /**
     * 点击条目监听
     */
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

    /**
     * onItemChilkLisener
     */
    public interface onItemChilkLisener {
        /**
         * OnLisener
         * @param imageView imageView
         * @param videoView  videoView
         */
        void OnLisener(ImageView imageView, JZVideoPlayerStandard videoView);
    }

    /**
     * setOnItemChilkLisener
     * @param onItemChilkLisener onItemChilkLisener
     */
    public void setOnItemChilkLisener(onItemChilkLisener onItemChilkLisener) {
        this.onItemChilkLisener = onItemChilkLisener;
    }

    /**
     * viewHolder
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        /**
         * mImg
         */
        private final ImageView mImg;
        /**
         * mVideoView
         */
        private final JZVideoPlayerStandard mVideoView;

        /**
         * MyViewHolder
         * @param itemView itemView
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImg = itemView.findViewById(R.id.im_picture_item);
            mVideoView = itemView.findViewById(R.id.jd_view);

        }
    }
}
