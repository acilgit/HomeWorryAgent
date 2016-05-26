package com.android.housingonitoringagent.homeworryagent.pages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.housingonitoringagent.homeworryagent.R;
import com.android.housingonitoringagent.homeworryagent.User;
import com.android.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2014/9/30.
 */

public class MeFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.rlOrderList)
    RelativeLayout rlOrderList;
    @Bind(R.id.rlSecurityCenter)
    RelativeLayout rlSecurityCenter;
    @Bind(R.id.sivHead)
    SimpleDraweeView sivHead;
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.tvDetail)
    TextView tvDetail;

    public MeFragment() {
    }

    private BaseActivity getThis() {
        return ((BaseActivity) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, currentView);
        rlOrderList.setOnClickListener(this);
        rlSecurityCenter.setOnClickListener(this);

        sivHead.setImageURI(Uri.parse(User.getHeadUrl()));
        tvName.setText(User.getUsername());
        tvDetail.setText(User.getAccount());

        return currentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlOrderList:

                break;
            case R.id.rlSecurityCenter:

                break;
            default:
                break;
        }
    }
}
