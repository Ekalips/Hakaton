package hax.crutchbike.hakaton;

import android.Manifest;
import android.animation.TimeInterpolator;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;

import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;


import org.json.JSONException;
import org.json.JSONObject;

import io.codetail.animation.arcanimator.ArcAnimator;
import io.codetail.animation.arcanimator.Side;

public class StartActivity extends AppCompatActivity {


    FragmentTransaction fTrans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Registration"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final Context context = this;

        final TextInputLayout textInputEditText = (TextInputLayout) findViewById(R.id.input_layout_login);
        final TextInputLayout registr = (TextInputLayout) findViewById(R.id.input_layout_password);
        final TextInputLayout lel = (TextInputLayout) findViewById(R.id.input_layout_lel);
        final TextInputLayout lol = (TextInputLayout) findViewById(R.id.input_layout_lol);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x;
        int height = size.y;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] perms = {"android.permission.INTERNET"};
                int permsRequestCode = 200;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(perms, permsRequestCode);
                }
                sendData();
            }
        });
        final FrameLayout fabHome2 = (FrameLayout) findViewById(R.id.fabHome2);
        final FrameLayout fabHome1 = (FrameLayout) findViewById(R.id.fabHome1);
        lel.animate().translationX(width).setDuration(0).start();
        lol.animate().translationX(width).setDuration(0).start();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                hideKeyboard();
                if (tab.getPosition() == 1) {

                    textInputEditText.animate().translationY(-textInputEditText.getHeight()*2).setInterpolator(new DecelerateInterpolator()).setStartDelay(0).start();
                    registr.animate().translationY(-registr.getHeight()*2).setInterpolator(new DecelerateInterpolator()).setStartDelay(0).start();
                    lel.animate().translationX(0).setDuration(300).setInterpolator(new DecelerateInterpolator()).setStartDelay(300).start();
                    lol.animate().translationX(0).setDuration(300).setInterpolator(new DecelerateInterpolator()).setStartDelay(250).start();
                    ArcAnimator.createArcAnimator(fab,fabHome2,45, Side.LEFT).setDuration(300)
                            .start();

                } else {
                    textInputEditText.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).setStartDelay(100).start();
                    registr.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).setStartDelay(100).start();
                    lol.animate().translationX(width).setDuration(300).setInterpolator(new DecelerateInterpolator()).setStartDelay(0).start();
                    lel.animate().translationX(width).setDuration(300).setInterpolator(new DecelerateInterpolator()).setStartDelay(20).start();
                    ArcAnimator.createArcAnimator(fab, fabHome1, 40, Side.LEFT).setDuration(300)
                            .start();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public int pxToDp(int px) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    private void sendData() {
        JSONObject postRequest = new JSONObject();
        Log.d("asd","sent");
        try {
            postRequest.put("username", "us");
            postRequest.put("password", "pas");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        PostRequester sender = null;
        try {

            sender = new PostRequester("http://37.139.24.39/index.php?huh=alice",  Helpers.JSONObjectToString(postRequest), new PostRequester.Answer() {

                @Override
                public void onSuccess(JSONObject answer) {
                    Log.d("Trash", "Trash");
                    String UId =  answer.toString();
                    Log.d("Trash", UId.toString());
                    // savePreferences("UId", UId);


                }

            }, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sender.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        Toast.makeText(getActivity(), String.valueOf(sharedPreferences.getString("UId", "")), Toast.LENGTH_SHORT).show();
//
//        if(String.valueOf(sharedPreferences.getString("UId", ""))!=null){
//            Intent intent = new Intent(getActivity(), HelpLawyerActivity.class);
//            startActivity(intent);
//        }


//        Log.d("NewLog", String.valueOf(PostRequester.id));

    }

}



