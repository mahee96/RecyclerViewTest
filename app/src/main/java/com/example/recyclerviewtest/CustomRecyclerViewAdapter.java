package com.example.recyclerviewtest;

import android.app.LauncherActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomViewHolder> {

    private int mItemCount ;
    private static int counter = 0;
    private ListItemClickListener mListItemClickListener;

    CustomRecyclerViewAdapter(int viewCount, ListItemClickListener listItemClickListener){
        this.mItemCount = viewCount;
        this.mListItemClickListener = listItemClickListener;    //register any handler's instances to be invoked.
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int resID){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_view_layout,viewGroup,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.setData(++counter+"");
    }

    @Override
    public int getItemCount() {
        return this.mItemCount;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTextView;

        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_msg);
            mTextView.setOnClickListener(this); //register(subscribe) self to TextView's Onclick events
        }
        
        void setData(String msg){
            mTextView.setText(msg);
        }

        @Override
        public void onClick(View v){
            mListItemClickListener.ListItemOnClickHandler(this.getAdapterPosition());
            // used "this" as context above since getAdapterPosition is a method of the ViewHolder.
        }
    }

    // Interface used by this Adapter to invoke all the handlers(mainActivity) which Implement this interface.
    public interface ListItemClickListener{
        void ListItemOnClickHandler(int currentItemIndex);
    }
}
