package com.yizutiyu.brightbeacon.mvp.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yizutiyu.brightbeacon.R;
import com.yizutiyu.brightbeacon.adapter.ErrorPictureAdapter;
import com.yizutiyu.brightbeacon.base.BaseMvpActivity;
import com.yizutiyu.brightbeacon.info.PictureInfo;
import com.yizutiyu.brightbeacon.mvp.impl.RegionErrorPersenterImpl;
import com.yizutiyu.brightbeacon.mvp.uiinterface.RegionErrorUiinterface;
import com.yizutiyu.brightbeacon.utils.ContensUtils;
import com.yizutiyu.brightbeacon.utils.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.functions.Consumer;


/**
 * RegionErrorActivity
 */
public class RegionErrorActivity extends BaseMvpActivity<RegionErrorPersenterImpl> implements RegionErrorUiinterface {
    private static final String TAG = "RegionErrorActivity";
    @Inject
    RegionErrorPersenterImpl impl;
    @BindView(R.id.iv_header_back)
    ImageView ivHeaderBack;
    @BindView(R.id.relay_back)
    RelativeLayout relayBack;
    @BindView(R.id.tv_back_header)
    TextView tvBackHeader;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.iv_lien)
    View ivLien;
    @BindView(R.id.ed_content)
    EditText edContent;
    @BindView(R.id.ll_error)
    LinearLayout llError;
    @BindView(R.id.iv_error)
    View ivError;
    @BindView(R.id.iv_top)
    View ivTop;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    /**
     * mList
     */
    private List<Integer> mList;
    /**
     * mImg ,从本地获取图片展示
     */
    private ImageView mImg;
    /**
     * mPicture
     */
    private TextView mPicture;
    /**
     * mCamera
     */
    private TextView mCamera;
    /**
     * mCancel
     */
    private TextView mCancel;
    /**
     * 选择图片
     */
    private static final int RC_CHOOSE_PHOTO = 100;
    /**
     * 选择相机
     */
    private static final int RC_CHOOSE_CAMERA = 101;
    /**
     * 裁剪回调
     */
    private static final int CROP_SMALL_PICTURE = 102;//裁剪
    /**
     * 小米裁剪回调
     */
    private static final int CROP_SMALL_PICTURE_MIUI = 103;//小米裁剪
    /**
     * file 相机回调的路径
     */
    private File cameraSavePath;
    /**
     * tempUri
     */
    private Uri tempUri;
    /**
     * filePath  图片回调的路径
     */
    private String filePath;

    /**
     * uritempFile
     */
    private Uri uritempFile;
    /**
     * path
     */
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/test/" + System.currentTimeMillis() + ".jpg";
    /**
     * mGalleryFile
     */
    private File mGalleryFile = new File(path, "IMAGE_GALLERY_NAME.jpg");//相册的File对象
    /**
     * 上传图片集合
     */
    private List<String> mMagList = new ArrayList<>();
    /**
     * 判断上传图片是否请求成功
     */
    private boolean isRequestSuccess;

    // 图片选择器显示的数量
    private int maxSelectNum = 9;

