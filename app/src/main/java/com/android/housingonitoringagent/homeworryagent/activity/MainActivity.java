package com.android.housingonitoringagent.homeworryagent.activity;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.housingonitoringagent.homeworryagent.R;
import com.android.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.android.housingonitoringagent.homeworryagent.pages.MeFragment;
import com.android.housingonitoringagent.homeworryagent.pages.RecordFragment;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    @Bind(R.id.tabMain)
    TabLayout tabMain;
    @Bind(R.id.vpMain)
    ViewPager vpMain;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private MainPagerAdapter mainPagerAdapter;
    private EaseChatFragment chatFragment;
    private RecordFragment recordFragment;
    private MeFragment meFragment;

    private String toChatUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        vpMain.setAdapter(mainPagerAdapter);
        tabMain.setupWithViewPager(vpMain);

        init();
    }

    private void init() {

        //聊天人或群id
        toChatUsername = getIntent().getExtras().getString("userId");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
   /* public static class PlaceholderFragment extends Fragment {
        *//**
         * The fragment argument representing the section number for this
         * fragment.
         *//*
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        *//**
         * Returns a new instance of this fragment for the given section
         * number.
         *//*
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            return rootView;
        }
    }*/

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class MainPagerAdapter extends FragmentPagerAdapter {

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    //可以直接new EaseChatFratFragment使用
                    if (chatFragment != null) {
                        return chatFragment;
                    }
                    chatFragment = new EaseChatFragment();
                    //传入参数
                    chatFragment.setArguments(getIntent().getExtras());
//        getSupportFragmentManager().beginTransaction().add(R., chatFragment).commit();
                    chatFragment.setChatFragmentListener(new EaseChatFragment.EaseChatFragmentListener() {
                        @Override
                        public void onSetMessageAttributes(EMMessage message) {

                        }

                        @Override
                        public void onEnterToChatDetails() {

                        }

                        @Override
                        public void onAvatarClick(String username) {

                        }

                        @Override
                        public boolean onMessageBubbleClick(EMMessage message) {
                            return false;
                        }

                        @Override
                        public void onMessageBubbleLongClick(EMMessage message) {

                        }

                        @Override
                        public boolean onExtendMenuItemClick(int itemId, View view) {
                            return false;
                        }

                        @Override
                        public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
                            EaseCustomChatRowProvider provider = new EaseCustomChatRowProvider() {
                                @Override
                                public int getCustomChatRowTypeCount() {
                                    return 0;
                                }

                                @Override
                                public int getCustomChatRowType(EMMessage message) {
                                    int type = message.getIntAttribute("msgType", 0);
                                    if (type > 0) {
                                    } else {

                                    }
                                    try {
                                        JSONObject jsonObject = new JSONObject(message.getBody().toString());
                                        if (jsonObject.getInt("type") > 0) {
                                            type = jsonObject.getInt("type");
                                        } else {
                                            EMChatManager manager;
                                            EMMessage msg = EMMessage.createTxtSendMessage("abc", "123");
                                            msg.setAttribute("msgType", 1);

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    return type;
                                }

                                @Override
                                public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
                                    EaseChatRow row = new EaseChatRow(MainActivity.this, message, position, adapter) {

                                        @Override
                                        protected void onInflatView() {
                                        }

                                        @Override
                                        protected void onFindViewById() {

                                        }

                                        @Override
                                        protected void onUpdateView() {
                                        }

                                        @Override
                                        protected void onSetUpView() {
                                            adapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        protected void onBubbleClick() {

                                        }
                                    };
                                    return row;
                                }
                            };
                            return provider;
                        }
                    });
                    return chatFragment;
//                    break;
                case 1:
                    recordFragment = new RecordFragment();
                    return recordFragment;
//                    break;
                case 2:
                    meFragment = new MeFragment();
                    return meFragment;
//                    break;
                default:
                    return null;
//                    break;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title;
            switch (position) {
                case 0:
                    title = "消息";
                    break;
                case 1:
                    title = "看房";
                    break;
                case 2:
                    title = "我的";
                    break;
                default:
                    title = "";
                    break;
            }
            return title;
        }
    }
}
