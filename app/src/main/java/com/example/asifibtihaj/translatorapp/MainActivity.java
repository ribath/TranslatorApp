package com.example.asifibtihaj.translatorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView helo;
    EditText editText;
    Button submit_button;
    Spinner from_spinner, to_spinner;
    String from, to;
    String TAG = "MainActivity_Translation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helo = (TextView)findViewById(R.id.helo);
        editText = (EditText)findViewById(R.id.editText);
        submit_button = (Button)findViewById(R.id.submit_button);
        from_spinner = (Spinner)findViewById(R.id.from_spinner);
        to_spinner = (Spinner)findViewById(R.id.to_spinner);

        List<String> languages = new ArrayList<String>();
        languages.add("English");
        languages.add("Bengali");
        languages.add("French");
        languages.add("Spanish");
        languages.add("Russian");
        languages.add("Portuguese");
        languages.add("Chinese");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        from_spinner.setAdapter(dataAdapter);
        to_spinner.setAdapter(dataAdapter);

        from_spinner.setOnItemSelectedListener(this);
        to_spinner.setOnItemSelectedListener(this);

        from_spinner.setSelection(0);
        to_spinner.setSelection(1);

        editText.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            SubmitButtonAction();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitButtonAction();
            }
        });
    }

    void Translate(String textToBeTranslated,String languagePair){
        TranslatorBackgroundTask translatorBackgroundTask= new TranslatorBackgroundTask(this, helo);
        translatorBackgroundTask.execute(textToBeTranslated,languagePair); // Returns the translated text as a String
    }

    void SubmitButtonAction()
    {
        String textToBeTranslated = editText.getText().toString();
        String languagePair = from+"-"+to;
        Log.i(TAG, "languagePair - "+languagePair);
        //Executing the translation function
        Translate(textToBeTranslated.replace(" ", "%20"),languagePair);
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(this.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(adapterView.getId())
        {
            case R.id.from_spinner:
                int from_possition = (int) adapterView.getSelectedItemId();
                Log.i(TAG, "Item-" + from + " selected at first spinner");
                if(from_possition==0)
                {
                    from = "en";
                }
                if(from_possition==1)
                {
                    from = "bn";
                }
                if(from_possition==2)
                {
                    from = "fr";
                }
                if(from_possition==3)
                {
                    from = "es";
                }
                if(from_possition==4)
                {
                    from = "ru";
                }
                if(from_possition==5)
                {
                    from = "pt";
                }
                if(from_possition==6)
                {
                    from = "zh";
                }
                break;
            case R.id.to_spinner:
                int to_possition = (int) adapterView.getSelectedItemId();
                if(to_possition==0)
                {
                    to = "en";
                }
                if(to_possition==1)
                {
                    to = "bn";
                }
                if(to_possition==2)
                {
                    to = "fr";
                }
                if(to_possition==3)
                {
                    to = "es";
                }
                if(to_possition==4)
                {
                    to = "ru";
                }
                if(to_possition==5)
                {
                    to = "pt";
                }
                if(to_possition==6)
                {
                    to = "zh";
                }
                Log.i(TAG, "Item-" + to + " selected at second spinner");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(adapterView.getContext(), "you need to select both languages", Toast.LENGTH_LONG).show();
    }
}
