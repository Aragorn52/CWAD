package sa.cwad

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sa.cwad.screens.splash.SplashFragment
import sa.cwad.screens.splash.SplashViewModel

/**
 * Entry point of the app.
 *
 * Splash activity contains only window background, all other initialization logic is placed to
 * [SplashFragment] and [SplashViewModel].
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

}