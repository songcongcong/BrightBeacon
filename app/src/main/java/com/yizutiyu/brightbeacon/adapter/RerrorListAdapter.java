package com.yizutiyu.brightbeacon.adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yizutiyu.brightbeacon.R;

import java.util.List;

/**
 * RerrorListAdapter
 */
public class RerrorListAdapter extends RecyclerView.Adapter<RerrorListAdapter.MyViewHolder> {
    /**
     * mList
     */
    private List<String> mList;
    /**
     * mImag
     */
    private String[] mImag;
    /**
     * mContext
     */
    private Activity mContext;

    /**
     * RerrorListAdapter
     * @param mList mList
     * @param mImag mImag
     * @param mContext mContext
     */
    public RerrorListAdapter(List<String> mList, String[] mImag, Activity mContext) {
        this.mList = mList;
        this.mImag = mImag;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RerrorListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.error_list_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RerrorListAdapter.MyViewHolder holder, int position) {
        // 加载圆形
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(mImag[position]))
                .setTapToRetryEnabled(true)
                .build();

        holder.mImg.setController(controller);
        holder.mTitle.setText(mList.get(position));
        holder.mDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.OnClick();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * MyViewHolder
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        /**
         * mImg
         */
        private final SimpleDraweeView mImg;
        /**
         * mTitle
         */
        private final TextView mTitle;
        /**
         * mContent
         */
        private final TextView mContent;
        /**
         * mDetail
         */
        private final TextView mDetail;

        /**
         * MyViewHolder
         * @param itemView itemView
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImg = itemView.findViewById(R.id.error_img);
            mTitle = itemView.findViewById(R.id.error_title);
            mContent = itemView.findViewById(R.id.error_content);
            mDetail = itemView.findViewById(R.id.error_detail);
        }
    }

    /**
     * 接口回调
     */
    private onItemClickListener onItemClickListener;

    /**
     * onItemClickListener
     */
    public interface onItemClickListener {
        /**
         * OnClick
         */
        void OnClick();
    }

    /**
     * setOnItemClickListener
     * @param onItemClickListener onItemClickListener
     */
    public void setOnItemClickListener(RerrorListAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
