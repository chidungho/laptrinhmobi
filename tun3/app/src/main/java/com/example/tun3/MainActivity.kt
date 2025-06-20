package com.example.tun3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tun3.ui.theme.Tuần3Theme

// Định nghĩa data class và danh sách thành phần UI
data class UIComponent(
    val name: String,
    val description: String
)

val componentList = listOf(
    UIComponent("Text", "Displays text"),
    UIComponent("Image", "Displays an image"),
    UIComponent("TextField", "Input field for text"),
    UIComponent("PasswordField", "Input field for passwords"),
    UIComponent("Column", "Arranges elements vertically"),
    UIComponent("Row", "Arranges elements horizontally")
)

// Định nghĩa các màn hình cho Navigation
sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object ComponentList : Screen("component_list")
    object ComponentDetail : Screen("component_detail/{componentName}") {
        fun createRoute(componentName: String) = "component_detail/$componentName"
    }
    object LazyColumn : Screen("lazy_column")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tuần3Theme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = Screen.Welcome.route) {
                    composable(Screen.Welcome.route) {
                        WelcomeScreen {
                            navController.navigate(Screen.LazyColumn.route)
                        }
                    }
                    composable(Screen.ComponentList.route) {
                        ComponentListScreen { component ->
                            navController.navigate(Screen.ComponentDetail.createRoute(component.name))
                        }
                    }
                    composable(
                        route = Screen.ComponentDetail.route,
                        arguments = listOf(navArgument("componentName") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val name = backStackEntry.arguments?.getString("componentName")
                        val component = componentList.find { it.name == name }
                        ComponentDetailScreen(component) {
                            navController.popBackStack()
                        }
                    }
                    composable(Screen.LazyColumn.route) {
                        LazyColumnScreen(navController)
                    }
                }
            }
        }
    }
}

// Các Composable function UI bên ngoài class MainActivity

@Composable
fun WelcomeScreen(onPush: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Phần logo và chữ: căn giữa màn hình
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Logo",
                modifier = Modifier.size(140.dp)
            )
            Spacer(Modifier.height(24.dp))
            Text(
                "Jetpack Compose",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                "Jetpack Compose is a modern UI toolkit for building native Android applications using a declarative programming approach.",
                color = Color.Gray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        // Nút dưới đáy màn hình
        Button(
            onClick = onPush,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("I'm ready", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}


@Composable
fun ComponentListScreen(onComponentClick: (UIComponent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("UI Components List", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        componentList.forEach { component ->
            Button(
                onClick = { onComponentClick(component) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column {
                    Text(component.name, fontWeight = FontWeight.Medium)
                    Text(component.description, fontSize = 12.sp, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun ComponentDetailScreen(component: UIComponent?, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back"
                )
            }
            Text("Detail", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (component != null) {
            when (component.name) {
                "Text" -> {
                    Text(
                        buildAnnotatedString {
                            append("The ")
                            withStyle(SpanStyle(textDecoration = TextDecoration.LineThrough)) { append("quick ") }
                            withStyle(SpanStyle(color = Color(0xFFB8860B))) { append("Brown ") }
                            append("fox ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("jumps ") }
                            append("over the ")
                            withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) { append("lazy dog.") }
                        },
                        fontSize = 24.sp
                    )
                }
                "Image" -> {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "Demo image",
                        modifier = Modifier.size(500.dp)
                    )
                }
                "TextField" -> {
                    var text by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Enter text") }
                    )
                }
                "PasswordField" -> {
                    var pwd by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = pwd,
                        onValueChange = { pwd = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                }
                "Column" -> {
                    Column {
                        Text("Item 1")
                        Text("Item 2")
                        Text("Item 3")
                    }
                }
                "Row" -> {
                    Row {
                        Text("Left", modifier = Modifier.padding(8.dp))
                        Text("Right", modifier = Modifier.padding(8.dp))
                    }
                }
                else -> {
                    Text(component.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(component.description)
                }
            }
        }
    }
}

@Composable
fun LazyColumnScreen(navController: NavController) {
    val items = listOf(
        "01 | The only way to do great work is to love what you do.",
        "02 | The only way to do great work is to love what you do.",
        "03 | The only way to do great work is to love what you do.",
        "04 | The only way to do great work is to love what you do.",
        "05 | The only way to do great work is to love what you do.",
        "1.000.000 | The only way to do great work is to love what you do."
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            IconButton(onClick = { navController.popBackStack(Screen.Welcome.route, inclusive = false) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF2196F3)
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(
                "LazyColumn",
                color = Color(0xFF2196F3),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            items(items) { item ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            item,
                            modifier = Modifier.weight(1f),
                            fontSize = 16.sp
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next",
                            tint = Color.Black
                        )
                    }
                }
            }
        }
        // Nút Back to root ở dưới cùng
        Button(
            onClick = { navController.popBackStack(Screen.Welcome.route, inclusive = false) },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Back to root", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}
