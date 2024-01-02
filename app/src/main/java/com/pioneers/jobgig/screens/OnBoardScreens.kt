package com.pioneers.jobgig.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pioneers.jobgig.R

class OnBoardScreens {
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
                        Text(color = Color.White ,textAlign = TextAlign.Center ,text = stringResource(id = R.string.get_startedspeech2), fontSize = MaterialTheme.typography.titleSmall.fontSize)
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
        fun Login(modifier: Modifier){
            var passwordVisibility = remember {
                mutableStateOf(false)
            }
            var passwordIcon = if (passwordVisibility.value)Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
            var visualTransformation = if (passwordVisibility.value)VisualTransformation.None else PasswordVisualTransformation()


            Box(modifier = modifier) {
                Surface(modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                    color = colorResource(id = R.color.background),
                    shape = MaterialTheme.shapes.extraLarge
                )
                {
                    Text(text = "Log in", fontSize = MaterialTheme.typography.bodyLarge.fontSize ,
                        fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
                        color = colorResource(id = R.color.white),
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .align(alignment = Alignment.Center)
                    )
                }
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 75.dp),
                    shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.fillMaxHeight(0.15f))
                        OutlinedTextField(value = "",
                            onValueChange = {},
                            leadingIcon = { Icon(imageVector = Icons.Outlined.Email, contentDescription = "") },
                            label = { Text(text = "Password")},
                            supportingText = { Text(text = "")},
                            placeholder = { Text(text = "Email")})
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(value = "",
                            onValueChange = {},
                            trailingIcon = { IconButton(onClick = {passwordVisibility.value = !passwordVisibility.value}) {
                                Icon(imageVector = passwordIcon, contentDescription = "")
                            }},
                            label = { Text(text = "Password")},
                            visualTransformation = visualTransformation,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            placeholder = { Text(text = "Password")})
                        Text(text = "Forgot Password?")
                        Spacer(modifier = Modifier.fillMaxHeight(0.15f))
                        Button(onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.btn)),
                            modifier = Modifier.fillMaxWidth(0.7f),
                            shape = MaterialTheme.shapes.small) {
                            Text(text = "Login")
                        }
                        Text(text = "Don't have an account? Sign in")
                    }
                }
            }
        }
        
        
        
        @Composable
        fun LoginScreen(){
            Scaffold {
                Box(modifier = Modifier.background(color = colorResource(id = R.color.white))) {
                    Box(modifier = Modifier
                        .background(color = colorResource(id = R.color.btn))
                        .fillMaxWidth()
                        .height(100.dp + it.calculateTopPadding())) {
                    }
                    Login(modifier = Modifier
                        .fillMaxSize()
                        .padding(top = it.calculateTopPadding() + 50.dp))
                }
            }
        }
    }
}