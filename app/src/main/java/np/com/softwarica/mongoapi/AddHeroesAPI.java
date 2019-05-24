package np.com.softwarica.mongoapi;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddHeroesAPI extends AppCompatActivity {

    private EditText etName, etDescription;
    private ImageView ivHero;
    private Button btnAdd;
    String imagePath;
String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_heroes_api);

        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.eDescription);
        ivHero = findViewById(R.id.ivHero);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Save();
            }
        });

        ivHero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }

            private void BrowseImage() {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }


        });
    }

    private void Save() {
        SaveImageOnly();
        String name = etName.getText().toString();
        String desc = etDescription.getText().toString();

        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("desc", desc);
        map.put("image",imageName);

        HeroAPI heroAPI = Url.getInstance().create(HeroAPI.class);
        Call<Void> heroesCall = MyRetrofit.getAPI().addHero(Url.Cookie,map);


        Hero hero = new Hero(name, desc, name + ".jpg");

        //       Call<Void> heroesCall = MyRetrofit.getAPI().addHero(name, desc);

        heroesCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(AddHeroesAPI.this, "Code" + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(AddHeroesAPI.this, "Successfully Added", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddHeroesAPI.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });


        MyRetrofit.getAPI().addHero(Url.Cookie,hero).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AddHeroesAPI.this, "Code" + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(AddHeroesAPI.this, "Successfully Added", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddHeroesAPI.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "please select an image", Toast.LENGTH_SHORT).show();
            }
        }
        Uri uri = data.getData();
        imagePath = getRealPathFromUri(uri);
        previewImage(imagePath);
    }




    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(),uri,projection,null,null,null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;

    }

    private void previewImage(String imagePath) {
        File imgFile = new File(imagePath);
        if (imgFile.exists()) {

            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ivHero.setImageBitmap(bitmap);
        }
    }

    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void SaveImageOnly(){

        File file= new File(imagePath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",file.getName(),requestBody);

        HeroAPI heroAPI = Url.getInstance().create(HeroAPI.class);
        Call<ImageResponse> responseBodyCall = heroAPI.uploadImage(Url.Cookie,body);

        StrictMode();

        try {
            Response<ImageResponse> imageResponseResponse =  responseBodyCall.execute();

             imageName = imageResponseResponse.body().getFilename();
        }
        catch (IOException e){
            Toast.makeText(this,"error",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}