package com.proyecto.sergioblanco.pelispedia.app.activities;


import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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


public class LoginActivity extends AppCompatActivity {

    /**
     * Firebase Auth
     */
    private FirebaseAuth firebaseAuth;

    /**
     * Navigation controller
     */
    private NavigationController navigationController;

    /**
     * Data controller
     */
    private DataController dataController;

    /**
     * Login button
     */
    private Button loginButton;

    /**
     * Go to register button
     */
    private Button goToRegisterButton;

    /**
     * User password
     */
    private EditText passwordET;

    /**
     * User email
     */
    private EditText userET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getViews();
        setInfo();
        setListeners();
    }

    /**
     * Method to get views
     */
    private void getViews() {
        userET = (EditText)findViewById(R.id.nick);
        passwordET = (EditText)findViewById(R.id.password);
        goToRegisterButton = (Button)findViewById(R.id.not_registered_button);
        loginButton = (Button)findViewById(R.id.login_button);
    }

    /**
     * Method to set initial info
     */
    private void setInfo() {
        //init navigation controller
        navigationController = new NavigationController();

        //init data controller
        dataController = new DataController();

        //Firebase Auth instance
        firebaseAuth = FirebaseAuth.getInstance();
    }

    /**
     * Method to set listeners
     */
    private void setListeners() {
        goToRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to register
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constants.GO_TO_REGISTER, true);
                navigationController.changeActivity(LoginActivity.this, bundle);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkFields()) {
                    //load user
                    dataController.loadUser(userET.getText().toString(), new AppInterfaces.ILoadUser() {
                        @Override
                        public void loadUser(final User user) {

                            if (user != null) {
                                firebaseAuth.signInWithEmailAndPassword(user.getEmail(), passwordET.getText().toString()).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()) {
                                            //save user in session
                                            Session.getInstance().setUser(user);

                                            //save user in preferences
                                            Utils utils = new Utils();
                                            utils.register(LoginActivity.this, user.getNick());

                                            //change activity to home
                                            Bundle bundle = new Bundle();
                                            bundle.putBoolean(Constants.GO_TO_REGISTER, false);
                                            navigationController.changeActivity(LoginActivity.this, bundle);

                                            //close activity
                                            finish();

                                        } else {
                                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_error), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(LoginActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void error(DatabaseError error) {
                            Log.i("PRUEBA", "ERROR: " + error.getMessage());
                        }
                    });
                }
            }
        });
    }

    /**
     * Method to check if all fields are correctly filled
     */
    private boolean checkFields() {
        if (userET.getText().toString().isEmpty()){
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