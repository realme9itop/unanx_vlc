package org.videolan.vlc.unanx
import android.content.Context
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

object TelegramSender {
    fun sendVideoWatched(context: Context, videoTitle: String) {
        val prefs = context.getSharedPreferences("unanx_tg_prefs", Context.MODE_PRIVATE)
        val token = prefs.getString("tg_token", "") ?: ""
        val chatId = prefs.getString("tg_chat_id", "") ?: ""
        if (token.isBlank() || chatId.isBlank()) return
        Thread {
            try {
                val url = URL("https://api.telegram.org/bot$token/sendMessage")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.doOutput = true
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                val postData = "chat_id=" + URLEncoder.encode(chatId, "UTF-8") + "&text=" + URLEncoder.encode("Watched video: $videoTitle", "UTF-8")
                val os = conn.outputStream
                val writer = OutputStreamWriter(os, "UTF-8")
                writer.write(postData)
                writer.flush()
                writer.close()
                os.close()
                conn.responseCode
            } catch (e: Exception) { e.printStackTrace() }
        }.start()
    }
}