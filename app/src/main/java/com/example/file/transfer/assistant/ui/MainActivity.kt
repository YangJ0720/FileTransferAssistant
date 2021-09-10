package com.example.file.transfer.assistant.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import com.example.file.transfer.assistant.R
import com.yanzhenjie.andserver.AndServer
import com.yanzhenjie.andserver.Server
import java.lang.Exception
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var mServer: Server
    private lateinit var mTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        stop()
    }

    private fun initData() {
        this.mServer = AndServer.webServer(this).apply {
            // 设置端口号
            port(8080)
            // 设置超时时间
            timeout(30, TimeUnit.SECONDS)
            // 设置状态监听
            listener(object : Server.ServerListener {
                override fun onStarted() {
                    println("onStarted")
                    this@MainActivity.mTextView.text = "连接开启"
                    val inetAddress = this@MainActivity.mServer.inetAddress
                    val name = inetAddress.hostName
                    val address = inetAddress.hostAddress
                    println("name = $name, address = $address")
                }

                override fun onStopped() {
                    println("onStopped")
                    this@MainActivity.mTextView.text = "连接关闭"
                }

                override fun onException(e: Exception?) {
                    println("onException -> e = ${e?.message}")
                    this@MainActivity.mTextView.text = "连接错误: ${e?.message}"
                }

            })

        }.build()
    }

    private fun initView() {
        this.mTextView = findViewById(R.id.textView)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            if (TextUtils.equals(getString(R.string.start), button.text)) {
                start()
                button.setText(R.string.stop)
            } else {
                stop()
                button.setText(R.string.start)
            }
        }
    }

    private fun start() {
        this.mServer.startup()
    }

    private fun stop() {
        this.mServer.shutdown()
    }
}