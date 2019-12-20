package com.yizutiyu.brightbeacon.mvp.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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
import com.iceteck.silicompressorr.SiliCompressor;
import com.yizutiyu.brightbeacon.R;
import com.yizutiyu.brightbeacon.adapter.ErrorPictureAdapter;
import com.yizutiyu.brightbeacon.base.BaseMvpActivity;
import com.yizutiyu.brightbeacon.info.PictureInfo;
import com.yizutiyu.brightbeacon.info.VideoInfo;
import com.yizutiyu.brightbeacon.mvp.impl.RegionErrorPersenterImpl;
import com.yizutiyu.brightbeacon.mvp.uiinterface.RegionErrorUiinterface;
import com.yizutiyu.brightbeacon.utils.ContensUtils;
import com.yizutiyu.brightbeacon.utils.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;


/**
 * RegionErrorActivity
 */
public class RegionErrorActivity extends BaseMvpActivity<RegionErrorPersenterImpl> implements RegionErrorUiinterface {
    /**
     * TAG
     */
    private static final String TAG = "RegionErrorActivity";
    /**
     * impl
     */
    @Inject
    RegionErrorPersenterImpl impl;
    /**
     * impl
     */
    @BindView(R.id.iv_header_back)
    ImageView ivHeaderBack;
    /**
     * relayBack
     */
    @BindView(R.id.relay_back)
    RelativeLayout relayBack;
    /**
     * tvBackHeader
     */
    @BindView(R.id.tv_back_header)
    TextView tvBackHeader;
    /**
     * tvDescription
     */
    @BindView(R.id.tv_description)
    TextView tvDescription;
    /**
     * ivLien
     */
    @BindView(R.id.iv_lien)
    View ivLien;
    /**
     * edContent
     */
    @BindView(R.id.ed_content)
    EditText edContent;
    /**
     * llError
     */
    @BindView(R.id.ll_error)
    LinearLayout llError;
    /**
     * ivError
     */
    @BindView(R.id.iv_error)
    View ivError;
    /**
     * ivTop
     */
    @BindView(R.id.iv_top)
    View ivTop;
    /**
     * tvPhone
     */
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    /**
     * recycleView
     */
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    /**
     * tvSubmit
     */
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    /**
     * mList
     */
    private List<Integer> mList;
    /**
     * 视频集合
     */
    private List<String> mVideoList;
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
    private static final int CROP_SMALL_PICTURE = 102; //裁剪
    /**
     * 小米裁剪回调
     */
    private static final int CROP_SMALL_PICTURE_MIUI = 103; //小米裁剪
    /**
     * 选择录制视频
     */
    private static final int RC_CHOOSE_VIDEO = 104;
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
    private File mGalleryFile = new File(path, "IMAGE_GALLERY_NAME.jpg"); //相册的File对象
    /**
     * 上传图片集合
     */
    private List<String> mMagList = new ArrayList<>();
    /**
     * 判断上传图片是否请求成功
     */
    private boolean isRequestSuccess;
    /**
     * 是否点击选择图片
     */
    private boolean isRequestPicture;
    /**
     * 是否点击选择录制视频
     */
    private boolean isRequestVideo;

    /**
     * 图片选择器显示的数量
     */
    private int maxSelectNum = 9;
    /**
     * 录制视频
     */
    private TextView mVideo;
    /**
     * 录制视频返回的路径
     */
    private String temp;
    /**
     * 录制视频存储的路径
     */
    private File mVideoFile;
    /**
     * 巡检异常的适配器
     */
    private ErrorPictureAdapter mAdapter;
    /**
     * 视频播放器（MP4）
     */
    private JZVideoPlayerStandard mVideoView;
    /**
     * 录制视频上传成功标志
     */
    private boolean isVideoResult;
    /**
     * 视频录制压缩后的视频路径，以便于上传到服务器
     */
    private String comPressPath;

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
        mVideoList = new ArrayList<>();
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

        mAdapter = new ErrorPictureAdapter(this, mList);
        recycleView.setLayoutManager(new GridLayoutManager(this, 3));
        recycleView.setAdapter(mAdapter);

        mAdapter.setOnItemChilkLisener(new ErrorPictureAdapter.onItemChilkLisener() {
            @Override
            public void OnLisener(ImageView imageView, JZVideoPlayerStandard videoView) {
                mImg = imageView;
                mVideoView = videoView;
                getPopup();
            }
        });

