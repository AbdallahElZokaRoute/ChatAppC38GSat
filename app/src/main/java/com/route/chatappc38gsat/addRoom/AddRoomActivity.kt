package com.route.chatappc38gsat.addRoom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.route.chatappc38gsat.R
import com.route.chatappc38gsat.addRoom.ui.theme.ChatAppC38GSatTheme
import com.route.chatappc38gsat.register.ChatDialog
import com.route.chatappc38gsat.register.ChatTextField
import com.route.chatappc38gsat.register.LoadingDialog

class AddRoomActivity : ComponentActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppC38GSatTheme {
                // A surface container using the 'background' color from the theme
                AddRoomContent(navigator = this)
            }
        }
    }

    override fun navigateUp() {
        finish()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoomContent(viewModel: AddRoomViewModel = viewModel(), navigator: Navigator) {
    viewModel.navigator = navigator
    Scaffold(contentColor = colorResource(id = R.color.white), topBar = {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = {
                viewModel.navigator?.navigateUp()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Icon back"
                )
            }
            Text(
                text = stringResource(id = R.string.add_room),
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 22.sp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.width(30.dp))

        }
    }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,

            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillBounds
                )
                .padding(top = it.calculateTopPadding())
        ) {
            AddRoomCard(navigator = navigator)
        }
    }
}

@Composable
fun AddRoomCard(viewModel: AddRoomViewModel = viewModel(), navigator: Navigator) {
    viewModel.navigator = navigator
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.white),
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth(0.85F)
            .padding(vertical = 50.dp),

        ) {
        Text(
            text = "Create New Room",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Image(
            painter = painterResource(id = R.drawable.add_room_image),
            contentDescription = "add Room Image",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(50.dp), contentScale = ContentScale.FillHeight
        )
        ChatTextField(
            state = viewModel.roomNameState,
            label = "Room Name",
            errorState = viewModel.roomNameErrorState
        )
        Spacer(modifier = Modifier.padding(vertical = 6.dp))
        ChatCategoryDropDown(modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(vertical = 6.dp))
        ChatTextField(
            state = viewModel.roomDescriptionState,
            label = "Room Description",
            errorState = viewModel.roomDescriptionErrorState
        )
        Button(
            onClick = { viewModel.addRoomToFirestore() },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.blue), contentColor = colorResource(
                    id = R.color.white
                )
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 20.dp)
        ) {
            Text(text = "Create", modifier = Modifier.padding(horizontal = 12.dp))
        }
    }
    LoadingDialog(isLoadingState = viewModel.isLoadingState)
    ChatDialog(dialogMessageState = viewModel.dialogMessageState, onClick = {
        viewModel.navigator?.navigateUp()
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatCategoryDropDown(viewModel: AddRoomViewModel = viewModel(), modifier: Modifier = Modifier) {
    ExposedDropdownMenuBox(expanded = viewModel.isDropDownExpanded.value, onExpandedChange = {
        viewModel.isDropDownExpanded.value = it
    }, modifier = modifier) {
        TextField(
            // The `menuAnchor` modifier must be passed to the text field for correctness.
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = viewModel.selectedCategory.value.name ?: "",
            onValueChange = {},
            leadingIcon = {
                Image(
                    painter = painterResource(
                        id = viewModel.selectedCategory.value.imageId ?: R.drawable.movies
                    ),
                    contentDescription = "Room Category",
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                )
            },
            label = { Text("Room Category") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.isDropDownExpanded.value) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(containerColor = Color.Transparent),
        )
        ExposedDropdownMenu(
            expanded = viewModel.isDropDownExpanded.value,
            onDismissRequest = { viewModel.isDropDownExpanded.value = false },
        ) {
            viewModel.categories.forEach { selectionOption ->
                DropdownMenuItem(
                    text = {
                        Row {
                            Image(
                                painter = painterResource(
                                    id = selectionOption.imageId ?: R.drawable.movies
                                ),
                                contentDescription = "Room category selection",
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(50.dp)
                            )
                            Text(selectionOption.name ?: "")
                        }
                    },
                    onClick = {
                        viewModel.selectedCategory.value = selectionOption
                        viewModel.isDropDownExpanded.value = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    ChatAppC38GSatTheme {
        AddRoomContent(navigator = object : Navigator {
            override fun navigateUp() {

            }

        })
    }
}