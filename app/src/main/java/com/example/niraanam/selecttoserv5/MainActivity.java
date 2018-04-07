package com.example.niraanam.selecttoserv5;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.erikagtierrez.multiple_media_picker.Gallery;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final int OPEN_MEDIA_PICKER = 1; // The request code

    int checkSelected;
    ArrayList<String> selectionResult;
    TextView txtv;
    StringBuilder builder;

    ListView listView;
    ArrayList<Spacecraft> spacecrafts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                Intent intent= new Intent(MainActivity.this, Gallery.class);
                // Set the title
                intent.putExtra("title","Select media");
                // Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
                intent.putExtra("mode",2);
                intent.putExtra("maxSelection",100);
                startActivityForResult(intent,OPEN_MEDIA_PICKER);
            }
        });


        txtv = (TextView) findViewById(R.id.textView);
        builder = new StringBuilder();
        listView= (ListView) findViewById(R.id.listview);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openGallery(View view) {
        Intent intent= new Intent(this, Gallery.class);
        // Set the title
        intent.putExtra("title","Select media");
        // Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
        intent.putExtra("mode",2);
        intent.putExtra("maxSelection",100);
        startActivityForResult(intent,OPEN_MEDIA_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == OPEN_MEDIA_PICKER) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK && data != null) {
                selectionResult = data.getStringArrayListExtra("result");
                checkSelected = selectionResult.size();

                for(int i=0;i<checkSelected;i++){

                    builder.append(selectionResult.get(i) + "\n");
                }
                txtv.setText(builder.toString());
                //Toast.makeText(getApplicationContext(), checkSelected+"\n"+selectionResult.toString(), Toast.LENGTH_LONG).show();

                listView.setAdapter(new CustomAdapter(MainActivity.this,getData()));
            }
        }
    }

    private ArrayList<Spacecraft> getData() {

        Spacecraft s;
        for (int j = 0; j < checkSelected; j++) {

            s = new Spacecraft();
            s.setName(selectionResult.get(j));
            //String tmp = selectionResult.get(j);
            s.setUri(Uri.fromFile(new File(selectionResult.get(j))));

            spacecrafts.add(s);
        }
        return spacecrafts;
    }
}
