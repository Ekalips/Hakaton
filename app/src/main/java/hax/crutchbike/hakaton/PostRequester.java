package hax.crutchbike.hakaton;

/**
 * Created by lantain on 17.03.16.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by slava on 24.07.15.
 */
class PostRequester extends AsyncTask<String,Void,Void> {

    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    protected String mUrl;
    protected String mPostRequest;
    protected Answer mAnswer;
    protected JSONObject jsonAnswer;
    protected Context context;
    protected String mMethod;


    public PostRequester(String mUrl, String requst, Answer answer, Context applicationContext) {
        this.mUrl = mUrl;
        this.mPostRequest = requst;
        this.mAnswer = answer;
        this.context = applicationContext;
    }

    public PostRequester(String mUrl, String requst, Answer answer, Context applicationContext,String method) {
        this.mUrl = mUrl;
        this.mPostRequest = requst;
        this.mAnswer = answer;
        this.context = applicationContext;
        this.mMethod = method;
    }

    @Override
    protected Void doInBackground(String... params) {
        HttpURLConnection connection = null;
        InputStream input =null;
        try {

            URL url = new URL(mUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);

            if(mMethod != PostRequester.GET){
                connection.setDoOutput(true);
            }

            if(mMethod != null){
                connection.setRequestMethod(mMethod);
            }
//            connection.setRequestMethod("POST");
            Log.d("Logs", String.valueOf(url));
            Log.d("Logs",mPostRequest);
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream ());
            wr.writeBytes(mPostRequest);
            wr.flush();
            wr.close();

            input = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(input));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            jsonAnswer = new JSONObject(String.valueOf(response));

            rd.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    protected void onPostExecute(Void result) {
        if(jsonAnswer != null){
            this.mAnswer.onSuccess(jsonAnswer);
        }

    }

    public interface Answer {
        void onSuccess(JSONObject answer);
    }

}
