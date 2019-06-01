package com.mfk.donotdistrub;



import android.os.Parcel;
import android.os.Parcelable;


public class Contact implements Parcelable {

    public String id,name,phone,label,message;
    int busy,dayto,dayfrom,timeto,timefrom;

    Contact(String id, String name,String phone,String label,int busy,int dayto,int timeto,int timefrom,int dayfrom,String message){
        this.id=id;
        this.name=name;
        this.phone=phone;
        this.label=label;
        this.busy=busy;
        this.dayto=dayto;
        this.dayfrom=dayfrom;
        this.timeto=timeto;
        this.timefrom=timefrom;
        this.message=message;
    }

    protected Contact(Parcel in) {
        id = in.readString();
        name = in.readString();
        phone = in.readString();
        label = in.readString();
        busy=in.readInt();
        dayfrom=in.readInt();
        dayto=in.readInt();
        timeto=in.readInt();
        timefrom=in.readInt();
        message=in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public String toString()
    {
        return name+" | "+label+" : "+phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(label);
        dest.writeInt(busy);
        dest.writeInt(dayto);
        dest.writeInt(dayfrom);
        dest.writeInt(timeto);
        dest.writeInt(timefrom);
        dest.writeString(message);
    }
}