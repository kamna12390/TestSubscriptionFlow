package com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import java.lang.Exception

object BlurBuilder {

    private const val BITMAP_SCALE = 1f
    private const val BLUR_RADIUS = 25f

    fun blur(context: Context?, image: Bitmap): Bitmap? {
        try {
            val width = Math.round(image.width * BITMAP_SCALE)
            val height = Math.round(image.height * BITMAP_SCALE)

            val inputBitmap = Bitmap.createScaledBitmap(image, width, height, false)
            val outputBitmap = Bitmap.createBitmap(inputBitmap)
            val rs = RenderScript.create(context)
            var theIntrinsic: ScriptIntrinsicBlur? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
            }
            val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
            val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                theIntrinsic!!.setRadius(BLUR_RADIUS)
                theIntrinsic.setInput(tmpIn)
                theIntrinsic.forEach(tmpOut)
            }
            tmpOut.copyTo(outputBitmap)
            val canvas = Canvas(outputBitmap)
            canvas.drawColor(Color.parseColor("#48000000"))
            return outputBitmap
        }catch (ex:OutOfMemoryError){
            ex.printStackTrace()
        }catch (ex:Exception){
            ex.printStackTrace()
        }
        return null
    }
}