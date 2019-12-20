package com.yizutiyu.brightbeacon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brtbeacon.sdk.BRTBeacon;
import com.yizutiyu.brightbeacon.R;


import java.util.ArrayList;
import java.util.List;

/**
 * 扫描适配器
 */
public class BeaconViewAdapter extends RecyclerView.Adapter<BeaconViewAdapter.BeaconViewHolder> {
    /**
     * beaconList
     */
    private List<BRTBeacon> beaconList = new ArrayList<>();

    @NonNull
    @Override
    public BeaconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_device_info, parent, false);
        return new BeaconViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BeaconViewHolder holder, int position) {
        BRTBeacon beacon = beaconList.get(position);
        holder.bind(beacon);
    }

    @Override
    public int getItemCount() {
        return beaconList.size();
    }

    /**
     * 添加数据，并刷新
     * @param beacons beacons
     */
    public void replaceAll(List<BRTBeacon> beacons) {
        beaconList.clear();
        if (beacons != null) {
            beaconList.addAll(beacons);
        }
        notifyDataSetChanged();
    }

    /**
     * ViewHolder
     */
    public class BeaconViewHolder extends RecyclerView.ViewHolder {
        /**
         * beacon
         */
        private BRTBeacon beacon;
        /**
         * tvRssi
         */
        TextView tvRssi;
        /**
         * tvName
         */
        TextView tvName;
        /**
         * tvAddr
         */
        TextView tvAddr;
        /**
         * tvMajor
         */
        TextView tvMajor;
        /**
         * tvMinor
         */
        TextView tvMinor;
        /**
         * tvUuid
         */
        TextView tvUuid;
        /**
         * mLinear
         */
        private final LinearLayout mLinear;

        /**
         * BeaconViewHolder
         * @param itemView itemView
         */
        public BeaconViewHolder(View itemView) {
            super(itemView);
            tvRssi = itemView.findViewById(R.id.device_rssi);
            tvName = itemView.findViewById(R.id.device_name);
            tvAddr = itemView.findViewById(R.id.device_address);
            tvMajor = itemView.findViewById(R.id.tv_major);
            tvMinor = itemView.findViewById(R.id.tv_minor);
            tvUuid = itemView.findViewById(R.id.tv_uuid);
            mLinear = itemView.findViewById(R.id.item_linear);
        }

        /**
         * bind
         * @param beacon beacon
         */
        public void bind(BRTBeacon beacon) {
            this.beacon = beacon;
            updateView();
        }

        /**
         * 刷新UI
         */
        private void updateView() {
            if (beacon == null) {
                return;
            }
            tvRssi.setText(String.valueOf(beacon.getRssi()));
            tvName.setText(String.valueOf(beacon.getName()));
            tvAddr.setText(beacon.getMacAddress());
            tvMajor.setText(String.valueOf(beacon.getMajor()));
            tvMinor.setText(String.valueOf(beacon.getMinor()));
            tvUuid.setText(beacon.getUuid());
            mLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemViewListener != null) {
                        onItemViewListener.onItemListener(beacon.getMacAddress(), beacon.getName(), beacon);
                    }
                }
            });
        }
    }

    /**
     * 点击条目接口回调
     */
    private onItemViewListener onItemViewListener;

    /**
     * 定义接口
     */
    public interface onItemViewListener {
        /**
         * onItemListener
         * @param mAdress mAdress
         * @param name name
         * @param brtBeacon brtBeacon
         */
        void onItemListener(String mAdress, String name, BRTBeacon brtBeacon);
    }

    /**
     * setOnItemViewListener
     * @param onItemViewListener onItemViewListener
     */
    public void setOnItemViewListener(onItemViewListener onItemViewListener) {
        this.onItemViewListener = onItemViewListener;
    }

}
