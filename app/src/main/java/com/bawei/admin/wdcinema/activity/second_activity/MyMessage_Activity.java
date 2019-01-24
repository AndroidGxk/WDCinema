package com.bawei.admin.wdcinema.activity.second_activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bawei.admin.wdcinema.activity.thirdly_activity.UpdateNameActivity;
import com.bawei.admin.wdcinema.core.ResultInfe;
import com.bawei.admin.wdcinema.core.utils.Constant;
import com.bawei.admin.wdcinema.core.utils.GetRealPath;
import com.bawei.admin.wdcinema.presenter.UpdateHeadPresenter;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class MyMessage_Activity extends AppCompatActivity implements CustomAdapt, View.OnClickListener, ResultInfe {
    @BindView(R.id.back_image)
    ImageView back_image;
    @BindView(R.id.myheader)
    SimpleDraweeView myheader;
    private int SELECT_PICTURE = 1; // 从图库中选择图片
    private int SELECT_CAMER = 0; // 用相机拍摄照片
    private Bitmap bmp;
    private UpdateHeadPresenter updateHeadPresenter;
    private File file;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message_);
        ButterKnife.bind(this);
        findViewById(R.id.update_name).setOnClickListener(this);
        findViewById(R.id.update_mail).setOnClickListener(this);
        findViewById(R.id.update_sex).setOnClickListener(this);
        updateHeadPresenter = new UpdateHeadPresenter(this);
        sp = getSharedPreferences("login", MODE_PRIVATE);
    }

    @OnClick(R.id.myheader)
    public void myheader() {
        showChoosePhotoDialog();
    }

    @OnClick(R.id.go_updapwd)
    public void go_updapwd() {
        startActivity(new Intent(MyMessage_Activity.this, UpdatePwdActivity.class));

    }

    @OnClick(R.id.back_image)
    public void back_image() {
        finish();
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            Bitmap bitmap = data.getParcelableExtra("data");
            Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
            myheader.setImageURI(uri);
            String realPathFromUri = GetRealPath.getRealPathFromUri(MyMessage_Activity.this, uri);
            file = new File(realPathFromUri);
            String seesionId = sp.getString("sessionId", "");
            int userId = sp.getInt("userId", 0);
            File file = null;
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = this.managedQuery(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filepath = cursor.getString(column_index);
            file = new File(filepath);
            // TODO: 2019/1/24 上传头像
            updateHeadPresenter.request(userId, seesionId, "http://mobile.bwstudent.com/images/movie/head_pic/2019-01-24/20190124132033.jpg");
            return;
        }
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try {
                if (bmp != null) {
                    bmp.recycle();
                    bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));
                    file = new File(uri.toString());
                    String seesionId = sp.getString("seesionId", "");
                    int userId = sp.getInt("userId", 0);
                    updateHeadPresenter.request(userId, seesionId, file);
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            myheader.setImageURI(uri);
        } else {
            Toast.makeText(MyMessage_Activity.this, "选择图片失败,请重新选择", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 相机相册选择照片弹框
     */
    private void showChoosePhotoDialog() {
        CharSequence[] items = {"相册", "相机"};
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("选择图片来源")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        if (which == SELECT_PICTURE) {
                            if (ContextCompat.checkSelfPermission(MyMessage_Activity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                                    ContextCompat.checkSelfPermission(MyMessage_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                                    ) {
                                // 申请权限
                                ActivityCompat.requestPermissions(MyMessage_Activity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQ_PERM_CAMERA);
                                return;
                            }
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.addCategory("android.intent.category.DEFAULT");
                            startActivityForResult(intent, SELECT_CAMER);
                        } else {
                            Intent intent = new Intent(
                                    Intent.ACTION_GET_CONTENT);
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            intent.setType("image/*");
                            startActivityForResult(intent, SELECT_PICTURE);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                }).create();
        dialog.show();
    }

    /**
     * 修改信息跳转
     *
     * @param v
     */
    // TODO: 2019/1/24 修改信息 
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_name:
                startActivity(new Intent(MyMessage_Activity.this, UpdateNameActivity.class));
                break;
        }
    }

    @Override
    public void success(Object data) {
        Toast.makeText(this, "chengg", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errors(Throwable throwable) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateHeadPresenter.unBind();
    }
}
