package com.exercise.roomex

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exercise.roomex.ui.theme.RoomExTheme

lateinit var intentF :() -> Unit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent = Intent(this,AddTodoActivity::class.java)
        intentF = {
            println("intentFC")
            startActivity(intent)
            this.finish()
        }
        setContent {
            RoomExTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val u = arrayListOf<String>(
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
        "asdfasdf","joyce",
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .verticalScroll(rememberScrollState())
                .background(Color.Yellow)
        ) {
            for(i in u){
                Text(text = i,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .width(300.dp)
                        .height(50.dp), fontSize = 40.sp
                )
            }
        }
        Button(onClick = {
            intentF()
        },
            modifier = Modifier
                .width(300.dp)
                .height(100.dp)
                .padding(0.dp, 20.dp, 20.dp, 20.dp)
            ,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White,
                contentColor = Color.Black)
        ){
            Text("HI",
                fontSize = 40.sp,
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    RoomExTheme {
        MainScreen()
    }
}