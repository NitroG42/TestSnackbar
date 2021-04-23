package com.nitro.testsnackbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import com.nitro.testsnackbar.ui.theme.TestSnackbarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            TestSnackbarTheme {
            // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
            Screen()
//                }
//            }
        }
    }
}

@Composable
fun Cell(text: String) {
    Text(
        text = text,
        Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(16.dp)
    )
}

@Composable
fun ListContent(modifier: Modifier) {
    LazyColumn() {
        items(contentItems) { message ->
            Cell(text = message)
        }
    }
}

@Composable
fun ScrollContent(modifier: Modifier = Modifier) {
    val scrollstate = rememberScrollState()
    Column(
        Modifier
            .background(backgroundCell)
            .verticalScroll(scrollstate)
            .then(modifier)

    ) {
        contentItems.forEach {
            Cell(text = it)
        }
    }
}

@Composable
fun Screen() {
    var snackbarHeight = remember { mutableStateOf(0) }
    Box {
        ScrollContent(Modifier.layout { measurable, constraints ->
            val placeable = measurable.measure(constraints.offset(0, -snackbarHeight.value))
            val height = constraints.constrainHeight(placeable.height + snackbarHeight.value)

            layout(placeable.width, height) {
                placeable.place(0, 0)
            }
        })
        Snackbar(
            modifier = Modifier
                .background(Color.Transparent)
                .padding(16.dp)
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    snackbarHeight.value = placeable.height
                    layout(placeable.width, placeable.height) {
                        placeable.place(0, 0)
                    }
                }
                .align(Alignment.BottomCenter)

        ) {
            Text(text = "my Snackbar")
        }
    }
}

@OptIn(ExperimentalStdlibApi::class)
val contentItems by lazy {
    buildList {
        repeat(12) {
            add("Item $it")
        }
    }
}

val backgroundCell = Color(0x33ff0000)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    TestSnackbarTheme {
        Screen()
    }
}