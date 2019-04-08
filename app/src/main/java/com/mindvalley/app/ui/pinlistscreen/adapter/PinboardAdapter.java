package com.mindvalley.app.ui.pinlistscreen.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mindvalley.app.R;
import com.mindvalley.app.data.model.api.Pinboard;
import com.mindvalley.asyncloader.AsyncLoader;
import com.mindvalley.asyncloader.callback.ImageRequestListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: shrik
 * Created on 4/8/2019
 */
public class PinboardAdapter extends RecyclerView.Adapter<PinboardAdapter.ViewHolder> {
    private Context mContext;

    private List<Pinboard> mDataList;

    public PinboardAdapter(Context context, List<Pinboard> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_pinboard_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Pinboard pinboard = mDataList.get(position);

        holder.tvTitle.setText(pinboard.getUser().getName());

        holder.pbLoadImage.setVisibility(View.VISIBLE);

        AsyncLoader.get()
                .displayImage(pinboard.getUser().getProfileImage().getLarge(),
                        holder.ivThumb,
                        new ImageRequestListener() {
                            @Override
                            public void onLoadFailed(String errorMessage) {
                                holder.pbLoadImage.setVisibility(View.GONE);
                            }

                            @Override
                            public void onResourceReady(Bitmap bitmap) {
                                holder.pbLoadImage.setVisibility(View.GONE);
                            }
                        });


    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.iv_thumb)
        ImageView ivThumb;

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.pb_load_image)
        ProgressBar pbLoadImage;

        public ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }


}
