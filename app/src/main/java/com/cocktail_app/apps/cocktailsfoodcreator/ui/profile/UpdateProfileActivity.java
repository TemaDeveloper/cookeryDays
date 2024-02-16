package com.cocktail_app.apps.cocktailsfoodcreator.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.cocktail_app.apps.cocktailsfoodcreator.R;
import com.cocktail_app.apps.cocktailsfoodcreator.activities.MainActivity;
import com.cocktail_app.apps.cocktailsfoodcreator.services.APIService;
import com.cocktail_app.apps.cocktailsfoodcreator.services.DBConstructor;
import com.cocktail_app.apps.cocktailsfoodcreator.services.SharedPrefManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {

    private TextInputEditText emailEditText, nameEditText;
    private CircleImageView userImage;
    private MaterialButton updateButton;
    private static final int PICK_IMAGE_REQUEST = 777;
    private Uri path;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        updateButton = findViewById(R.id.updateButton);
        emailEditText = findViewById(R.id.emailEditText);
        nameEditText = findViewById(R.id.nameEditText);
        userImage = findViewById(R.id.profileImage);

        nameEditText.setText(getIntent().getStringExtra("name"));
        emailEditText.setText(getIntent().getStringExtra("email"));

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser(view);
            }
        });

    }

    public String convertImageToString(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST && data != null) {
            path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                userImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateUser(View view){
        APIService apiService = DBConstructor.CreateService(APIService.class);
        int id = SharedPrefManager.getInstance(getApplicationContext()).getUserID();
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String imagePath = convertImageToString(bitmap);

        Call<ResponseBody> userUpdateCall = apiService.setUser(id, name, email, imagePath);
        userUpdateCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class)
                            .putExtra("email", email));
                }else{
                    Snackbar.make(view, "Something Went Wrong", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Snackbar.make(view, "Something Went Wrong", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

}