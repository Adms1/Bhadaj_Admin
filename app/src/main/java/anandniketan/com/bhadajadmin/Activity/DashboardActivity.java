package anandniketan.com.bhadajadmin.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajadmin.Adapter.ExpandableListAdapterMenu;
import anandniketan.com.bhadajadmin.Adapter.MenuoptionItemAdapter;
import anandniketan.com.bhadajadmin.Fragment.Fragment.AccountFragment;
import anandniketan.com.bhadajadmin.Fragment.Fragment.ActivityLoggingFragment;
import anandniketan.com.bhadajadmin.Fragment.Fragment.AnnouncementFragment;
import anandniketan.com.bhadajadmin.Fragment.Fragment.BullkSmsFragment;
import anandniketan.com.bhadajadmin.Fragment.Fragment.EmployeeSmsFragment;
import anandniketan.com.bhadajadmin.Fragment.Fragment.HRFragment;
import anandniketan.com.bhadajadmin.Fragment.Fragment.HolidayFragment;
import anandniketan.com.bhadajadmin.Fragment.Fragment.HomeFragment;
import anandniketan.com.bhadajadmin.Fragment.Fragment.PTMMainFragment;
import anandniketan.com.bhadajadmin.Fragment.Fragment.SingleSmsFragment;
import anandniketan.com.bhadajadmin.Fragment.Fragment.StaffFragment;
import anandniketan.com.bhadajadmin.Fragment.Fragment.StudentAbsentFragment;
import anandniketan.com.bhadajadmin.Fragment.Fragment.StudentFragment;
import anandniketan.com.bhadajadmin.Fragment.Fragment.SummaryFragment;
import anandniketan.com.bhadajadmin.Fragment.Fragment.TransportFragment;
import anandniketan.com.bhadajadmin.Model.MenuoptionItemModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;


public class DashboardActivity extends FragmentActivity {
    static DrawerLayout mDrawerLayout;
    //    static ListView mDrawerList;

    Context mContext;
    ActionBarDrawerToggle mDrawerToggle;
    static RelativeLayout leftRl;
    private ArrayList<MenuoptionItemModel> navDrawerItems_main;
    private MenuoptionItemAdapter adapter_menu_item;
    String MenuName[];
    String token;
    int dispPOS = 0;
    SharedPreferences SP;
    public static String filename = "Valustoringfile";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private String putData = "0";

    //
    ExpandableListAdapterMenu expandableListAdapterMenu;
    static ExpandableListView mDrawerList;
    List<String> listDataHeader;
    HashMap<String, ArrayList<String>> listDataChild;
    ArrayList<String> imagesId = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mContext = this;
        Initialize();
        displayView(0);


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_launcher, // nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            @SuppressLint("NewApi")
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            @SuppressLint("NewApi")
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    private void Initialize() {
        // TODO Auto-generated method stub
        MenuName = getResources().getStringArray(R.array.menuoption1);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        leftRl = (RelativeLayout) findViewById(R.id.whatYouWantInLeftDrawer);
        mDrawerList = (ExpandableListView) findViewById(R.id.list_slidermenu);
        navDrawerItems_main = new ArrayList<MenuoptionItemModel>();
//        mDrawerList.setAdapter(new MenuoptionItemAdapter(mContext));
        fillExpLV();

//        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
        mDrawerList.setOnChildClickListener(new SlideMenuClickListener());
        mDrawerList.setOnGroupClickListener(new SlideMenuClickListener());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener, ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
//            displayView(position);

        }

        @Override
        public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
//            Utils.ping(mContext,""+expandableListAdapterMenu.getChild(i,i1));
            String str = expandableListAdapterMenu.getChild(i, i1);
            displayView1(str);
            return true;

        }

        @Override
        public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
