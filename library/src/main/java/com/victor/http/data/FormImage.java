package com.victor.http.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by Administrator on 2017/12/20.
 */

public class FormImage {
    public String token;
    public String tokentime;
    public String tokentype;
    public String imgname;
    //参数的名称
    private String mName ;
    //文件名
    private String mFileName ;
    //文件的 mime，需要根据文档查询
    public String mMime ;
    //需要上传的图片资源，因为这里测试为了方便起见，直接把 bigmap 传进来，真正在项目中一般不会这般做，而是把图片的路径传过来，在这里对图片进行二进制转换
    private Bitmap mBitmap ;
    public String imgPath;

    public FormImage(Bitmap mBitmap,String imgPath,String mime,String name) {
        this.mBitmap = mBitmap;
        this.imgPath = imgPath;
        this.mMime = mime;
        this.mName = name;
    }
    public FormImage(String imgPath,String mime) {
        this(null,imgPath,mime,"file");
    }

    public String getName() {
            return mName;
    //测试，把参数名称写死
//        return "uploadimg" ;
//        return "file" ;
    }

    public String getFileName() {
        if (!TextUtils.isEmpty(imgPath) && imgPath.contains("/")) {
            String[] datas = imgPath.split("/");
            return datas[datas.length - 1];
        }
        return "test.png";
    }
    //对图片进行二进制转换
    public byte[] getValue() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
        getBitmap().compress(Bitmap.CompressFormat.JPEG,80,bos) ;
        return bos.toByteArray();
    }
    //因为我知道是 png 文件，所以直接根据文档查的
    public String getMime() {
        return mMime;
    }

    private Bitmap getBitmap () {
        if (!TextUtils.isEmpty(imgPath)) {
            mBitmap = BitmapFactory.decodeFile(imgPath);
        }
        return mBitmap;
    }
}
