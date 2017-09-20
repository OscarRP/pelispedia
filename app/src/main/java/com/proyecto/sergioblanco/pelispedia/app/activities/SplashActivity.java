package com.proyecto.sergioblanco.pelispedia.app.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.proyecto.sergioblanco.pelispedia.R;
import com.proyecto.sergioblanco.pelispedia.app.controllers.DataController;
import com.proyecto.sergioblanco.pelispedia.app.controllers.NavigationController;
import com.proyecto.sergioblanco.pelispedia.app.interfaces.AppInterfaces;
import com.proyecto.sergioblanco.pelispedia.app.models.User;
import com.proyecto.sergioblanco.pelispedia.app.session.Session;
import com.proyecto.sergioblanco.pelispedia.app.utils.Constants;

public class SplashActivity extends AppCompatActivity {

    /**
     * Animation
     */
    private Animation animation;

    /**
     * Progress bar
     */
    private ProgressBar progressBar;

    /**
     * Logo
     */
    private ImageView logo;

    /**
     * Data controller
     */
    private DataController dataController;

    /**
     * Handler to controll time in splash activity
     */
    private Handler handler;

    /**
     * Controls time in splash activity
     */
    private int time;

    /**
     * Navigation controller
     */
    private NavigationController navigationController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getViews();

        startAnimations();

        //init navigation controller
        navigationController = new NavigationController();

        //init data controller
        dataController = new DataController();

        startHandler();
    }

    /**
     * Method to get views
     */
    private void getViews() {
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        logo = (ImageView)findViewById(R.id.logo);
    }

    /**
     * Method to animate views
     */
    private void startAnimations() {
        animation = AnimationUtils.loadAnimation(this, R.anim.right_to_left);
        logo.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                logo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    /**
     * Method to handler time to change activity
     */
    private void startHandler() {

        //time init
        time = 0;

        //Handler creation
        handler = new Handler();

        handler.postAtTime(new Runnable() {
            @Override
            public void run() {
                if (time >= Constants.SPLASH_TIME) {
                    //remove callbacks
                    handler.removeCallbacks(this);

                    //check user is logged
                    //retrieve info from sharedPreferences
                    SharedPreferences sharedPreferences = SplashActivity.this.getSharedPreferences(Constants.USER_LOGGED, Context.MODE_PRIVATE);
                    boolean userLoged = sharedPreferences.getBoolean(Constants.USER_LOGGED, false);
                    String nick = sharedPreferences.getString(Constants.NICK, "");

                    if (userLoged) {
                        //load user from database
                        dataController.loadUser(nick, new AppInterfaces.ILoadUser() {
                            @Override
                            public void loadUser(User user) {
                                //save user in session
                                Session session = Session.getInstance();
                                session.setUser(user);

                                //go to home activity
                                Bundle bundle = new Bundle();
                                bundle.putBoolean(Constants.USER_LOGGED, true);
                                navigationController.changeActivity(SplashActivity.this, bundle);

                                //finish activity
                                finish();
                            }

                            @Override
                            public void error(DatabaseError error) {
                                Toast.makeText(SplashActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        //change Activity to register
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(Constants.USER_LOGGED, false);
                        navigationController.changeActivity(SplashActivity.this, bundle);

                        //finish activity
                        finish();
                    }

                } else {
                    //increments time
                    time = time + Constants.INCREMENTAL_TIME;
                    handler.postDelayed(this, Constants.INCREMENTAL_TIME);
                }
            }
        }, Constants.INCREMENTAL_TIME);
    }
}