//            Utils.ping(mContext,""+i);
            displayView(i);
            return false;
        }
    }

    Fragment fragment = null;
    int myid;
    boolean first_time_trans = true;

    public void displayView(int position) {
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 1:
                fragment = new StudentFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 2:
                fragment = new StaffFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 3:
                fragment = new AccountFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 4:
                fragment = new TransportFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 5:
                fragment = new HRFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
//            case 6:
//                fragment = new OtherFragment();
//                myid = fragment.getId();
//                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//                break;
        }
        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            if (fragment instanceof HomeFragment) {
                if (first_time_trans) {
                    first_time_trans = false;
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();

                } else {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                }
            } else {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
//            mDrawerLayout.closeDrawers();
        } else {
            // error in creating fragment
            Log.e("Dashboard", "Error in creating fragment");
        }
    }

    public void displayView1(String position) {
        switch (position) {
            case "STUDENT ABSENT":
                fragment = new StudentAbsentFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case "BULK SMS":
                fragment = new BullkSmsFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case "SINGLE SMS":
                fragment = new SingleSmsFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case "EMPLOYEE SMS":
                fragment = new EmployeeSmsFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case "SUMMARY":
                fragment = new SummaryFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case "HOLIDAY":
                fragment = new HolidayFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case "PTM":
                fragment = new PTMMainFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case "ACTIVITY LOGGING":
                fragment = new ActivityLoggingFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case "ANNOUNCMENT":
                fragment = new AnnouncementFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
//            case "Quick Email":
//                fragment = new OtherFragment();
//                myid = fragment.getId();
//                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//                break;
        }
        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            if (fragment instanceof HomeFragment) {
                if (first_time_trans) {
                    first_time_trans = false;
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();

                } else {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                }
            } else {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }

            // update selected item and title, then close the drawer
//            mDrawerList.setItemChecked(position, true);
//            mDrawerList.setSelection(position);
            mDrawerLayout.closeDrawers();
        } else {
            // error in creating fragment
            Log.e("Dashboard", "Error in creating fragment");
        }
    }

    public static void onLeft() {
        // TODO Auto-generated method stub
        mDrawerList.setSelectionAfterHeaderView();
        mDrawerLayout.openDrawer(leftRl);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        displayView(0);
//        Intent i =new Intent(DashboardActivity.this,SplashScreen.class);
//        startActivity(i);
    }

    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, ArrayList<String>>();
        ArrayList<String> finalImageArray = new ArrayList<String>();

        ArrayList<String> finalheaderArray = new ArrayList<>();
        finalheaderArray.add("HOME");
        finalheaderArray.add("STUDENT");
        finalheaderArray.add("STAFF");
        finalheaderArray.add("ACCOUNT");
        finalheaderArray.add("TRANSPORT");
        finalheaderArray.add("HR");
        finalheaderArray.add("OTHER");
        finalheaderArray.add("LOGOUT");

        ArrayList<String> finalchildArray = new ArrayList<>();
        finalchildArray.add("STUDENT ABSENT");
        finalchildArray.add("BULK SMS");
        finalchildArray.add("SINGLE SMS");
        finalchildArray.add("EMPLOYEE SMS");
        finalchildArray.add("SUMMARY");
        finalchildArray.add("HOLIDAY");
        finalchildArray.add("PTM");
        finalchildArray.add("ACTIVITY LOGGING");
        finalchildArray.add("ANNOUNCMENT");
        finalchildArray.add("QUICK EMAIL");

        imagesId.add(AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Home.png");
        imagesId.add(AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Student.png");
        imagesId.add(AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Staff.png");
        imagesId.add(AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Account.png");
        imagesId.add(AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Transport.png");
        imagesId.add(AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_HR.png");
        imagesId.add(AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Other.png");
        imagesId.add(AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Logout.png");

        for (int k = 0; k < imagesId.size(); k++) {
            finalImageArray.add(imagesId.get(k));
        }
        Log.d("finalImageArray", "" + finalImageArray);

        for (int i = 0; i < finalheaderArray.size(); i++) {
                listDataHeader.add(finalheaderArray.get(i)+"|"+imagesId.get(i));//+"|"+mThumbIds[k]
                Log.d("header", "" + listDataHeader);
                ArrayList<String> row = new ArrayList<String>();

                for (int j = 0; j < finalchildArray.size(); j++) {
                    if (finalheaderArray.get(i).equalsIgnoreCase("OTHER")) {
                        row.add(finalchildArray.get(j));
                        Log.d("row", "" + row);
                    }
                }
                listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }
        expandableListAdapterMenu = new ExpandableListAdapterMenu(mContext, listDataHeader, listDataChild,imagesId);
        mDrawerList.setAdapter(expandableListAdapterMenu);
    }
}
