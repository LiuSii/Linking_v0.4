package com.example.fzh.linking_v04;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.example.fzh.linking_v04.R.id.imageView;

public class MainGame extends AppCompatActivity
        implements AdapterView.OnItemClickListener{

    ArrayList icontype = new ArrayList();//记录图标类型
    private GridView gridView;//创建gridView
    private List<Map<String,Object>> datalist;
    private TextView tv;

    private TextView test;

    String Level="1";

    //图标数据
    private int[]icon1={R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,
            R.drawable.empty,R.drawable.one2,R.drawable.empty,R.drawable.one2,R.drawable.empty,R.drawable.one2,R.drawable.empty,
            R.drawable.empty,R.drawable.two2,R.drawable.five2,R.drawable.four2,R.drawable.five2,R.drawable.two2,R.drawable.empty,
            R.drawable.empty,R.drawable.two2,R.drawable.three2,R.drawable.four2,R.drawable.two2,R.drawable.empty,R.drawable.empty,
            R.drawable.empty,R.drawable.two2,R.drawable.two2,R.drawable.two2,R.drawable.two2,R.drawable.empty,R.drawable.empty,
            R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,
            R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty};
    private int[]icon2={R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,
            R.drawable.empty,R.drawable.one2,R.drawable.one2,R.drawable.one2,R.drawable.empty,R.drawable.empty,R.drawable.empty,
            R.drawable.empty,R.drawable.one2,R.drawable.empty,R.drawable.five2,R.drawable.one2,R.drawable.empty,R.drawable.empty,
            R.drawable.empty,R.drawable.two2,R.drawable.three2,R.drawable.two2,R.drawable.five2,R.drawable.empty,R.drawable.empty,
            R.drawable.empty,R.drawable.empty,R.drawable.five2,R.drawable.empty,R.drawable.five2,R.drawable.empty,R.drawable.empty,
            R.drawable.empty,R.drawable.empty,R.drawable.five2,R.drawable.empty,R.drawable.five2,R.drawable.empty,R.drawable.empty,
            R.drawable.empty,R.drawable.one2,R.drawable.three2,R.drawable.five2,R.drawable.three2,R.drawable.empty,R.drawable.empty,
            R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.one2,R.drawable.five2,R.drawable.empty,R.drawable.empty,
            R.drawable.empty,R.drawable.two2,R.drawable.five2,R.drawable.four2,R.drawable.four2,R.drawable.two2,R.drawable.empty,
            R.drawable.empty,R.drawable.two2,R.drawable.five2,R.drawable.two2,R.drawable.one2,R.drawable.one2,R.drawable.empty,
            R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,};

    private int[]icon;

    private SimpleAdapter adapter;//新建适配器
    Random random=new SecureRandom();//定义随机数
    Handler handler = new Handler();

//    public int []clickstates={0};//每位判断

    private int Gap=0;
    private int []mImgView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏系统状态栏
        getSupportActionBar().hide();//隐藏系统标题栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);//设置屏不随手机旋转
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置屏幕直向显示
        setContentView(R.layout.activity_main_game);

        tv=(TextView)findViewById(R.id.tv);
        test =(TextView)findViewById(R.id.test);
        gridView=(GridView)findViewById(R.id.gridView);//绑定gridView
        datalist=new ArrayList<Map<String,Object>>();

        OnResume();//读取当前关数
        tv.setText(String.valueOf(Level));//显示当前关数

        adapter=new SimpleAdapter(this,getData(),R.layout.item,new String[]{"imageView"},new int[]{imageView}) ;
        gridView.setAdapter(adapter);//加载配置器

        TypedArray imgs = getResources().obtainTypedArray(R.array.map_1);



        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<icon.length;i++) {//循环将每一个图标都进行随机打乱
                    int r=random.nextInt(4);
                    RandomRotate(i,r);//随机旋转以打乱
                }
                BulidIconType();//获得图标类型
