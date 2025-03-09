package com.muajjin.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.muajjin.app.ui.theme.MuajjinTheme

class MainActivity : ComponentActivity() {
    private var doubleBackToExitPressedOnce = false

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Double press back to exit
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    finish()
                    return
                }
                doubleBackToExitPressedOnce = true
                Toast.makeText(this@MainActivity, "Press back again to exit", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
            }
        })

        setContent {
            MuajjinTheme {
                WebViewContent()
            }
        }
    }

    @Composable
    fun WebViewContent() {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.apply {
                        // Enable JavaScript
                        javaScriptEnabled = true

                        // Enable DOM storage for local storage and IndexedDB
                        domStorageEnabled = true

                        // Set cache mode (use default cache behavior)
                        cacheMode = WebSettings.LOAD_DEFAULT

                        // Use wide viewport and overview mode for better responsiveness
                        useWideViewPort = true
                        loadWithOverviewMode = true

                        // Disable zoom controls if not needed
                        builtInZoomControls = false
                        displayZoomControls = false

                        // Enable hardware acceleration for rendering
                        setLayerType(WebView.LAYER_TYPE_HARDWARE, null)
                    }
                    // Set WebViewClient to handle page navigation
                    webViewClient = WebViewClient()

                    // Set WebChromeClient for handling progress and custom dialogs (optional)
                    webChromeClient = WebChromeClient()

                    // Load the webpage URL
                    loadUrl("https://muajjin.surge.sh")
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
