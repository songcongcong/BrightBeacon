package com.yizutiyu.brightbeacon.adapter;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.brtbeacon.sdk.BRTBeacon;
import com.yizutiyu.brightbeacon.R;
import com.yizutiyu.brightbeacon.eventbus.RegionNumEventBus;
import com.yizutiyu.brightbeacon.info.RegionListInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author scc
 * @data 2019/9/21
 */
public class PatrolAdapter extends RecyclerView.Adapter<PatrolAdapter.ViewHolder> {
    /**
     * TAG
     */
    public static String TAG = "PatrolAdapter";
    /**
     * BRTBeacon
     */
    private BRTBeacon mbrt;
    /**
     * 存放已被选中的CheckBox
     * map
     */
    private static Map<Integer, Boolean> mCheckBoxMap = new HashMap<>();
    /**
     * 是否巡检成功
     */
    private boolean mSuccess;
    /**
     * 存放是否异常状态
     */
    private static Map<Integer, Boolean> mapPosition;
    /**
     * 巡检完成的数量
     */
    private int mCount;
    /**
     * mList
     */
    private List<RegionListInfo> mList;
    /**
     * 上下文
     */
    private Context mContext;


    /**
     * 构造器
     *
     * @param mContext mContext
     */
    public PatrolAdapter(Context mContext, List<RegionListInfo> list) {
        this.mContext = mContext;
        this.mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.patrol_recycle_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 禁止recycleview刷新，解决状态错乱问题
        holder.setIsRecyclable(false);
        holder.mTvName.setText(mList.get(position).getRegion());
        String format = String.format(mContext.getResources().getString(R.string.region_name), "张亮");
        holder.mTvRegionNmae.setText(format);
        if (mList.get(position).isFlag() && !TextUtils.isEmpty(mbrt.getMacAddress())
                && mList.get(position).getBluetoothKey().equals(mbrt.getMacAddress())) {
            holder.mLinRegion.setFocusable(true);
            holder.mTvStart.setFocusable(true);
            holder.mTvStart.setBackground(mContext.getResources().getDrawable(R.drawable.region_bg));
            holder.mIvImg.setBackgroundResource(R.mipmap.iv_user_name);
            holder.mLinRegion.setBackgroundResource(R.drawable.login_backgrount_noin);
            String format1 = String.format(mContext.getResources().getString(R.string.tv_into), mbrt.name);
            holder.mTvAccountName.setText(format1);
            Log.d(TAG, "进入：" + mList.get(position).getRegion() + ",:" + mList.get(position).isFlag());
            holder.mTvStart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (mSuccess) {
//                        Iterator<Map.Entry<Integer, Boolean>> it = mCheckBoxMap.entrySet().iterator();
//                        while (it.hasNext()) {
//                            Map.Entry<Integer, Boolean> entry = it.next();
//                            it.remove();//使用迭代器的remove()方法删除元素
//                        }
//                    }
                    Log.d(TAG, "删除：" + mCheckBoxMap.toString());
                    mCheckBoxMap.put(position, true);
                    Log.d(TAG, "存值：" + mCheckBoxMap.toString());
                    if (onItemListener != null) {
                        onItemListener.onItemListener(position);
                    }
                    Log.d(TAG, "不点击传position：" + position);
                }
            });
        } else {
            holder.mLinRegion.setFocusable(false);
            holder.mTvStart.setFocusable(false);
            holder.mTvStart.setBackground(mContext.getResources().getDrawable(R.drawable.tv_backgrount));
            holder.mIvImg.setBackgroundResource(R.mipmap.iv_green_back);
            holder.mLinRegion.setBackgroundResource(R.drawable.login_bg_into);
            holder.mTvAccountName.setText("暂未进入");
        }

        if (mCheckBoxMap != null && mCheckBoxMap.containsKey(position)) {
            if (mapPosition != null) {
                for (Integer key : mapPosition.keySet()) {
                    Boolean isState = mapPosition.get(key);
                    if (isState != null) {
                        if (isState) {
                            mList.get(key).setIsState(0);
                            Log.d(TAG, "正常：" + mList.get(key).getIsState() + ",:" + mList.get(key).getRegion());
                        } else {
                            mList.get(key).setIsState(1);
                            Log.d(TAG, "异常：" + mList.get(key).getIsState() + ",:" + mList.get(key).getRegion());
                        }
                    }
                }
                if (mList.get(position).getIsState() == 0) {
                    refreshUi(holder);
                    holder.mTvNoState.setText(R.string.no_normal);
                    holder.mTvSee.setVisibility(View.GONE);
                    Log.d(TAG, "设置正常：" + mList.get(position).getIsState() + ",:" + mList.get(position).getRegion());
                } else if (mList.get(position).getIsState() == 1) {
                    refreshUi(holder);
                    holder.mTvNoState.setText(R.string.no_abnormal);
                    holder.mTvNoState.setTextColor(mContext.getResources().getColor(R.color.color_see));
                    holder.mTvSee.setVisibility(View.VISIBLE);
                    holder.mTvSee.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onSeeChilk != null) {
                                onSeeChilk.onItemSeeChilk();
                            }
                        }
                    });
                    Log.d(TAG, "设置异常：" + mList.get(position).getIsState() + ",:" + mList.get(position).getRegion());
                } else {
                    Log.d(TAG, "否则：" + mList.get(position).getRegion());

                }

                if (!mList.get(position).isRegionNum() && holder.mTvAccountName.getText()
                        .toString().trim().equals(mContext.getResources().getString(R.string.region_data))) {
                    mCount++;
                    mList.get(position).setRegionNum(true);
                    EventBus.getDefault().post(new RegionNumEventBus(mCount));
                    Log.d(TAG, "已完成区域数量：" + mCount);
                }
            }
        }
        holder.mTvSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSeeChilk != null) {
                    onSeeChilk.onItemSeeChilk();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 记录扫描到的设备
     * @param brt brt
     */
    public void setBrt(BRTBeacon brt) {
        this.mbrt = brt;
    }

    /**
     * 点击监听回调
     */
    private onItemListener onItemListener;

    /**
     * onItemListener
     */
    public interface onItemListener {
        /**
         * onItemListener
         * @param position position
         */
        void onItemListener(int position);
    }

    /**
     * setOnItemListener
     * @param onItemListener onItemListener
     */
    public void setOnItemListener(PatrolAdapter.onItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    /**
     * 用来记录用户是否取消弹窗
     * @param isSucces isSucces
     */
    public void setIsSucces(boolean isSucces) {
        this.mSuccess = isSucces;
    }

    /**
     * 用来存放异常状态
     * @param map map
     */
    public void setMapPosition(Map<Integer, Boolean> map) {
        this.mapPosition = map;
    }

    /**
     * 接收数据
     * @param listInfos listInfos
     */
    public void setDate(List<RegionListInfo> listInfos) {
        this.mList = listInfos;
    }

    /**
     * map
     *
     * @return map
     */
    public Map<Integer, Boolean> getMap() {
        return mCheckBoxMap;
    }

    /**
     * 点击查看按钮监听
     */
    private onSeeChilk onSeeChilk;

    /**
     * onSeeChilk
     */
    public interface onSeeChilk {
        /**
         * onItemSeeChilk
         */
        void onItemSeeChilk();
    }

    /**
     * setOnSeeChilk
     * @param onSeeChilk onSeeChilk
     */
    public void setOnSeeChilk(PatrolAdapter.onSeeChilk onSeeChilk) {
        this.onSeeChilk = onSeeChilk;
    }

    /**
     * viewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * mTvName
         */
        private final TextView mTvName;
        /**
         * mLinRegion
         */
        private final LinearLayout mLinRegion;
        /**
         * mIvImg
         */
        private final ImageView mIvImg;
        /**
         * mTvAccountName
         */
        private final TextView mTvAccountName;
        /**
         * mTvStart
         */
        private final CheckBox mTvStart;
        /**
         * mTvNoRegion
         */
        private final TextView mTvNoRegion;
        /**
         * mTvNoState
         */
        private final TextView mTvNoState;
        /**
         * mTvRegionNmae
         */
        private final TextView mTvRegionNmae;
        /**
         * mTvSee
         */
        private final TextView mTvSee;
        /**
         * mRelayout
         */
        private final RelativeLayout mRelayout;

        /**
         * ViewHolder
         * @param  itemView itemView
         */
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.tv_name);
            mLinRegion = itemView.findViewById(R.id.lin_region);
            mIvImg = itemView.findViewById(R.id.iv_img);
            mTvAccountName = itemView.findViewById(R.id.tv_account_name);
            mTvStart = itemView.findViewById(R.id.tv_start);
            mTvNoRegion = itemView.findViewById(R.id.tv_no_region);
            mTvNoState = itemView.findViewById(R.id.tv_no_state);
            mTvRegionNmae = itemView.findViewById(R.id.tv_region_name);
            mTvSee = itemView.findViewById(R.id.tv_see_item);
            mRelayout = itemView.findViewById(R.id.region_relayout);
        }
    }

    /**
     * 改变UI
     * @param holder holder
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void refreshUi(ViewHolder holder) {
        holder.mLinRegion.setFocusable(false);
        holder.mLinRegion.setBackgroundResource(R.drawable.region_bg);
        holder.mTvStart.setFocusable(false);
        holder.mTvStart.setBackground(mContext.getResources().getDrawable(R.drawable.tv_backgrount));
        holder.mIvImg.setBackgroundResource(R.mipmap.iv_user_name);
        holder.mTvAccountName.setText(R.string.region_data);
        holder.mTvAccountName.setTextColor(mContext.getResources().getColor(R.color.color_white));
        holder.mTvStart.setOnClickListener(null);
    }

}
