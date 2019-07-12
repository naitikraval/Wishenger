package com.wishenger.android.wishengerr;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MsgList extends ArrayAdapter<SaveMsgModelClass> {

    private Activity context;
    private List<SaveMsgModelClass> msgList;

    public MsgList(@NonNull Activity context, List<SaveMsgModelClass> msgList) {
        super(context, R.layout.saved_msg_listview, msgList);
        this.context = context;
        this.msgList = msgList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();

        View listViewItem = layoutInflater.inflate(R.layout.saved_msg_listview,null,true);

        TextView viewName = (TextView) listViewItem.findViewById(R.id.show_name);
        TextView viewMsg = (TextView) listViewItem.findViewById(R.id.show_msg);
        TextView viewPhoneNumber = (TextView) listViewItem.findViewById(R.id.show_phone_no);
        TextView viewDate = (TextView) listViewItem.findViewById(R.id.show_msg_date);
        TextView viewTime = (TextView) listViewItem.findViewById(R.id.show_msg_time);
        TextView viewid = (TextView) listViewItem.findViewById(R.id.idno);
        TextView view12hrTime = (TextView) listViewItem.findViewById(R.id.hr12Time);

        SaveMsgModelClass msgModelClass  =msgList.get(position);
        viewName.setText(" "+msgModelClass.getName());
        viewMsg.setText(" "+msgModelClass.getMsg());
        viewPhoneNumber.setText(" "+msgModelClass.getContact_no());
        viewDate.setText(" "+msgModelClass.getDate());
        viewTime.setText(" "+msgModelClass.getTime());
        viewid.setText(" "+msgModelClass.get_id());
        view12hrTime.setText(" "+msgModelClass.get_hr12Time());
        return listViewItem;

    }


}
