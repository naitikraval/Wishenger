package com.wishenger.android.wishengerr;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codemybrainsout.ratingdialog.RatingDialog;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.karan.churi.PermissionManager.PermissionManager;
import com.wishenger.android.wishengerr.RegisterAndLogin.Login;
import com.wishenger.android.wishengerr.Settings.Autostartsettings;
import com.wishenger.android.wishengerr.Settings.WithoutAutostart;
import com.wishenger.android.wishengerr.Templates.Anniversary;
import com.wishenger.android.wishengerr.Templates.Festival;
import com.wishenger.android.wishengerr.Templates.Templates;

import java.util.List;

import maes.tech.intentanim.CustomIntent;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    ImageView imageView;
    TextView profilename;
    PermissionManager permissionManager;
    DrawerLayout drawer;
    BroadcastReceiver broadcastReceiver;

    final String modelname= Build.MODEL;
    final String devicename= Build.BRAND;

    //tour guide
    MaterialShowcaseSequence sequence;
    ShowcaseConfig config;

    //rateUs
    Firebase applink;
    String MyappLink;

    //firebase notification
    String SFcm;
    String tokenStr;

    //intAd
//    private AdView mAdView;
//    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        permissionManager = new PermissionManager() {
        };
        permissionManager.checkAndRequestPermissions(this);

        mAuth = FirebaseAuth.getInstance();

//        FirebaseUser user = mAuth.getCurrentUser();
//        String userId = user.getUid();

        //advertise
//        MobileAds.initialize(this,"ca-app-pub-2788542615708731~6318716719");
//
//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//
//
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/8691691433");
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());


//   get link
        Firebase.setAndroidContext(getApplicationContext());
        applink = new Firebase("https://wishenger-4b351.firebaseio.com/applink");
        applink.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MyappLink = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);

        imageView = headerview.findViewById(R.id.nav_header_imageView);
        profilename = headerview.findViewById(R.id.nav_header_textView);

//        LinearLayout header = (LinearLayout) headerview.findViewById(R.id.nav_header);
//        header.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, WishlistActivity.class);
//                startActivity(i);
//            }
//        });

        navigationView.setNavigationItemSelectedListener(this);


        LinearLayout setMsg = (LinearLayout) findViewById(R.id.set_msg);
        LinearLayout gifts = (LinearLayout) findViewById(R.id.gifts);
        LinearLayout wishlist = (LinearLayout) findViewById(R.id.wishList);
        LinearLayout comingSoon = (LinearLayout) findViewById(R.id.share_app);




        setMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SaveMessage.class);
                startActivity(i);
                CustomIntent.customType(MainActivity.this, "left-to-right");

            }
        });


        gifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent card = new Intent(MainActivity.this, Home_cards.class);
                startActivity(card);
                CustomIntent.customType(MainActivity.this, "left-to-right");

//                if (mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.show();
//                } else {
//                    Log.d("/TAG", "The interstitial wasn't loaded yet.");
//                }


            }
        });

//
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, WishlistActivity.class);
                startActivity(i);
                CustomIntent.customType(MainActivity.this, "left-to-right");
            }
        });

        comingSoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Snackbar snackbar = Snackbar
