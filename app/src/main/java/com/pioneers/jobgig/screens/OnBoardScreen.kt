package com.pioneers.jobgig.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.pioneers.jobgig.R
import com.pioneers.jobgig.ui.theme.JobGigTheme
import com.pioneers.jobgig.viewmodels.OnBoardViewModel

@Composable
fun GettingStarted(navHostController: NavHostController){
    Scaffold {scaffold ->
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
            Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.btn)),
                shape = MaterialTheme.shapes.small,
                onClick = { navHostController.navigate(route = ScreenRoute.Auth.route){popUpTo(route = ScreenRoute.GetStarted.route){inclusive = true} } },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(bottom = scaffold.calculateBottomPadding())
            ) {
                Text(text = stringResource(id = R.string.get_started))
            }
        }
    }

}



@Composable
fun Login(modifier: Modifier, viewModel: OnBoardViewModel, navController: NavController?){

    val passwordVisibility = remember {
        mutableStateOf(false)
    }
    val passwordIcon = if (passwordVisibility.value) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
    val visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()


   if (viewModel._isLoading){
       Dialog(onDismissRequest = {}) {
           CircularProgressIndicator()
       }
   }
    Box(modifier = modifier) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
            color = colorResource(id = R.color.background),
            shape = MaterialTheme.shapes.extraLarge
        )
        {
            Text(text = "Log in", fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
                fontWeight = FontWeight.ExtraBold,
                color = colorResource(id = R.color.white),
                textAlign = TextAlign.Center,
                modifier= Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp)

            )
        }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 75.dp),
            shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)
        ) {
            Column(modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(vertical = 32.dp)  ,verticalArrangement = Arrangement.spacedBy(16.dp),horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(100.dp))
                TextField(value = viewModel._email,
                    onValueChange = {changed->viewModel.updateEmail(changed)},
                    leadingIcon = { Icon(imageVector = Icons.Rounded.Email, contentDescription = "") },
                    label = { Text(text = "Email") },
                    supportingText = { Text(text = viewModel._errorEmail) },
                    isError = viewModel._isError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    placeholder = { Text(text = "Email") })
                TextField(value = viewModel._password,
                    onValueChange = {update ->viewModel.updatePassword(update)},
                    trailingIcon = { IconButton(onClick = {passwordVisibility.value = !passwordVisibility.value}) {
                        Icon(imageVector = passwordIcon, contentDescription = "") }
                    },
                    supportingText = { Text(text = viewModel._errorPassword)},
                    isError = viewModel._isErrorP ,
                    label = { Text(text = "Password") },
                    visualTransformation = visualTransformation,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    placeholder = { Text(text = "Password") })
                Text(text = "Forgot Password?", color = colorResource(id = R.color.btn), fontWeight = FontWeight.Bold,modifier = Modifier
                    .clickable { navController?.navigate(route = ScreenRoute.ForgetPassword.route) }
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), textAlign = TextAlign.End)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.reset()},
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.btn)),
                    modifier = Modifier.fillMaxWidth(0.7f),
                    shape = MaterialTheme.shapes.small) {
                    Text(text = "Log in", fontWeight = FontWeight.Bold)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Don't have an account?", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.labelMedium.fontSize)
                    Text(modifier = Modifier.clickable { navController?.navigate(route = ScreenRoute.Signup.route) } ,text = "Sign Up", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.labelMedium.fontSize, color = Color.Green)
                }
            }
        }
    }
}


@Composable
fun LoginScreen(viewModel: OnBoardViewModel, navController: NavController?){
    Scaffold {
        Box(modifier = Modifier.background(color = colorResource(id = R.color.white))) {
            Box(modifier = Modifier
                .background(color = colorResource(id = R.color.btn))
                .fillMaxWidth()
                .height(100.dp + it.calculateTopPadding())) {
            }
            Login(navController = navController,viewModel = viewModel,modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding() + 50.dp))
        }
    }
}

