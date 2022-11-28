package com.example.runningbuddy.repositories

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import com.example.runningbuddy.MainActivity
import com.example.runningbuddy.models.ImagePostResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class RunImageRepository (private val application: Application){
    interface APIService {
        @Multipart
        @POST("course/image/{id_course}")
        suspend fun uploadFile(
                        @Path("id_course") id_course: Int,
                        @Part("image_course") image_course: RequestBody):
                        Response<ImagePostResponse>

    }

    fun uploadImage(id_course: Int, imageBitmap: Bitmap) {
        // val converters = Converters()
        // val imageUri = converters.getImageUri(application, imageBitmap)
        // val file = File(imageUri)

        //create a file to write bitmap data
        val f = File(application.cacheDir, "new_image")
        f.createNewFile()

        val bos = ByteArrayOutputStream()
        imageBitmap.compress(CompressFormat.PNG, 0 , bos)
        val bitmapdata: ByteArray = bos.toByteArray()

        //write the bytes in file
        val fos = FileOutputStream(f)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()

        // Creation de la requete avec les parametres
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.SRVURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: APIService = retrofit.create(APIService::class.java)
        val mpBody: RequestBody = MultipartBody.create(MediaType.parse("image/*"), f)


        CoroutineScope(Dispatchers.IO).launch {
            val response = service.uploadFile(1, mpBody)
            println("MY RESPONSE: $response")
        }
    }
}
