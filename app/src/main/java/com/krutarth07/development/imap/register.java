package com.krutarth07.development.imap;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;
import java.util.List;

public class register extends Activity {
    private int _Student_Id=0;

    Button marker;
    EditText name, desc;
    Spinner cat;
    String n,d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install((Application) getApplicationContext());

        marker = (Button)findViewById(R.id.marker);
        name = (EditText)findViewById(R.id.ename);
        desc = (EditText)findViewById(R.id.edesc);
        cat = (Spinner)findViewById(R.id.spinner);



        marker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences prefs = getSharedPreferences("names", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                n = name.getText().toString();
                d = desc.getText().toString();

                editor.putString("name", n);
                editor.putString("desc",d);
                editor.commit();

                List<String> l = new ArrayList<String>();

                manipulate repo = new manipulate(register.this);
                Rows rows = new Rows();
                rows.title= name.getText().toString();
                rows.desc=desc.getText().toString();
                _Student_Id = repo.insert(rows);

                l=repo.getStudentList();
                //repo.getStudentList();        //................................for the one entered
                //repo.getStudentList(16);       //...............................for specific id

                Toast.makeText(register.this, repo.id+repo.name+repo.desc, Toast.LENGTH_SHORT).show();

                //  for(int j=0;j<l.size();j++){
                //    Toast.makeText(register.this, l.get(j), Toast.LENGTH_SHORT).show();
                //}


                Intent i = new Intent(register.this,geomap.class);
                startActivity(i);

            }
        });
    }
}


