package com.erp.apparel.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.erp.apparel.Data.Prefs;
import com.erp.apparel.Fragments.ApprovalsFrag;
import com.erp.apparel.Fragments.DashFrag;
import com.erp.apparel.Fragments.ProfileFrag;
import com.erp.apparel.Fragments.ReportsFrag;
import com.erp.apparel.Fragments.StyleInfoFrag;

import com.erp.apparel.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,NavigationView.OnNavigationItemSelectedListener,BottomNavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    public static DrawerLayout m_draw;
    LinearLayout m_home_menu,m_styleInfo_menu, m_process_menu, m_business_menu, m_otds_menu, m_score_menu,m_produciton_menu,m_preorder_menu, m_spo_menu,m_about_menu,m_profile_menu,m_logout_menu;
    RelativeLayout m_release_menu;

    DashFrag dashFrag;
    StyleInfoFrag styleInfoFrag;
    ImageView m_debox;
    TextView m_firstname;
    ImageView m_navigation_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        navigationView = findViewById(R.id.nav_view);
        m_draw = findViewById(R.id.drawlayout);

        bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);





        dashFrag=new DashFrag();
        styleInfoFrag=new StyleInfoFrag();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_lat, new DashFrag()).commit();


        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        View header = navigationView.getHeaderView(0);
        m_firstname=header.findViewById(R.id.firstname_HEADER);
        m_home_menu = header.findViewById(R.id.home_ll_HEADER);


        m_logout_menu = header.findViewById(R.id.logout_ll_HEADER);
        m_debox= header.findViewById(R.id.deoxicon);

        m_styleInfo_menu=header.findViewById(R.id.styleinfo_MENU);
        m_profile_menu=header.findViewById(R.id.profile_MENU);
        m_process_menu=header.findViewById(R.id.processlight_MENU);
        m_business_menu=header.findViewById(R.id.businesssummary_MENU);
        m_otds_menu=header.findViewById(R.id.otds_MENU);
        m_score_menu=header.findViewById(R.id.scorecard_MENU);
        m_produciton_menu=header.findViewById(R.id.produtionreport_MENU);
        m_preorder_menu=header.findViewById(R.id.preordercost_MENU);
        m_spo_menu=header.findViewById(R.id.spomgmt_MENU);
        m_about_menu=header.findViewById(R.id.about_MENU);
        m_release_menu=header.findViewById(R.id.aboutrelease_MENU);


        m_firstname.setText(Prefs.INSTANCE.getFirstName());

        m_navigation_icon=findViewById(R.id.navigation_icon);
        m_navigation_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_draw.openDrawer(GravityCompat.START);
            }
        });

        m_home_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


        m_debox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, DeboxPage.class);
                startActivity(intent);
            }
        });


        m_styleInfo_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(HomeActivity.this,StyleInfo.class);
                startActivity(intent);*/
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container_lat, new StyleInfoFrag()).commit();

                m_draw.closeDrawers();
            }
        });



        m_process_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,ProcessLight.class);
                startActivity(intent);
                m_draw.closeDrawers();
            }
        });


        m_business_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,BusinessSummary.class);
                startActivity(intent);
                m_draw.closeDrawers();
            }
        });


        m_otds_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,Otds.class);
                startActivity(intent);
                m_draw.closeDrawers();
            }
        });



        m_score_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,ScoreCard.class);
                startActivity(intent);
                m_draw.closeDrawers();
            }
        });

        m_produciton_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,ProdutionReport.class);
                startActivity(intent);
                m_draw.closeDrawers();
            }
        });


        m_preorder_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,PreOrder.class);
                startActivity(intent);
                m_draw.closeDrawers();
            }
        });


        m_spo_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,SpoMgmt.class);
                startActivity(intent);
                m_draw.closeDrawers();
            }
        });

        m_about_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,AboutUs.class);
                startActivity(intent);
                m_draw.closeDrawers();
            }
        });


        m_logout_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog();
            }
        });

        m_profile_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(HomeActivity.this, Profile.class);
                startActivity(intent);*/
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container_lat, new ProfileFrag()).commit();
                m_draw.closeDrawers();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {

       /* int id = item.getItemId();

        if (id == R.id.nav_dashboard) {

            item.setIcon(R.mipmap.dashboardmenu);

            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem1 = menu.getItem(1);
            menuItem1.setIcon(R.mipmap.stylewhitemenu);
            MenuItem menuItem2 = menu.getItem(2);
            menuItem2.setIcon(R.mipmap.approvalwhitemenu);
            MenuItem menuItem3 = menu.getItem(3);
            menuItem3.setIcon(R.mipmap.reportwhitemenu);
            MenuItem menuItem4 = menu.getItem(4);
            menuItem3.setIcon(R.mipmap.profilewhitemenu);

            getSupportFragmentManager().beginTransaction().replace(R.id.main_container_lat, new DashFrag()).commit();
        }

       else if (id == R.id.nav_styleinfo) {

            item.setIcon(R.mipmap.styleinfomenu);

            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem1 = menu.getItem(0);
            menuItem1.setIcon(R.mipmap.dashboardwhitemenu);
            MenuItem menuItem2 = menu.getItem(2);
            menuItem2.setIcon(R.mipmap.approvalwhitemenu);
            MenuItem menuItem3 = menu.getItem(3);
            menuItem3.setIcon(R.mipmap.reportwhitemenu);
            MenuItem menuItem4 = menu.getItem(4);
            menuItem3.setIcon(R.mipmap.profilewhitemenu);

            getSupportFragmentManager().beginTransaction().replace(R.id.main_container_lat, new StyleInfoFrag()).commit();
        }

        else if (id == R.id.nav_approvals) {

            item.setIcon(R.mipmap.approvalmenu);

            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem1 = menu.getItem(0);
            menuItem1.setIcon(R.mipmap.dashboardwhitemenu);
            MenuItem menuItem2 = menu.getItem(1);
            menuItem2.setIcon(R.mipmap.approvalmenu);
            MenuItem menuItem3 = menu.getItem(3);
            menuItem3.setIcon(R.mipmap.reportwhitemenu);
            MenuItem menuItem4 = menu.getItem(4);
            menuItem3.setIcon(R.mipmap.profilewhitemenu);

         //   getSupportFragmentManager().beginTransaction().replace(R.id.main_container_lat, new StyleInfoFrag()).commit();
        }

        else if (id == R.id.nav_reports) {

            item.setIcon(R.mipmap.reportmenu);

            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem1 = menu.getItem(0);
            menuItem1.setIcon(R.mipmap.dashboardwhitemenu);
            MenuItem menuItem2 = menu.getItem(1);
            menuItem2.setIcon(R.mipmap.approvalmenu);
            MenuItem menuItem3 = menu.getItem(2);
            menuItem3.setIcon(R.mipmap.reportmenu);
            MenuItem menuItem4 = menu.getItem(4);
            menuItem3.setIcon(R.mipmap.profilewhitemenu);

            //   getSupportFragmentManager().beginTransaction().replace(R.id.main_container_lat, new StyleInfoFrag()).commit();
        }

        else if (id == R.id.nav_profile) {

            item.setIcon(R.mipmap.profilemenu);

            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem1 = menu.getItem(0);
            menuItem1.setIcon(R.mipmap.dashboardwhitemenu);
            MenuItem menuItem2 = menu.getItem(1);
            menuItem2.setIcon(R.mipmap.approvalmenu);
            MenuItem menuItem3 = menu.getItem(2);
            menuItem3.setIcon(R.mipmap.reportmenu);
            MenuItem menuItem4 = menu.getItem(3);
            menuItem3.setIcon(R.mipmap.profilemenu);

            //   getSupportFragmentManager().beginTransaction().replace(R.id.main_container_lat, new StyleInfoFrag()).commit();
        }
*/


        switch (item.getItemId()) {
            case R.id.nav_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container_lat, new DashFrag()).commit();
                item.setIcon(R.mipmap.dashboardmenu);
                break;

            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container_lat, new ProfileFrag()).commit();
                item.setIcon(R.mipmap.profilemenucolor);
                break;

            case R.id.nav_styleinfo:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container_lat, new StyleInfoFrag()).commit();
                item.setIcon(R.mipmap.styleinfomenu);
                break;


            case R.id.nav_approvals:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container_lat, new ApprovalsFrag()).commit();
                item.setIcon(R.mipmap.approvalmenu);
                break;

            case R.id.nav_reports:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container_lat, new ReportsFrag()).commit();
                item.setIcon(R.mipmap.reportmenu);
                break;




        }
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame, temp).commit();

        m_draw.closeDrawer(GravityCompat.START);
        return true;



    }

    private void opendialog() {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to Logout?");
        //  alertDialogBuilder.setTitle("Confirmation Required");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Prefs.INSTANCE.clear();
                        Intent i=new Intent(HomeActivity.this,LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
                //   progressBar.setVisibility(View.GONE);

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}