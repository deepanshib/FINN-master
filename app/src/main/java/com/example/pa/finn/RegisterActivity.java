package com.example.pa.finn;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pa.finn.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class RegisterActivity extends AppCompatActivity {
    User user;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        new RegisterActivityViewHolder();

        user = new User("", "null", true, -1, -1, -1, 0, 0, 0);
    }

    private class RegisterActivityViewHolder {
        EditText etEmail, etPassword, etWeight, etHeight, etFirstName, etLastName;
        Spinner spinnerGender;
        TextView tvSelectDob, tvDob, tvImage;
        Button btnRegister;
        ProgressBar pbLoading;

        RegisterActivityViewHolder() {
            etEmail = (EditText) findViewById(R.id.etEmailRegister);
            etPassword = (EditText) findViewById(R.id.etPasswordRegister);
            etWeight = (EditText) findViewById(R.id.etWeight);
            etHeight = (EditText) findViewById(R.id.etHeight);
            etFirstName = (EditText) findViewById(R.id.etFirstName);
            etLastName = (EditText) findViewById(R.id.etLastName);
            spinnerGender = (Spinner) findViewById(R.id.spinnerGender);
            tvSelectDob = (TextView) findViewById(R.id.tvSelectDob);
            tvDob = (TextView) findViewById(R.id.tvDob);
            tvImage = (TextView) findViewById(R.id.tvRegisterChooseImage);
            btnRegister = (Button) findViewById(R.id.btnRegister);
            pbLoading = (ProgressBar) findViewById(R.id.pbLoadingRegister);

            setOnClickListeners();
        }

        void setOnClickListeners() {
            spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    user.setMale(i == 0);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            tvSelectDob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(RegisterActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                    user.setDobYears(i);
                                    user.setDobMonth(i1);
                                    user.setDobDays(i2);
                                    tvDob.setText(String.valueOf(i2) + "/" + String.valueOf(i1 + 1) + "/" + String.valueOf(i));
                                }
                            },
                            user.getDobYears() == -1 ? 2000 : user.getDobYears(),
                            user.getDobMonth() == -1 ? 0 : user.getDobMonth(),
                            user.getDobDays() == -1 ? 1 : user.getDobDays()).show();
                }
            });
            tvImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("Select Image")
                            .setMessage("Do you want to take a new picture or choose an existing picture?")
                            .setPositiveButton("Take a picture", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Nammu.init(RegisterActivity.this);
                                    if (!Nammu.checkPermission(Manifest.permission.CAMERA)) {
                                        Nammu.askForPermission(RegisterActivity.this, Manifest.permission.CAMERA, new PermissionCallback() {
                                            @Override
                                            public void permissionGranted() {
                                                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 101);
                                            }

                                            @Override
                                            public void permissionRefused() {
                                                Toast.makeText(RegisterActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 101);
                                    }
                                }
                            })
                            .setNegativeButton("Choose Existing", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 102);
                                }
                            })
                            .create().show();
                }
            });
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkInfo()) {
                        clickable(false);
                        setUserInfo();
                        mAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                            clickable(true);
                                        } else {
                                            register(task.getResult().getUser().getUid());
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(RegisterActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        void setUserInfo() {
            user.setName(etFirstName.getText().toString() + " " + etLastName.getText().toString());
            float height = Float.valueOf(etHeight.getText().toString());
            float weight = Float.valueOf(etWeight.getText().toString());
            user.setHeight(height);
            user.setWeight(weight);
            user.setBmi(weight*10000/(height*height));
        }

        void clickable (boolean b) {
            if (!b) {
                btnRegister.setVisibility(View.GONE);
                pbLoading.setVisibility(View.VISIBLE);
            } else {
                pbLoading.setVisibility(View.GONE);
                btnRegister.setVisibility(View.VISIBLE);
            }
        }

        boolean checkInfo() {
            return !(etEmail.getText().toString().isEmpty()
                    || etPassword.getText().toString().isEmpty()
                    || etPassword.getText().toString().length() < 6
                    || etWeight.getText().toString().isEmpty()
                    || etHeight.getText().toString().isEmpty()
                    || user.getDobYears() == -1
                    || etFirstName.getText().toString().isEmpty()
                    || etLastName.getText().toString().isEmpty()
            );
        }
    }

    public void register(String userId) {
        myRef.child(userId).setValue(user);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bitmap image = null;
            if (requestCode == 101) {
                image = (Bitmap) data.getExtras().get("data");
            } else if (requestCode == 102) {
                try {
                    image = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            ((ImageView) findViewById(R.id.ivRegisterUserImage)).setImageBitmap(image);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 1, baos);
            byte[] imageData = baos.toByteArray();
            user.setImage(Base64.encodeToString(imageData, 0, imageData.length, Base64.DEFAULT));
        }
    }
}
