package com.victor.http.module;

import com.victor.http.data.FormImage;
import com.victor.http.data.UpLoadParm;
import com.victor.http.interfaces.OkHttpListener;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttpPostImgRequest<T> extends OkHttpMethod<T> {

    /**
     * 请求格式
     */
    private MediaType mMediaType = MediaType.parse("image/png");

    /**
     * 请求体
     */
    private RequestBody mRequestBody;

    private UpLoadParm upLoadParm;

    public OkHttpPostImgRequest(String url, UpLoadParm upLoadParm, OkHttpClient okHttpClient) {
        super(url,okHttpClient,upLoadParm.headers);
        this.upLoadParm = upLoadParm;
    }

    public OkHttpPostImgRequest(String url, Class<T> clazz,
                                UpLoadParm upLoadParm, OkHttpClient okHttpClient, OkHttpListener<T> listener) {
        this(url,upLoadParm,okHttpClient);
        mClass = clazz;
        requestUrl = url;
        mListener = listener;
    }

    @Override
    protected Request toRequest() {
        mRequestBody = getRequestBody(mMediaType, mParm);
        return getRequestBuilder().url(url).post(mRequestBody).build();
    }

    /**
     * 构造请求体
     *
     * @return
     */
    private RequestBody getRequestBody(MediaType type, String content) {
        if (upLoadParm == null) {
            if (mRequestBody == null) {
                mRequestBody = RequestBody.create(type, content);
            }
        }
        if (upLoadParm.imgs != null && upLoadParm.imgs.size() > 0) {
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            //创建MultipartBody.Builder，用于添加请求的数据
            for(FormImage img : upLoadParm.imgs) {
                File file = new File(img.imgPath); //生成文件
                //根据文件的后缀名，获得文件类型
                String fileType = getMimeType(file.getName());
                builder.addFormDataPart( //给Builder添加上传的文件
                        img.getName(),  //请求的名字
                        img.getFileName(), //文件的文字，服务器端用来解析的
                        RequestBody.create(MediaType.parse(fileType), file) //创建RequestBody，把上传的文件放入
                );
            }
            mRequestBody = builder.build();
        }

        return mRequestBody;
    }

    /**
     * 获取文件MimeType
     *
     * @param filename 文件名
     * @return
     */
    private static String getMimeType(String filename) {
        FileNameMap filenameMap = URLConnection.getFileNameMap();
        String contentType = filenameMap.getContentTypeFor(filename);
        if (contentType == null) {
            contentType = "application/octet-stream"; //* exe,所有的可执行程序
        }
        return contentType;
    }
}
