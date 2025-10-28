package com.example.panpanweatherapp.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.panpanweatherapp.R

@Composable
fun WeatherCardView (
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    imageIcon: Int
){
    Card (
        modifier = modifier
            .width(100.dp)
            .height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
//            containerColor = Color.Black
        )
    ){
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ){
            Image(
                painter = painterResource(imageIcon),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
            )
            Text(
                text = title,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(vertical = 4.dp)
            )
            Text(
                text = value,
                color = Color.White,
                modifier = Modifier
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun WeatherCardPreview(){
    WeatherCardView(title = "Clouds", value = "8%", imageIcon = R.drawable.cloud)
}