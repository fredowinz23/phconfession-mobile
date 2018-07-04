package com.actinbox.mobibox.syncpack

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class SyncHttpHandler(val context: Context, val url: String) {

    /** Initialize sharedPreferences */
    internal var sharedPreferences: SharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

    /**
     * Http post request method
     *
     * @param params: post parameters
     *
     */
    fun postMethod(params: String): InputStream? {

        var DataInputStream: InputStream? = null
        try {
            //Preparing
            val url = URL(url)
            val cc = url.openConnection() as HttpURLConnection
            //set request in header
//            cc.setRequestProperty("Authorization", "Token " + sharedPreferences.getString("token", null))
//            cc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            //set timeout for reading InputStream
            cc.readTimeout = 5000
            // set timeout for connection
            cc.connectTimeout = 5000
            //set HTTP method to POST
            cc.requestMethod = "POST"
            //set it to true as we are connecting for input
            cc.doInput = true
            //opens the communication link
            cc.connect()

            //Writing data (bytes) to the data output stream
            val dos = DataOutputStream(cc.outputStream)
            dos.writeBytes(params)
            //flushes data output stream.
            dos.flush()
            dos.close()

            //if response code is 200 / OK then read Inputstream
            //HttpURLConnection.HTTP_OK is equal to 200
            if (cc.responseCode == HttpURLConnection.HTTP_OK) {
                DataInputStream = cc.inputStream
            }

        } catch (e: Exception) {
            Log.e("Error in GetData: ", e.toString())
        }

        return DataInputStream

    }

    /**
     * Data input stream from postMethod()
     *
     * @param stream: InputStream from http request postMethod function
     *
     */
    fun convertStreamToString(stream: InputStream): String {

        val isr = InputStreamReader(stream)
        val reader = BufferedReader(isr)
        val response = StringBuilder()

        val line = reader.readLine().toString()
        try {
            response.append(line)
        } catch (e: IOException) {
            Log.e("Error: ", e.toString())
        } catch (e: Exception) {
            Log.e( "Error", e.toString())
        } finally {

            try {
                stream.close()

            } catch (e: IOException) {
                Log.e( "Error", e.toString())

            } catch (e: Exception) {
                Log.e( "Error", e.toString())
            }

        }
        return response.toString()
    }
}