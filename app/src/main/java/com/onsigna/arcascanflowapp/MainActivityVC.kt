package com.onsigna.arcascanflowapp

import android.text.Html
import android.widget.TextView

class MainActivityVC(var mainActivity: MainActivity) {



    private val GREEN_COLOR = "#008000"
    private val RED_COLOR = "#FF0000"
    private val WHITE_COLOR = "#000000"

    companion object {
        val CODE_ERROR = 1
        val CODE_SUCESSFUL = 2
        val CODE_NORMAL = 0
    }

    private var tvData : TextView = mainActivity.findViewById(R.id.tvData)



    fun writeConsole(code: Int, message: String?) {
        nextLine()
        mainActivity.runOnUiThread {
            when (code) {
                CODE_ERROR -> tvData.append(Html.fromHtml(getColoredSpanned(message!!, RED_COLOR)))
                CODE_SUCESSFUL -> tvData.append(
                    Html.fromHtml(
                        getColoredSpanned(
                            message!!,
                            GREEN_COLOR
                        )
                    )
                )
                CODE_NORMAL -> tvData.append(
                    Html.fromHtml(
                        getColoredSpanned(
                            message!!,
                            WHITE_COLOR
                        )
                    )
                )
                else -> {
                    tvData.append(Html.fromHtml(getColoredSpanned(message!!, WHITE_COLOR)))
                    throw IllegalStateException("Unexpected value: $code")
                }
            }
        }
    }

    private fun nextLine() {
        mainActivity.runOnUiThread { tvData.append("\n") }
    }

    private fun getColoredSpanned(text: String, color: String): String? {
        return "<font color=$color>$text</font>"
    }


}