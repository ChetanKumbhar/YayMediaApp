package com.example.yaymediaapp.presenter.profile

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.yaymediaapp.navigation.Screen
import com.example.yaymediaapp.ui.theme.YayMediaAppTheme

@Composable
fun CreateProfileScreen(
    navController: NavController,
    vm: CreateProfileViewModel = hiltViewModel()
) {
    if(vm.isUserCreated.value){
        navController.navigate(Screen.PostList.route){
            navController.popBackStack()
        }
    } else{
        val viewState by vm.state.collectAsState()
        CreateAccountScreenContent(
            viewState = viewState,
            setName = { vm.setName(it) },
            setUsername = { vm.setUsername(it) },
            setImageUri = { vm.setImageUri(it) },
            onCreateAccountButtonClicked = { vm.createAccount{
                if(it) navController.navigate(Screen.PostList.route)
            } }
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountScreenContent(
    viewState: CreateAccountViewState,
    setName: (String) -> Unit,
    setUsername: (String) -> Unit,
    setImageUri: (Uri) -> Unit,
    onCreateAccountButtonClicked: () -> Unit,
) {
    Scaffold(
        bottomBar = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(all = 40.dp)
            ) {
                Text(
                    text = "Create your account",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(28.dp))

                ProfileImageChooser(
                    imageUri = viewState.imageUri,
                    setImageUri = { setImageUri(it) },
                    modifier = Modifier.size(140.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                CreateAccountTextField(
                    text = viewState.name,
                    setText = { setName(it) },
                    label = "Name",
                    placeholder = "Enter Name",
                )

                Spacer(modifier = Modifier.height(12.dp))

                CreateAccountTextField(
                    text = viewState.username,
                    setText = { setUsername(it) },
                    label = "Username",
                    placeholder = "Enter UserName",
                )

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = onCreateAccountButtonClicked,
                    enabled = viewState.enableCreateButton,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Create account")
                }
            }
        },
        content = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountTextField(
    text: String,
    setText: (String) -> Unit,
    label: String,
    placeholder: String,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    OutlinedTextField(
        value = text,
        onValueChange = { setText(it) },
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
        keyboardActions = KeyboardActions(onDone = { onImeAction() })
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileImageChooser(imageUri: Uri, setImageUri: (Uri) -> Unit, modifier: Modifier = Modifier) {
    var showGallerySelect by remember { mutableStateOf(false) }

    Card(
        shape = RectangleShape,
        onClick = { showGallerySelect = true },
        modifier = modifier
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (imageUri == EMPTY_IMAGE_URI) {
                Icon(
                    Icons.Rounded.AccountCircle,
                    contentDescription = "Profile image chooser",
                    modifier = modifier
                )
            } else {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Account image",
                    contentScale = ContentScale.Crop,
                    modifier = modifier.fillMaxSize()
                )
            }
        }
    }

    // Show the user gallery
    if (showGallerySelect) {
        GallerySelect(onImageUri = {

            showGallerySelect = false
            setImageUri(it)
        })
    }
}

@Preview
@Composable
fun CreateAccountScreenContentPreview() {
    YayMediaAppTheme {
        CreateAccountScreenContent(
            viewState = CreateAccountViewState(),
            setImageUri = {},
            setUsername = {},
            setName = {},
            onCreateAccountButtonClicked = {}
        )
    }
}
