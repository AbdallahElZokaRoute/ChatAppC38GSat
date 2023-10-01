package com.route.chatappc38gsat.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
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
import com.route.chatappc38gsat.addRoom.AddRoomActivity
import com.route.chatappc38gsat.home.ui.theme.ChatAppC38GSatTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.route.chatappc38gsat.model.Category
import com.route.chatappc38gsat.model.Room

class HomeActivity : ComponentActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppC38GSatTheme {
                // A surface container using the 'background' color from the theme
                HomeContent(navigator = this)
            }
        }
    }

    override fun navigateToAddRoom() {
        val intent = Intent(this@HomeActivity, AddRoomActivity::class.java)
        startActivity(intent)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(viewModel: HomeViewModel = viewModel(), navigator: Navigator) {
    viewModel.navigator = navigator
    viewModel.getRoomsList()
    Scaffold(contentColor = colorResource(id = R.color.white), topBar = {
        Text(
            text = stringResource(id = R.string.home),
            style = TextStyle(textAlign = TextAlign.Center, fontSize = 22.sp),
            modifier = Modifier.fillMaxWidth()
        )
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                viewModel.navigateToAddRoomScreen()
            },
            containerColor = colorResource(id = R.color.blue),
            contentColor = colorResource(id = R.color.white)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "icon Add"
            )
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillBounds
                )
                .padding(top = it.calculateTopPadding())
        ) {
            ChatRooms()
        }
    }
}

@Composable
fun ChatRooms(viewModel: HomeViewModel = viewModel()) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(viewModel.roomListState.value.size) {
            val item = viewModel.roomListState.value.get(it)
            ChatRoomCard(room = item)
        }
    }
}

@Composable
fun ChatRoomCard(room: Room) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Image(
            painter = painterResource(
                id = Category.fromId(room.categoryId).imageId ?: R.drawable.sports
            ),
            contentDescription = "Room Category Image",
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = room.name ?: "",
            style = TextStyle(color = Color.Black),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    ChatAppC38GSatTheme {
        HomeContent(navigator = object : Navigator {
            override fun navigateToAddRoom() {

            }
        })
    }
}