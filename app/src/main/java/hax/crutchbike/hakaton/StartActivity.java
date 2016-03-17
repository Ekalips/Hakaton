package hax.crutchbike.hakaton;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.TabLayout;

import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rey.material.widget.RippleManager;

public class StartActivity extends AppCompatActivity {
    RippleManager mRippleManager;

    FragmentTransaction fTrans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);



        final LoginFragment loginFragment = new LoginFragment();
        fTrans = getFragmentManager().beginTransaction();
        fTrans.replace(R.id.fragmCont, loginFragment);
        fTrans.addToBackStack(null);
        fTrans.commit();
        final RegistrationFragment registrationFragment = new RegistrationFragment();
        Transition changeTransform = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
             changeTransform = TransitionInflater.from(this).
                    inflateTransition(R.transition.transform);
        }
        //final TextInputLayout textInputLayout = (TextInputLayout)loginFragment.getView().findViewById(R.id.input_layout_login);
        final Transition finalChangeTransform = changeTransform;
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {

                } else {
                    // Setup exit transition on first fragment
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        loginFragment.setSharedElementReturnTransition(finalChangeTransform);
                    }
                    // loginFragment.setExitTransition(explodeTransform);

                    // Setup enter transition on second fragment
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        registrationFragment.setSharedElementEnterTransition(finalChangeTransform);
                    }
                    // registrationFragment.setEnterTransition(explodeTransform);

                    FragmentTransaction ft = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        ft = getFragmentManager().beginTransaction()
                                .replace(R.id.fragmCont, registrationFragment)
                                .addToBackStack("transaction")
                        .addSharedElement(loginFragment.GetLoginLay(),"login");
                    }
                    // Apply the transaction
                    ft.commit();
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
}



