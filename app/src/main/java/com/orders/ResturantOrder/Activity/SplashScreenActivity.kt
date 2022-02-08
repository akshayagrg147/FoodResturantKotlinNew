package com.orders.ResturantOrder.Activity

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.airbnb.lottie.LottieAnimationView
import com.orders.ResturantOrder.MainActivity
import com.orders.ResturantOrder.R
import com.orders.ResturantOrder.SharedPreference.sharedpreferenceCommon
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar()?.hide();
        setContentView(R.layout.activity_splash_screen)


        val lottieAnimationView: LottieAnimationView = findViewById(R.id.lootie)
        val backgroundimage: LottieAnimationView = findViewById(R.id.backgroundimg)

     //   backgroundimage.animate().translationY(-3500f).setDuration(1000).startDelay = 2000
        lottieAnimationView.animate().translationX(3500f).setDuration(1000).setStartDelay(1000)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}
                    override fun onAnimationEnd(animation: Animator) {
                        val sharedpreference:sharedpreferenceCommon= sharedpreferenceCommon(this@SplashScreenActivity)
                        if(sharedpreference.getUserData().isEmpty())
                        {
                            val intent = Intent(this@SplashScreenActivity, MobileNumberIntegration::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent)
                        }
                        else
                        {
                            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent)
                        }

                    }

                    override fun onAnimationCancel(animation: Animator) {}
                    override fun onAnimationRepeat(animation: Animator) {}
                })

    }
}