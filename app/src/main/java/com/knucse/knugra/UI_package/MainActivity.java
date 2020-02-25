package com.knucse.knugra.UI_package;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import com.knucse.knugra.DM_package.Database;
import com.knucse.knugra.DM_package.ServerConnectTask;

import com.knucse.knugra.PD_package.User_package.User;
import com.knucse.knugra.R;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private int majorposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//생성하기
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Intent it = getIntent();
        majorposition = it.getIntExtra("mposition", 0);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top` level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_career_success, R.id.nav_g_info_search)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // user logout

        User.logout();
        Database.destroy();
        ServerConnectTask.updateCompleted = false;

    }

    public void navigateTo(int resId) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(resId);
    }

    public int getMajorposition() {
        return majorposition;
    }

    public void setMajorposition(int majorposition) {
        this.majorposition = majorposition;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int menuId = menuItem.getItemId();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        switch (menuId){
            case R.id.nav_career_success:
                navController.popBackStack(R.id.nav_home, false);
                DataLoadingTask dataloading = new DataLoadingTask();
                dataloading.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                break;
            case R.id.nav_home:
                navController.popBackStack(R.id.nav_home, true);
                navController.navigate(menuId);
                break;
            default:
                navController.popBackStack(R.id.nav_home, false);
                navController.navigate(menuId);
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class DataLoadingTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog dataLoadingProgress = new ProgressDialog(MainActivity.this);
        int timeOut = 0;
        @Override
        protected void onPreExecute() {
            dataLoadingProgress.show();
            dataLoadingProgress.setContentView(R.layout.dataloading_progress_dialog);
            dataLoadingProgress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while (!ServerConnectTask.updateCompleted && timeOut < 20){
                try {
                    Thread.sleep(1000);
                    timeOut++;
                }catch (Exception e){
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dataLoadingProgress.dismiss();
            if (ServerConnectTask.updateCompleted) {
                NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                navController.navigate(R.id.nav_career_success);
            } else {
            }
            super.onPostExecute(aVoid);
        }
    }
}
