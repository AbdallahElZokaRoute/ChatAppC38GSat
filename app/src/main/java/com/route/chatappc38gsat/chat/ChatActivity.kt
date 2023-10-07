package com.route.chatappc38gsat.chat

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import com.route.chatappc38gsat.chat.ui.theme.ChatAppC38GSatTheme
import com.route.chatappc38gsat.model.Constants
import com.route.chatappc38gsat.model.Room
import androidx.lifecycle.viewmodel.compose.viewModel
import com.route.chatappc38gsat.model.DataUtils
import com.route.chatappc38gsat.model.Message
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatActivity : ComponentActivity() {
    var room: Room? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        room = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getParcelableExtra(Constants.EXTRA_ROOM, Room::class.java)
        else
            intent.getParcelableExtra(Constants.EXTRA_ROOM) as Room?

        setContent {
            ChatAppC38GSatTheme {
                // A surface container using the 'background' color from the theme
                ChatScreenContent(roomTitle = room?.name ?: "", room = room!!)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreenContent(roomTitle: String, room: Room) {
    Scaffold(contentColor = colorResource(id = R.color.white), topBar = {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {

            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Icon back"
                )
            }
            Text(
                text = roomTitle,
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 22.sp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.width(30.dp))

        }
    }, bottomBar = { ChatBottomBar(room = room) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillBounds
                )
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
        ) {
            ChatLazyColumn()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBottomBar(viewModel: ChatViewModel = viewModel(), room: Room) {
    viewModel.room = room
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        OutlinedTextField(
            value = viewModel.messageFieldState.value,
            onValueChange = {
                viewModel.messageFieldState.value = it
            },
            label = {
                Text(text = "Enter Message")
            },
            shape = RoundedCornerShape(topEnd = 24.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.messageBoxBorderColor),
                unfocusedBorderColor = colorResource(id = R.color.messageBoxBorderColor)
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                viewModel.addMessageToFirestore()
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue))
        ) {
            Text(
                text = stringResource(id = R.string.send), style = TextStyle(
                    color = colorResource(
                        id = R.color.white
                    ),
                    fontSize = 18.sp
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(painter = painterResource(id = R.drawable.send), contentDescription = "Icon Send")
        }
    }
}

@Composable
fun ChatLazyColumn(viewModel: ChatViewModel = viewModel()) {
    viewModel.listenToMessagesChanges()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        reverseLayout = true
    ) {
        items(viewModel.messagesListState.value.size) {
            val message = viewModel.messagesListState.value.get(it)
            if (message.senderId == DataUtils.appUser?.uid) {
                // I Sent a message
                SentMessageCard(message = message)
            } else {
                // I Received a message
                ReceivedMessageCard(message = message)
            }
        }
    }
}

@Composable
fun ReceivedMessageCard(message: Message) {
    val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val date = Date(message.dateTime!!)
    val dateString = simpleDateFormat.format(date)
    Log.e("Hello", "${message.senderName}")
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = message.senderName ?: "", style = TextStyle(color = Color.Black))

        Row(
            horizontalArrangement = Arrangement.Start, modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = message.content ?: "",
                modifier = Modifier
                    .background(
                        colorResource(id = R.color.colorGrey4),
                        shape = RoundedCornerShape(
                            bottomEnd = 24.dp,
                            topEnd = 24.dp,
                            topStart = 24.dp
                        )
                    )
                    .padding(16.dp),
                style = TextStyle(fontSize = 22.sp, color = colorResource(id = R.color.black))
            )
            Text(
                text = dateString,
                modifier = Modifier.align(Alignment.Bottom),
                style = TextStyle(color = colorResource(id = R.color.colorBlack2))
            )

        }
    }
}

@Composable
fun SentMessageCard(message: Message) {
    val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val date = Date(message.dateTime!!)
    val dateString = simpleDateFormat.format(date)
    Row(
        horizontalArrangement = Arrangement.End, modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = dateString,
            modifier = Modifier.align(Alignment.Bottom),
            style = TextStyle(color = colorResource(id = R.color.colorBlack2))
        )
        Text(
            text = message.content ?: "",
            modifier = Modifier
                .background(
                    colorResource(id = R.color.blue),
                    shape = RoundedCornerShape(
                        bottomStart = 24.dp,
                        topEnd = 24.dp,
                        topStart = 24.dp
                    )
                )
                .padding(16.dp),
            style = TextStyle(fontSize = 22.sp, color = colorResource(id = R.color.white))
        )

    }
}

@Preview(showBackground = true)
@Composable
fun Preview2() {
    SentMessageCard(Message(content = "Hello world"))
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview6() {
    ChatAppC38GSatTheme {
        ChatScreenContent(roomTitle = "Hello World Room", Room())
    }
}