//                for(int i=0;i<clickstates.length;i++){
//                    clickstates[i]=0;
//                }

                Gap=FirstCountGap();//遍历获得初始参差值
                JudgeRecreate(Gap);//判断是否随机完成
            }
        },5);
        gridView.setOnItemClickListener(this);//加载监视器
    }

    private List<Map<String,Object>> getData(){//获得数据
//        switch(Integer.valueOf(Level)%2) {//判断地图
//            case 1:icon=icon1;break;
//            case 0:icon=icon2;break;
//        }

        for(int i=0;i<icon.length;i++) {//添加数据
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("imageView", icon[i]);
            datalist.add(map);
        }
        return datalist;
    }

    private void RandomRotate(int i,int r){//随机打乱函数
        gridView.getChildAt(i-gridView.getFirstVisiblePosition()).setRotation(r*90);
    }

    private void BulidIconType(){//遍历icon数组获得每一个位置的图标类型
        for(int i=0;i<icon.length;i++){
            switch (icon[i]){
                case R.drawable.one2:icontype.add(i,1);break;
                case R.drawable.two2:icontype.add(i,2);break;
                case R.drawable.three2:icontype.add(i,3);break;
                case R.drawable.four2:icontype.add(i,4);break;
                case R.drawable.five2:icontype.add(i,5);break;
                case R.drawable.empty:icontype.add(i,0);break;
            }
        }
    }

    private int FirstCountGap(){
        int count=0;
        for(int i=0;i<icon.length;i++){//遍历图标类型数组获得初始参差值
            int c=SolveRotation(i);
            switch((int)icontype.get(i)){//根据不同组件对其不同方位进行计数
                case 0:break;
                case 1: {//one型组件
                    switch(c){//判断接口方位
                        case 0:{count+=CheckRight(i);break;}
                        case 90:{count+=CheckDown(i);break;}
                        case 180:{count+=CheckLeft(i);break;}
                        case 270:{count+=CheckUp(i);break;}
                    }
                    break;
                }
                case 2:{
                    switch(c) {//判断接口方位
                        case 0:{count+=CheckUp(i)+CheckLeft(i);break;}
                        case 90:{count+=CheckUp(i)+CheckRight(i);break;}
                        case 180:{count+=CheckDown(i)+CheckRight(i);break;}
                        case 270:{count+=CheckDown(i)+CheckLeft(i);break;}
                    }
                    break;
                }
                case 3:{
                    switch(c) {//判断接口方位
                        case 0:{count+=CheckUp(i)+CheckLeft(i)+CheckRight(i);break;}
                        case 90:{count+=CheckUp(i)+CheckRight(i)+CheckDown(i);break;}
                        case 180:{count+=CheckDown(i)+CheckRight(i)+CheckLeft(i);break;}
                        case 270:{count+=CheckDown(i)+CheckLeft(i)+CheckUp(i);break;}
                    }
                    break;
                }
                case 4:{count+=CheckUp(i)+CheckLeft(i)+CheckDown(i)+CheckRight(i);break;}//four型全加
                case 5:{
                    switch(c) {//判断接口方位
                        case 0:{count+=CheckLeft(i)+CheckRight(i);break;}
                        case 90:{count+=CheckUp(i)+CheckDown(i);break;}
                        case 180:{count+=CheckRight(i)+CheckLeft(i);break;}
                        case 270:{count+=CheckDown(i)+CheckUp(i);break;}
                    }
                    break;
                }
                default:break;

            }
        }
        return count;
    }

    protected void Back(View v){//返回主界面
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {//按键事件
        float r1 =  view.getRotation();//求取旋转之前状态以修改Gap值
//        test.setText(String.valueOf(clickstates[i]));

        RotateAnimation rotateAnimation = new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(300);//设置动画持续时间
        rotateAnimation.setFillAfter(true);//动画执行完后停留在执行完的状态

        view.setAnimation(rotateAnimation);

//        if(clickstates[i]==0){
//
//            clickstates[i]=1;
//        }
        view.setRotation(view.getRotation()+90);//设置旋转后方位

        if (view.getRotation() >= 360)
            view.setRotation(0);
        //test.setText(String.valueOf(view.getRotation()));

        switch ((int) icontype.get(i)) {//判断控件类型
            case 0: {break;}
            case 1: {
                switch((int)r1){//判断Gap变化
                    case 0:{
                        Gap+=+(1-CheckRight(i))*2-(1-CheckDown(i))*2;//减去原来方向的，加上后来方向的
                        break;
                    }
                    case 90:{
                        Gap+=+(1-CheckDown(i))*2-(1-CheckLeft(i))*2;
                        break;
                    }
                    case 180:{
                        Gap+=+(1-CheckLeft(i))*2-(1-CheckUp(i))*2;
                        break;
                    }
                    case 270:{
                        Gap+=+(1-CheckUp(i))*2-(1-CheckRight(i))*2;
                        break;
                    }
                }
                break;
            }
            case 2: {
                switch((int)r1){//判断Gap变化
                    case 0:{
                        Gap+=+(1-CheckLeft(i))*2-(1-CheckRight(i))*2;//减去原来方向的，加上后来方向的
                        break;
                    }
                    case 90:{
                        Gap+=+(1-CheckUp(i))*2-(1-CheckDown(i))*2;
                        break;
                    }
                    case 180:{
                        Gap+=+(1-CheckRight(i))*2-(1-CheckLeft(i))*2;
                        break;
                    }
                    case 270:{
                        Gap+=+(1-CheckDown(i))*2-(1-CheckUp(i))*2;
                        break;
                    }
                }
                break;
            }
            case 3: {
                switch((int)r1){//判断Gap变化
                    case 0:{
                        Gap+=+(1-CheckLeft(i))*2-(1-CheckDown(i))*2;//减去原来方向的，加上后来方向的
                        break;
                    }
                    case 90:{
                        Gap+=+(1-CheckUp(i))*2-(1-CheckLeft(i))*2;
                        break;
                    }
                    case 180:{
                        Gap+=+(1-CheckRight(i))*2-(1-CheckUp(i))*2;
                        break;
                    }
                    case 270:{
                        Gap+=+(1-CheckDown(i))*2-(1-CheckRight(i))*2;
                        break;
                    }
                }
                break;
            }
            case 4: {break;}
            case 5: {
                switch((int)r1){//判断Gap变化
                    case 0:{
                        Gap+=+(1-CheckRight(i))*2+(1-CheckLeft(i))*2-(1-CheckDown(i))*2-(1-CheckUp(i))*2;//减去原来方向的，加上后来方向的
                        break;
                    }
                    case 90:{
                        Gap+=-(1-CheckRight(i))*2-(1-CheckLeft(i))*2+(1-CheckDown(i))*2+(1-CheckUp(i))*2;
                        break;
                    }
                    case 180:{
                        Gap+=+(1-CheckRight(i))*2+(1-CheckLeft(i))*2-(1-CheckDown(i))*2-(1-CheckUp(i))*2;
                        break;
                    }
                    case 270:{
                        Gap+=-(1-CheckRight(i))*2-(1-CheckLeft(i))*2+(1-CheckDown(i))*2+(1-CheckUp(i))*2;
                        break;
                    }
                }
                break;
            }
            default:
                break;
        }
        //tv.setText(String.valueOf(Gap));
        JudgeRecreate(Gap);//判断是否完成
    }

    public void JudgeRecreate(int Gap){
        int i;
        if(Gap==0) {//完成
            Toast.makeText(this, "next", Toast.LENGTH_SHORT).show();
            i=Integer.valueOf(Level);//Level数据类型转换，加1后再转换回去
            i++;
            Level=String.valueOf(i);
            OnPause();//存储
            recreate();//重启Activity
        }
    }

    public int SolveRotation(int i){//求目前角度
        return (int)gridView.getChildAt(i-gridView.getFirstVisiblePosition()).getRotation();
    }

    public int CheckRight(int i){//检验右边接口函数
        int c=SolveRotation(i+1);
        switch ((int)icontype.get(i+1)){//检验右边为什么组件型
            case 1:{
                if(c==180)
                    return 0;
                else return 1;
            }
            case 2:{
                if(c==0||c==270)
                    return 0;
                else return 1;
            }
            case 3:{
                if(c==90)
                    return 1;
                else return 0;
            }
            case 4:{ return 0;}
            case 5:{
                if(c==0||c==180)
                    return 0;
                else return 1;
            }
            default:return 1;
        }
    }
    public int CheckLeft(int i){
        int c=SolveRotation(i-1);
        switch ((int)icontype.get(i-1)){//检验左边为什么组件型
            case 1:{
                if(c==0)
                    return 0;
                else return 1;
            }
            case 2:{
                if(c==90||c==180)
                    return 0;
                else return 1;
            }
            case 3:{
                if(c==270)
                    return 1;
                else return 0;
            }
            case 4:{return 0;}
            case 5:{
                if(c==0||c==180)
                    return 0;
                else return 1;
            }
            default:return 1;
        }
    }
    public int CheckUp(int i){
        int c=SolveRotation(i-7);
        switch ((int)icontype.get(i-7)){//检验上边为什么组件型
            case 1:{
                if(c==90)
                    return 0;
                else return 1;
            }
            case 2:{
                if(c==180||c==270)
                    return 0;
                else return 1;
            }
            case 3:{
                if(c==0)
                    return 1;
                else return 0;
            }
            case 4:{return 0;}
            case 5:{
                if(c==90||c==270)
                    return 0;
                else return 1;
            }
            default:return 1;
        }
    }
    public int CheckDown(int i){
        int c=SolveRotation(i+7);
        switch ((int)icontype.get(i+7)){//检验下边为什么组件型
            case 1:{
                if(c==270)
                    return 0;
                else return 1;
            }
            case 2:{
                if(c==0||c==90)
                    return 0;
                else return 1;
            }
            case 3:{
                if(c==180)
                    return 1;
                else return 0;
            }
            case 4:{return 0;}
            case 5:{
                if(c==90||c==270)
                    return 0;
                else return 1;
            }
            default:return 1;
        }
    }

    protected void OnResume(){
        super.onResume();
        SharedPreferences mylevel = getPreferences(MODE_PRIVATE);//获取首选项对象
        Level=mylevel.getString("关数","1");//读取存储的字符串项，如果不存在返回"1"

    }
    protected void OnPause(){
        super.onPause();
       SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();//获取编辑器对象
        editor.putString("关数",Level);//存储
        editor.commit();

    }
}
