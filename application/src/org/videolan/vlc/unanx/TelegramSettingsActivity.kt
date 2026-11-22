package org.videolan.vlc.unanx
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class TelegramSettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme(background = androidx.compose.ui.graphics.Color.Black)) {
                val context = LocalContext.current
                val prefs = context.getSharedPreferences("unanx_tg_prefs", Context.MODE_PRIVATE)
                var token by remember { mutableStateOf(prefs.getString("tg_token", "") ?: "") }
                var chatId by remember { mutableStateOf(prefs.getString("tg_chat_id", "") ?: "") }
                var saved by remember { mutableStateOf(false) }
                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    Text("Telegram Bot unanx", color = androidx.compose.ui.graphics.Color.White)
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(value = token, onValueChange = { token = it }, label = { Text("Bot Token") })
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(value = chatId, onValueChange = { chatId = it }, label = { Text("Chat ID") })
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = {
                        prefs.edit().putString("tg_token", token).putString("tg_chat_id", chatId).apply()
                        saved = true
                    }) { Text("Save") }
                    if (saved) Text("Saved!", color = androidx.compose.ui.graphics.Color(0xFFFF69B4))
                }
            }
        }
    }
}