        // 点击提交
        subscribeClick(tvSubmit, o -> {
            if (isRequestSuccess || isVideoResult) {
                if (mMagList != null || mVideoList != null) {
                    try {
//                        ContensUtils.setPictureList("pilis", mMagList, config);
//                        List<String> pilist = ContensUtils.getPictureDataList("pilis", config);
//                        Log.d("song","32333取出:"+pilist);
                        if ((isRequestSuccess && isVideoResult) || (isRequestPicture
                                && isRequestSuccess && !isRequestVideo)
                                || (isRequestVideo && isVideoResult && !isRequestPicture)) {
                            // 将存入的图片集合传给 巡检详情页
                            Intent intent = new Intent();
                            intent.putStringArrayListExtra("imgList", (ArrayList<String>) mMagList);
                            intent.putStringArrayListExtra("videoList", (ArrayList<String>) mVideoList);
                            setResult(2, intent);
                            isRequestSuccess = false; // 上传图片成功
                            isVideoResult = false; // 上传视频成功
                            isRequestPicture = false; // 点击选择图片的标志
                            isRequestVideo = false; // 点击选择录制视频的标志
                            finish();
                        }
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

    /**
     * 图片上传成功
     *
     * @param pictureInfo pictureInfo
     */
    @Override
    public void updataimgSuccess(PictureInfo pictureInfo) {
        mMagList.add(pictureInfo.getImage());
        isRequestSuccess = true;
        Toast.makeText(this, "图片上传成功！", Toast.LENGTH_LONG).show();
    }

    /**
     * 录制视频上传成功
     *
     * @param videoInfo videoInfo
     */
    @Override
    public void uploadVideoSuccess(VideoInfo videoInfo) {
        mVideoList.add(videoInfo.getVideo());
        isVideoResult = true;
        Toast.makeText(this, "视频上传成功！", Toast.LENGTH_LONG).show();
    }


    /**
     * 弹出选择相册或相机弹窗或录制视频
     */
    private void getPopup() {
        View partView = View.inflate(this, R.layout.activity_region_error, null);
        View view = View.inflate(this, R.layout.popup_bottom_layout, null);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        // 点击空白处隐藏弹窗
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        mPicture = view.findViewById(R.id.tv_picture);
        mCamera = view.findViewById(R.id.tv_camera);
        mCancel = view.findViewById(R.id.tv_cancel);
        mVideo = view.findViewById(R.id.tv_video);

        // 选择相册
        subscribeClick(mPicture, o -> {
            if (ContensUtils.checkAndApplyfPermissionActivity(RegionErrorActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, RC_CHOOSE_PHOTO)) {
                choosePhoto();
                isRequestPicture = true; // 标识点击选择显示图片
            }
        });

        // 相机拍照
        subscribeClick(mCamera, o -> {
            if (ContensUtils.checkAndApplyfPermissionActivity(RegionErrorActivity.this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    RC_CHOOSE_CAMERA)) {
                startCamera();
                isRequestPicture = true; // 标识点击选择显示图片
            }
        });

        // 录制视频
        subscribeClick(mVideo, o -> {
            if (ContensUtils.checkAndApplyfPermissionActivity(RegionErrorActivity.this,
                    new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO}, RC_CHOOSE_VIDEO)) {
                startVideo();
                isRequestVideo = true; // 标识点击选择显示录制视频
            }
        });

        // 点击取消按钮
        subscribeClick(mCancel, o -> {
            popupWindow.dismiss();
        });
        // 点击外部空白处消失
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
            }
        });
        //设置底部弹出
        popupWindow.showAtLocation(partView, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 跳转相册页面
     */
    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //如果大于等于7.0使用FileProvider
            Uri uriForFile = FileProvider.getUriForFile(this, "com.scc.customview.fileprovider", mGalleryFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivityForResult(intent, RC_CHOOSE_PHOTO);
    }

    /**
     * 打开相机
     */
    public void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //改变Uri  com.xykj.customview.fileprovider注意和xml中的一致
        tempUri = FileProvider.getUriForFile(this, "com.scc.customview.fileprovider", cameraSavePath);
        //添加权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        startActivityForResult(intent, RC_CHOOSE_CAMERA);
    }

    /**
     * 调用相机录制视频
     */
    public void startVideo() {
        try {
            Uri mVideoUri = null;
            Intent intent = new Intent();
            //指定动作，启动相机
            intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            //创建文件
            createMediaFile();
            //添加权限
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //做一些处理
                //获取uri
                mVideoUri = FileProvider.getUriForFile(this, "com.scc.customview.fileprovider", mVideoFile);
            } else {
                //在版本低于此的时候，做一些处理
                mVideoUri = Uri.fromFile(mVideoFile);
            }
            //将uri加入到额外数据
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mVideoUri);
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 15); // 限制录制时间15秒
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high  // 比例
            //启动相机并要求返回结果
            startActivityForResult(intent, RC_CHOOSE_VIDEO);
        } catch (Exception e) {
            Log.d(TAG, "startVideo：" + e.toString());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
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
            case RC_CHOOSE_VIDEO:
                startVideo();
                Log.d(TAG, "===========权限回调---用户同意了----录制视频");
                break;
            default:
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
                        mImg.setVisibility(View.VISIBLE);
                        mVideoView.setVisibility(View.GONE);
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
            case RC_CHOOSE_VIDEO: // 录制视频回调
                try {
                    Uri uri = data.getData();
                    if (uri != null) {
                        getAudioFilePathFromUri(uri);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "------视频回调异常--------" + e.toString());
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri uri
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
            uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath()
                    + "/test/" + System.currentTimeMillis() + ".jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            startActivityForResult(intent, CROP_SMALL_PICTURE_MIUI);
            return;
        }
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }


