package com.inc.mountzoft.demo1;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int TAKE_PICTURE = 1, LOAD_IMAGE = 2, PICK_SONG=3;
    MediaPlayer mediaPlayer = new  MediaPlayer();
    private Uri imageUri_cam, muri ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar()/* or getSupportActionBar() */.setSubtitle(Html.fromHtml("<font color=\"red\">" + "Hello World !" + "</font>"));
        getSupportActionBar()/* or getSupportActionBar() */.setTitle(Html.fromHtml("<font color=\"red\">" + "J.A.R.V.I.S" + "</font>"));


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_buttons, menu);
        return super.onCreateOptionsMenu(menu);
    }




    public void takePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri_cam = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView imageView = (ImageView) findViewById(R.id.cam);
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    final Uri selectedImageCam = imageUri_cam;
                    getContentResolver().notifyChange(selectedImageCam, null);
                    ContentResolver cr = getContentResolver();
                    final Bitmap bitmapCam;
                    try {
                        bitmapCam = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImageCam);

                        imageView.setImageBitmap(bitmapCam);
                        Toast.makeText(this, selectedImageCam.toString(),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
                break;
            case LOAD_IMAGE:
                    if (resultCode == Activity.RESULT_OK) {
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(MainActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
                }
                break;
            case PICK_SONG :
                if (resultCode == RESULT_OK) {
                muri = getIntent().getData();
                //String ringTonePath = muri.toString();

                if (muri != null)
                {
                    try
                    {
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                        {
                            @Override
                            public void onCompletion(MediaPlayer mp)
                            {
                                mp.release();
                                mp = null;
                            }
                        });
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }
                }
            }

        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.mybutton) {
            Toast.makeText(this, "Ha Haaa haa !", Toast.LENGTH_SHORT).show();
            super.onOptionsItemSelected(item);
            System.gc();
            Intent intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            Uri data = Uri.parse("file:///sdcard/Music");
            String type = "audio/mp3";
            intent.setDataAndType(data, type);
            startActivityForResult(intent, PICK_SONG);
            return true;
        }else if(id == R.id.mybutton1){
            Toast.makeText(this, "Hi Hiii hii !", Toast.LENGTH_SHORT).show();
        }else if(id == R.id.mybutton2){
            Toast.makeText(this, "Ho Hooo hoo !", Toast.LENGTH_SHORT).show();
            Intent newintent = new Intent(this, Main2Activity.class);
            startActivity(newintent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            takePhoto(null);
        } else if (id == R.id.nav_gallery) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, LOAD_IMAGE);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