//    // 存放图片的集合
//    private List<LocalMedia> selectList = new ArrayList<>();
//    private PopupWindow popupWindow;
//    private GridImageAdapter mAdapter;

    @Override
    protected RegionErrorPersenterImpl initInjector() {
        component.inject(this);
        return impl;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_region_error;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        impl.setUiinterface(this);

        tvBackHeader.setText("巡检异常上报");

        // 保存拍照路径
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/test/" + System.currentTimeMillis() + ".jpg");
//        cameraSavePath.getParentFile().mkdirs();


        //点击返回
        ivHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mList = new ArrayList<>();
        mList.add(R.mipmap.ic_tianjia);
        mList.add(R.mipmap.ic_tianjia);
        mList.add(R.mipmap.ic_tianjia);
        mList.add(R.mipmap.ic_tianjia);
        mList.add(R.mipmap.ic_tianjia);
        mList.add(R.mipmap.ic_tianjia);
//
//        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
//        recycleView.setLayoutManager(manager);
//        mAdapter = new GridImageAdapter(this, onAddPicClickListener);
//        mAdapter.setList(selectList);
//        mAdapter.setSelectMax(maxSelectNum);
//        recycleView.setAdapter(mAdapter);
//        mAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position, View v) {
//                if (selectList.size() > 0) {
//                    LocalMedia media = selectList.get(position);
//                    String pictureType = media.getPictureType();
//                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
//                    switch (mediaType) {
//                        case 1:
//                            // 预览图片 可自定长按保存路径
//                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
//                            PictureSelector.create(RegionErrorActivity.this).externalPicturePreview(position, selectList);
//                            break;
//                        case 2:
//                            // 预览视频
//                            PictureSelector.create(RegionErrorActivity.this).externalPictureVideo(media.getPath());
//                            break;
//                        case 3:
//                            // 预览音频
//                            PictureSelector.create(RegionErrorActivity.this).externalPictureAudio(media.getPath());
//                            break;
//                    }
//                }
//            }
//        });

        ErrorPictureAdapter mAdapter = new ErrorPictureAdapter(R.layout.picture_layout_item, mList);
        recycleView.setLayoutManager(new GridLayoutManager(this, 3));
        recycleView.setAdapter(mAdapter);

        mAdapter.setOnItemChilkLisener(new ErrorPictureAdapter.onItemChilkLisener() {
            @Override
            public void OnLisener(ImageView imageView) {
                mImg = imageView;
                getPopup();
            }
        });

        // 点击提交
        subscribeClick(tvSubmit, o -> {
            if (isRequestSuccess) {
                if (mMagList != null) {
                    try {
//                        ContensUtils.setPictureList("pilis", mMagList, config);
//                        List<String> pilist = ContensUtils.getPictureDataList("pilis", config);
//                        Log.d("song","32333取出:"+pilist);
                        // 将存入的图片集合传给 巡检详情页
                        Intent intent = new Intent();
                        intent.putStringArrayListExtra("imgList", (ArrayList<String>) mMagList);
                        setResult(2, intent);
                        isRequestSuccess = false;
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void init() {

    }

    @Override
    public void updataimgSuccess(PictureInfo pictureInfo) {
        mMagList.add(pictureInfo.getImage());
        isRequestSuccess = true;
    }


    // 弹出选择相册或相机弹窗
    private void getPopup() {
        View partView = View.inflate(this, R.layout.activity_region_error, null);
        View view = View.inflate(this, R.layout.popup_bottom_layout, null);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // 点击空白处隐藏弹窗
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        mPicture = view.findViewById(R.id.tv_picture);
        mCamera = view.findViewById(R.id.tv_camera);
        mCancel = view.findViewById(R.id.tv_cancel);
        mPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContensUtils.checkAndApplyfPermissionActivity(RegionErrorActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, RC_CHOOSE_PHOTO)) {
                    choosePhoto();
                }
            }
        });

        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContensUtils.checkAndApplyfPermissionActivity(RegionErrorActivity.this, new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        RC_CHOOSE_CAMERA)) {
                    startCamera();
                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
            }
        });
        //设置底部弹出
        popupWindow.showAtLocation(partView, Gravity.BOTTOM, 0, 0);
    }

    //跳转相册页面
    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果大于等于7.0使用FileProvider
            Uri uriForFile = FileProvider.getUriForFile(this, "com.scc.customview.fileprovider", mGalleryFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivityForResult(intent, RC_CHOOSE_PHOTO);
    }

    //打开相机
    public void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //改变Uri  com.xykj.customview.fileprovider注意和xml中的一致
        tempUri = FileProvider.getUriForFile(this, "com.scc.customview.fileprovider", cameraSavePath);
        //添加权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        startActivityForResult(intent, RC_CHOOSE_CAMERA);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            //相册回调
            case RC_CHOOSE_PHOTO:
                choosePhoto();
                break;
            case RC_CHOOSE_CAMERA:
                if (permissions[0].equals(Manifest.permission.CAMERA)) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "===========权限回调---用户同意了");
                        startCamera();
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String photoPath;
        switch (requestCode) {
            case RC_CHOOSE_PHOTO: // 相册回调请求接口
                if (data != null) {
                    Uri uri = data.getData();
                    filePath = FileUtil.getFilePathByUri(this, uri);
                    // 在控件上进行显示
                    if (!TextUtils.isEmpty(filePath)) {
                        Glide.with(this).load(filePath).into(mImg);
                    }
                    impl.updateImg(this, filePath);
                }

                break;
            case RC_CHOOSE_CAMERA: // 相机回调
                photoPath = tempUri.getEncodedPath(); // 获取相机图片
                photoClip(tempUri);  // 调用裁剪方法
                break;
            case CROP_SMALL_PICTURE: // 普通手机裁剪回调
                if (data != null) {
                    setImageToView(data);
                }
                break;
            case CROP_SMALL_PICTURE_MIUI:  // 小米适配----裁剪之后intent系统获取不到，只能显示裁剪之后的图片，而不是从intent中获取
                setImageMiuiToView();
                break;
        }



//        List<LocalMedia> images;
//        if (resultCode == RESULT_OK) {
//            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调
//
//                images = PictureSelector.obtainMultipleResult(data);
//                selectList.addAll(images);
//
//                //selectList = PictureSelector.obtainMultipleResult(data);
//
//                // 例如 LocalMedia 里面返回三种path
//                // 1.media.getPath(); 为原图path
//                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
//                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
//                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                mAdapter.setList(selectList);
//                mAdapter.notifyDataSetChanged();
//            }
//        }
    }


    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    private void photoClip(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        if (Build.MODEL.contains("MI") || Build.MODEL.contains("Redmi 6A")) {
            uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/test/" + System.currentTimeMillis() + ".jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            startActivityForResult(intent, CROP_SMALL_PICTURE_MIUI);
            return;
        }
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }


    /* * 保存裁剪之后的图片数据
     *
     * @param
     */
    protected void setImageToView(Intent data) {
        Log.d("song", "huisdsdf：" + data);
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
//            Utils utils = new Utils();
//            photo = utils.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了
            mImg.setImageBitmap(photo);
            File file = FileUtil.getFile(photo);
            Log.d("song", "返回图片：" + file.toString() + ",:" + file.getName());
            impl.updateImg(this, file.toString());
        }
    }

    /* * 保存裁剪之后的图片数据----适配小米
     *
     * @param
     */
    protected void setImageMiuiToView() {
        //将Uri图片转换为Bitmap
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
            mImg.setImageBitmap(bitmap);
            File file = FileUtil.getFile(bitmap);
            impl.updateImg(this, file.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
