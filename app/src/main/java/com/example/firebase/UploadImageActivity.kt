package com.example.firebase

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.net.URL
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class UploadImageActivity : AppCompatActivity() {
    lateinit var imageUri    : Uri
    lateinit var ivUpload   : ImageView
    lateinit var btnUpload  : Button

    // ---------------------------------
    private lateinit var executor: Executor
    private lateinit var handler : Handler
    private var image : Bitmap? = null

    // Untuk penggunaan storage firebase
    lateinit var uploadTask : UploadTask
    lateinit var fStore     : FirebaseStorage
    lateinit var sRef       : StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_image)

        ivUpload    = findViewById(R.id.ivUpload)
        btnUpload   = findViewById(R.id.btn_upload)
        fStore      = FirebaseStorage.getInstance("gs://crud-firebase-6a9c0.appspot.com")
        sRef        = fStore.reference

        btnUpload.setOnClickListener {
            selectImage()
        executor = Executors.newSingleThreadExecutor()
        handler = Handler(Looper.getMainLooper())
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==1 && resultCode== RESULT_OK && data !=null
            && data.data !=null)
            imageUri = data.data!!
            ivUpload.setImageURI(imageUri)
            btnUpload.text = "Upload Image"
            btnUpload.setOnClickListener {
                uploadImage(imageUri)
            }
    }

    private fun uploadImage(imageUri: Uri){
        val UUID = UUID.randomUUID().toString()
        val imageRef = sRef.child("uploads/images/${UUID}.jpg")
        uploadTask = imageRef.putFile(imageUri)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful){
                task.exception?.let {
                    throw it
                }
            }

            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful){
                val downloadUri = task.result
                Log.d("URL", downloadUri.toString())
                Toast.makeText(this,"Image berhasil diupload", Toast.LENGTH_LONG).show()
                ivUpload.setImageResource(R.drawable.muslimah)
                btnUpload.text = "Select Image"
                btnUpload.setOnClickListener {
                    selectImage()
                }
                getBitmapFormUrl(downloadUri.toString())
            }else{
                // Handler value
                Log.e("Error", task.isSuccessful.toString())
                Toast.makeText(this, "Image gagal diupload. ", Toast.LENGTH_LONG).show()
            }

        }

    }


    private fun getBitmapFormUrl(src: String) {
        executor.execute {
            // Image URL
            val imageURL = src
            // Tries to get the image and post it in the imageView
            // with the help of Handler
            try {
                val ins = URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(ins)
                // only for making changes in UI
                handler.post {
                    ivUpload.setImageBitmap(image)
                }
            }

            // If the URL doesnot point to
            // image or any other kind of failure
            catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}