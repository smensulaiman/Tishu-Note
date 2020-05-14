package com.ekattorit.tishunote.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class NoteModel extends RealmObject implements Parcelable {

    private String name;
    private String description;
    private int count;
    private int target;
    private String date;
    private boolean isVisible;

    public NoteModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public static Creator<NoteModel> getCREATOR() {
        return CREATOR;
    }

    protected NoteModel(Parcel in) {
        name = in.readString();
        description = in.readString();
        count = in.readInt();
        target = in.readInt();
        date = in.readString();
        isVisible = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(count);
        dest.writeInt(target);
        dest.writeString(date);
        dest.writeByte((byte) (isVisible ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoteModel> CREATOR = new Creator<NoteModel>() {
        @Override
        public NoteModel createFromParcel(Parcel in) {
            return new NoteModel(in);
        }

        @Override
        public NoteModel[] newArray(int size) {
            return new NoteModel[size];
        }
    };
}
