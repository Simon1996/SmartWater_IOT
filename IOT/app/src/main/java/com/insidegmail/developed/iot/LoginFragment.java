package com.insidegmail.developed.iot;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {


    EditText edtLogin;
    EditText edtPassword;
    Button btnLogin;
    MyCallBack myCallBack;



    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        edtLogin = (EditText)view.findViewById(R.id.input_email);
        edtPassword = (EditText)view.findViewById(R.id.input_password);
        btnLogin = (Button)view.findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        MyAsynkTask asynkTask = new MyAsynkTask();
        asynkTask.execute(edtLogin.getText().toString(),edtPassword.getText().toString());
    }

    public class MyAsynkTask extends AsyncTask<String,Void,String> {

        ProgressDialog progressDialog ;
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Logging....");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String login = strings[0];
            String password = strings[1];
            JSONObject object = Util.getResponse(Str.GET_TOKEN_LINK+login+"/"+password);
            String token = Util.getToken(object);
            return token;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if(s!=null){
                Log.wtf("TOKEN",s);
                myCallBack.openMainFragment(s);
            }else{
                Toast.makeText(getActivity().getApplicationContext(),"No such login&password combination",Toast.LENGTH_SHORT).show();
            }
        }
    }



    public interface MyCallBack{

        void openMainFragment(String token);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyCallBack) {
            myCallBack = (MyCallBack) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MyCallBack");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        myCallBack = null;

    }


}
