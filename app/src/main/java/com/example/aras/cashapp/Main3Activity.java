package com.example.aras.cashapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.aras.cashapp.R.id.list;
import static com.example.aras.cashapp.R.id.total;

public class Main3Activity extends AppCompatActivity {
    EditText e1;
    EditText e2;
    EditText e3;
    Button bt;
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    double sum = 0d;
    int skatt;
    int timlon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        e1 = (EditText) findViewById(R.id.timmar);
        e2 = (EditText) findViewById(R.id.rast);
        e3 = (EditText) findViewById(R.id.datum);
        bt = (Button) findViewById(R.id.spara);
        listView = (ListView) findViewById(list);
        Intent intent = getIntent();
        skatt = intent.getIntExtra("skatt", 0);
        timlon = intent.getIntExtra("timlon", 0);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(Main3Activity.this, android.R.layout.simple_list_item_1,
                arrayList);
        listView.setAdapter(adapter);

        onBtnClick();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String deleteTotal = arrayList.get(position);
                String[] parts = deleteTotal.split(" ");
                double tim = Double.valueOf(parts[1]);
                double ra =Double.valueOf(parts[2]);

                sum = sum - ((double) tim - ((double) ra / 60f));

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                database.getReference("sum").setValue(Double.toString(sum));
                database.getReference("entry").child(parts[0]).removeValue();
                arrayList.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                arrayList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String result = child.getKey() + " " + child.child("timmar").getValue() + " " + child.child("rast").getValue();
                    arrayList.add(result);
                }


                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };


        ValueEventListener sumListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TextView t1 = (TextView) findViewById(total);
                String sumStr = (String) dataSnapshot.getValue();
//                Log.d("sum", String.valueOf(sum));
                t1.setText(sumStr);
                sum = Double.parseDouble(sumStr);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("entry");
        myRef.addValueEventListener(postListener);

        DatabaseReference sumRef = database.getReference("sum");
       sumRef.addValueEventListener(sumListener);



    }

    public void startButtonClick(View view) {
        Intent intent = new Intent(this, Main4Activity.class);
        intent.putExtra("summa", sum);
        intent.putExtra("timlon", timlon);
        intent.putExtra("skatt", skatt);


        startActivity(intent);

    }

    public void onBtnClick() {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String rast = e2.getText().toString();
                String datum = e3.getText().toString();
                String timmar = e1.getText().toString();
                String result = datum + "                " + timmar + "              " + rast + "                ";
                arrayList.add(result);
                adapter.notifyDataSetChanged();

                EditText e1 = (EditText) findViewById(R.id.timmar);
                EditText e2 = (EditText) findViewById(R.id.rast);

                int tim = Integer.parseInt(e1.getText().toString());
                int ra = Integer.parseInt(e2.getText().toString());
                sum = sum + (double) tim - ((double) ra / 60f);



                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("entry").child(datum);

                myRef.child("timmar").setValue(timmar);
                myRef.child("rast").setValue(rast);

                database.getReference("sum").setValue(Double.toString(sum));
            }


        });
    }


}
