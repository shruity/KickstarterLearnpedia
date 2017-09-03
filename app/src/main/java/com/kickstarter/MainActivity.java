package com.kickstarter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    Context context;
    ProgressDialog pDialog;
    private static String url = "http://starlord.hackerearth.com/kickstarter";
    private String TAG = MainActivity.class.getSimpleName();
    DataModel dataModel;
    ArrayList<DataModel> dataList;
    RecyclerView recyclerView;
    DataAdapter adapter;
    RadioGroup rg_sort;
    RadioButton rb_amt_pledge, rb_sno, rb_num_backers;
    int sorting = 1;
    SearchView searchView;
    int num_backers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context         = this;
        dataList        = new ArrayList<>();
        searchView      = (SearchView) findViewById(R.id.searchView);
        recyclerView    = (RecyclerView) findViewById(R.id.rv_data);
        rg_sort         = (RadioGroup) findViewById(R.id.rg_type);
        rb_amt_pledge   = (RadioButton) findViewById(R.id.rb_amt_pledge);
        rb_sno          = (RadioButton) findViewById(R.id.rb_sno);
        rb_num_backers  = (RadioButton) findViewById(R.id.rb_num_backers);

        new GetData().execute();

        rg_sort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_amt_pledge) {
                    sorting = 1;
                    Collections.sort(dataList, new Comparator<DataModel>() {
                        public int compare(DataModel s1, DataModel s2) {
                            //ascending order
                            return s1.getAmt_pledge() -(s2.getAmt_pledge());
                        }
                    });
                } else if (checkedId == R.id.rb_sno) {
                    sorting = 2;
                    Collections.sort(dataList, new Comparator<DataModel>() {
                        public int compare(DataModel s1, DataModel s2) {
                            //ascending order
                            return s1.getSno() -(s2.getSno());
                        }
                    });
                } else if (checkedId == R.id.rb_num_backers){
                    sorting = 3;
                    Collections.sort(dataList, new Comparator<DataModel>() {
                        public int compare(DataModel s1, DataModel s2) {
                            return (s1.getNum_backers()).compareTo(s2.getNum_backers());
                        }
                    });
                }
                adapter.notifyDataSetChanged();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (TextUtils.isEmpty(newText)) {
                    adapter.getFilter().filter("");
                    adapter = new DataAdapter(context,dataList);
                    recyclerView.setAdapter(adapter);

                    adapter.notifyDataSetChanged();
                } else {
                    adapter.getFilter().filter(newText.toString());
                }
                return true;
            }
        });

    }

    public class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            if(!pDialog.isShowing())
                pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject c = jsonArray.getJSONObject(i);

                        int sno                  = c.getInt("s.no");
                        int amt_pledged          = c.getInt("amt.pledged");
                        String blurb             = c.getString("blurb");
                        String by                = c.getString("by");
                        String country           = c.getString("country");
                        String currency          = c.getString("currency");
                        String end_time          = c.getString("end.time");
                        String location          = c.getString("location");
                        String percentage_funded = c.getString("percentage.funded");
                        String num_backers       = c.getString("num.backers");

                        String state             = c.getString("state");
                        String title             = c.getString("title");
                        String type              = c.getString("type");
                        String url               = c.getString("url");

                        dataModel = new DataModel();
                        dataModel.setSno(sno);
                        dataModel.setAmt_pledge(amt_pledged);
                        dataModel.setBlurb(blurb);
                        dataModel.setBy(by);
                        dataModel.setCountry(country);
                        dataModel.setCurrency(currency);
                        dataModel.setEnd_time(end_time);
                        dataModel.setLocation(location);
                        dataModel.setPercentage_funded(percentage_funded);
                        dataModel.setNum_backers(num_backers);
                        dataModel.setState(state);
                        dataModel.setTitle(title);
                        dataModel.setType(type);
                        dataModel.setUrl(url);

                        dataList.add(dataModel);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            if (sorting == 1) {
                Collections.sort(dataList, new Comparator<DataModel>() {
                    public int compare(DataModel s1, DataModel s2) {
                        //ascending order
                        return s1.getAmt_pledge() -(s2.getAmt_pledge());
                    }
                });
            } else if (sorting == 2) {
                Collections.sort(dataList, new Comparator<DataModel>() {
                    public int compare(DataModel s1, DataModel s2) {
                        //ascending order
                        return s1.getSno() -(s2.getSno());
                    }
                });
            }
            else if (sorting == 3){
                Collections.sort(dataList, new Comparator<DataModel>() {
                    public int compare(DataModel s1, DataModel s2) {
                        return (s1.getNum_backers()).compareTo(s2.getNum_backers());
                    }
                });
            }
            adapter = new DataAdapter(context,dataList);
            recyclerView.setAdapter(adapter);
        }

    }

}
