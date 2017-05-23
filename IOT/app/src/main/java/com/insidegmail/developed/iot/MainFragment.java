package com.insidegmail.developed.iot;

/**
 * Created by Admin on 16.05.2017.
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Observable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    MyCallBackMainFragment myCallBack;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    String getdata;
    View alert_view;
    EditText automat_id, locationx, locationy, status, user_count, water_level;
    String automat_id_str, locationx_str, locationy_str, status_str, user_count_str, water_level_str;
    public MainFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        getdata = getResources().getString(R.string.get_data);
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        alert_view = inflater.inflate(R.layout.alert_dialog, null);
        MyAsynkTask asynkTask = new MyAsynkTask();
        asynkTask.execute();

        setHasOptionsMenu(true);



        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                myCallBack.logOut();
                return true;

        }
        return false;
    }


    public interface MyCallBackMainFragment{

        void logOut();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyCallBackMainFragment) {
            myCallBack = (MyCallBackMainFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MyCallBackMainFragment");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        myCallBack = null;
    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        ArrayList<Automats> arrayList;

        public MyAdapter() {

            arrayList = new ArrayList<>();

            notifyDataSetChanged();

        }

        public void addNews(Automats automats) {
            arrayList.add(automats);
            notifyDataSetChanged();

        }
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyAdapter.ViewHolder holder, final int position) {

            holder.locy.setText(arrayList.get(position).getLocationy());
            holder.locx.setText(arrayList.get(position).getLocationx());
            holder.waterlvl.setText(arrayList.get(position).getWaterlevel());
            holder.status.setText(arrayList.get(position).getStatus());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                    alertDialog.setTitle("Set information");
                    alertDialog.setIcon(R.drawable.alert_icon);
                    alertDialog.setCancelable(false);
                    //alertDialog.setMessage("");

//FIND elements
                   automat_id = (EditText) alert_view.findViewById(R.id.automat_id);
                    locationx = (EditText) alert_view.findViewById(R.id.locx);
                   locationy = (EditText) alert_view.findViewById(R.id.locy);
                     status = (EditText) alert_view.findViewById(R.id.status);
                     user_count = (EditText) alert_view.findViewById(R.id.user_count);
                     water_level = (EditText) alert_view.findViewById(R.id.water_lvl);
//SET text to elements
                    automat_id.setText(arrayList.get(position).getId());
                    locationx.setText(arrayList.get(position).getLocationx());
                    locationy.setText(arrayList.get(position).getLocationy());
                    status.setText(arrayList.get(position).getStatus());
                    user_count.setText(arrayList.get(position).getUsercount());
                    water_level.setText(arrayList.get(position).getWaterlevel());
//SET non editable
                    automat_id.setKeyListener(null);

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((ViewGroup)alert_view.getParent()).removeView(alert_view);

                            automat_id_str = automat_id.getText().toString();
                            locationx_str = locationx.getText().toString();
                            locationy_str = locationy.getText().toString();
                            status_str = status.getText().toString();
                            user_count_str = user_count.getText().toString();
                            water_level_str = water_level.getText().toString();
                            automat_id_str = automat_id.getText().toString();

                            AutomatsAsynk sasynk = new AutomatsAsynk();
                            sasynk.execute(MainActivity.token);



                            alertDialog.dismiss();

                        }
                    });


                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((ViewGroup)alert_view.getParent()).removeView(alert_view);
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.setView(alert_view);
                    alertDialog.show();


                }
            });
        }



        @Override
        public int getItemCount() {
            return arrayList.size();
        }
        //Определяем елементы
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView locy;
            TextView locx;
            TextView waterlvl;
            TextView status;
            TextView usercount;
            TextView id;

            //Определяем title, article, image;
            public ViewHolder(View itemView) {
                super(itemView);
                locy = (TextView) itemView.findViewById(R.id.locy);
                locx = (TextView) itemView.findViewById(R.id.locx);
                waterlvl = (TextView) itemView.findViewById(R.id.water_lvl);
                status = (TextView) itemView.findViewById(R.id.status);
            }
        }
    }

    class MyAsynkTask extends AsyncTask<Void, Void, StringBuilder> {
        ProgressDialog progressDialog ;
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(getdata);
            progressDialog.show();
        }
        @Override
        //работа в бекграунде
        protected StringBuilder doInBackground(Void... voids) {
            StringBuilder stringBuilder = new StringBuilder();

            try {
                URL url = new URL(Str.GET_AUTOAMATS);
                URLConnection uc = url.openConnection();
                uc.connect();
                BufferedInputStream in = new BufferedInputStream(uc.getInputStream());
                int ch;
                while ((ch = in.read()) != -1) {
                    stringBuilder.append((char) ch);
                }
            } catch (Exception e) {
            }
            return stringBuilder;
        }

        @Override
        protected void onPostExecute(StringBuilder stringBuilder) {

            try {
                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                JSONArray array = jsonObject.getJSONArray("automates");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String locationy = object.getString("locationy");
                    String locationx = object.getString("locationx");
                    String waterlevel = object.getString("waterlevel");
                    String lastupdate = object.getString("lastupdate");
                    String usercount = object.getString("usercount");
                    String status = object.getString("status");
                    String id = object.getString("id");
                   // if(status.contains("bad")) {
                    Automats news = new Automats(locationx, locationy, waterlevel ,lastupdate ,usercount ,status,id );
                    myAdapter.addNews(news);
                    myAdapter.notifyDataSetChanged();

                }
                progressDialog.dismiss();

            } catch (Exception e) {

            }
        }
    }

    class AutomatsAsynk extends AsyncTask<String,Void,Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            String token = strings[0];
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            String date = df.format(Calendar.getInstance().getTime());

            JSONObject post = new JSONObject();
            try{

                post.put("locationy",locationy_str);
                post.put("waterlevel",water_level_str);
                post.put("locationx",locationx_str);
                post.put("usercount",user_count_str);
                post.put("id",automat_id_str);
                post.put("lastupdate",date);
                post.put("status",status_str);

                JSONObject object = Util.getResponse(Str.EDIT_AUTOMAT,post);
                if(object.getString("status").equals("OK")){
                    return true;
                }
            }catch (Exception e){
                System.out.println(e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

        }
    }
}
