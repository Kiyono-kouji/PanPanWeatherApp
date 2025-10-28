package com.example.panpanweatherapp.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.panpanweatherapp.R
import com.example.panpanweatherapp.ui.viewmodel.PanPanWeatherAppViewModel
import kotlin.math.roundToInt

@Composable
fun PanPanWeatherAppView(
    modifier: Modifier = Modifier,
    viewModel: PanPanWeatherAppViewModel = viewModel()
){
    var userInput by remember { mutableStateOf("") }
    val weatherInfo by viewModel.weather.collectAsState()
    val weatherIcon by viewModel.weatherIcon.collectAsState()
    val date by viewModel.formatedDate.collectAsState()
    val time by viewModel.formatedTime.collectAsState()
    val infoCardList by viewModel.listWeatherInfo.collectAsState()
    val sunrise by viewModel.sunrieTime.collectAsState()
    val sunset by viewModel.sunsetTime.collectAsState()
    Column (
        modifier = modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(R.drawable.weather___home_2),
                contentScale = ContentScale.Crop
            )
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row (
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(20)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.25f),
                        shape = RoundedCornerShape(20)
                    )
                    .clip(RoundedCornerShape(20))
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.padding(start = 8.dp)
                )

                TextField(
                    value = userInput,
                    onValueChange = {userInput = it},
                    placeholder = { Text("Enter city name...", color = Color.White.copy(alpha = 0.7f)) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                )

            }
            Button (
                onClick = {
                    if (userInput.isNotBlank()){
                        viewModel.searchCity(userInput)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.2f)
                ),
                shape = RoundedCornerShape(30),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier
                    .padding(end = 16.dp)
                    .height(62.dp)
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.25f),
                        shape = RoundedCornerShape(30)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Search", color = Color.White)
            }
        }
        if (weatherInfo.errorOccured){
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ){
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier
                        .size(60.dp)
                )
                Text(
                    "Opps! Something went wrong",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
                Text(
                    text = weatherInfo.errorResponse!!,
                    color = Color.LightGray,
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
            }
        } else if (weatherInfo.cityName.isNotBlank()){
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
            ){
                item {
                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp)
                    ){
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = null,
                                tint = Color.White,
                            )
                            Text(
                                text = weatherInfo.cityName,
                                color = Color.White
                            )
                        }
                        Text(
                            text = date,
                            color = Color.White,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(top = 8.dp)
                        )
                        Text(
                            text = "Updated as of ${time}",
                            color = Color.LightGray,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(top = 8.dp)
                        )
                        Row (
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 36.dp)
                        ){
                            Column (
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ){
                                weatherIcon?.let {
                                    Image(
                                        painter = rememberAsyncImagePainter(it),
                                        contentDescription = null,
                                        modifier = Modifier.size(64.dp)
                                    )
                                }
                                Text(
                                    text = weatherInfo.weatherConditions,
                                    color = Color.White,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${weatherInfo.temperature.roundToInt()}°C",
                                    color = Color.White,
                                    fontSize = 48.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Image(
                                painter = painterResource(if (weatherInfo.weatherConditions.toLowerCase() == "clear"){
                                    R.drawable.blue_and_black_bold_typography_quote_poster_3
                                } else if (weatherInfo.weatherConditions.toLowerCase() == "rain"){
                                    R.drawable.blue_and_black_bold_typography_quote_poster_2
                                } else {
                                    R.drawable.blue_and_black_bold_typography_quote_poster
                                }
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(170.dp)
                            )
                        }
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = modifier
                                .fillMaxWidth()
                                .height(280.dp)
                                .padding(horizontal = 12.dp)
                        ) {
                            items(infoCardList){ info->
                                WeatherCardView(title = info.get(0) as String, value = info.get(1) as String, imageIcon = info.get(2) as Int)
                            }
                        }
                        Row (
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Column (
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Image(
                                    painter = painterResource(R.drawable.vector),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(40.dp)
                                )
                                Text(
                                    "Sunrise",
                                    color = Color.LightGray
                                )
                                Text(
                                    text = sunrise,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column (
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Image(
                                    painter = painterResource(R.drawable.vector_21png),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(40.dp)
                                )
                                Text(
                                    "Sunset",
                                    color = Color.LightGray
                                )
                                Text(
                                    text = sunset,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        } else{
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ){
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier
                        .size(60.dp)
                )
                Text(
                    "Search for a city to get started",
                    color = Color.LightGray,
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun PanPanWeatherAppPreview(){
    PanPanWeatherAppView()
}