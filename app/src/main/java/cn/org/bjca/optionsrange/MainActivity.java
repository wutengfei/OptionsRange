package cn.org.bjca.optionsrange;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText yuanJia, oldNumber, xianJia, newNumber, newZhangDieFu, oldZhangDieFu, et_zongJia, et_junJia, et_new_buy_price;
    private TextView tv_huiBenXuZhang;
    private DecimalFormat df;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        yuanJia = findViewById(R.id.yuanJia);
        oldNumber = findViewById(R.id.yuanNumber);
        xianJia = findViewById(R.id.xianJia);
        newNumber = findViewById(R.id.newNumber);
        newZhangDieFu = findViewById(R.id.newZhangDieFu);
        oldZhangDieFu = findViewById(R.id.oldZhangDieFu);
        et_zongJia = findViewById(R.id.et_zongJia);
        et_junJia = findViewById(R.id.et_junJia);
        tv_huiBenXuZhang = findViewById(R.id.tv_huiBenXuZhang);
        et_new_buy_price = findViewById(R.id.et_new_buy_price);
        btn_next = findViewById(R.id.next);
        btn_next.setOnClickListener(this);
    }

    public void calculateRange(View view) {
        if (TextUtils.isEmpty(yuanJia.getText().toString().trim()) || TextUtils.isEmpty(oldNumber.getText().toString().trim()) ||
                TextUtils.isEmpty(xianJia.getText().toString().trim()) || TextUtils.isEmpty(newNumber.getText().toString().trim())) {
            Toast.makeText(this, "原价、原数量、现价、新购买数量不能为空", Toast.LENGTH_SHORT).show();
        } else {
            double yuanjia = Double.parseDouble(yuanJia.getText().toString().trim());
            double oldnumber = Double.parseDouble(oldNumber.getText().toString().trim());
            double xianjia = Double.parseDouble(xianJia.getText().toString().trim());
            double newnumber = Double.parseDouble(newNumber.getText().toString().trim());
            //四舍五入保留两位小数
            df = new DecimalFormat("#.00");

            double yuandiefu = -(1 - xianjia / yuanjia);
            oldZhangDieFu.setText(df.format(yuandiefu * 100) + "%");

            double diefu = -((yuanjia * oldnumber - oldnumber * xianjia) / (yuanjia * oldnumber + xianjia * newnumber));
            newZhangDieFu.setText(df.format(diefu * 100) + "%");

            double zongJia = yuanjia * oldnumber + xianjia * newnumber;
            et_zongJia.setText(df.format(zongJia));

            double newJunJia = zongJia / (oldnumber + newnumber);
            et_junJia.setText(df.format(newJunJia));

            double newBuyPrice = xianjia * newnumber;
            et_new_buy_price.setText(df.format(newBuyPrice));

            double huiBenXuZhang = 1 / (1 + diefu) - 1;//回本所需涨幅
            tv_huiBenXuZhang.setText(df.format(huiBenXuZhang * 100) + "%");


        }
    }

    public void calculateNum(View view) {
        if (TextUtils.isEmpty(yuanJia.getText().toString().trim()) || TextUtils.isEmpty(oldNumber.getText().toString().trim()) ||
                TextUtils.isEmpty(xianJia.getText().toString().trim()) || TextUtils.isEmpty(newZhangDieFu.getText().toString().trim())) {
            Toast.makeText(this, "原价、原数量、现价、新涨跌幅不能为空", Toast.LENGTH_SHORT).show();
        } else {
            double yuanjia = Double.parseDouble(yuanJia.getText().toString().trim());
            double oldnumber = Double.parseDouble(oldNumber.getText().toString().trim());
            double xianjia = Double.parseDouble(xianJia.getText().toString().trim());
            double diefu = Double.parseDouble(newZhangDieFu.getText().toString().trim().replace("%", "")) / 100;
            double huiBenXuZhang = 1 / (1 + diefu) - 1;//回本所需涨幅
            df = new DecimalFormat("#.00");

            double yuandiefu = -(1 - xianjia / yuanjia);
            oldZhangDieFu.setText(df.format(yuandiefu * 100) + "%");

            double newnumber = -oldnumber * (yuanjia - xianjia + diefu * yuanjia) / diefu / xianjia;
            newNumber.setText(df.format(newnumber));

            tv_huiBenXuZhang.setText(df.format(huiBenXuZhang * 100) + "%");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                startActivity(new Intent(this, SecondActivity.class));
        }
    }
}