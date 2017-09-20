package com.proyecto.sergioblanco.pelispedia.app.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.proyecto.sergioblanco.pelispedia.R;
import com.proyecto.sergioblanco.pelispedia.app.controllers.DataController;
import com.proyecto.sergioblanco.pelispedia.app.controllers.NavigationController;
import com.proyecto.sergioblanco.pelispedia.app.interfaces.AppInterfaces;
import com.proyecto.sergioblanco.pelispedia.app.models.User;
import com.proyecto.sergioblanco.pelispedia.app.session.Session;
import com.proyecto.sergioblanco.pelispedia.app.utils.Constants;
import com.proyecto.sergioblanco.pelispedia.app.utils.Utils;

public class RegisterActivity extends AppCompatActivity {

    /**
     * Utils instance
     */
    private Utils utils;

    /**
     * Firebase Auth instance
     */
    private FirebaseAuth firebaseAuth;

    /**
     * User instance
     */
    private User user;

    /**
     * Data controller
     */
    private DataController dataController;

    /**
     * Navigation controller
     */
    private NavigationController navigationController;

    /**
     * Login button
     */
    private Button registerButton;

    /**
     * Go to register button
     */
    private Button goToLoginButton;

    /**
     * User nick
     */
    private EditText nickET;

    /**
     * User password
     */
    private EditText passwordET;

    /**
     * User email
     */
    private EditText emailET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getViews();
        setInfo();
        setListeners();
    }

    /**
     * Method to get views
     */
    private void getViews() {
        registerButton = (Button)findViewById(R.id.register_button);
        goToLoginButton = (Button)findViewById(R.id.registered_button);
        nickET = (EditText)findViewById(R.id.nick);
        passwordET = (EditText)findViewById(R.id.password);
        emailET = (EditText)findViewById(R.id.email);
    }

    /**
     * Method to set initial info
     */
    private void setInfo() {
        //init navigation controller
        navigationController = new NavigationController();

        //init data controller
        dataController = new DataController();

        //firebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance();

        // utils instance
        utils = new Utils();
    }

    /**
     * Method to set listeners
     */
    private void setListeners() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkFields()) {
                    //check if user already exists
                    dataController.loadUser(nickET.getText().toString(), new AppInterfaces.ILoadUser() {
                        @Override
                        public void loadUser(User userLoaded) {
                            //if user dont exists
                            if (userLoaded == null) {
                                //Create user
                                firebaseAuth.createUserWithEmailAndPassword(emailET.getText().toString(), passwordET.getText().toString()).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()) {
                                            //Save user in session
                                            user = new User(firebaseAuth.getCurrentUser().getUid(), nickET.getText().toString(), emailET.getText().toString(), passwordET.getText().toString());
                                            Session.getInstance().setUser(user);

                                            //Save user in database
                                            dataController.saveUser(user);

                                            utils.register(RegisterActivity.this, user.getNick());
                                            Toast.makeText(RegisterActivity.this, getString(R.string.user_created), Toast.LENGTH_SHORT).show();

                                            //go to HomeActivity
                                            Bundle bundle = new Bundle();
                                            bundle.putBoolean(Constants.GO_TO_LOGIN, false);
                                            navigationController.changeActivity(RegisterActivity.this, bundle);
                                            //finish activity
                                            finish();

                                        } else {
                                            Toast.makeText(RegisterActivity.this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(RegisterActivity.this, getString(R.string.user_exsits), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void error(DatabaseError error) {

                        }
                    });
                }
            }
        });

        goToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to login
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constants.GO_TO_LOGIN, true);
                navigationController.changeActivity(RegisterActivity.this, bundle);
            }
        });
    }

    /**
     * Method to check if all fields are correctly filled
     */
    private boolean checkFields() {
        if (nickET.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.nick_empty), Toast.LENGTH_SHORT).show();
            return false;
        } else if (emailET.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.email_empty), Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordET.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.password_empty), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