@Composable
fun Signup(modifier: Modifier, viewModel: OnBoardViewModel,  navController: NavController?){
    val passwordVisibility = remember {
        mutableStateOf(false)
    }
    val passwordIcon = if (passwordVisibility.value) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
    val visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()


    Box(modifier = modifier) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
            color = colorResource(id = R.color.background),
            shape = MaterialTheme.shapes.extraLarge
        )
        {
            Text(text = "Create account", fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
                fontWeight = FontWeight.ExtraBold,
                color = colorResource(id = R.color.white),
                textAlign = TextAlign.Center,
                modifier= Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp)

            )
        }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 75.dp),
            shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)
        ) {
            Column(modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(vertical = 32.dp)  ,verticalArrangement = Arrangement.spacedBy(16.dp),horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(100.dp))
                TextField(value = "",
                    onValueChange = {},
                    leadingIcon = { Icon(imageVector = Icons.Rounded.Person, contentDescription = "") },
                    label = { Text(text = "FullName") },
                    supportingText = { Text(text = "") },
                    placeholder = { Text(text = "FullName...") })
                TextField(value = "",
                    onValueChange = {},
                    leadingIcon = { Icon(imageVector = Icons.Rounded.Email, contentDescription = "") },
                    label = { Text(text = "Email") },
                    supportingText = { Text(text = "") },
                    placeholder = { Text(text = "Email") })
                TextField(value = "",
                    onValueChange = {},
                    trailingIcon = { IconButton(onClick = {passwordVisibility.value = !passwordVisibility.value}) {
                        Icon(imageVector = passwordIcon, contentDescription = "")
                    }
                    },
                    label = { Text(text = "Password") },
                    visualTransformation = visualTransformation,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    placeholder = { Text(text = "Password") })
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.btn)),
                    modifier = Modifier.fillMaxWidth(0.7f),
                    shape = MaterialTheme.shapes.small) {
                    Text(text = "Sign Up", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun SignUpScreen(viewModel: OnBoardViewModel, navController: NavController?){
    Scaffold {
        Box(modifier = Modifier.background(color = colorResource(id = R.color.white))) {
            Box(modifier = Modifier
                .background(color = colorResource(id = R.color.btn))
                .fillMaxWidth()
                .height(100.dp + it.calculateTopPadding())) {
            }
            Signup(navController = navController,viewModel = viewModel,modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding() + 50.dp))
        }
    }

}

@Composable
fun ResetPassword(modifier: Modifier, viewModel: OnBoardViewModel,  navController: NavController?){
    val passwordVisibility = remember {
        mutableStateOf(false)
    }
    val passwordVisibilityConfirm = remember {
        mutableStateOf(false)
    }
    val passwordIcon = if (passwordVisibility.value) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
    val passwordIconConfirm = if (passwordVisibilityConfirm.value) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
    val visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()
    val visualTransformationConfirm = if (passwordVisibilityConfirm.value) VisualTransformation.None else PasswordVisualTransformation()


    Box(modifier = modifier) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
            color = colorResource(id = R.color.background),
            shape = MaterialTheme.shapes.extraLarge
        )
        {
            Text(text = "Reset password", fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
                fontWeight = FontWeight.ExtraBold,
                color = colorResource(id = R.color.white),
                textAlign = TextAlign.Center,
                modifier= Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp)

            )
        }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 75.dp),
            shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)
        ) {
            Column(modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(vertical = 32.dp)  ,verticalArrangement = Arrangement.spacedBy(16.dp),horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(100.dp))
                TextField(value = "",
                    onValueChange = {},
                    leadingIcon = { Icon(imageVector = Icons.Rounded.Email, contentDescription = "") },
                    label = { Text(text = "Email") },
                    supportingText = { Text(text = "") },
                    placeholder = { Text(text = "Email") })
                TextField(value = "",
                    onValueChange = {},
                    trailingIcon = { IconButton(onClick = {passwordVisibility.value = !passwordVisibility.value}) {
                        Icon(imageVector = passwordIcon, contentDescription = "")
                    }
                    },
                    label = { Text(text = "New Password") },
                    visualTransformation = visualTransformation,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    placeholder = { Text(text = "New Password") })
                TextField(value = "",
                    onValueChange = {},
                    trailingIcon = { IconButton(onClick = {passwordVisibilityConfirm.value = !passwordVisibilityConfirm.value}) {
                        Icon(imageVector = passwordIconConfirm, contentDescription = "")
                    }
                    },
                    label = { Text(text = "Confirm Password") },
                    visualTransformation = visualTransformationConfirm,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    placeholder = { Text(text = "Confirm Password") })
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.btn)),
                    modifier = Modifier.fillMaxWidth(0.7f),
                    shape = MaterialTheme.shapes.small) {
                    Text(text = "Reset password", fontWeight = FontWeight.Bold)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Don't have an account?", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.labelMedium.fontSize)
                    Text(text = "Sign in", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.labelMedium.fontSize, color = Color.Green)
                }
            }
        }
    }
}


@Composable
fun ResetPasswordScreen(viewModel: OnBoardViewModel, navController: NavController?){
    Scaffold {
        Box(modifier = Modifier.background(color = colorResource(id = R.color.white))) {
            Box(modifier = Modifier
                .background(color = colorResource(id = R.color.btn))
                .fillMaxWidth()
                .height(100.dp + it.calculateTopPadding())) {
            }
            ResetPassword(navController = navController,viewModel = viewModel,modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding() + 50.dp))
        }
    }
}
















@Preview(showBackground = true)
@Composable

fun GreetingPreview() {
    JobGigTheme {
        Login(modifier = Modifier, viewModel = viewModel(), navController = null)
    }
}