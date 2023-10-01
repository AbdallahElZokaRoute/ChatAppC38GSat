package com.route.chatappc38gsat.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.route.chatappc38gsat.R
import com.route.chatappc38gsat.home.HomeActivity
import com.route.chatappc38gsat.login.LoginActivity
import com.route.chatappc38gsat.register.RegisterActivity

import androidx.lifecycle.viewmodel.compose.viewModel
import com.route.chatappc38gsat.splash.ui.theme.ChatAppC38GSatTheme

class SplashActivity : ComponentActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppC38GSatTheme {
                // A surface container using the 'background' color from the theme
//                Handler(Looper.getMainLooper()).postDelayed(
//                    {
//                        val intent = Intent(this, LoginActivity::class.java)
//                        startActivity(intent)
//                        finish()
//                    }, 2000
//                )
                SplashContent(navigator = this)
            }
        }
    }

    override fun navigateToHome() {
        val intent = Intent(this@SplashActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun navigateToLogin() {
        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}

@Composable
fun SplashContent(viewModel: SplashViewModel = viewModel(), navigator: Navigator) {
    viewModel.navigator = navigator
    viewModel.navigate()
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (logo, signature) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.logo), contentDescription = "Logo Image",
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxSize(0.35F)
        )
        Image(
            painter = painterResource(id = R.drawable.signature),
            contentDescription = "App Signature",
            modifier = Modifier
                .constrainAs(signature) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(0.6F)
                .fillMaxHeight(0.15F)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChatAppC38GSatTheme {
        SplashContent(navigator = object : Navigator {
            override fun navigateToHome() {

            }

            override fun navigateToLogin() {

            }

        })
    }
}