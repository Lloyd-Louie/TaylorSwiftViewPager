package com.example.myapplicationwrq

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplicationwrq.ui.theme.MyApplicationwrqTheme

class AlbumActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationwrqTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    AlbumScreen()
                }
            }
        }
    }
}


@Composable
fun AlbumScreen(albumName: String, imageUrl: String, animationNumber: String) {
    Column {
        var progress by remember {
            mutableStateOf(0f)
        }
        AlbumHeader(progress,albumName,imageUrl,animationNumber)
        Spacer(modifier = Modifier.height(32.dp))
        Slider(value = progress, onValueChange = {
            progress = it
        }, modifier = Modifier.padding(horizontal = 32.dp))
    }

}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun AlbumHeader(progress: Float, albumName: String, imageUrl: String, animationNumber: String) {
    val context = LocalContext.current
    val motionScene = remember {
        context.resources.openRawResource(R.raw.motion_scene).readBytes().decodeToString()
    }
    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = progress,
        modifier = Modifier.fillMaxWidth()
    ) {
        val profilePicProperties = motionProperties(id = "profile_pic")
        val usernameProperties = motionProperties(id = "username_$animationNumber")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .layoutId("box")
        )

        AsyncImage(
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(
                    CircleShape
                )
                .border(
                    width = 2.dp,
                    color = profilePicProperties.value.color("background"),
                    shape = CircleShape
                )
                .layoutId("profile_pic"),
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .build(),

        )

        Text(
            text = albumName,
            modifier = Modifier.layoutId("username_$animationNumber"),
            fontSize = 24.sp,
            color = usernameProperties.value.color("background")
        )
    }
}