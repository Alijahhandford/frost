package com.dkanada.icecons.activities;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;

import com.dkanada.icecons.utils.ImageUtils;
import com.dkanada.icecons.utils.ScreenUtils;
import com.dkanada.icecons.R;

public class WallpaperSetActivity extends BaseActivity {

    private int imageId;
    private int screenWidth;
    private int screenHeight;
    float scale;

    Bitmap wallpaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        imageId = intent.getIntExtra("image", 0);
        screenWidth = ScreenUtils.width(getApplicationContext());
        screenHeight = ScreenUtils.height(getApplicationContext());
        wallpaper = ImageUtils.bitmapLoad(getApplicationContext().getResources(), imageId, screenWidth, screenHeight);
        scale = ScreenUtils.densityScale(getApplicationContext());

        createLayout();
    }

    private void createLayout() {
        LinearLayout baseLayout = new LinearLayout(this);
        baseLayout.setOrientation(LinearLayout.VERTICAL);
        baseLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        baseLayout.setGravity(Gravity.BOTTOM);
        setContentView(baseLayout);

        ImageView wallPreview = new ImageView(this);
        wallPreview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        wallPreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        wallPreview.setImageBitmap(wallpaper);
        baseLayout.addView(wallPreview);

        LinearLayout buttonBar = new LinearLayout(this);
        buttonBar.setOrientation(LinearLayout.HORIZONTAL);
        buttonBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Math.round(96 * scale)));
        baseLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        buttonBar.setGravity(Gravity.CENTER);
        buttonBar.setBackgroundColor(getResources().getColor(R.color.colorLight));
        baseLayout.addView(buttonBar);

        ImageView backButton = new ImageView(this);
        backButton.setLayoutParams(new LinearLayout.LayoutParams(0, Math.round(72 * scale), 1));
        backButton.setImageBitmap(ImageUtils.bitmapLoad(getApplicationContext().getResources(), R.drawable.ic_close, Math.round(72 * scale), Math.round(72 * scale)));
        buttonBar.addView(backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView applyButton = new ImageView(this);
        applyButton.setLayoutParams(new LinearLayout.LayoutParams(0, Math.round(72 * scale), 1));
        applyButton.setImageBitmap(ImageUtils.bitmapLoad(getApplicationContext().getResources(), R.drawable.ic_apply, Math.round(72 * scale), Math.round(72 * scale)));
        buttonBar.addView(applyButton);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWallpaper(v);
            }
        });
    }

    public void setWallpaper(View v) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        try {
            wallpaperManager.setBitmap(wallpaper);
            Toast toast = Toast.makeText(this, "wallpaper set", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
