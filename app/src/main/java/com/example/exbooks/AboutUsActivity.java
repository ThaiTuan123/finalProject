package com.example.exbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;
import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


        Element adsElement = new Element();
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .enableDarkMode(false)
                .setImage(R.drawable.logo)
                .setDescription(" This is an application for a final year project")
                .addItem(new Element("Version " + BuildConfig.VERSION_NAME, R.drawable.ic_baseline_info))
                .addGroup("CONNECT WITH US!")
                .addEmail("tuangcd17201@fpt.edu.vn", "Email")
                .addFacebook("vo.t.tuan.98","Facebook")
                .addGitHub("ThaiTuan123/finalProject","Github")
                .addWebsite("https://jpegteams.com/","Website")
                .addYoutube("UCdq9VLY5J3dbE9kSjMQJOUQ","Youtube")
                .addInstagram("vophamthaituan")
                .addPlayStore("com.example.yourprojectname")
                .addItem(createCopyright())
                .create();
        setContentView(aboutPage);
    }

    private Element createCopyright()
    {
        Element copyright = new Element();
        @SuppressLint("DefaultLocale") final String copyrightString = String.format("Copyright %d by Vo Pham Thai Tuan", Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyrightString);
//        copyright.setIconTint(R.drawable.logo);
        copyright.setGravity(Gravity.CENTER);
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutUsActivity.this,copyrightString,Toast.LENGTH_SHORT).show();
            }
        });
        return copyright;
    }
}