package com.proyecto.sergioblanco.pelispedia.app.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.proyecto.sergioblanco.pelispedia.R;
import com.proyecto.sergioblanco.pelispedia.app.controllers.NavigationController;
import com.proyecto.sergioblanco.pelispedia.app.fragments.BestFilmsFragment;
import com.proyecto.sergioblanco.pelispedia.app.fragments.FavoritesFragment;
import com.proyecto.sergioblanco.pelispedia.app.fragments.HomeFragment;
import com.proyecto.sergioblanco.pelispedia.app.fragments.ProfileFragment;
import com.proyecto.sergioblanco.pelispedia.app.fragments.UpcomingFragment;
import com.proyecto.sergioblanco.pelispedia.app.interfaces.AppInterfaces;
import com.proyecto.sergioblanco.pelispedia.app.models.User;
import com.proyecto.sergioblanco.pelispedia.app.session.Session;
import com.proyecto.sergioblanco.pelispedia.app.utils.Constants;
import com.proyecto.sergioblanco.pelispedia.app.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Action bar drawer toggle
     */
    private ActionBarDrawerToggle toggle;

    /**
     * User profile image ImageView
     */
    private ImageView imageView;

    /**
     * User nick Text View
     */
    private TextView nickTV;

    /**
     * User email Text View
     */
    private TextView emailTV;

    /**
     * Current user
     */
    private User user;

    /**
     * Boolean to know if go to detail or home fragment
     */
    private boolean toDetail;

    /**
     * Intent content user select photo
     */
    private Intent data;

    /**
     * Array with all profile image bytes
     */
    private  ByteArrayOutputStream bytes;

    /**
     * Toolbar
     */
    private Toolbar toolbar;

    /**
     * Interface listener to add image
     */
    private AppInterfaces.IAddImage listener;

    /**
     * Drawer layout
     */
    private DrawerLayout drawer;

    /**
     * Navigation controller
     */
    private NavigationController navigationController;

    /**
     * Navigation view
     */
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getViews();

        setInfo();

        setListeners();

        initNavigation();

    }

    /**
     * Method to get views
     */
    private void getViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_home, navigationView, true);
        nickTV = (TextView)headerView.findViewById(R.id.nick_text_view);
        emailTV = (TextView)headerView.findViewById(R.id.email_text_view);
        imageView = (ImageView)headerView.findViewById(R.id.profile_image_view);
    }

    /**
     * Method to set initial info
     */
    private void setInfo() {
        setSupportActionBar(toolbar);

        //init navigation controller
        navigationController = new NavigationController();

        //set title
        toolbar.setTitle(getString(R.string.home));

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //get user data
                user = Session.getInstance().getUser();
                if (user != null) {
                    nickTV.setText(user.getNick());
                    emailTV.setText(user.getEmail());
                    if (user.getProfileImage() != null) {
                        Utils utils = new Utils();
                        utils.setImage(user.getProfileImage(), HomeActivity.this, imageView, null);
                    }
                }
            }
        };
        toggle.syncState();
    }

    /**
     * Mehtod to init navigation
     */
    private void initNavigation() {
        Fragment fragment = new HomeFragment();
        navigationController.initNavigation(HomeActivity.this, fragment);
    }

    /**
     * Method to set listeners
     */
    private void setListeners() {
        navigationView.setNavigationItemSelectedListener(this);

        drawer.addDrawerListener(toggle);
    }


    /**
     * Method when user select an option on navigation menu
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            //go to Home Fragment
            HomeFragment fragment = new HomeFragment();
            navigationController.changeFragment(this, fragment, null, Constants.HOME_STATES.HOME_STATE);
        } else if (id == R.id.favourites) {
            //go to Favorites fragment
            FavoritesFragment favoritesFragment = new FavoritesFragment();
            navigationController.changeFragment(this, favoritesFragment, null, Constants.HOME_STATES.FAVORITES_STATE);

        } else if (id == R.id.upcoming) {
            //go to upcoming fragment
            UpcomingFragment upcomingFragment = new UpcomingFragment();
            navigationController.changeFragment(this, upcomingFragment,  null, Constants.HOME_STATES.UPCOMING_STATE);
        } else if (id == R.id.top_rated) {
            BestFilmsFragment bestFilmsFragment = new BestFilmsFragment();
            navigationController.changeFragment(this, bestFilmsFragment, null, Constants.HOME_STATES.BEST_STATE);

        } else if (id == R.id.my_profile) {
            ProfileFragment profileFragment = new ProfileFragment();
            navigationController.changeFragment(this, profileFragment, null, Constants.HOME_STATES.PROFILE_STATE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Method when user press back button
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //if is home fragment, exit
            if (navigationController.getHomeState() == Constants.HOME_STATES.HOME_STATE) {
                finishAffinity();
            } else {
                //go to Home Fragment
                HomeFragment fragment = new HomeFragment();
                navigationController.changeFragment(this, fragment, null, Constants.HOME_STATES.HOME_STATE);
            }
        }
    }

    /**
     * Method to initialize trailer in Detail fragment
     */
    public void initializeVideo(final String videoURL) {
        YouTubePlayerFragment youtubeFragment = (YouTubePlayerFragment)getFragmentManager().findFragmentById(R.id.youtubeFragment);

        youtubeFragment.initialize(Constants.GOOGLE_API, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(videoURL);
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
            }
        });
    }

    /**
     * Method to set title in Action bar
     */
    public void setToolbarTitle (String title) {
        toolbar.setTitle(title);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //save data
        this.data = data;

        //user profile photo url
        String userPhotoUrl = null;

        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.SELECT_CAMERA) {
                //get data from intent
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                FileOutputStream fo;

                if(Utils.isPermission(this)){
                    try {

                        //get directory
                        File direct = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "myshopping/profile/photos/");

                        //if directory not exits
                        if(!direct.exists()){
                            //create
                            if(!direct.mkdirs()){
                                direct.mkdir();
                            }
                        }

                        //save image
                        String filename = System.currentTimeMillis() + ".jpg";
                        File destination = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "myshopping/profile/photos/", filename);
                        destination.createNewFile();
                        //get path
                        userPhotoUrl =Environment.getExternalStorageDirectory().toString() + File.separator + "myshopping/profile/photos/" + filename;
                        //write in external storage
                        fo = new FileOutputStream(destination);
                        fo.write(bytes.toByteArray());
                        //close
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //callback
                    listener.addImage(userPhotoUrl);
                }
            } else if (requestCode == Constants.SELECT_GALLERY) {
                if(Utils.isPermission(this)) {
                    //get data from intent
                    Uri selectedImageUri = data.getData();

                    //callback
                    listener.addImage(selectedImageUri.toString());
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //camera result permission
        if (requestCode == Constants.SELECT_CAMERA && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, Constants.SELECT_CAMERA);
        }

        //gallery result permission
        if (requestCode == Constants.SELECT_GALLERY && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
            //open gallery
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, Constants.SELECT_GALLERY);
        }

        //check permision
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED && data!=null){

            if (requestCode == Constants.SELECT_CAMERA) {

            } else if (requestCode == Constants.SELECT_GALLERY) {
                if(Utils.isPermission(this)) {
                    //get data from intent
                    Uri selectedImageUri = data.getData();
                    //callback
                    listener.addImage(selectedImageUri.toString());
                }
            }
        }
    }

    /**
     * Method to change profile image
     */
    public void changeImageProfile(AppInterfaces.IAddImage listener, AppInterfaces.IRemoveImage removeListener){
        //save listener
        this.listener = listener;
        //show dialog with options
        Utils.selectProfileImage(getResources().getStringArray(R.array.add_image_options),this,getString(R.string.select_image_dialog_tittle), removeListener);
    }
}
