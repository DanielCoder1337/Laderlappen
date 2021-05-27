package com.example.laderlappenlawnmower

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_automowercontroller.*


class AutomowerControllerActivity : AppCompatActivity() {
    companion object {
        val socket = MainActivity.socket
        var canSendMessage:Boolean = true
        var autoDriveMode : Boolean = true
    }

    @ExperimentalUnsignedTypes
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_automowercontroller)
        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.hide()
        }
        switchAutodrive.isChecked = true
        autoDrive(switchAutodrive.isChecked)
        //send initial command to arduino that we are starting with auto driving
        sendMessage("15", "22")

        // Go back to MainAcvtivity if we disconnect from the WebSocket.
        socket.onDisconnectWebServer.add {
            finish()
        }
        socket.onConnectRaspberry.removeAt(0)

        // Makes it so we can only send messages if the raspberry is also connected.
        socket.onDisconnectRaspberry.add { canSendMessage = false }
        socket.onConnectRaspberry.add {
            canSendMessage = true
            var bodyString : String = ""
            if (autoDriveMode){
                bodyString = "22"
            }
            else bodyString = "23"
            sendMessage("15",bodyString)
        }

        // Listener for when the raspberry collides with something.
        socket.onMessage["10"] = { body ->
            if(body == "20"){
                runOnUiThread {
                    Toast.makeText(this, "Collision", Toast.LENGTH_SHORT).show()
                    statusText.text = getString(R.string.statusCollision)
                    statusButtonLight.background = resources.getDrawable(R.drawable.circlered,theme)
                }

            }
            else if(body == "47"){
                runOnUiThread {
                    statusText.text = getString(R.string.statusOk)
                    statusButtonLight.background = resources.getDrawable(R.drawable.circlegreen,theme)
                }

            }
        }

        //Creates a handler which checks if internet connection is available every 2 seconds
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                checkWifi()
                mainHandler.postDelayed(this, 2000)
            }
        })

        // The following 4 listeners are called when each respective "arrow" is pressed and released.
        // We tell the raspberry to, for example, drive forward when we press the button, and stop driving forward when we release the button.
        buttonArrowUp.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
                view.performClick()
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        checkWifi()

                        buttonArrowUp.backgroundTintMode = PorterDuff.Mode.SRC_ATOP
                        sendMessage("16", "30")
                    }
                    MotionEvent.ACTION_UP -> {
                        checkWifi()

                        buttonArrowUp.backgroundTintMode = PorterDuff.Mode.MULTIPLY
                        sendMessage("16", "40")
                    }
                }
                return view.onTouchEvent(motionEvent) ?: true
            }
        })

        buttonArrowDown.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
                view.performClick()
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        checkWifi()
                        buttonArrowDown.backgroundTintMode = PorterDuff.Mode.SRC_ATOP
                        sendMessage("16", "32")
                    }
                    MotionEvent.ACTION_UP -> {
                        checkWifi()

                        buttonArrowDown.backgroundTintMode = PorterDuff.Mode.MULTIPLY
                        sendMessage("16", "42")
                    }
                }
                return view.onTouchEvent(motionEvent) ?: true
            }
        })

        buttonArrowLeft.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
                view.performClick()
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        checkWifi()

                        buttonArrowLeft.backgroundTintMode = PorterDuff.Mode.SRC_ATOP
                        sendMessage("16", "33")
                    }
                    MotionEvent.ACTION_UP -> {
                        checkWifi()

                        buttonArrowLeft.backgroundTintMode = PorterDuff.Mode.MULTIPLY
                        sendMessage("16", "43")
                    }
                }
                return view.onTouchEvent(motionEvent) ?: true
            }
        })

        buttonArrowRight.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
                view.performClick()
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        checkWifi()
                        buttonArrowRight.backgroundTintMode = PorterDuff.Mode.SRC_ATOP
                        sendMessage("16", "31")
                    }
                    MotionEvent.ACTION_UP -> {
                        checkWifi()
                        buttonArrowRight.backgroundTintMode = PorterDuff.Mode.MULTIPLY
                        sendMessage("16", "41")
                    }
                }
                return view.onTouchEvent(motionEvent) ?: true
            }
        })

        switchAutodrive.setOnClickListener {
            autoDrive(switchAutodrive.isChecked)
        }
    }

    // Function that checks if the raspberry is also connected to the web socket server before attempting to send anything.
    private fun sendMessage(head: String, body: String){
        if (canSendMessage){
            socket.send(head,body)
        }
        else Toast.makeText(this, "Can't send message because Raspberry is disconnected!", Toast.LENGTH_SHORT).show()
    }

    // Function that sets the visibility of the arrow buttons based on whether the mower is currently on auto-drive.
    @ExperimentalUnsignedTypes
    private fun autoDrive(active: Boolean){
        if (canSendMessage){
            if (active){
                sendMessage("15", "22")
                buttonArrowRight.isVisible = false
                buttonArrowLeft.isVisible = false
                buttonArrowUp.isVisible = false
                buttonArrowDown.isVisible = false
                statusText.isVisible = false
                statusTextView.isVisible = false
                statusButtonLight.isVisible=false
                autoDriveText.isVisible=true
            }
            else {
                sendMessage("15", "23")
                buttonArrowRight.isVisible = true
                buttonArrowLeft.isVisible = true
                buttonArrowUp.isVisible = true
                buttonArrowDown.isVisible = true
                statusText.isVisible = true
                statusTextView.isVisible = true
                statusButtonLight.isVisible=true
                autoDriveText.isVisible = false
            }
        }
        else {
            switchAutodrive.isChecked = !active
            Toast.makeText(this,"Raspberry is not connected and therefor you can't switch driving modes",Toast.LENGTH_SHORT).show()
        }
        autoDriveMode = active
    }

    // Checks if wifi is available and disconnects if it is not.
    private fun checkWifi(){
        if (!isOnline(this)) {
            socket.disconnect()
        }
    }

    // Function that checks if the device is currently able to connect to the internet.
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.onDisconnectRaspberry.removeAt(0)
        socket.onConnectRaspberry.removeAt(1)
        socket.onDisconnectWebServer.removeAt(1)
    }
}