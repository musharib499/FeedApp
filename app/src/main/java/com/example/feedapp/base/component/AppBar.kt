package com.example.feedapp.base.component


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.feedapp.app.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarWidget(name: String, icon: ImageVector = Icons.Default.ArrowBack, content: @Composable () -> Unit, backClick: () -> Unit) {
    val context = LocalContext.current
    Scaffold(
        topBar = { AppBar(context = context, icon, title = { Text(name) }, backClick) },
        content = { paddingValues ->
            onContent(paddingValues, content)
        }
    )
}

@Composable
fun onContent(paddingValues: PaddingValues, content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(context: Context, icon: ImageVector, title: @Composable () -> Unit, backClick: () -> Unit) {
    TopAppBar(
        title = title,
        navigationIcon = { NavIcon(icon, context = context, backClick) },
        actions = { },
        colors = appBarColor(),
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun appBarColor() = TopAppBarDefaults.smallTopAppBarColors(
    containerColor = Purple40,
    titleContentColor = Color.White,
    navigationIconContentColor = Color.White
)

@Composable
fun NavIcon(icon: ImageVector, context: Context, backClick: () -> Unit) = Icon(
    imageVector = icon,
    modifier = Modifier
        .padding(horizontal = 12.dp)
        .clickable { backClick() },
    contentDescription = ""
)

@Composable
fun FloatingActionButton(context: Context) = FloatingActionButton(onClick = { Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show() }) {
    Icon(Icons.Filled.Add, contentDescription = "Add item")
}






