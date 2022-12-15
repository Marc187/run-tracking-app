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
import java.io.*


class RunImageRepository (private val application: Application){

    interface APIService {
        @Multipart
        @POST("course/image/{id_course}")
        suspend fun uploadFile(
                        @Path("id_course") id_course: Int,
                        @Part image_course: MultipartBody.Part):
                        Response<ImagePostResponse>

    }

    fun uploadImage(id_course: Int, imageBitmap: Bitmap) {

        //create a file to write bitmap data
        val f = File(application.cacheDir, "image_course")
        f.createNewFile()

        val bos = ByteArrayOutputStream()
        imageBitmap.compress(CompressFormat.PNG, 0 , bos)
        val bitmapdata: ByteArray = bos.toByteArray()

        //write the bytes in file
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(f)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            fos!!.write(bitmapdata)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Creation de la requete avec les parametres
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.SRVURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: APIService = retrofit.create(APIService::class.java)
        //val mpBody: RequestBody = MultipartBody.create(MediaType.parse("image/*"), f)

        val reqFile = RequestBody.create(MediaType.parse("image/*"), f)
        val body = MultipartBody.Part.createFormData("image_course", f.name, reqFile)

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.uploadFile(id_course, body)
            println("MY RESPONSE: $response")
        }
    }
}
