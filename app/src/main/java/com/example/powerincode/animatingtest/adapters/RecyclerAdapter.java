package com.example.powerincode.animatingtest.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.powerincode.animatingtest.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    public interface RecyclerAdapterEvent {
        void onHolderTap(View holder, String color);
    }

    private final Context mContext;
    private final int mCount;
    private RecyclerAdapterEvent mEventListener;

    public RecyclerAdapter(Context context, int count) {
        mContext = context;
        mCount = count;
    }

    public void setEventListener(RecyclerAdapterEvent e) {
        mEventListener = e;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_survey, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(position % 2 == 0);
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final View mAvatarView;
        private String mColor;

        public MyViewHolder(final View itemView) {
            super(itemView);

            mAvatarView = itemView.findViewById(R.id.v_avatar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mEventListener != null) {
                        mEventListener.onHolderTap(mAvatarView, mColor);
                    }
                }
            });
        }

        public void bind(boolean isOdd) {
            mColor = isOdd ? "#385972" : "#ff9800";
            GradientDrawable background = (GradientDrawable) mAvatarView.getBackground();
            background.mutate();
            background.setColor(Color.parseColor(mColor));
        }
    }
}