package np.com.bishalheuju.firebaseconnection;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (mUser == null) {
            //not signed in
            Log.e(TAG, "No user present");
        } else {
            //user is logged in
            Log.e(TAG, "Already Signed In: " + mUser.getEmail());
        }

        Button btnSignIn = (Button) findViewById(R.id.btnSignIn);
        Button btnSignUp = (Button) findViewById(R.id.btnSigUp);
        Button btnSignOut = (Button) findViewById(R.id.btnSignOut);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInUserWith("b@heuju.com", "password");
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpUserWith("b@heuju.com", "password");
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: Signed out " + mUser.getEmail());
                mAuth.signOut();
            }
        });
    }

    void signUpUserWith(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Signin success
                            mUser = mAuth.getCurrentUser();
                            Log.e(TAG, "onComplete SignUp Success: " + mUser.getEmail());
                            Toast.makeText(MainActivity.this, "Success: " + mUser.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            //Sigin failed
                            Log.e(TAG, "onComplete SignUp Fail", task.getException());
                            Toast.makeText(MainActivity.this, "Fail: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    void signInUserWith(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //signin success
                            mUser = mAuth.getCurrentUser();
                            Log.e(TAG, "onComplete SignIn Success: " + mUser.getEmail());
                            Toast.makeText(MainActivity.this, "Success: " + mUser.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "onComplete SignIn Fail", task.getException());
                            Toast.makeText(MainActivity.this, "Fail: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
