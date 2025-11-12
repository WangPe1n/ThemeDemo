package com.example.themedemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private boolean isDefaultTheme;

    // 使用 static final 定义常量
    public static final String PREFS_NAME = "ThemePrefs";
    public static final String KEY_THEME = "isDefaultTheme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // --- 步骤 1: 应用主题 ---
        // 必须在 super.onCreate() 和 setContentView() 之前调用
        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        isDefaultTheme = prefs.getBoolean(KEY_THEME, true); // 第二个参数是默认值

        if (isDefaultTheme) {
            setTheme(R.style.Theme_MyApp_Default);
        } else {
            setTheme(R.style.Theme_MyApp_Orange);
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // --- 步骤 2: 设置按钮点击事件 ---
        Button changeThemeButton = findViewById(R.id.changeThemeButton);

        changeThemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 切换主题标志
                isDefaultTheme = !isDefaultTheme;

                // --- 步骤 3: 保存新的主题选择 ---
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(KEY_THEME, isDefaultTheme);
                editor.apply(); // 使用 apply() 在后台异步保存

                // --- 步骤 4: 重启 Activity 以应用新主题 ---
                recreate();
            }
        });
    }
}