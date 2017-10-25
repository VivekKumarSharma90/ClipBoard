package com.vivek.clipboard;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    ClipboardManager clipboardManager;
    ClipData clipData;
    TextView copy, paste_here;
    Button button_copy, button_paste;
    String selectedString;
    int startSelection, endSelection, version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        copy = (TextView) findViewById(R.id.copy);
        paste_here = (TextView) findViewById(R.id.paste_here);
        button_copy = (Button) findViewById(R.id.button_copy);
        button_paste = (Button) findViewById(R.id.button_paste);

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        version = android.os.Build.VERSION_CODES.HONEYCOMB;

        button_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("startSelection" + startSelection);
                System.out.println("endSelection" + endSelection);

                if (copy.getText().toString() != null && endSelection > startSelection) {
                    selectedString = copy.getText().toString().substring(startSelection, endSelection);
                    System.out.println("selectedString" + selectedString);
                    if (version <= android.os.Build.VERSION_CODES.HONEYCOMB)
                        clipboardManager.setText(selectedString);
                    else {
                        clipData = ClipData.newPlainText("text", selectedString);
                        clipboardManager.setPrimaryClip(clipData);
                    }
                }
            }
        });

        copy.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEvent.ACTION_UP == motionEvent.getAction()) {
                    if (copy.hasSelection()) {
                        startSelection = copy.getSelectionStart();
                        endSelection = copy.getSelectionEnd();

                        /*System.out.println("startSelection" + startSelection);
                        System.out.println("endSelection" + endSelection);*/
                    }
                }
                return false;
            }
        });

       /* copy.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                startSelection = copy.getSelectionStart();
                endSelection = copy.getSelectionEnd();

                System.out.println("startSelection" + startSelection);
                System.out.println("endSelection" + endSelection);

                return false;
            }
        });*/

        button_paste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paste_here.setText(clipboardManager.getPrimaryClip().getItemAt(0).getText().toString());
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
