package fr.carlosantunes.monappdeformation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

const val MYAPP = "CAA_APP"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i(MYAPP, "onCreate")
    }

    override fun onStart() {
        super.onStart()

        Log.i(MYAPP, "onStart")
    }

    override fun onResume() {
        super.onResume()

        Log.i(MYAPP, "onResume")
    }

    override fun onRestart() {
        super.onRestart()

        Log.i(MYAPP, "onRestart")
    }

    override fun onPause() {
        super.onPause()

        Log.i(MYAPP, "onResume")
    }

    override fun onStop() {
        super.onStop()

        Log.i(MYAPP, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.i(MYAPP, "onDestroy")
    }
}