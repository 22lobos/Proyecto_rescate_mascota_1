package com.example.rescate_animales.activities

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import com.example.rescate_animales.ui.screens.CameraScreen
import com.example.rescate_animales.ui.theme.Rescate_AnimalesTheme
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraActivity : ComponentActivity() {
    private var imageUri: Uri? = null

    private val takePicture = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            setResult(RESULT_OK, imageUri?.let { uri ->
                android.content.Intent().apply {
                    data = uri
                }
            })
        } else {
            setResult(RESULT_CANCELED)
        }
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageUri = createTempImageFile()

        setContent {
            Rescate_AnimalesTheme {
                CameraScreen(
                    onPhotoTaken = {
                        imageUri?.let { takePicture.launch(it) }
                    },
                    onCancel = {
                        setResult(RESULT_CANCELED)
                        finish()
                    }
                )
            }
        }
    }

    private fun createTempImageFile(): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
        return Uri.fromFile(file)
    }
}