//                        .make(v, "New feature is coming soon, Stay tuned!", Snackbar.LENGTH_LONG);
//                snackbar.show();

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                String sharebody = "Download Wishenger App....Deceription here    " + MyappLink;
                share.putExtra(Intent.EXTRA_TEXT, sharebody);
                startActivity(Intent.createChooser(share, "Share App Via"));


            }
        });

        // sequence example
         config = new ShowcaseConfig();
        config.setDelay(300); // half second between each showcase view

         sequence = new MaterialShowcaseSequence(this, "9");

        sequence.setConfig(config);

        sequence.addSequenceItem(new MaterialShowcaseView.Builder(this)
                .setTarget(setMsg)
                .setDismissText("GOT IT")
                .setContentText("Set message from here..!")
                .withRectangleShape()
                .build());

        sequence.addSequenceItem(new MaterialShowcaseView.Builder(this)
                .setTarget(gifts)
                .setDismissText("GOT IT")
                .setContentText("Share wish cards on differnt social media platform instantly..! ")
                .withRectangleShape()
                .build());
        sequence.addSequenceItem(new MaterialShowcaseView.Builder(this)
                .setTarget(wishlist)
                .setDismissText("GOT IT")
                .setContentText("Check all the list of messages..!")
                .withRectangleShape()
                .build());



        //firebasenotification
                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {

            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {

                if (!task.isSuccessful()) {

                    Log.e("firebase", "getInstanceId failed", task.getException());
                    return;
                }

                // Get new Instance ID token
                tokenStr = task.getResult().getToken();
                SFcm = tokenStr;
                Log.e("***firebase", tokenStr);

            }
        });

    }

    //permission setup
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        permissionManager.checkResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        IntentFilter intentFilter = new IntentFilter("my.custom.action.tag.forDemo");
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(broadcastReceiver, intentFilter);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        else {


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

            alertDialog.setPositiveButton("Yes", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();

                }
            });

            alertDialog.setNegativeButton("No", null);

            alertDialog.setMessage("Do you want to exit?");
            alertDialog.setTitle("Wishenger");
            alertDialog.setIcon(R.drawable.logo);
            alertDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        ImageButton locButton = (ImageButton) menu.findItem(R.id.action_autostart).getActionView();
        locButton.setBackgroundResource(R.drawable.ic_warning_black_24dp);

        sequence.addSequenceItem(new MaterialShowcaseView.Builder(this)
                .setTarget(locButton)
                .setShapePadding(30)
                .setDismissText("GOT IT")
                .setContentText("Must enable autostart permission for required devices.")
                .build());

        sequence.start();

        locButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAutoStartup();
            }
        });


        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        NavigationView nv = (NavigationView) findViewById(R.id.nav_view);
        Menu m = nv.getMenu();

        int id = item.getItemId();

        if (id == R.id.dashboard) {
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);
            CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
            finish();
        } else if (id == R.id.Faq) {
            Intent i1 = new Intent(MainActivity.this, Faq.class);
            startActivity(i1);
            CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
        } else if (id == R.id.templates) {
            boolean b = !m.findItem(R.id.birthday).isVisible();

            m.findItem(R.id.birthday).setVisible(b);
            m.findItem(R.id.anniversary).setVisible(b);
            m.findItem(R.id.festivals).setVisible(b);
            return true;

//            Intent i1 = new Intent(MainActivity.this, Templates.class);
//            startActivity(i1);
        } else if (id == R.id.birthday) {
            Intent i1 = new Intent(MainActivity.this, Templates.class);
            startActivity(i1);
            Toast.makeText(this, "Birthday Templates", Toast.LENGTH_SHORT).show();
            CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
        } else if (id == R.id.anniversary) {
            Intent i1 = new Intent(MainActivity.this, Anniversary.class);
            startActivity(i1);
            Toast.makeText(this, "Anniversary Templates", Toast.LENGTH_SHORT).show();
            CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
        } else if (id == R.id.festivals) {
            Intent i1 = new Intent(MainActivity.this, Festival.class);
            startActivity(i1);
            Toast.makeText(this, "Festivals Templates", Toast.LENGTH_SHORT).show();
            CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
        } else if (id == R.id.about_us) {
            Intent i1 = new Intent(MainActivity.this, About.class);
            startActivity(i1);
            CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.



        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.log_out) {
            mAuth.signOut();
            Logout();
        }

        if (id == R.id.setting) {
          settings();
        }

        if (id == R.id.rate_us) {
            rateUs();
        }

