package app.pickage.com.pickage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import app.pickage.com.pickage.Opening.Opening;
import app.pickage.com.pickage.Opening.Opening1;
import app.pickage.com.pickage.Opening.Opening2;
import app.pickage.com.pickage.Opening.Opening3;
import app.pickage.com.pickage.Opening.Opening4;
import app.pickage.com.pickage.Opening.Opening5;

public class MainActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {
                case 0:
                    return new Opening();
                case 1:
                    return new Opening1();
                case 2:
                    return new Opening2();
                case 3:
                    return new Opening3();
                case 4:
                    return new Opening4();
                case 5:
                    return new Opening5();
                default:
                    return new Opening();
            }
        }

        @Override
        public int getCount() {
            return 6;
        }
    }
}


//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import app.pickage.com.pickage.Opening.Opening1;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        TextView nextScreen = (TextView) findViewById(R.id.title1_os);
//        nextScreen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    Intent intent = new Intent(MainActivity.this, Opening1.class);
//                    startActivity(intent);
//            }
//        });
//    }
//}
