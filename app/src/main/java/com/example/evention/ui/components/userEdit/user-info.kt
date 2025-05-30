package com.example.evention.ui.components.userEdit

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.evention.model.User
import com.example.evention.ui.screens.home.details.getDrawableId
import com.example.evention.ui.theme.EventionBlue

@Composable
fun UserEditInfo(user: User) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            Log.d("UserEditInfo", "Imagem escolhida: $uri")
        }
    }

    if (user.profilePicture != null) {
        val drawableId = getDrawableId(user.profilePicture)
        if (drawableId != 0) {
            Box(
                modifier = Modifier
                    .size(170.dp)
                    .clip(CircleShape)
                    .border(3.dp, EventionBlue, CircleShape)
                    .clickable {
                        launcher.launch("image/*")
                    }
            ) {
                Image(
                    painter = painterResource(id = drawableId),
                    contentDescription = "User Profile Image",
                    modifier = Modifier.matchParentSize()
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .size(170.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .clickable {
                    launcher.launch("image/*")
                }
        )
    }

    Spacer(modifier = Modifier.height(12.dp))

    Button(
        onClick = {
            launcher.launch("image/*")
        },
        modifier = Modifier
            .height(36.dp)
            .padding(horizontal = 8.dp)
            .border(1.dp, EventionBlue, RoundedCornerShape(20.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(20.dp),
        elevation = ButtonDefaults.buttonElevation(0.dp)
    ) {
        Text(
            text = "Change Avatar",
            color = EventionBlue,
            fontWeight = FontWeight.Medium
        )
    }
}
