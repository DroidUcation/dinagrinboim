package app.pickage.com.pickage.Opening;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import app.pickage.com.pickage.R;

public class Opening2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_opening2, container, false);

        return v;
    }
}


//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//
//import app.pickage.com.pickage.R;
//
//public class Opening2 extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_opening2);
//
//        ImageView nextScreen = (ImageView) findViewById(R.id.opening2_img);
//        nextScreen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Opening2.this, Opening3.class);
//                startActivity(intent);
//            }
//        });
//    }
//

//}
