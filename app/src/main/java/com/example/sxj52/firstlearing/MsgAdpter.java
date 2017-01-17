package com.example.sxj52.firstlearing;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sxj52 on 2017/1/15.
 */

public class MsgAdpter extends  RecyclerView.Adapter<MsgAdpter.ViewHoler> {
    private List<Msg> mMsgs;

    public MsgAdpter(List<Msg> msgs) {
        mMsgs = msgs;
    }

    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.msg_item,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        Msg msg=mMsgs.get(position);
        if(msg.getType()==Msg.TYPE_RE){
            holder.msg_left.setVisibility(View.VISIBLE);
            holder.msg_right.setVisibility(View.GONE);
            holder.text_left.setText(msg.getContext());
        }
        else if(msg.getType()==Msg.TYPE_CO){
            holder.msg_right.setVisibility(View.VISIBLE);
            holder.msg_left.setVisibility(View.GONE);
            holder.text_right.setText(msg.getContext());
        }

    }

    @Override
    public int getItemCount() {
        return mMsgs.size();
    }
    public class ViewHoler extends RecyclerView.ViewHolder {
        private LinearLayout msg_left;
        private LinearLayout msg_right;
        private TextView text_left;
        private TextView text_right;
        public ViewHoler(View itemView) {
            super(itemView);
            msg_left= (LinearLayout) itemView.findViewById(R.id.msg_left);
            msg_right= (LinearLayout) itemView.findViewById(R.id.msg_right);
            text_left= (TextView) itemView.findViewById(R.id.text_left);
            text_right= (TextView) itemView.findViewById(R.id.text_right);
        }
    }
}
