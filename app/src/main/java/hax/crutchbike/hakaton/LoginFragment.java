package hax.crutchbike.hakaton;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class LoginFragment extends Fragment {
    TextInputLayout textInputLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        textInputLayout = (TextInputLayout) view.findViewById(R.id.input_layout_login);
        return view;
    }

    public TextInputLayout GetLoginLay()
    {
        return textInputLayout;
    }
}
