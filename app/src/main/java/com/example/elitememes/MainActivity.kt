package com.example.elitememes
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONObject
import android.content.ComponentCallbacks2
import android.widget.Button

class MainActivity : AppCompatActivity(), ComponentCallbacks2 {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        button.performClick()

        val progressView = findViewById<ImageView>(R.id.progressView)
        Glide.with(this).load(R.drawable.progress).into(progressView)
    }

    fun nextMeme(view: View) {
        val progressView = findViewById<ImageView>(R.id.progressView)
        val imgView = findViewById<ImageView>(R.id.imageView)
        progressView.visibility = View.VISIBLE
        imgView.visibility = View.INVISIBLE
        val api: String = "https://meme-api.herokuapp.com/gimme"
        val queue = Volley.newRequestQueue(this)

// Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET,
            api,
            { response: String ->
                // Display the first 500 characters of the response string.
                val obj = JSONObject(response)
                val img = obj.getString("url")
                Glide.with(this).load(img).into(imgView)
                progressView.visibility = View.GONE
                imgView.visibility = View.VISIBLE
            },
            { println("That didn't work!") }
        )
// Add the request to the RequestQueue.
        queue.add(stringRequest)

    }


//    release memory

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)

        // Determine which lifecycle or system event was raised.
        when (level) {

            ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN -> {
                /*
                   Release any UI objects that currently hold memory.

                   The user interface has moved to the background.
                */
            }

            ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE,
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW,
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL -> {
                /*
                   Release any memory that your app doesn't need to run.

                   The device is running low on memory while the app is running.
                   The event raised indicates the severity of the memory-related event.
                   If the event is TRIM_MEMORY_RUNNING_CRITICAL, then the system will
                   begin killing background processes.
                */
            }

            ComponentCallbacks2.TRIM_MEMORY_BACKGROUND,
            ComponentCallbacks2.TRIM_MEMORY_MODERATE,
            ComponentCallbacks2.TRIM_MEMORY_COMPLETE -> {
                /*
                   Release as much memory as the process can.

                   The app is on the LRU list and the system is running low on memory.
                   The event raised indicates where the app sits within the LRU list.
                   If the event is TRIM_MEMORY_COMPLETE, the process will be one of
                   the first to be terminated.
                */
            }

            else -> {
                /*
                  Release any non-critical data structures.

                  The app received an unrecognized memory level value
                  from the system. Treat this as a generic low-memory message.
                */
            }
        }
    }

}
