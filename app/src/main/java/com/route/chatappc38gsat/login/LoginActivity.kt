package com.route.chatappc38gsat.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.route.chatappc38gsat.R
import com.route.chatappc38gsat.login.ui.theme.ChatAppC38GSatTheme
import com.route.chatappc38gsat.register.ChatButton
import com.route.chatappc38gsat.register.ChatDialog
import com.route.chatappc38gsat.register.ChatTextField
import com.route.chatappc38gsat.register.LoadingDialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.route.chatappc38gsat.home.HomeActivity
import com.route.chatappc38gsat.register.RegisterActivity

class LoginActivity : ComponentActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppC38GSatTheme {
                // A surface container using the 'background' color from the theme
                LoginContent(navigator = this)
            }
        }
    }

    override fun navigateToHome() {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun navigateToRegister() {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginContent(viewModel: LoginViewModel = viewModel(), navigator: Navigator) {
    viewModel.navigator = navigator
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentColor = colorResource(id = R.color.white),
        topBar = {
            Text(
                text = stringResource(id = R.string.login),
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 22.sp),
                modifier = Modifier.fillMaxWidth()
            )
        }

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .paint(
                    painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.TopCenter
                )
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.45F))
            Spacer(modifier = Modifier.height(15.dp))
            ChatTextField(state = viewModel.emailState, "Email", viewModel.emailErrorState)
            Spacer(modifier = Modifier.height(15.dp))
            ChatTextField(
                state = viewModel.passwordState,
                "Password",
                viewModel.passwordErrorState,
                isPassword = true
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.10F))
            ChatButton(label = "Login", onClick = {
                viewModel.authenticate()
            })
            TextButton(onClick = { viewModel.navigator?.navigateToRegister() }) {
                Text(text = "create an account")
            }
        }
        LoadingDialog(viewModel.isLoadingState)
        ChatDialog(viewModel.dialogMessageState)

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    ChatAppC38GSatTheme {
        LoginContent(navigator = object : Navigator {
            override fun navigateToHome() {

            }

            override fun navigateToRegister() {

            }
        })
    }
}