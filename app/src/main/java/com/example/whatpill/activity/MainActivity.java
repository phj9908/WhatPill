package com.example.whatpill.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.whatpill.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final static int TAKE_PICTURE = 1;
    private static final String TAG = "MyActivity";

    Button btnCamera, btnSave;
    ImageView ivPill;
    Uri imageUri;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCamera = findViewById(R.id.btnCamera);
        btnSave = findViewById(R.id.btnSave);
        ivPill = findViewById(R.id.ivPill);

        btnCamera.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        // 권한 체크 후 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    // 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
            Log.d("로그", "Permission: " + permissions[0] + " was " + grantResults[0]);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent1;

        switch (v.getId()) {
            case R.id.btnCamera:
                intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if(intent1.resolveActivity(getPackageManager())!=null){
                    File photoFile = null;
                    File tempDir = getCacheDir();

                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String imageFileName = "Capture_" + timeStamp + "_";

                    try {
                        File tempImage = File.createTempFile(imageFileName, ".jpg", tempDir);
                        mCurrentPhotoPath = tempImage.getAbsolutePath();
                        photoFile = tempImage;
                    } catch (IOException e){
                        e.printStackTrace();
                    }

                    if(photoFile!=null){
                        Uri photoURI = FileProvider.getUriForFile(MainActivity.this, getPackageName() + ".fileprovider", photoFile);
                        intent1.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                        startActivityForResult(intent1, 1);
                        break;
                    }
                }

            case R.id.btnSave:
                clickUpload();
                imageUri = null;

                try{
                    Thread.sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

                Intent intent2 = new Intent(this, Name.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 사진 촬영 완료 후 응답
        if (requestCode == TAKE_PICTURE) {
            if (resultCode == RESULT_OK) {

                File file = new File(mCurrentPhotoPath);
                boolean bExist = file.exists();

                if (bExist) {
                    Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
                    ivPill.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    Glide.with(getApplicationContext()).load(bitmap).into(ivPill);

                    String imageSaveUri = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap , "사진 저장", "사진이 저장되었습니다.");
                    imageUri = Uri.parse(imageSaveUri);
                    Log.d(TAG, "MainActivity - onActivityResult() called" + imageUri);
                }
            }
        }
    }

    // 파이어베이스 업로드 함수
    public void clickUpload() {

        // Firebase Storage를 관리하는 객체 얻어오기
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        // 원래 확장자는 파일의 실제 확장자를 얻어와서 사용해야함. 그러려면 이미지의 절대 주소를 구해야함.
        String filename = "pill.png";

        // 폴더가 없으면 자동 생성
        StorageReference imgRef = firebaseStorage.getReference("uploads/" + filename);

        // 참조 객체를 통해 이미지 파일 업로드
        // imgRef.putFile(imgUri);
        // 업로드 결과를 받고 싶다면 아래와 같이 UploadTask를 사용하면 된다.
        UploadTask uploadTask = imgRef.putFile(imageUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "MainActivity - onFailure() called");
                    }
                });
    }
}