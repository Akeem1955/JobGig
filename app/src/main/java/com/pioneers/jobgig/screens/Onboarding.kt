package com.pioneers.jobgig.screens

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pioneers.jobgig.MainActivity
import com.pioneers.jobgig.MainViewModel
import com.pioneers.jobgig.R
import com.pioneers.jobgig.ui.theme.JobGigTheme

class Onboarding {
    companion object{
        @Composable
        fun GettingStarted(){
            Column(modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        0.0f to Color.White,
                        0.5f to Color.DarkGray,
                        1f to Color.Black,
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                )
            ) {
                Box (modifier = Modifier
                    .weight(3f)
                    .padding(bottom = 16.dp)){
                    Image(painter = painterResource(id = R.drawable.unboard1),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier
                        .align(
                            Alignment.BottomCenter
                        )
                        .padding(bottom = 16.dp)) {
                        Text(color = Color.White ,textAlign = TextAlign.Center  ,text = stringResource(id = R.string.get_startedspeech), fontSize = MaterialTheme.typography.headlineSmall.fontSize)
                        Text(color = Color.White ,textAlign = TextAlign.Center ,text =stringResource(id = R.string.get_startedspeech2), fontSize = MaterialTheme.typography.titleSmall.fontSize)
                    }
                }
                Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.btn)), shape = MaterialTheme.shapes.small, onClick = { /*TODO*/ }, modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
                ) {
                    Text(text = stringResource(id = R.string.get_started))
                }
            }

        }
        @Composable
        fun LoginScreen(viewModel: MainViewModel){
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
                OutlinedTextField(value = viewModel.email, onValueChange = {value-> viewModel.email = value}, placeholder = { Text(text = "Email")})
                OutlinedTextField(value = viewModel.password, onValueChange = {value-> viewModel.password = value}, placeholder = { Text(text = "password")})
                Button(onClick = {viewModel.authenticate()}) {
                    Text(text = "Login")
                }
            }
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun Preview(){

    JobGigTheme {
        //val view:MainViewModel= viewModel()
        Scaffold {
            OnBoardScreens.LoginScreen()
        }
    }
}