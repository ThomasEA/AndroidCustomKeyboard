package me.worksit.keyboardapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import me.worksit.keyboardapp.ui.Dialog;

public class MainActivity extends AppCompatActivity {

    private Button btnShowDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShowDialog = (Button) findViewById(R.id.button_show_dialog);
        btnShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dlg = new Dialog();
                dlg.showKeyboardBox(MainActivity.this);
            }
        });
    }
}
