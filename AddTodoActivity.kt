package com.exercise.roomex

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exercise.roomex.db.AppDatabase
import com.exercise.roomex.db.ToDoDao
import com.exercise.roomex.db.ToDoEntity
import com.exercise.roomex.ui.theme.RoomExTheme

lateinit var activity : Activity
var qt = 0
var dc = dataClass()
lateinit var db : AppDatabase
lateinit var toDoDao: ToDoDao
lateinit var insertTodo : () -> Unit

class AddTodoActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity = this
        setContent {
            RoomExTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AddToScreen()
                }
            }
        }

        db = AppDatabase.getInstance(this)!!
        toDoDao = db.getTodoDao()


    }
}
@Composable
fun AddToScreen(){
    Column(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .height(400.dp)
                .padding(20.dp),
            horizontalAlignment = Alignment.Start,)
        {
            Text("제목", fontSize = 50.sp)
            var text by remember{
                mutableStateOf("해야할 일을 입력해주세요")
            }
            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it},
                Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(0.dp, 20.dp, 20.dp, 0.dp)
                ,
                textStyle = TextStyle(fontSize = 20.sp),
            )
            dc.todoTitle = text
            val declarations = listOf("낮음", "중간", "높음")
            RadioButtons(declaration = declarations)
            
        }

        Box(Modifier.weight(1.0f))
        Button(onClick = {
                         qt++
                        println("dd() = 왜 너가 반응해? " + dc.todoImportance++)
        },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White,
                contentColor = Color.Black
            )
        ){
            Text("HI",
                fontSize = 40.sp,
            )
        }
    }

}

@Composable
fun RadioButtons(declaration: List<String>) {
    val selectedValue = remember { mutableStateOf("") }
    val isSelectedItem: (String) -> Boolean = { selectedValue.value == it}
    val onChangeState: (String) -> Unit = { selectedValue.value = it }

    when(dc.todoImportance){
        selectedValue.value.compareTo("낮음") ->{
            dc.todoImportance = 0
        }
        selectedValue.value.compareTo("중간") ->{
            dc.todoImportance = 1
        }
        selectedValue.value.compareTo("높음") ->{
            dc.todoImportance = 2
        }
    }

    if(dc.todoImportance == -1 || dc.todoTitle.isBlank()){
        Toast.makeText(activity,"채워!" + qt ++,
            Toast.LENGTH_SHORT).show()
    }else{
        Thread{
            toDoDao.insertTodo(ToDoEntity(null, dc.todoTitle, dc.todoImportance))
        }.start()
    }

    Column(modifier = Modifier.padding(top = 10.dp)) {
        declaration.forEach { item ->
            Column {
                Row(
                    modifier = Modifier
                        .selectable( // 선택 가능한 상태
                            // selectedValue가 해당 item 과 같을때 선택된 상태를 의미
                            selected = isSelectedItem(item),
                            // 해당 라인 클릭시 selectedValue 값을 변경해 상태 변경
                            onClick = { onChangeState(item) },
                            role = Role.RadioButton
                        )
                        .padding(bottom = 13.dp)
                ) {
                    RadioButton(
                        // 선택됨을 나타내는 인수와 같음
                        // selectedValue가 해당 item 과 같을때 선택됨 표시
                        selected = isSelectedItem(item),// 선택됨을 나타내는 인수와 같음
                        onClick = null,
                        modifier = Modifier.padding(end = 15.dp)
                    )
                    Text(text = item)
                }
                // 기타 버튼 선택시 작성할 수 있는 TextField 노출
                if (selectedValue.value == "기타" && item == "기타") {
                    TextField(
                        value = selectedValue.value,
                        onValueChange = { selectedValue.value = it },
                        modifier = Modifier.height(30.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview2() {
    RoomExTheme {
        AddToScreen()
    }
}

class dataClass{
    public var todoTitle = ""
    public var todoImportance = -1
}