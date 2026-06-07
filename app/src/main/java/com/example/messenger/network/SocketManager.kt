package com.example.messenger.network

import android.content.Context
import android.os.Build
import android.util.Log
import com.example.messenger.R
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocket
import javax.net.ssl.TrustManagerFactory

object SocketManager {
    private var socket: Socket? = null
    private var writer: PrintWriter? = null
    private var reader: BufferedReader? = null

    /**
     * Uses TLS with the bundled dev server certificate (matches server/certs/cert.pem).
     * Endpoint identification is disabled so connections by LAN IP work with CN=test.
     */
    fun connect(context: Context, host: String, port: Int) {
        disconnect()
        try {
            val sslContext = sslContextForMessengerServer(context)
            val factory = sslContext.socketFactory
            val s = factory.createSocket(host, port)
            if (s is SSLSocket && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val params = s.sslParameters
                params.endpointIdentificationAlgorithm = null
                s.sslParameters = params
            }
            socket = s
            writer = PrintWriter(socket!!.getOutputStream(), true)
            reader = BufferedReader(InputStreamReader(socket!!.getInputStream()))
            Log.d("SocketManager", "Connected to $host:$port")
        } catch (e: Exception) {
            Log.e("SocketManager", "Error connecting: $e")
        }
    }

    private fun sslContextForMessengerServer(context: Context): SSLContext {
        val cf = CertificateFactory.getInstance("X.509")
        val cert = context.resources.openRawResource(R.raw.messenger_server_cert).use { stream ->
            cf.generateCertificate(stream) as X509Certificate
        }
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
            load(null, null)
            setCertificateEntry("messenger_dev", cert)
        }
        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
            init(keyStore)
        }
        return SSLContext.getInstance("TLS").apply {
            init(null, tmf.trustManagers, null)
        }
    }

    fun sendLine(line: String) {
        try {
            writer?.println(line)
            writer?.flush()
            Log.d("SocketManager", "Sent: $line")
        } catch (e: Exception) {
            Log.e("SocketManager", "Error sending: $e")
        }
    }

    fun readLine(): String? {
        return try {
            reader?.readLine()
        } catch (e: Exception) {
            Log.e("SocketManager", "Error reading: $e")
            null
        }
    }

    fun disconnect() {
        try {
            writer?.close()
            reader?.close()
            socket?.close()
            socket = null
            writer = null
            reader = null
            Log.d("SocketManager", "Disconnected")
        } catch (e: Exception) {
            Log.e("SocketManager", "Error disconnecting: $e")
        }
    }

    fun isConnected(): Boolean {
        return socket?.isConnected == true && socket?.isClosed == false
    }

    fun getSocket(): Socket? {
        return socket
    }
}
