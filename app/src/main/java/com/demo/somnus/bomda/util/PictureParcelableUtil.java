package com.demo.somnus.bomda.util;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Somnus on 2018/4/12.
 * 把 BmobFile 封装成 Parcelable
 * 工具类
 */

public class PictureParcelableUtil implements Parcelable {

    private BmobFile picture;

    public PictureParcelableUtil(BmobFile picture) {
        this.picture = picture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) { //按顺序写
        dest.writeSerializable(picture);
    }

    public static final Parcelable.Creator<PictureParcelableUtil> CREATOR = new Parcelable.Creator<PictureParcelableUtil>() {
        @Override
        public PictureParcelableUtil createFromParcel(Parcel source) {
            return new PictureParcelableUtil((BmobFile) source.readSerializable()); //按顺序读
        }

        @Override
        public PictureParcelableUtil[] newArray(int size) {
            return new PictureParcelableUtil[size];
        }
    };

    public BmobFile getPicture() {
        return picture;
    }
}