//        if (id == R.id.action_autostart) {
//
//            addAutoStartup();
//        }

        return super.onOptionsItemSelected(item);
    }

    private void settings() {





        if (devicename.equalsIgnoreCase("OPPO")
                || devicename.equalsIgnoreCase("XIAOMI")
                || devicename.equalsIgnoreCase("VIVO")
                || devicename.equalsIgnoreCase("LETV")
                || devicename.equalsIgnoreCase("HONOR")
                || devicename.equalsIgnoreCase("ONEPLUS")) {

            Intent intent = new Intent(MainActivity.this, Autostartsettings.class);
            startActivity(intent);

        } else {
            Intent intent = new Intent(MainActivity.this, WithoutAutostart.class);
            startActivity(intent);
            CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
        }


    }


    private void Logout() {
        Intent i = new Intent(MainActivity.this, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    private void rateUs() {

        final RatingDialog ratingDialog = new RatingDialog.Builder(this)
                .title("How was your experience with us?")
                .titleTextColor(R.color.black)
                .ratingBarColor(R.color.colorAccent)
                .playstoreUrl(MyappLink)
                .build();
        ratingDialog.show();



        final RatingDialog ratingDialog1 = new RatingDialog.Builder(this)
                .threshold(3)
                .title("How was your experience with us?")
                .titleTextColor(R.color.black)
                .positiveButtonText("Not Now")
                .negativeButtonText("Never")
                .positiveButtonTextColor(R.color.black)
                .negativeButtonTextColor(R.color.black)
                .formHint("Tell us where we can improve")
                .ratingBarColor(R.color.colorAccent)
                .playstoreUrl("https://play.google.com/store/apps/details?id=com.whatsapp&hl=en")
                .onRatingChanged(new RatingDialog.Builder.RatingDialogListener() {
                    @Override
                    public void onRatingSelected(float rating, boolean thresholdCleared) {

                    }
                })
                .onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
                    @Override
                    public void onFormSubmitted(String feedback) {
                        Intent email = new Intent(Intent.ACTION_SENDTO);
                        email.setType("message/rfc822");
                        email.setData(Uri.parse("mailto:wishenger.info@gmail.com"));
                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"wishenger.info@gmail.com"});
                        email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                        email.putExtra(Intent.EXTRA_TEXT, feedback);
                        email.putExtra(Intent.EXTRA_TEXT,"Your Device Name:-  "+devicename+" "+modelname);
                        startActivity(Intent.createChooser(email, "Choose an Email client:"));

                    }
                }).build();

        ratingDialog1.show();

    }

    public void addAutoStartup() {

        try {
            Intent intent = new Intent();
            String manufacturer = android.os.Build.MANUFACTURER;

            if ("xiaomi".equalsIgnoreCase(manufacturer))
            {
                Toast.makeText(this, "Make sure permission is on", Toast.LENGTH_LONG).show();
                intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
            }
            else if ("oppo".equalsIgnoreCase(manufacturer))
            {
                Toast.makeText(this, "Make sure permission is on", Toast.LENGTH_LONG).show();
                intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
            }
            else if ("vivo".equalsIgnoreCase(manufacturer))
            {
                Toast.makeText(this, "Make sure permission is on", Toast.LENGTH_LONG).show();
                intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
            }
            else if ("Letv".equalsIgnoreCase(manufacturer))
            {
                Toast.makeText(this, "Make sure permission is on", Toast.LENGTH_LONG).show();
                intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity"));
            }
            else if ("Honor".equalsIgnoreCase(manufacturer))
            {
                Toast.makeText(this, "Make sure permission is on", Toast.LENGTH_LONG).show();
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
            }
            else if("oneplus".equalsIgnoreCase(manufacturer)) {
                Toast.makeText(this, "Make sure permission is on", Toast.LENGTH_LONG).show();
                intent.setComponent(new ComponentName("com.oneplus.security", "com.oneplus.security.chainlaunch.view.ChainLaunchAppListAct‌​ivity")); }

            else
                {
                Toast.makeText(this, "App works fine in your device!", Toast.LENGTH_SHORT).show();
                }

            List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (list.size() > 0) {
                startActivity(intent);
            }
        } catch (Exception e) {
            Log.e("exc", String.valueOf(e));
        }
    }

}
