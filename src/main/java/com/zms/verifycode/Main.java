package com.zms.verifycode;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


public class Main extends ActionBarActivity {
    // Type-1:数值型验证码-ABCD; Type-2:计算型验证码-EFG

    private String numStrTmp = "";
    private String numStr = "";
    private int intResult = -100;   // Type-2：计算结果，-100为ErrorCode
    private int[] numArray = new int[4]; // Type-1:四位验证码数字
    private int[] intVerify = new int[3]; // 0,1是计算数值，2是运算符
    private int[] colorArray = new int[6];
    private String[] strVerify = new String[3];

    private TextView tvHideA;
    private TextView tvHideB;
    private TextView tvHideC;
    private TextView tvHideD;
    private TextView tvHideE;
    private TextView tvHideF;
    private TextView tvHideG;
    private TextView tvCheck;
    private TextView tvVerify;

    private ImageView ivNumA;
    private ImageView ivNumB;
    private ImageView ivNumC;
    private ImageView ivNumD;
    private ImageView ivNumE;
    private ImageView ivNumF;
    private ImageView ivNumG;

    private Button btnLock;
    private Button btnCheck;
    private Button btnVerify;
    private EditText etLock;
    private EditText etCheck;
    private EditText etVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnLock = (Button) findViewById(R.id.btnLock);
        etLock = (EditText) findViewById(R.id.etLock);
        btnLock.setOnClickListener(new onClickListenerImp());

        tvHideA = (TextView) findViewById(R.id.tvHideA);
        tvHideB = (TextView) findViewById(R.id.tvHideB);
        tvHideC = (TextView) findViewById(R.id.tvHideC);
        tvHideD = (TextView) findViewById(R.id.tvHideD);
        tvHideE = (TextView) findViewById(R.id.tvHideE);
        tvHideF = (TextView) findViewById(R.id.tvHideF);
        tvHideG = (TextView) findViewById(R.id.tvHideG);
        ivNumA = (ImageView) findViewById(R.id.ivNumA);
        ivNumB = (ImageView) findViewById(R.id.ivNumB);
        ivNumC = (ImageView) findViewById(R.id.ivNumC);
        ivNumD = (ImageView) findViewById(R.id.ivNumD);
        ivNumE = (ImageView) findViewById(R.id.ivNumE);
        ivNumF = (ImageView) findViewById(R.id.ivNumF);
        ivNumG = (ImageView) findViewById(R.id.ivNumG);

        etVerify = (EditText) findViewById(R.id.etVerify);
        btnVerify = (Button) findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(new onClickListenerImp());
        tvVerify = (TextView) findViewById(R.id.tvVerify);
        tvCheck = (TextView) findViewById(R.id.tvCheck);
        etCheck = (EditText) findViewById(R.id.etCheck);
        btnCheck = (Button) findViewById(R.id.btnCheck);
        btnCheck.setOnClickListener(new onClickListenerImp());

