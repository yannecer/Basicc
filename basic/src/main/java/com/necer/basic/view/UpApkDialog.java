package com.necer.basic.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.necer.basic.R;
import com.necer.basic.base.MyLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UpApkDialog extends AlertDialog implements View.OnClickListener {


    private String downUrl;
    private TextView tvUpData;
    private TextView tvPer;
    private ProgressBar pb;
    LinearLayout llPb;

    Disposable disposable;


    int fileSize;
    String filePath;


    public UpApkDialog(@NonNull Context context, String url, String desc) {
        super(context, R.style.AlertDialogStyle);
        this.downUrl = url;

        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_up_data, null);
        setView(inflate);

        setCanceledOnTouchOutside(false);

        TextView tvMessage = inflate.findViewById(R.id.tv_message);
        tvUpData = inflate.findViewById(R.id.tv_up_data);
        pb = inflate.findViewById(R.id.down_pb);
        tvPer = inflate.findViewById(R.id.tv_per);
        llPb = inflate.findViewById(R.id.ll_pb);

        tvUpData.setOnClickListener(this);
        tvMessage.setText(desc);

    }

    @Override
    public void onClick(View view) {

        downApk();
        tvUpData.setVisibility(View.GONE);
        llPb.setVisibility(View.VISIBLE);
    }


    private void downApk() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                URL u = new URL(downUrl);
                URLConnection conn = u.openConnection();
                InputStream is = conn.getInputStream();
                fileSize = conn.getContentLength();
                int downloadSize = 0;
                String path = getContext().getExternalCacheDir().getAbsolutePath();
                File downFile = new File(path, "/" + System.currentTimeMillis() + ".apk");
                if (!downFile.getParentFile().exists()) {
                    downFile.getParentFile().mkdirs();
                }
                filePath = downFile.getAbsolutePath();
                FileOutputStream fos = new FileOutputStream(downFile);
                byte[] bytes = new byte[1024];
                int len = -1;
                while ((len = is.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                    downloadSize += len;
                    fos.flush();
                    e.onNext(downloadSize);
                }
                is.close();
                fos.close();
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        pb.setMax(fileSize);
                        pb.setProgress(integer);
                        tvPer.setText(Math.round(integer * 100 / fileSize) + "%");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                       // MyLog.d("filePath::" + filePath);
                        if (onCompleteListener != null) {
                            onCompleteListener.onComplete(filePath);
                        }

                        dismiss();
                    }
                });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        disposable.dispose();
    }

    private OnCompleteListener onCompleteListener;

    public UpApkDialog setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
        return this;
    }
    public interface OnCompleteListener{
        void onComplete(String filePath);
    }
}
