import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.evention.ui.screens.home.payment.PaymentMethod
import com.example.evention.ui.screens.home.payment.PaymentScreen
import com.example.evention.ui.theme.EventionTheme
import com.example.evention.R


@Composable
fun SplashScreen() {
    val context = LocalContext.current
    val lifecycleScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        // Simula carregamento
        delay(2000)
        //navega para home
        //navController.navigate("home") {
        //    popUpTo("splash") { inclusive = true } // remove splash do backstack
        //}
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), // fundo
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logosplash), // logo
            contentDescription = "Logo da App",
            modifier = Modifier.size(280.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    EventionTheme {
        SplashScreen()
    }
}