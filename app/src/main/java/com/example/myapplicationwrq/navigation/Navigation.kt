package com.example.myapplicationwrq.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplicationwrq.AlbumScreen
import com.example.myapplicationwrq.data.AlbumData
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController)
        }
        composable(
            route = Screen.AlbumScreen.route + "/albumName={albumName}/imageUrl={imageUrl}/animationNumber={animationNumber}",
            arguments = listOf(
                navArgument("albumName") {
                    type = NavType.StringType
                },
                navArgument("imageUrl") {
                    type = NavType.StringType
                },
                navArgument("animationNumber") {
                    type = NavType.StringType
                },
            )
        ) { entry ->

            val albumName = entry.arguments?.getString("albumName").orEmpty()
            val imageUrl =  URLDecoder.decode(entry.arguments?.getString("imageUrl").orEmpty(),StandardCharsets.UTF_8.toString())
            val animationNumber = entry.arguments?.getString("animationNumber").orEmpty()
            AlbumScreen(albumName,imageUrl,animationNumber)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(navController: NavController) {
    val images = remember {
        mutableStateListOf(
            AlbumData(
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/1/1f/Taylor_Swift_-_Taylor_Swift.png",
                name = "Taylor Swift"
            ),
            AlbumData(
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/5/5b/Fearless_%28Taylor%27s_Version%29_%282021_album_cover%29_by_Taylor_Swift.png",
                name = "Fearless"
            ),
            AlbumData(
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/5/5b/Taylor_Swift_-_Speak_Now_%28Taylor%27s_Version%29.png",
                name = "Speak Now"
            ),
            AlbumData(
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/4/47/Taylor_Swift_-_Red_%28Taylor%27s_Version%29.png?20211226114756",
                name = "Red"
            ),
            AlbumData(
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/f/f6/Taylor_Swift_-_1989.png?20140818215455",
                name = "1989"
            ),
            AlbumData(
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/f/f2/Taylor_Swift_-_Reputation.png?20211226113824",
                name = "reputation"
            ),
            AlbumData(
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/c/cd/Taylor_Swift_-_Lover.png?20211226113953",
                name = "Lover"
            ),
            AlbumData(
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/f/f8/Taylor_Swift_-_Folklore.png?20200723121311",
                name = "folklore"
            ),
            AlbumData(
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/0/0a/Taylor_Swift_-_Evermore.png?20211226114425",
                name = "evermore"
            ),
            AlbumData(
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/9/9f/Midnights_-_Taylor_Swift.png?20221030194148",
                name = "Midnights"
            ),

            )
    }

    val pagerState = rememberPagerState(
    )
    val matrix = remember {
        ColorMatrix()
    }
    Scaffold(modifier = Modifier.padding(vertical = 48.dp)) {
        HorizontalPager(
            pageCount = images.size,
            state = pagerState
        ) { index ->
            val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
            val imageSize by animateFloatAsState(
                targetValue = if (pageOffset != 0.0f) 0.75f else 1f,
                animationSpec = tween(durationMillis = 300),
                label = ""
            )
            LaunchedEffect(key1 = imageSize) {
                if (pageOffset != 0.0f) {
                    matrix.setToSaturation(0f)
                } else {
                    matrix.setToSaturation(1f)
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .graphicsLayer {
                            scaleX = imageSize
                            scaleY = imageSize
                        }
                        .clickable {
                            val encodedUrl = URLEncoder.encode(
                                images[index].imageUrl,
                                StandardCharsets.UTF_8.toString()
                            )
                            navController.navigate(
                                Screen.AlbumScreen.withArgs(
                                    "albumName=${images[index].name}",
                                    "imageUrl=${encodedUrl}",
                                    "animationNumber=${index}"
                                )
                            )

                        },
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(images[index].imageUrl)
                        .build(),
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.colorMatrix(matrix),
                )
                Text(
                    text = images[index].name,
                    fontSize = 24.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center

                )
            }




        }

    }
}