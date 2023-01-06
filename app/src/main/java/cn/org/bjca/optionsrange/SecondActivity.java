package cn.org.bjca.optionsrange;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class SecondActivity extends AppCompatActivity {

    private EditText et_current_price, et_up, et_down;
    private TextView tv_result_up, tv_result_down;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        et_current_price = findViewById(R.id.et_current_price);
        et_up = findViewById(R.id.et_up);
        et_down = findViewById(R.id.et_down);
        tv_result_up = findViewById(R.id.tv_result_up);
        tv_result_down = findViewById(R.id.tv_result_down);
    }

    public void enter(View view) {
        double up;
        double down;
        if (TextUtils.isEmpty(et_current_price.getText().toString().trim())) {
            Toast.makeText(this, "请输入价格", Toast.LENGTH_SHORT).show();
            return;
        }
        double price = Double.parseDouble(et_current_price.getText().toString().trim());

        if (TextUtils.isEmpty(et_up.getText().toString().trim())) {
            up = 0;
        } else {
            up = Double.parseDouble(et_up.getText().toString().trim());
        }

        if (TextUtils.isEmpty(et_down.getText().toString().trim())) {
            down = 0;
        } else {
            down = Double.parseDouble(et_down.getText().toString().trim());
        }

        double result_up = price * (1 + up / 100);
        double result_down = price * (1 - down / 100);
        DecimalFormat df = new DecimalFormat("#.00");

        tv_result_up.setText(df.format(result_up));
        tv_result_down.setText(df.format(result_down));
    }
}