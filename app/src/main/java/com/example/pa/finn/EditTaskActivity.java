package com.example.pa.finn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (getIntent().getIntExtra("id", 0)) {
                    case 1:
                        ((TextView) view.findViewById(R.id.tvTaskTitle1)).setText(((EditText) findViewById(R.id.etMed)).getText().toString());
                        ((TextView) view.findViewById(R.id.tvTaskHour1)).setText(((EditText) findViewById(R.id.etTime)).getText().toString());
                        break;
                    case 2:
                        ((TextView) view.findViewById(R.id.tvTaskTitle2)).setText(((EditText) findViewById(R.id.etMed)).getText().toString());
                        ((TextView) view.findViewById(R.id.tvTaskHour2)).setText(((EditText) findViewById(R.id.etTime)).getText().toString());
                        break;
                    case 3:
                        ((TextView) view.findViewById(R.id.tvTaskTitle3)).setText(((EditText) findViewById(R.id.etMed)).getText().toString());
                        ((TextView) view.findViewById(R.id.tvTaskHour3)).setText(((EditText) findViewById(R.id.etTime)).getText().toString());
                        break;
                }

                finish();
            }
        });
    }
}
