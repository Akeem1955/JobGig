package com.pioneers.jobgig

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pioneers.jobgig.ui.theme.JobGigTheme

class ChatRoom {
    companion object{
        @Composable
        fun ChatRoomA(){

        }
        @Composable
        fun Reciever(painter:Painter){
            val width = (LocalConfiguration.current.screenWidthDp * 0.75).dp
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.width(2.dp))
                Image(
                    painter = painter, contentDescription = "", modifier = Modifier
                        .size(30.dp)
                        .clip(
                            CircleShape
                        )
                )
               Spacer(modifier = Modifier.width(2.dp))
                Surface(modifier = Modifier
                    .wrapContentWidth()
                    .widthIn(max = width)
                    .padding(bottom = 8.dp, top = 4.dp)
                    , color = MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.medium) {
                    Box {
                        Text(text = "Dignified Humility - Khutbah by Nouman Ali Khan" +
                                "TimeStamp:" +
                                "Minute 10 upward", modifier = Modifier.padding(end = 4.dp, start = 4.dp, bottom = 20.dp))
                        Text(fontWeight = FontWeight.Bold, fontSize = 10.sp, text = "2:30 pm", modifier = Modifier
                            .align(alignment = Alignment.BottomEnd)
                            .padding(4.dp))
                    }
                }

            }
        }
        @Composable
        fun Sender(){
            val width = (LocalConfiguration.current.screenWidthDp * 0.75).dp
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Surface(modifier = Modifier
                    .wrapContentWidth()
                    .widthIn(max = width)
                    .padding(bottom = 8.dp, top = 4.dp, end = 20.dp)
                    , color = MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.medium) {
                    Box {
                        Text(text = "Dignified Humility - Khutbah by Nouman Ali Khan" +
                                "TimeStamp:" +
                                "Minute 10 upward", modifier = Modifier.padding(end = 4.dp, start = 4.dp, bottom = 20.dp))
                        Text(fontWeight = FontWeight.Bold, fontSize = 10.sp, text = "2:30 pm", modifier = Modifier
                            .align(alignment = Alignment.BottomEnd)
                            .padding(4.dp))
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevJ() {
    JobGigTheme {
        Column {
            ChatRoom.Reciever(painter = painterResource(id = R.drawable.map_plumber))
            ChatRoom.Sender()
        }

    }
}