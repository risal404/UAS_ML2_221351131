package com.example.breastcancerapp

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DatasetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dataset)

        val textView = findViewById<TextView>(R.id.kaggle_link)
        textView.text = Html.fromHtml(
            "<b>Link Dataset:</b> <a href='https://www.kaggle.com/datasets/uciml/breast-cancer-wisconsin-data'>Kaggle Breast Cancer Dataset</a>",
            Html.FROM_HTML_MODE_LEGACY
        )
        textView.movementMethod = LinkMovementMethod.getInstance()
    }
}
