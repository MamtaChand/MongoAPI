package np.com.softwarica.mongoapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser();
            }

            private void checkUser() {
                HeroAPI heroAPI = Url.getInstance().create(HeroAPI.class);

                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                Call<LoginSignUpResponse> usersCall = heroAPI.checkUser(username,password);

                usersCall.enqueue(new Callback<LoginSignUpResponse>() {
                    @Override
                    public void onResponse(Call<LoginSignUpResponse> call, Response<LoginSignUpResponse> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Either username or password is incorrect",Toast.LENGTH_LONG).show();
                            return;
                        }
                        else{
                            if (response.body().getSuccess()){
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginSignUpResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this,"Error" + t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

                    }
                });
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
