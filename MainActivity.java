package com.example.harshitgupta.naam;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.conversation.v1.model.Context;
import com.ibm.watson.developer_cloud.conversation.v1.model.InputData;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {


    EditText text;
    String response1, response2;

    ListView l4;
    ProgressBar bar1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
       text = (EditText)findViewById(R.id.text);
        l4 = (ListView) findViewById(R.id.l4);
        bar1 = (ProgressBar)findViewById(R.id.bar1);
        bar1.setVisibility(View.GONE);

     Task1 t = new Task1();
     t.execute("a123","","");

    }

    public void click(View v) {
        bar1.setVisibility(View.VISIBLE);
         Task t = new Task();
         t.execute();

    }


    class Task extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            Conversation service = new Conversation(Conversation.VERSION_DATE_2017_05_26);
            service.setUsernameAndPassword("424e2140-e6c8-4cd7-9589-edf84038e067", "xojcZizmzjy5");

            InputData input = new InputData.Builder("Hi").build();
            MessageOptions options = new MessageOptions.Builder("2a735dc5-0ec4-4079-ba23-7d629d86d8f0").input(input).build();
            MessageResponse response = service.message(options).execute();


            Context context = null;


            MessageOptions newMessageOptions = new MessageOptions.Builder()
                    .workspaceId("2a735dc5-0ec4-4079-ba23-7d629d86d8f0")
                    .input(new InputData.Builder(text.getText().toString()).build())
                    .context(context)
                    .build();

            response = service.message(newMessageOptions).execute();
            String res = response.toString();
            ArrayList<String> tmp = new ArrayList<>();
            try {
                JSONObject o1 = new JSONObject(res);
                JSONObject o2 = o1.getJSONObject("output");
                JSONArray a2 = o2.getJSONArray("text");
                JSONObject o3 = o1.getJSONObject("input");
                String tmp1 = o3.getString("text");


                for (int i = 0; i < a2.length(); i++) {
                    tmp.add(a2.getString(i));
                }
                response1 = tmp.toString();
                response1 = response1.replaceAll("\\p{P}", "");
                response2 = tmp1;


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Task1 t = new Task1();
            t.execute("a123",response1,response2);

        }
    }

    class Task1 extends AsyncTask<String, String, List<Model>> {

        @Override
        protected List<Model> doInBackground(String... strings) {
            HttpURLConnection x = null;
            BufferedReader br = null;
            String page_url = "http://studyforfun.000webhostapp.com/chatbot.php";
            try {

                String user = strings[0];
                String msg1 = strings[1];
                String msg2 = strings[2];

                URL url = new URL(page_url);
                x = (HttpURLConnection) url.openConnection();
                x.setRequestMethod("POST");
                x.setDoOutput(true);
                x.setDoInput(true);
                OutputStream outputStream = x.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8")
                        + "&" + URLEncoder.encode("msg1", "UTF-8") + "=" + URLEncoder.encode(msg1, "UTF-8")
                        + "&" + URLEncoder.encode("msg2", "UTF-8") + "=" + URLEncoder.encode(msg2, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = x.getInputStream();
                br = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = br.readLine()) != null) {
                    buffer.append(line);
                }
                br.close();
                inputStream.close();
                x.disconnect();
                String res = buffer.toString();

                List<Model> modellist = new ArrayList<>();
                JSONObject o1 = new JSONObject(res);
                JSONArray a1 = o1.getJSONArray("details");
                for (int i = 0; i < a1.length(); i++) {
                    Model model = new Model();
                    JSONObject o2 = a1.getJSONObject(i);
                    String id = o2.getString("id");
                    String message_1 =o2.getString("message1");
                    String message_2 = o2.getString("message2");
                    String user1 = o2.getString("user");
                    model.setId(id);
                    model.setUser(user1);
                    model.setMessage2(message_2);
                    model.setMessage1(message_1);


                    modellist.add(model);
                }

                return modellist;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            } finally {
                if (x != null) {
                    x.disconnect();
                }
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


            return null;
        }

        @Override
        protected void onPostExecute(List<Model> result) {
            super.onPostExecute(result);

            CustomAdapter ad = new CustomAdapter(getApplicationContext(), R.layout.messages, result);
            l4.setAdapter(ad);
            bar1.setVisibility(View.GONE);


        }
    }


    class CustomAdapter extends ArrayAdapter {
        public List<Model> modellist;
        private int resource;
        private LayoutInflater inflater;


        public CustomAdapter(android.content.Context context, int resource, List<Model> objects) {
            super(context, resource, objects);
            modellist = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.messages, null);
            }
       TextView res = (TextView)convertView.findViewById(R.id.res);
       TextView res1 = (TextView)convertView.findViewById(R.id.res1);


       String msg1 = modellist.get(position).getMessage1();
       String msg2 = modellist.get(position).getMessage2();


       res.setText(msg1);
       res1.setText(msg2);


            return  convertView;
        }
    }
}
