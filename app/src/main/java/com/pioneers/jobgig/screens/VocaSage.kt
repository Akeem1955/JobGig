package com.pioneers.jobgig.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.TextPart
import com.pioneers.jobgig.R
import com.pioneers.jobgig.viewmodels.TrendModel
import com.pioneers.jobgig.viewmodels.VocaSageModel


@Composable
fun ChatItem(model: VocaSageModel, text: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(R.drawable.round_send_24)
                .placeholder(R.drawable.round_send_24).build(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(16.dp))
        if (model.isLoading.value) {
            CircularProgressIndicator()
        }
        Text(text = text, fontSize = MaterialTheme.typography.bodyMedium.fontSize)
    }
}


@Composable
fun ChatItemB(model: VocaSageModel, text: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(R.drawable.round_send_24)
                .placeholder(R.drawable.round_send_24).build(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(16.dp))
        if (model.isLoading.value) {
            CircularProgressIndicator()
        }
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(model._uri).placeholder(
                    R.drawable.round_camera_alt_24
                ).build(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentDescription = ""
            )
            Text(text = text, fontSize = MaterialTheme.typography.bodyMedium.fontSize)
        }
    }
}


@Composable
fun ChatBox(models: VocaSageModel, isActive: MutableState<Boolean>) {

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
            if (it != null) {
                models.setUri(it)
            }
        }
    val generativeModel = GenerativeModel(
        // For text-only input, use the gemini-pro model
        modelName = "gemini-pro",
        systemInstruction = Content("system", listOf(TextPart(promptB))),
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = stringResource(R.string.key)
    )
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 32.dp,
        modifier = Modifier.padding(horizontal = 16.dp, 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, end = 8.dp, start = 8.dp)
                .heightIn(min = 35.dp, max = 80.dp)
                .clip(MaterialTheme.shapes.large)
        ) {
            CustomTextField(state = models.promptState)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {
                    launcher.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly))
                })
                {
                    Icon(
                        tint = colorResource(id = R.color.prim_a),
                        painter = painterResource(id = R.drawable.round_camera_alt_24),
                        contentDescription = ""
                    )
                }
                IconButton(onClick = {
                    isActive.value = true
                    models.generateContent()
                })
                {
                    Icon(
                        tint = colorResource(id = R.color.prim_a),
                        painter = painterResource(id = R.drawable.round_send_24),
                        contentDescription = ""
                    )
                }
            }
        }
    }
}


@Composable
fun ChatboxB(modifier: Modifier, model: VocaSageModel) {
    Button(modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.prim_a),
            contentColor = Color.White
        ),
        onClick = {
            model.promptState.value = "Next"
            model.generateContent()
        }) {
        Text(text = "Next Tutorial Session --->")
    }
}


@Composable
fun ChatScreen() {
    val ctx = LocalContext.current
    val models: VocaSageModel = viewModel()
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {

        val chatType = rememberSaveable {
            mutableStateOf(ChatType.Normal)
        }
        AnimatedContent(
            modifier = Modifier.weight(1f),
            targetState = models.isActive, label = "Chat-Screen"
        ) { isActive ->
            println(isActive.value)
            if (isActive.value) {
                ConversationBox(model = models, modifier = Modifier.weight(1f))
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Hello, Adetunji",
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.purple_200)
                            )
                            Text(
                                text = "How Can VocaSage Help You Today?",
                                fontSize = MaterialTheme.typography.titleLarge.fontSize
                            )
                            Spacer(modifier = Modifier.height(24.dp))

                        }
                    }
                    item {
                        Column {
                            val text = "Unlock The Secrets Of Product Marketing"
                            SuggestionCard(text) { suggestClickedB(models, text) }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                    item {
                        Column {
                            val text = "How To Improve Design of This Gate"
                            val bitmap =
                                BitmapFactory.decodeResource(ctx.resources, R.drawable.gate)
                            SuggestionCardB(text) { suggestClickedC(models, text, ctx, bitmap) }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                    item {
                        Column {
                            val text = "Need to stay updated on industry trends?"
                            SuggestionCard(text) { suggestClicked(models, text) }
                        }
                    }
                }
            }
        }
        when (models.chatType.value) {
            ChatType.Normal -> {
                ChatBox(models = models, isActive = models.isActive)
            }

            ChatType.ProductMarketing -> {
                ChatboxB(modifier = Modifier.fillMaxWidth(0.8f), models)
            }
        }
    }
}

fun suggestClickedC(model: VocaSageModel, text: String, ctx: Context, bitmap: Bitmap) {
    model.isActive.value = true
    model.generateContentC(ctx, text, bitmap)
}

fun suggestClicked(model: VocaSageModel, text: String) {
    model.promptState.value = text
    model.isActive.value = true
    model.generateContent()
}

fun suggestClickedB(model: VocaSageModel, text: String) {
    model.chatType.value = ChatType.ProductMarketing
    model.isActive.value = true
    model.promptState.value = "next"
    model.generateContent()

}


@Composable
fun SuggestionCard(suggest: String, onClick: () -> Unit) {
    Surface(
        tonalElevation = 32.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .clickable(onClick = onClick),
        color = Color.Transparent,
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Text(
            text = suggest,
            modifier = Modifier.padding(16.dp),
            fontSize = MaterialTheme.typography.labelMedium.fontSize,
            fontStyle = FontStyle.Italic
        )
    }
}

@Composable
fun SuggestionCardB(suggest: String, onClick: () -> Unit) {
    Surface(
        tonalElevation = 32.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .clickable(onClick = onClick),
        color = Color.Transparent,
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Row(verticalAlignment = Alignment.Top, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = suggest,
                modifier = Modifier.padding(16.dp),
                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                fontStyle = FontStyle.Italic
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(R.drawable.gate)
                    .placeholder(R.drawable.gate).build(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentDescription = ""
            )
        }
    }
}


@Composable
fun CustomTextField(
    state: MutableState<String>,
    placeholder: String = "Message VocaSage..."
) {
    var isFocused by rememberSaveable {
        mutableStateOf(false)
    }
    BasicTextField(modifier = Modifier
        .onFocusChanged { change ->
            isFocused = change.isFocused
        }
        .padding(8.dp),
        value = state.value,
        onValueChange = { update -> state.value = update },
        decorationBox = { inner ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                if (state.value.isEmpty() && !isFocused) {
                    Text(
                        text = placeholder,
                        fontSize = MaterialTheme.typography.labelMedium.fontSize
                    )
                }
                inner()
            }
        })
}

enum class ChatType {
    ProductMarketing,
    Normal
}

@Composable
fun ConversationBox(model: VocaSageModel, modifier: Modifier) {
    LazyColumn(
        modifier = modifier, contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(model.chatData) { chat ->
            if (chat.role == "User") {
                ChatItem(model, chat.chat)
            } else {
                ChatItem(model, chat.chat)
            }
        }
    }
}

data class ChatData(var role: String, var chat: String)


@Composable
fun TrendScreen(model: TrendModel) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(34.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {

        item {
            ChartContainer(
                title = "Most Hired Vocational Skills",
                barData = model.bardataA.value, true
            )
        }
        item { Spacer(modifier = Modifier.height(50.dp)) }
        item {
            ChartContainer(
                title = "Most Payed Vocational Skills",
                barData = model.bardataB.value,
                false
            )
        }
        item { Spacer(modifier = Modifier.height(50.dp)) }
        item {
            ChartContainer(
                title = "Most Time Consuming Vocational Skills",
                barData = model.bardataC.value,
                false
            )
        }
        item { Spacer(modifier = Modifier.height(200.dp)) }
    }
}


