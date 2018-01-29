package com.veryworks.iyeongjun.hkapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.veryworks.iyeongjun.hkapp.EventDriven.RxEventBus;
import com.veryworks.iyeongjun.hkapp.EventDriven.RxPagerEventBus;
import com.veryworks.iyeongjun.hkapp.HTTP.DataReceiver;
import com.veryworks.iyeongjun.hkapp.HTTP.InitDataLoader;
import com.veryworks.iyeongjun.hkapp.Interface.TypeAndSectionSwapInterface;
import com.veryworks.iyeongjun.hkapp.Util.UserLocation;
import com.veryworks.iyeongjun.hkapp.adapter.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

import static com.veryworks.iyeongjun.hkapp.domain.Const.FRAGMENT.LIST;
import static com.veryworks.iyeongjun.hkapp.domain.Const.FRAGMENT.MAP;
import static com.veryworks.iyeongjun.hkapp.domain.Const.FRAGMENT.SECTION_AND_TYPE;
import static com.veryworks.iyeongjun.hkapp.domain.Const.FRAGMENT.TOUNAMENT;
import static com.veryworks.iyeongjun.hkapp.domain.StaticDrawble.tabImage;
import static com.veryworks.iyeongjun.hkapp.domain.StaticDrawble.tabSelectedImage;
import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.isTypeList;

public class MainActivity extends CustomFontAcitivity
        implements NavigationView.OnNavigationItemSelectedListener,TypeAndSectionSwapInterface {
    int curPos;
    DrawerLayout drawer;
    boolean drawerToggle = false;

    @BindView(R.id.btnMenu) ImageButton btnMenu;
    @BindView(R.id.tabLayout) TabLayout tab;
    @BindView(R.id.viewpager) ViewPager pager;
    @BindView(R.id.fab) ImageButton fab;

    /**/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setView();
        DataReceiver dataReceiver = new DataReceiver(this);
        dataReceiver.getData();
        UserLocation userLocation = new UserLocation(this);
        userLocation.getLocation();
    }

    private void setView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, //상태바 제거
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setPager();
        setObservableSubscribe();
    }

    @Override
    protected void onResume() {
        super.onResume();
        InitDataLoader initDataLoader = new InitDataLoader(this,true);
        initDataLoader.initData();
    }

    private void setObservableSubscribe(){
        RxPagerEventBus.getInstance().getObservable().subscribe(
                num -> fabContllor(num)
        );
    }

    private void fabContllor(int num){
        switch (num){
            case LIST :
                fab.setImageResource(R.drawable.fab_refresh);
                curPos = LIST;
                break;

            case SECTION_AND_TYPE :
                if(isTypeList) setTypeList();
                else setSectionList();
                curPos = SECTION_AND_TYPE;
                break;

            case TOUNAMENT :
                fab.setImageResource(R.drawable.fab_write);
                curPos = TOUNAMENT;
                break;

            case MAP :
                fab.setImageResource(R.drawable.fab_map);
                curPos =MAP;
                break;
        }
    }
    /**
     * 버튼 클릭사 각 프래그먼트로 이벤트 발행
     */
    @OnClick(R.id.fab)
    public void onFabClicked(){
        RxEventBus.getInstance().sendEvent(curPos);
    }

    /**
     * 메뉴버튼 터치리스너
     *
     * @param event
     * @return
     */

    @OnTouch(R.id.btnMenu)
    public boolean onMenuButtonTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            btnMenu.setImageResource(R.drawable.menu_c_image);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            btnMenu.setImageResource(R.drawable.menu_image);
            if (drawerToggle == false) {
                drawer.openDrawer(Gravity.RIGHT);
            } else {
                drawer.closeDrawer(Gravity.RIGHT);
            }
        }
        return false;
    }
    @Override
    public void setTypeList() {fab.setImageResource(R.drawable.swap_type);}

    @Override
    public void setSectionList() {fab.setImageResource(R.drawable.swap_location);}

    /*뷰페이저와 프래그먼트 세팅*/
    public void setPager() {

//        Log.d("ARPOINT", currentUserLocation.getLatitude() + "/" + currentUserLocation.getLongitude());
//
        tab.addTab(tab.newTab().setIcon(tabSelectedImage[0]));
        tab.addTab(tab.newTab().setIcon(tabImage[1]));
        tab.addTab(tab.newTab().setIcon(tabImage[2]));
        tab.addTab(tab.newTab().setIcon(tabImage[3]));
        List<Fragment> datas = new ArrayList<>();

        ListFragment listFragment = new ListFragment();
        SectionTypeFragment sectionFragment = new SectionTypeFragment();
        sectionFragment.setTypeAndSectionSwapInterface(this);
        TournamentFragment tagFragment = new TournamentFragment();
        MapFragment mapFragment = new MapFragment();

        datas.add(listFragment);
        datas.add(sectionFragment);
        datas.add(tagFragment);
        datas.add(mapFragment);

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), datas);
        // 아답터를 페이저 위젯에 연결
        pager.setAdapter(adapter);
        // 페이저가 변경되었을 때 탭을 변경해주는 리스너
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                RxPagerEventBus.getInstance().sendEvent(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        // 탭이 변경되었때 페이저를 변경해주는 리스너
        tab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tabs) {
                tabs.setIcon(tabSelectedImage[tabs.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tabs) {
                tabs.setIcon(tabImage[tabs.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tabs) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_ar) {
            Intent intent = new Intent(MainActivity.this, ARActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_qr) {
            IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
            intentIntegrator.initiateScan();
        } else if (id == R.id.nav_doc) {
            sendSnack("준비중입니다");
        } else if (id == R.id.nav_question) {
            sendSnack("준비중입니다");
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String url = "";
        String name ="";
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if(result.getContents() == null){
                sendSnack("취소 되었습니다");
            }else{
                if(!result.getContents().startsWith("http") || !result.getContents().startsWith("https")){
                    url = "https://" + result.getContents();
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        }else {
            Log.d("QRSCAN", result.toString());
            super.onActivityResult(requestCode, resultCode, data);
        }
        Log.d("MAINACTIVITY",result.getContents());

    }
    public void sendSnack(String str) {
        Snackbar.make(getWindow().getDecorView().getRootView()
                , str, Snackbar.LENGTH_LONG).show();
    }
}
