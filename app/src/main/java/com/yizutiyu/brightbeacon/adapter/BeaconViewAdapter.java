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

public class BeaconViewAdapter extends RecyclerView.Adapter<BeaconViewAdapter.BeaconViewHolder> {

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

    public void replaceAll(List<BRTBeacon> beacons) {
        beaconList.clear();
        if (beacons != null) {
            beaconList.addAll(beacons);
        }
        notifyDataSetChanged();
    }

    public class BeaconViewHolder extends RecyclerView.ViewHolder {

        private BRTBeacon beacon;

        TextView tvRssi;
        TextView tvName;
        TextView tvAddr;
        TextView tvMajor;
        TextView tvMinor;
        TextView tvUuid;
        private final LinearLayout mLinear;

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

        public void bind(BRTBeacon beacon) {
            this.beacon = beacon;
            updateView();
        }

        private void updateView() {
            if (beacon == null)
                return;
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

    //点击条目接口回调
    private onItemViewListener onItemViewListener;

    public interface onItemViewListener {
        void onItemListener(String mAdress, String name, BRTBeacon brtBeacon);
    }

    public void setOnItemViewListener(onItemViewListener onItemViewListener) {
        this.onItemViewListener = onItemViewListener;
    }

}
