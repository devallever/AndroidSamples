package com.allever.android.sample.fingerprint

import android.content.Context
import android.os.Handler
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.core.os.CancellationSignal

object FingerPrintHelper {
    private lateinit var mFingerprintManagerCompat: FingerprintManagerCompat
    val cancellationSignal = CancellationSignal()
    fun init(context: Context): FingerPrintHelper {
        //暂处理兼容性问题， 默认在android m ~ o 中操作
        mFingerprintManagerCompat = FingerprintManagerCompat.from(context)
        return this
    }

    fun release() {
        cancellationSignal.cancel()
    }

    fun hardwareSupport(): Boolean = mFingerprintManagerCompat.isHardwareDetected

    fun hasFingerPrints(): Boolean = mFingerprintManagerCompat.hasEnrolledFingerprints()

    fun authenticate(callback: AuthenticateCallback) {
        mFingerprintManagerCompat.authenticate(
            null,
            0,
            cancellationSignal,
            object : FingerprintManagerCompat.AuthenticationCallback() {
                override fun onAuthenticationError(errMsgId: Int, errString: CharSequence) {
                    callback.onAuthenticationError()
                }

                override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence) {

                }

                override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult) {
                    callback.onAuthenticationSucceeded()
                }

                override fun onAuthenticationFailed() {
                    callback.onAuthenticationFailed()
                }
            },
            Handler()
        )
    }

    public interface AuthenticateCallback {
        fun onAuthenticationError()
        fun onAuthenticationSucceeded()
        fun onAuthenticationFailed()
    }

}