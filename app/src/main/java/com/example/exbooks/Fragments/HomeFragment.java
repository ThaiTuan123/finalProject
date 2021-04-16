package com.example.exbooks.Fragments;

import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.exbooks.Adapter.PostAdapter;
import com.example.exbooks.Model.Post;
import com.example.exbooks.OptionsActivity;
import com.example.exbooks.R;
import com.example.exbooks.StartActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;


public class HomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerViewPosts;
    private PostAdapter postAdapter;
    private List<Post> postList;

    private List<String> followingList;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView menuIcon;
    private LinearLayout contentView;

    static final float END_SCALE = 0.7f;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int checkedItem;
    private String selected;

    static final String CHECKDITEM = "check_item";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // Menu hook
        drawerLayout = view.findViewById(R.id.drawer_layout);
        navigationView = view.findViewById(R.id.navigation_view);

        menuIcon = view.findViewById(R.id.menu_icon);
        contentView = view.findViewById(R.id.content);


        // dark mode
        sharedPreferences = this.getActivity().getSharedPreferences("themes", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        navigationDrawer();


        recyclerViewPosts = view.findViewById(R.id.recycler_view_posts);
        recyclerViewPosts.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerViewPosts.setLayoutManager(linearLayoutManager);

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(), postList);
        recyclerViewPosts.setAdapter(postAdapter);

        followingList = new ArrayList<>();

        checkFollowingUsers();

        return view;
    }

    private void navigationDrawer() {

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home_bar);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }

            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {

 // set color right of background
//        drawerLayout.setScrimColor(getResources().getColor(R.color.colorPrimary));

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }


    private void checkFollowingUsers() {
        FirebaseDatabase.getInstance().getReference().child("Follow").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid()).child("following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                followingList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    followingList.add(snapshot.getKey());
                }
                followingList.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
                readPosts();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readPosts() {
        FirebaseDatabase.getInstance().getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);

                    for (String id : followingList) {
                        if (post.getPublisher().equals(id)) {
                            postList.add(post);
                        }
                    }
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home_bar:
                Toast.makeText(getActivity(), "Home ", LENGTH_SHORT).show();
                break;

            case R.id.search_ebook:
                Toast.makeText(getActivity(), "Search Ebook ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.uploadEbook:
                Toast.makeText(getActivity(), "Upload Ebook ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.chat:
                Toast.makeText(getActivity(), "Chat ", Toast.LENGTH_SHORT).show();
                break;

            case R.id.category:
                Toast.makeText(getActivity(), "category ", Toast.LENGTH_SHORT).show();
                break;

            case R.id.settings:
                Toast.makeText(getActivity(), "settings ", Toast.LENGTH_SHORT).show();
                showDialog();
                break;

            case R.id.profile:
                Toast.makeText(getActivity(), "profile ", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logout:
                Toast.makeText(getActivity(), "logout ", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeFragment.this.getActivity(), StartActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                getActivity().finish();
                break;

            case R.id.cook:
                Toast.makeText(getActivity(), "cook ", Toast.LENGTH_SHORT).show();
                break;

            case R.id.design:
                Toast.makeText(getActivity(), "design ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.it:
                Toast.makeText(getActivity(), "it ", Toast.LENGTH_SHORT).show();
                break;

            case R.id.share:
                Toast.makeText(getActivity(), "share ", Toast.LENGTH_SHORT).show();

                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Exbook");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "https://play.google.com/store/apps/details?id=" + getActivity().getApplicationContext().getPackageName());
                    startActivity(Intent.createChooser(intent, "Share with"));
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "something wrong ", Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.rate_us:
                Toast.makeText(getActivity(), "rate_us ", Toast.LENGTH_SHORT).show();

                try {
                    Uri marketUri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                    startActivity(marketIntent);
                } catch (ActivityNotFoundException e) {
                    Uri marketUri = Uri.parse("https://play.google.com/store/apps/details?id=" + getActivity().getPackageName());
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                    startActivity(marketIntent);
                }

                break;


            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }

        return true;
    }

    // Light and Dark Theme.
    private void showDialog() {
        final String[] themes = this.getResources().getStringArray(R.array.theme);
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(HomeFragment.this.getActivity());
        builder.setTitle("Select Theme");
        builder.setSingleChoiceItems(R.array.theme, getCheckedItem(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selected = themes[i];
                checkedItem = i;
            }
        });

        builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (selected == null) {
                    selected = themes[i];
                    checkedItem = i;
                }

                switch (selected) {

                    case "System Default":
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

                        break;

                    case "Dark":
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                        break;
                    case "Light":
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                }

                setCheckedItem(i);

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private int getCheckedItem() {
        return sharedPreferences.getInt(CHECKDITEM, 0);
    }

    private void setCheckedItem(int i) {
        editor.putInt(CHECKDITEM, i);
        editor.apply();
    }


}