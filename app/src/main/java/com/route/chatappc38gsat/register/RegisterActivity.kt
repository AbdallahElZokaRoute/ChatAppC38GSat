package com.route.chatappc38gsat.register

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.route.chatappc38gsat.R
import com.route.chatappc38gsat.ui.theme.ChatAppC38GSatTheme

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppC38GSatTheme {
                // A surface container using the 'background' color from the theme
                RegisterContent()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterContent(viewModel: RegisterViewModel = viewModel()) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()

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

            ChatTextField(state = viewModel.firstNameState, "First name")
            Spacer(modifier = Modifier.height(15.dp))
            ChatTextField(state = viewModel.emailState, "email")
            Spacer(modifier = Modifier.height(15.dp))
            ChatTextField(state = viewModel.passwordState, "password")
            Spacer(modifier = Modifier.fillMaxHeight(0.10F))
            Button(
                onClick = {

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
                Text(text = "Create Account")
                Spacer(modifier = Modifier.fillMaxWidth(0.3F))
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "Icon arrow right"
                )
            }
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTextField(state: MutableState<String>, label: String) {
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
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),

        colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
    )


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ChatAppC38GSatTheme {
        RegisterContent()
    }
}