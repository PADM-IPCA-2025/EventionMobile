import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.evention.ui.theme.EventionBlue
import com.example.evention.ui.theme.EventionTheme

@Composable
fun CustomCreateEventTextField(labelText: String) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(labelText, color = EventionBlue) },
        textStyle = TextStyle(color = EventionBlue, fontWeight = FontWeight.Bold),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = EventionBlue,
            unfocusedBorderColor = EventionBlue,
            cursorColor = EventionBlue,
            focusedLabelColor = EventionBlue,
            unfocusedLabelColor = EventionBlue
        )
    )
}

@Preview(showBackground = true)
@Composable
fun CustomOutlinedTextFieldPreview() {
    EventionTheme {
        CustomCreateEventTextField("Label")
    }
}
