package workoutapp.tyler.workoutapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CompareGraphsFragment.OnFragmentInteractionListener, mainWeightFragment.OnFragmentInteractionListener, BodyWeightGraphFragment.OnFragmentInteractionListener, addBodyWeightFragment.OnFragmentInteractionListener, graphFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener, addWeightFragment.OnFragmentInteractionListener,CardTabView.OnFragmentInteractionListener, workoutapp.tyler.workoutapplication.exerciseCardsFragment.OnFragmentInteractionListener, newExerciseFragment.OnFragmentInteractionListener {

    NavigationView navigationView = null;
    Toolbar toolbar = null;
    private exerciseCardsFragment exerciseCardsFragment;
    private BodyWeightGraphFragment bodyWeightGraphFragment;
    private mainFragment mainFragment;
    private CompareGraphsFragment compareGraphsFragment;
    private UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userData = new UserData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainFragment = new mainFragment();
        exerciseCardsFragment = new exerciseCardsFragment();
        bodyWeightGraphFragment = new BodyWeightGraphFragment();
        compareGraphsFragment = new CompareGraphsFragment();

        //Set fragment
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, mainFragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, getExerciseCardsFragment())
                        .commit();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, mainFragment, "toMain");
            fragmentTransaction.addToBackStack("toMain");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_gallery) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, exerciseCardsFragment, "toCalc");
            fragmentTransaction.addToBackStack("toCalc");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_slideshow) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, bodyWeightGraphFragment, "toBodyWeight");
            fragmentTransaction.addToBackStack("toBodyWeight");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_manage) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, compareGraphsFragment, "toCompare");
            fragmentTransaction.addToBackStack("toCompare");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public exerciseCardsFragment getExerciseCardsFragment() {
        return exerciseCardsFragment;
    }

    public workoutapp.tyler.workoutapplication.mainFragment getMainFragment() {
        return mainFragment;
    }

    public UserData getUserData() {
        return userData;
    }

    private void saveData(){
        String fileName = "UserData";
        FileOutputStream outputStream;
        ArrayList[] saveData = userData.getData();
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(saveData);
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getData(){
        String fileName = "UserData";
        FileInputStream inputStream;
        ArrayList[] returnList = null;
        try {
            inputStream = openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            returnList = (ArrayList[])(objectInputStream.readObject());
            objectInputStream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        if (returnList != null) {
            if (returnList[0] != null) {
                userData.setExercises(returnList[0]);
            }
            if (returnList[1] != null) {
                userData.setBodyWeight(returnList[1]);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    public BodyWeightGraphFragment getBodyWeightGraphFragment() {
        return bodyWeightGraphFragment;
    }

    public CompareGraphsFragment getCompareGraphsFragment() {
        return compareGraphsFragment;
    }
}
