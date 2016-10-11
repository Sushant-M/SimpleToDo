package com.example.sushant.simpletodo;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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
       // if (id == R.id.action_add) {

         //   return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        String item_data = null;
        String value[] = new String[20];
        int valuecounter =0;
        String[] IDs = new String[20];
        String[] Pending = new String[20];
        String[] Done = new String[20];
        private String TAG = "Fragment";
        private static final String ARG_SECTION_NUMBER = "section_number";
        Adapter mAdapter;
        RecyclerView mRecyclerView;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            super.setHasOptionsMenu(true);
        }



        protected void showInputDialog() {

            // get prompts.xml view
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View promptView = layoutInflater.inflate(R.layout.prompts, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setView(promptView);

            final EditText editText = (EditText) promptView.findViewById(R.id.editTextDialogUserInput);
            // setup a dialog window
            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String newEntry = editText.getText().toString();
                            Log.d(TAG,newEntry);
                            value[valuecounter+1] = newEntry;
                            mAdapter.notifyDataSetChanged();

                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }


        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            super.onCreateOptionsMenu(menu, inflater);
            Log.d(TAG,"menu");
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            switch (id){
                case R.id.action_add:
                    Log.d(TAG,"lolol");
                    showInputDialog();
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            int value = getArguments().getInt(ARG_SECTION_NUMBER);
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            //mRecyclerView.setHasFixedSize(true);

            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);


            TextView text = (TextView)rootView.findViewById(R.id.type);
            text.setText("Hello from side" + value);
            new GetData().execute();

            return rootView;
        }


        public class GetData extends AsyncTask<String,Integer,String[]> {
            private String TAG = "GetData";
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String[] strings) {

                mAdapter = new Adapter(strings);
                mRecyclerView.setAdapter(mAdapter);
                value = strings;
                valuecounter = 2;
                super.onPostExecute(strings);
            }

            @Override
            protected String[] doInBackground(String... params) {

                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                try{
                    final String baseQuery = "https://dl.dropboxusercontent.com/u/6890301/tasks.json";


                    Uri builtUri = Uri.parse(baseQuery).buildUpon().build();
                    URL url = new URL(builtUri.toString());

                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                        return null;
                    }
                    item_data = buffer.toString();
                    //Log.d(TAG,item_data);
                }catch (IOException e){
                    e.printStackTrace();
                }finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                            Log.e(TAG, "Error closing stream", e);
                        }
                    }
                }

                try {
                    ParseType(item_data);
                    return ParseJSON(item_data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return new String[0];
            }

            public String[] ParseType(String rawData) throws JSONException{
                String key = "data";
                if(rawData != null) {
                    JSONObject Obj = new JSONObject(rawData);
                    JSONArray Arr = Obj.getJSONArray(key);
                    String[] parsedData = new String[20];

                    for (int i = 0; i < Arr.length(); i++) {
                        JSONObject object = Arr.getJSONObject(i);
                        String temp = object.getString("state");
                        parsedData[i] = temp;
                        Log.d(TAG, temp);
                    }
                    IDs = parsedData;
                    return parsedData;
                }
                return new String[0];
            }
            }



            public String[] ParseJSON(String rawData) throws JSONException{

                String key = "data";
                if(rawData != null) {
                    JSONObject Obj = new JSONObject(rawData);
                    JSONArray Arr = Obj.getJSONArray(key);
                    String[] parsedData = new String[20];

                    for (int i = 0; i < Arr.length(); i++) {
                        JSONObject object = Arr.getJSONObject(i);
                        String temp = object.getString("name");
                        parsedData[i] = temp;
                        Log.d(TAG, temp);
                    }
                    return parsedData;
                }
                return new String[0];
            }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
            }
            return null;
        }
    }



}
