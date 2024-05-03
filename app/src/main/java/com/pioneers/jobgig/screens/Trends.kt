package com.pioneers.jobgig.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pioneers.jobgig.R
import com.pioneers.jobgig.viewmodels.FeedBackModel
import kotlinx.serialization.Serializable

@Composable
fun Barchart(modifier: Modifier, percentage: Double, description: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        val color = colorResource(id = R.color.prim_a)

        Spacer(modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                val height = size.height
                val width = size.width
                val barheight = height / 8 * 7
                val barWidth = width / 5 * 3
                val barHeight3D = height - barheight
                val barWidth3D = (width - barWidth) * (height * 0.002f)
                var path = Path().apply {
                    moveTo(0f, height)
                    lineTo(barWidth, height)
                    lineTo(barWidth, height - barheight)
                    lineTo(0f, height - barheight)
                    close()
                }
                drawPath(path = path, brush = Brush.linearGradient(listOf(Color.Gray, color)))
                path = Path().apply {
                    moveTo(barWidth, height - barheight)
                    lineTo(barWidth3D + barWidth, 0f)
                    lineTo(barWidth3D + barWidth, barheight)
                    lineTo(barWidth, height)
                    close()
                }
                drawPath(
                    path = path,
                    brush = Brush.linearGradient(colors = listOf(color, Color.Gray))
                )
                path = Path().apply {
                    moveTo(0f, barHeight3D)
                    lineTo(barWidth, barHeight3D)
                    lineTo(barWidth + barWidth3D, 0f)
                    lineTo(barWidth3D, 0f)
                    close()
                }
                drawPath(
                    path = path,
                    brush = Brush.linearGradient(colors = listOf(color, Color.Green))
                )
                drawContext.canvas.nativeCanvas.apply {
                    drawText("${(percentage * 100).toInt()}%",
                        barWidth / 6f,
                        height + 55f,
                        android.graphics
                            .Paint()
                            .apply {
                                this.color = Color.Black.toArgb()
                                textSize = 16.dp.toPx()
                                isFakeBoldText = true
                            })
                    rotate(-55f, Offset(barWidth3D + 50, 0f)) {
                        drawText(
                            description,
                            0f,
                            0f,
                            android.graphics
                                .Paint()
                                .apply {
                                    this.color = Color.Black.toArgb()
                                    textSize = 14.dp.toPx()
                                    isFakeBoldText = true
                                })
                    }
                }
            })
    }
}


data class BarData(val value: Double, val description: String)


@Composable
fun Bar(datas: List<BarData>, modifier: Modifier = Modifier) {
    val valueSum = datas.sumOf { it.value }
    LazyRow(
        modifier = modifier, horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        items(datas) { item ->
            val percentage: Double = item.value / valueSum
        }
    }
}


@Composable
fun ChartContainer(title: String, barData: List<BarData>, isTimeline: Boolean) {
    val timeline = rememberSaveable {
        mutableStateOf(TimeLine.Daily)
    }
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = title, fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        if (isTimeline) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                TimelineHelper(timeline = timeline, type = TimeLine.Daily, text = "Daily")
                TimelineHelper(timeline = timeline, type = TimeLine.Monthly, text = "Monthly")
                TimelineHelper(timeline = timeline, type = TimeLine.ALlTime, text = "AllTime")

            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        LazyRow(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val dataSum = barData.sumOf { it.value }
            items(barData) { data ->
                Barchart(
                    modifier = Modifier.size(
                        50.dp,
                        (80 * (data.value / dataSum) * barData.size).dp
                    ), (data.value / dataSum), data.description
                )
            }
        }
    }
}

@Composable
fun FeedBackDialog(model: FeedBackModel) {
    val textState = rememberSaveable {
        mutableStateOf("")
    }
    val textStateB = rememberSaveable {
        mutableStateOf("")
    }
    val textStateC = rememberSaveable {
        mutableStateOf("")
    }
    val textStateD = rememberSaveable {
        mutableStateOf("")
    }

    Surface(tonalElevation = 24.dp, shape = MaterialTheme.shapes.large) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Your feedback is incredibly valuable to us and will help us enhance your experience with our app. Thank you for your time!",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = colorResource(
                    id = R.color.prim_a
                ),
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(text = "Comment your overall experience with the app!")
            CustomTextField(
                modifier = Modifier, type = TextType.Edit,
                textState = textState,
                label = "Feedback",
                placeholder = "Very Satisfied..."
            )
            Text(text = " What do you like most about the app?")
            CustomTextField(
                modifier = Modifier,
                type = TextType.Edit,
                textState = textStateB,
                label = "LikeFeature",
                placeholder = "your reply here..."
            )
            Text(text = "What do you think could be improved in the app?")
            CustomTextField(
                modifier = Modifier,
                type = TextType.Edit,
                textState = textStateC,
                placeholder = "your reply here..."
            )
            Text(text = "Are there any features you would like to see added to the app?")
            CustomTextField(
                modifier = Modifier,
                type = TextType.Edit,
                textState = textStateD,
                placeholder = "your reply here..."
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.btn),
                    contentColor = Color.White
                ), shape = MaterialTheme.shapes.large,
                    onClick = {
                        model.submitFeedback(
                            textState.value,
                            textStateB.value,
                            textStateC.value,
                            textStateD.value
                        )
                    }) {
                    Text(text = "Done")
                }
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                ), shape = MaterialTheme.shapes.large, onClick = {
                    model.showFeedbackDialog.value = false
                }) {
                    Text(text = "Cancel")
                }
            }
        }
    }

}


@Composable
fun TimelineHelperFake(timeline: MutableState<TimeLine>, type: TimeLine, text: String) {
    if (timeline.value.name == type.name) Button(colors = ButtonDefaults.buttonColors(
        containerColor = colorResource(
            id = R.color.purple_500
        ), contentColor = Color.White
    ), onClick = { /*TODO*/ }) {
        Text(text = text)
    } else TextButton(onClick = { timeline.value = type }) {
        Text(text = text)
    }
}


enum class TimeLines {
    Daily,
    Monthly,
    ALlTime
}

const val promptB =
    "Act like a vocational skill guru. You Will only strictly discuss vocational topics with user, make the content interesting and easy to understand and engaging, even for someone who might struggle with reading"
const val prompt =
    "Act like an instructor counseling users on marketing products. It will be a long session, so  split it into parts until user click next. use a tone that will help user understand easily, dive into topics as if they were an endless ocean of knowledge.Generate a response that will look better on a text composable in Android"
const val promptC =
    "Analyze the comment for sentiment analysis and classify your result as 'buggy', 'good', or 'neutral' in JSON format only.{\"sentiment\": \"good\"} "

data class Comments(
    val buggy: Int,
    val good: Int,
    val dislikeFeature: List<String>,
    val likeFeature: List<String>
)

@Serializable
data class Sentiment(var sentiment: String = "")
