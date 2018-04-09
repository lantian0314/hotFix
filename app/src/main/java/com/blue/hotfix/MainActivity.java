package com.blue.hotfix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "------------start");
                DexLoaderUtil.copyDex(MainActivity.this, DexLoaderUtil.SECONDARY_DEX_NAME);
                DexLoaderUtil.copyDex(MainActivity.this, DexLoaderUtil.THIRD_DEX_NAME);

                String secondDexPath = DexLoaderUtil.getDexPath(MainActivity.this, DexLoaderUtil.SECONDARY_DEX_NAME);
                String thirdDexPath = DexLoaderUtil.getDexPath(MainActivity.this, DexLoaderUtil.THIRD_DEX_NAME);

                final String optimizedDexOutputPath = DexLoaderUtil.getOptimizedDexPath(MainActivity.this);
                DexLoaderUtil.injectAboveEqualApiLevel14(thirdDexPath, optimizedDexOutputPath, null, DexLoaderUtil.TEST_CLASS_NAME);
                DexLoaderUtil.injectAboveEqualApiLevel14(secondDexPath, optimizedDexOutputPath, null, DexLoaderUtil.TEST_CLASS_NAME);
                DexLoaderUtil.call(getClassLoader());
                Log.d(TAG, "------------end");
            }
        }).start();
    }
}
