package co.twoh.roomtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edtUser, edtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btn_login);
        edtUser = (EditText) findViewById(R.id.edt_username);
        edtPass = (EditText) findViewById(R.id.edt_pass);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logicLogin();
            }
        });

    }

    private void logicLogin() {
        String username = edtUser.getText().toString();
        String pass = edtPass.getText().toString();
        if (username.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "data harus di isi", Toast.LENGTH_SHORT).show();
        } else  if (username.equals("aku") && pass.equals("aku")){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}
