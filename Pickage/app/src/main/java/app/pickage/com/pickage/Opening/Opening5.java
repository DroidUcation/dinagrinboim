package app.pickage.com.pickage.opening;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.pickage.com.pickage.R;
import app.pickage.com.pickage.useractivities.LoginActivity;

public class Opening5 extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_opening5, container, false);

        TextView btn = (TextView) v.findViewById(R.id.btnext);
        btn.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
    }
}