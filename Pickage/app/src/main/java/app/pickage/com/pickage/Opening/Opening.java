package app.pickage.com.pickage.Opening;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.pickage.com.pickage.R;

public class Opening extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_opening, container, false);

        return v;
    }
}


//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//
//import app.pickage.com.pickage.R;
//
//public class Opening extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_opening);
//    }
//}
