package com.heshe.store;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.ActionBarDrawerToggle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;
    SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInstances();
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_action_hestore);

        final WebView RefreshListenerWebView = (WebView) findViewById(R.id.webView);
        swipeRefresh  = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if( swipeRefresh.isRefreshing() )
                {
                    //Log.v("SK", "OnRefresh Called :D");
                    //Toast.makeText(getApplicationContext(), "Refreshing ...", Toast.LENGTH_SHORT).show();
                    RefreshListenerWebView.reload();
                    swipeRefresh.setRefreshing(false);
                }
            }
        });
    }

    private void initInstances() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.hello_world, R.string.hello_world);
        drawerLayout.setDrawerListener(drawerToggle);
        final Activity activityMain = this;

        navigation = (NavigationView) findViewById(R.id.navigation_view);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                WebView webview = (WebView) activityMain.findViewById(R.id.webView);

                if (id == R.id.navigation_item_1) {
                    if (ChkOnline()) {
                        webview.loadUrl("http://www.citeinfo.net/1.php");
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "No Internet!", Toast.LENGTH_SHORT).show();
                        webview.loadUrl("file:///android_asset/index.html");
                    }
                } else if (id == R.id.navigation_item_2) {
                    if (ChkOnline()) {
                        webview.loadUrl("http://www.citeinfo.net/2.php");
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "No Internet!", Toast.LENGTH_SHORT).show();
                        webview.loadUrl("file:///android_asset/index.html");
                    }

                } else if (id == R.id.navigation_item_3) {
                    if (ChkOnline()) {
                        webview.loadUrl("http://www.citeinfo.net/3.php");
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "No Internet!", Toast.LENGTH_SHORT).show();
                        webview.loadUrl("file:///android_asset/index.html");
                    }

                } else if (id == R.id.navigation_item_4) {
                    if (ChkOnline()) {
                        webview.loadUrl("http://www.citeinfo.net/4.php");
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "No Internet!", Toast.LENGTH_SHORT).show();
                        webview.loadUrl("file:///android_asset/index.html");
                    }

                } else if (id == R.id.navigation_item_5) {
                    if (ChkOnline()) {
                        webview.loadUrl("http://www.citeinfo.net/5.php");
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "No Internet!", Toast.LENGTH_SHORT).show();
                        webview.loadUrl("file:///android_asset/index.html");
                    }

                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });

        WebView myWebView = (WebView) findViewById(R.id.webView);

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());

        if (ChkOnline()) {
            myWebView.loadUrl("http://www.citeinfo.net/index.php");
        }
        else {
            Toast.makeText(getApplicationContext(), "No Internet!", Toast.LENGTH_SHORT).show();
            myWebView.loadUrl("file:///android_asset/index.html");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        WebView webView = (WebView) findViewById(R.id.webView);
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);

            if (drawer.isDrawerOpen(GravityCompat.START))
                drawer.closeDrawer(GravityCompat.START);
            else
                webView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    private static long back_pressed_time;
    private static long PERIOD = 2000;

    @Override
    public void onBackPressed()
    {
        if (back_pressed_time + PERIOD > System.currentTimeMillis()) super.onBackPressed();
        else Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        back_pressed_time = System.currentTimeMillis();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean ChkOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
