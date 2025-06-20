package com.example.thuc_hanh_4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.thuc_hanh_4.ui.theme.Thuc_hanh_4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Thuc_hanh_4Theme {
                var showSplash by remember { mutableStateOf(true) }
                var showMainScreen by remember { mutableStateOf(false) }

                when {
                    showSplash -> SplashScreen {
                        showSplash = false
                    }
                    showMainScreen -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Xin chào!", fontSize = 32.sp)
                    }
                    else -> OnboardingPagerScreen {
                        showMainScreen = true
                    }
                }
            }
        }
    }
}
