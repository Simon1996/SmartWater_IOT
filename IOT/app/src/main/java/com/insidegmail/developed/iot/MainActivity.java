package com.insidegmail.developed.iot;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements LoginFragment.MyCallBack,MainFragment.MyCallBackMainFragment {

    SharedPreferences preferences;
    public static String token;
    FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences= this.getPreferences(MODE_PRIVATE);

        if(!checkToken()){
            openLoginActivity();
        }else{
            openMainFragment();
        }


    }

    public boolean checkToken() {
        String myToken = preferences.getString("token", null);
        if (myToken != null) {
            token = myToken;
            return true;
        } else {
            return false;
        }
    }

    private void openLoginActivity() {
        fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,new LoginFragment());
        fragmentTransaction.commit();

    }

    public void openMainFragment() {
        fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,new MainFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void openMainFragment(String token) {
        fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,new MainFragment());
        fragmentTransaction.commit();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token",token);
        editor.commit();
        MainActivity.token = token;
    }

    @Override
    public void logOut() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token",null);
        editor.commit();
        openLoginActivity();
        token=null;
    }
}
