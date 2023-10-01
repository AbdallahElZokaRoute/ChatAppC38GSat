package com.route.chatappc38gsat.register

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import com.route.chatappc38gsat.R
import com.route.chatappc38gsat.home.HomeActivity
import com.route.chatappc38gsat.splash.ui.theme.ChatAppC38GSatTheme

class RegisterActivity : ComponentActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppC38GSatTheme {
                // A surface container using the 'background' color from the theme
                RegisterContent(navigator = this)
            }
        }
    }

    override fun navigateToHomeScreen() {
        val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterContent(viewModel: RegisterViewModel = viewModel(), navigator: Navigator) {
    viewModel.navigator = navigator
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentColor = colorResource(id = R.color.white),
        topBar = {
            Text(
                text = stringResource(id = R.string.register),
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

            ChatTextField(
                state = viewModel.firstNameState,
                "First name",
                viewModel.firstNameErrorState
            )
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
            ChatButton(label = "Create Account", onClick = {
                viewModel.sendDataToFirebaseAuth()
            })
        }
        LoadingDialog(viewModel.isLoadingState)
        ChatDialog(viewModel.dialogMessageState)

    }
}

@Composable
fun ChatDialog(dialogMessageState: MutableState<String>, onClick: () -> Unit = {}) {
    if (dialogMessageState.value.isNotEmpty())
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                TextButton(onClick = {
                    dialogMessageState.value = ""
                    onClick()
                }) {
                    Text(text = "OK", style = TextStyle(color = colorResource(id = R.color.blue)))
                }
            },
            text = {
                Text(text = dialogMessageState.value)
            },
        )
}

@Composable
fun LoadingDialog(isLoadingState: MutableState<Boolean>) {
    if (isLoadingState.value)
        Dialog(onDismissRequest = { }) {
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.blue),
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp)
                )
            }
        }
}

@Composable                                                                         // void
fun ChatButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .shadow(elevation = 5.dp, spotColor = colorResource(id = R.color.grey3)),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.blue),
            contentColor = colorResource(
                id = R.color.white,
            )
        )

    ) {
        Text(text = label)
        Spacer(modifier = Modifier.fillMaxWidth(0.3F))
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "Icon arrow right"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTextField(
    state: MutableState<String>,
    label: String,
    errorState: MutableState<String>,
    isPassword: Boolean = false
) {
    /*
        class InputFieldsWrapper{
            var state : String
            var errorStringResourceId : Int?

        }

     */
    TextField(
        value = state.value,
        onValueChange = { newValue ->
            state.value = newValue
        },
        label = {
            Text(
                text = label, style = TextStyle(
                    fontSize = 16.sp, color = colorResource(
                        id = R.color.grey
                    )
                )
            )
        },
        isError = errorState.value.isNotEmpty(),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.Transparent),

        colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
    )
    if (errorState.value.isNotEmpty())
        Text(
            text = errorState.value,
            style = TextStyle(color = Color.Red),
            modifier = Modifier.padding(horizontal = 16.dp)
        )


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ChatAppC38GSatTheme {
        RegisterContent(navigator = object : Navigator {
            override fun navigateToHomeScreen() {

            }
        })
    }
}