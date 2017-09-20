package com.proyecto.sergioblanco.pelispedia.app.activities;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.firebase.database.DatabaseError;
import com.proyecto.sergioblanco.pelispedia.R;
import com.proyecto.sergioblanco.pelispedia.app.controllers.DataController;
import com.proyecto.sergioblanco.pelispedia.app.controllers.NavigationController;
import com.proyecto.sergioblanco.pelispedia.app.dialogs.Dialogs;
import com.proyecto.sergioblanco.pelispedia.app.interfaces.AppInterfaces;
import com.proyecto.sergioblanco.pelispedia.app.models.Film;
import com.proyecto.sergioblanco.pelispedia.app.models.Trailer;
import com.proyecto.sergioblanco.pelispedia.app.models.User;
import com.proyecto.sergioblanco.pelispedia.app.session.Session;
import com.proyecto.sergioblanco.pelispedia.app.utils.Constants;

import java.util.ArrayList;

public class DetailActivity extends YouTubeBaseActivity {

    /**
     * Add to favorites
     */
    private Button addtoFavorites;

    /**
     * Back button
     */
    private ImageButton backButton;

    /**
     * Current user
     */
    private User user;

    /**
     * User favorites films
     */
    private ArrayList<Film> favorites;

    /**
     * Data controller
     */
    private DataController dataController;

    /**
     * Navigation Controller
     */
    private NavigationController navigationController;

    /**
     * Film overview
     */
    private TextView overviewTV;

    /**
     * Release date
     */
    private TextView releaseTV;

    /**
     * Film duration
     */
    private TextView durationTV;

    /**
     * Vote average
     */
    private TextView voteAverageTV;

    /**
     * Selected Film
     */
    private Film film;

    /**
     * Title edit text
     */
    private TextView titleTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getViews();
        setInfo();
        setListeners();
    }

    /**
     * Method to get views
     */
    private void getViews() {
        titleTV = (TextView)findViewById(R.id.title_text_view);
        overviewTV = (TextView)findViewById(R.id.overview);
        durationTV =(TextView)findViewById(R.id.duration);
        releaseTV = (TextView)findViewById(R.id.release);
        voteAverageTV = (TextView)findViewById(R.id.vote_average_text_view);
        backButton = (ImageButton)findViewById(R.id.back_button);
        addtoFavorites = (Button)findViewById(R.id.add_to_favorites);
    }

    /**
     * Method to get initial info
     */
    private void setInfo() {

        final Dialogs dialogs = new Dialogs();
        dialogs.showLoadingDialog(this);

        //init user
        user = Session.getInstance().getUser();

        //init data controller
        dataController = new DataController();

        //load user
        dataController.loadUser(user.getNick(), new AppInterfaces.ILoadUser() {
            @Override
            public void loadUser(User userLoaded) {
                user = userLoaded;
            }

            @Override
            public void error(DatabaseError error) {
            }
        });

        //init navigation controller
        navigationController = new NavigationController();

        //get film info
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            film = (Film) bundle.getSerializable(Constants.FILM);
        }

        //get details info
        dataController.getDetails(film.getId(), new AppInterfaces.ISelectFilm() {
            @Override
            public void selectFilm(Film selectedFilm) {
                film.setRuntime(selectedFilm.getRuntime());
                film.setReleaseDate(selectedFilm.getReleaseDate());
                film.setVoteAverage(selectedFilm.getVoteAverage());

                //set info
                titleTV.setText(film.getTitle());
                overviewTV.setText(film.getOverview());
                if (film.getRuntime() == 0) {
                    durationTV.setText("-");
                } else {
                    durationTV.setText(String.valueOf(film.getRuntime()) + Constants.MINS);
                }
                if (film.getReleaseDate().isEmpty()) {
                    releaseTV.setText("-");
                } else {
                    releaseTV.setText(film.getReleaseDate());
                }
                if (film.getVoteAverage() == 0) {
                    voteAverageTV.setText("-");
                } else {
                    voteAverageTV.setText(String.valueOf(film.getVoteAverage()));
                }
            }
        });

        //check if the film is favourite
        if (isFavourite(film.getId())) {
            addtoFavorites.setText(getString(R.string.quit_from_favorites));
        } else {
            addtoFavorites.setText(getString(R.string.add_to_favorites));
        }

        //init trailer
        dataController.getTrailes(film.getId(), new AppInterfaces.IGetTrailers() {
            @Override
            public void getTrailers(ArrayList<Trailer> trailers) {
                //open trailer in youtube
                film.setTrailers(trailers);
                if (trailers.size() > 0) {
                    initializeVideo(film.getTrailers().get(0).getKey());
                }
                dialogs.hideLoadingDialog();
            }
        });
    }

    /**
     * Method to set listeners
     */
    private void setListeners() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to home activity
                navigationController.changeActivity(DetailActivity.this, null);
                finish();
            }
        });

        addtoFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check if film is already in favourites
                if (isFavourite(film.getId())) {
                    //delete from favourite list
                    favorites = user.getFavorites();
                    for (int i = 0; i<favorites.size(); i++) {
                        if (favorites.get(i).getId() == film.getId()) {
                            favorites.remove(i);
                            user.setFavorites(favorites);
                        }
                    }
                    //save user in session
                    Session session = Session.getInstance();
                    session.setUser(user);

                    //save user in firebase
                    dataController.saveUser(user);
                    Toast.makeText(DetailActivity.this, getString(R.string.deleted_from_favorites), Toast.LENGTH_SHORT).show();

                } else {
                    //init favorites
                    if (user.getFavorites() == null) {
                        favorites = new ArrayList<>();
                    } else {
                        favorites = user.getFavorites();
                    }

                    //Add this film to user favorites
                    favorites.add(film);
                    user.setFavorites(favorites);

                    //save user in session
                    Session session = Session.getInstance();
                    session.setUser(user);

                    //save user in firebase
                    dataController.saveUser(user);
                    Toast.makeText(DetailActivity.this, getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    /**
     * Method to initialize trailer in Detail fragment
     */
    private void initializeVideo(final String videoURL) {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigationController.changeActivity(DetailActivity.this, null);
        finish();
    }

    /**
     * Method to check if a film is favourite
     */
    private boolean isFavourite(int filmId) {
        boolean favorite = false;

        favorites = user.getFavorites();

        for (int i=0; i<favorites.size(); i++) {
            if (favorites.get(i).getId() == filmId) {
                Log.i("PRUEBA", "getId: " + favorites.get(i).getId());
                favorite = true;
            }
        }
        return favorite;
    }
}
