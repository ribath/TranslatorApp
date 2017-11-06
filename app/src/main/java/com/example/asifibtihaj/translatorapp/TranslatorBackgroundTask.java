package com.example.asifibtihaj.translatorapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by asif.ibtihaj on 11/2/2017.
 */

public class TranslatorBackgroundTask extends AsyncTask<String, Void, String> {
    //Declare Context
    Context ctx;
    TextView textView;
    String resultString;
    //Set Context
    TranslatorBackgroundTask(Context ctx, TextView textView){
        this.ctx = ctx;
        this.textView = textView;
    }

    @Override
    protected String doInBackground(String... params) {
        //String variables
        String textToBeTranslated = params[0];
        String languagePair = params[1];
        String TAG = "AsyncTask_Translation";
        String jsonString;

        try {
            //Set up the translation call URL
            String yandexKey = "trnsl.1.1.20171102T052914Z.ed475252625aa7e8.3a2d8e16c0249c36870e0d26b8d2895f499bda47";
            String yandexUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + yandexKey
                    + "&text=" + textToBeTranslated + "&lang=" + languagePair;
            URL yandexTranslateURL = new URL(yandexUrl);

            //Set Http Conncection, Input Stream, and Buffered Reader
            HttpURLConnection httpJsonConnection = (HttpURLConnection) yandexTranslateURL.openConnection();
            InputStream inputStream = httpJsonConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //Set string builder and insert retrieved JSON result into it
            StringBuilder jsonStringBuilder = new StringBuilder();
            while ((jsonString = bufferedReader.readLine()) != null) {
                jsonStringBuilder.append(jsonString + "\n");
            }

            //Close and disconnect
            bufferedReader.close();
            inputStream.close();
            httpJsonConnection.disconnect();

            //Making result human readable
            resultString = jsonStringBuilder.toString().trim();
            Log.i(TAG+"1-", resultString);
            resultString = resultString.substring(resultString.indexOf('[')+1);
            //Getting the characters between [ and ]
            resultString = resultString.substring(0,resultString.indexOf("]"));
            //Getting the characters between " and "
            resultString = resultString.substring(resultString.indexOf("\"")+1);
            resultString = resultString.substring(0,resultString.indexOf("\""));

            Log.i(TAG+"2-", resultString);
            return resultString;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        textView.setText(resultString);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