    /**
     * 保存裁剪之后的图片数据
     * @param data data
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
//            Utils utils = new Utils();
//            photo = utils.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了
            mVideoView.setVisibility(View.GONE);
            mImg.setVisibility(View.VISIBLE);
            mImg.setImageBitmap(photo);
            File file = FileUtil.getFile(photo);
            impl.updateImg(this, file.toString());
        }
    }

    /**
     * 保存裁剪之后的图片数据----适配小米
     */
    protected void setImageMiuiToView() {
        //将Uri图片转换为Bitmap
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
            mVideoView.setVisibility(View.GONE);
            mImg.setVisibility(View.VISIBLE);
            mImg.setImageBitmap(bitmap);
            File file = FileUtil.getFile(bitmap);
            impl.updateImg(this, file.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过Uri，获取录音文件的路径（绝对路径）
     *
     * @param uri  uri 录音文件的uri
     * 录音文件的路径（String）
     */
    private void getAudioFilePathFromUri(Uri uri) {
        Cursor cursor = getContentResolver()
                .query(uri, null, null, null, null);
        if (cursor.moveToNext()) {
            int columnIndexOrThrow = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            temp = cursor.getString(columnIndexOrThrow);
            Log.d(TAG, " 路径为空：" + temp);
            cursor.close();
        }
//        cursor.moveToFirst();
        if (temp != null) {
            if (!TextUtils.isEmpty(temp)) {
                mImg.setVisibility(View.GONE);
                mVideoView.setVisibility(View.VISIBLE);
                mVideoView.setUp(temp
                        , JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, temp);
                mVideoView.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
//              jzVideoPlayerStandard.thumbImageView.
//              setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
            }
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    /**
                     * 视频压缩
                     * 第一个参数:视频源文件路径
                     * 第二个参数:压缩后视频保存的路径
                     */
                    try {
                        comPressPath = SiliCompressor.with(RegionErrorActivity.this)
                                .compressVideo(temp, mVideoFile.getAbsolutePath());
                        try {
                            impl.uploadVideo(RegionErrorActivity.this, comPressPath);
                        } catch (Exception e) {
                            Log.d(TAG, "返回异常--getAudioFilePathFromUri：" + e.toString());
                        }
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                        Log.d(TAG, "getAudioFilePathFromUri----返回异常--URISyntaxException：" + e.toString());
                    }
                }
            }.start();
        }
    }


    /**
     * 创建视频本地文件
     *
     * @return File File
     * @throws IOException
     */
    private File createMediaFile() throws IOException {
        mVideoFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
                "CameraDemo");
        if (!mVideoFile.exists()) {
            if (!mVideoFile.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "VID_" + timeStamp;
        String suffix = ".mp4";
        File mediaFile = new File(mVideoFile + File.separator + imageFileName + suffix);
        return mediaFile;
    }

}