        setNum(); // Type-1
        setVerify(); // Type-2
    }

    /**
     * 计算类型的验证码
     */
    private void setVerify() {
        initStrVerify();
        tvHideE.setText(strVerify[0]);
        tvHideE.setTextColor(randomColor());
        tvHideG.setText(strVerify[1]);
        tvHideG.setTextColor(randomColor());
        tvHideF.setText(strVerify[2]);
        tvHideF.setTextColor(randomColor());

        // Num 1
        Matrix matrixE = new Matrix();
        matrixE.reset();
        matrixE.setRotate(randomAngle());
        Bitmap bmNumE = Bitmap.createBitmap(getBitmapFromView(tvHideE, 50, 50), 0, 0, 50, 50, matrixE, true);
        ivNumE.setImageBitmap(bmNumE);

        //Operator
        Matrix matrixF = new Matrix();
        matrixF.reset();
        matrixF.setRotate(randomAngle());
        Bitmap bmNumF = Bitmap.createBitmap(getBitmapFromView(tvHideF, 70, 50), 0, 0, 70, 50, matrixF, true);
        ivNumF.setImageBitmap(bmNumF);

        // Num2
        Matrix matrixG = new Matrix();
        matrixG.reset();
        matrixG.setRotate(randomAngle());
        Bitmap bmNumG = Bitmap.createBitmap(getBitmapFromView(tvHideG, 50, 50), 0, 0, 50, 50, matrixG, true);
        ivNumG.setImageBitmap(bmNumG);
    }

    private void initStrVerify() {
        // 获得两个不相等运算数值：0-9
        do {
            intVerify[0] = new Random().nextInt(10);
            intVerify[1] = new Random().nextInt(10);
        } while (intVerify[0] == intVerify[1]);
        // 获得运算符号：+，-，x
        intVerify[2] = new Random().nextInt(3);
        if (intVerify[2] == 0) {
            intResult = intVerify[0] + intVerify[1];
            strVerify[2] = "加上";
        } else if (intVerify[2] == 1) {
            intResult = intVerify[0] - intVerify[1];
            strVerify[2] = "减去";
        } else if (intVerify[2] == 2) {
            intResult = intVerify[0] * intVerify[1];
            strVerify[2] = "乘以";
        } else {
            intResult = -100;
            strVerify[2] = "呵呵";
        }
        strVerify[0] = numToCharacter(intVerify[0]);
        strVerify[1] = numToCharacter(intVerify[1]);
    }

    /**
     * 将阿拉伯数字转成对应的汉字
     *
     * @param num
     * @return String
     */
    private String numToCharacter(int num) {
        switch (num) {
            case 0:
                return "零";
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 7:
                return "七";
            case 8:
                return "八";
            case 9:
                return "九";
            default:
                return "错";
        }
    }

    private class onClickListenerImp implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view == btnLock) {
                if (etLock.isEnabled()) {
                    etLock.setEnabled(false);
                    btnLock.setText("解锁");
                } else {
                    etLock.setEnabled(true);
                    btnLock.setText("锁定");
                }
            } else if (view == btnCheck) {
                if (etCheck.getText().toString() != null && etCheck.getText().toString().trim().length() > 0) {
                    tvCheck.setVisibility(View.VISIBLE);
                    if (numStr.equals(etCheck.getText().toString())) {
                        tvCheck.setText("正确");
                        tvCheck.setTextColor(Color.GREEN);
                    } else {
                        tvCheck.setText("错误");
                        tvCheck.setTextColor(Color.RED);
                    }
                } else {
                    setNum();
                    tvCheck.setVisibility(View.GONE);
                }
            } else if (view == btnVerify) {
                if (etVerify.getText().toString() != null && etVerify.getText().toString().trim().length() > 0) {
                    tvVerify.setVisibility(View.VISIBLE);
                    if (etVerify.getText().toString().equals(String.valueOf(intResult))) {
                        tvVerify.setText("正确");
                        tvVerify.setTextColor(Color.GREEN);
                    } else {
                        tvVerify.setText("错误");
                        tvVerify.setTextColor(Color.RED);
                    }
                } else {
                    setVerify();
                    tvVerify.setVisibility(View.GONE);
                }
            }
        }
    }

    public void initNum() {
        numStr = "";
        numStrTmp = "";
        for (int i = 0; i < numArray.length; i++) {
            int numIntTmp = new Random().nextInt(10);
            numStrTmp = String.valueOf(numIntTmp);
            numStr = numStr + numStrTmp;
            numArray[i] = numIntTmp;
        }
    }

    public void setNum() {
        initNum();
        tvHideA.setText("" + numArray[0]);
        tvHideA.setTextColor(randomColor());
        tvHideB.setText("" + numArray[1]);
        tvHideB.setTextColor(randomColor());
        tvHideC.setText("" + numArray[2]);
        tvHideC.setTextColor(randomColor());
        tvHideD.setText("" + numArray[3]);
        tvHideD.setTextColor(randomColor());

        // Num 1
        Matrix matrixA = new Matrix();
        matrixA.reset();
        matrixA.setRotate(randomAngle());
        Bitmap bmNumA = Bitmap.createBitmap(getBitmapFromView(tvHideA, 20, 50), 0, 0, 20, 50, matrixA, true);
        ivNumA.setImageBitmap(bmNumA);
        // Num 2
        Matrix matrixB = new Matrix();
        matrixB.reset();
        matrixB.setRotate(randomAngle());
        Bitmap bmNumB = Bitmap.createBitmap(getBitmapFromView(tvHideB, 20, 50), 0, 0, 20, 50, matrixB, true);
        ivNumB.setImageBitmap(bmNumB);
        // Num 3
        Matrix matrixC = new Matrix();
        matrixC.reset();
        matrixC.setRotate(randomAngle());
        Bitmap bmNumC = Bitmap.createBitmap(getBitmapFromView(tvHideC, 20, 50), 0, 0, 20, 50, matrixC, true);
        ivNumC.setImageBitmap(bmNumC);
        // Num 4
        Matrix matrixD = new Matrix();
        matrixD.reset();
        matrixD.setRotate(randomAngle());
        Bitmap bmNumD = Bitmap.createBitmap(getBitmapFromView(tvHideD, 20, 50), 0, 0, 20, 50, matrixD, true);
        ivNumD.setImageBitmap(bmNumD);
    }

    /**
     * 随机角度：[-40°,80°]
     * @return
     */
    public int randomAngle() {
        return 20 * (new Random().nextInt(5) - new Random().nextInt(3));
    }

    /**
     * 随机颜色
     * @return
     */
    public int randomColor() {
        colorArray[0] = 0xFF000000; // BLACK
        colorArray[1] = 0xFFFF00FF; // MAGENTA
        colorArray[2] = 0xFFFF0000; // RED
        colorArray[3] = 0xFF00FF00; // GREEN
        colorArray[4] = 0xFF0000FF; // BLUE
        colorArray[5] = 0xFF00FFFF; // CYAN
        //colorArray[6] = 0xFFFFFF00; // YELLOW:看不清楚

        int randomColorId = new Random().nextInt(6);
        return colorArray[randomColorId];
    }


    /**
     * 将View转成Bitmap
     * @param view
     * @param width 宽度
     * @param height 高度
     * @return Bitmap
     */
    public static Bitmap getBitmapFromView(View view, int width, int height) {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        view.measure(widthSpec, heightSpec);
        view.layout(0, 0, width, height);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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