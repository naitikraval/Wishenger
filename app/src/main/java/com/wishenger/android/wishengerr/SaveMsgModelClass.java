package com.wishenger.android.wishengerr;


public class SaveMsgModelClass {

    String msgId;
    String name;
    String msg;
    String date;
    String time;
    String contact_no;
    String _id;
    String _hr12Time;


    public SaveMsgModelClass() {

    }

    public SaveMsgModelClass(String msgId, String name, String msg, String date, String time, String contact_no, String _id,String _hr12Time) {
        this.msgId = msgId;
        this.name = name;
        this.msg = msg;
        this.date = date;
        this.time = time;
        this.contact_no = contact_no;
        this._id=_id;
        this._hr12Time=_hr12Time;
    }


    public String getMsgId() { return msgId; }
    public String getName() {
        return name;
    }

    public String getMsg() {
        return msg;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getContact_no() {
        return contact_no;
    }

    public  String get_id() {return _id;}

    public  String get_hr12Time() {return _hr12Time;}



}

