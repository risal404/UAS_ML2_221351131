package com.example.breastcancerapp

import android.os.Bundle
import android.text.Html
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil

class PredictionActivity : AppCompatActivity() {

    private lateinit var interpreter: Interpreter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prediction)

        val backButton: ImageButton = findViewById(R.id.button_back)
        backButton.setOnClickListener {
            onBackPressed()
        }

        // Load model .tflite dari assets
        val model = FileUtil.loadMappedFile(this, "breast_cancer_diagnosis.tflite")
        interpreter = Interpreter(model)

        // Daftar input EditText (10 fitur)
        val inputs = listOf(
            findViewById<EditText>(R.id.etRadiusMean),
            findViewById(R.id.etPerimeterMean),
            findViewById(R.id.etAreaMean),
            findViewById(R.id.etConcavityMean),
            findViewById(R.id.etConcavePointsMean),
            findViewById(R.id.etRadiusWorst),
            findViewById(R.id.etPerimeterWorst),
            findViewById(R.id.etAreaWorst),
            findViewById(R.id.etConcavityWorst),
            findViewById(R.id.etConcavePointsWorst)
        )

        val btnPredict = findViewById<Button>(R.id.btnPredict)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val tvConfidence = findViewById<TextView>(R.id.tvConfidence)
        val progressBar = findViewById<ProgressBar>(R.id.progressConfidence)
        val boxResult = findViewById<LinearLayout>(R.id.boxResult) // Tambahkan ID box hasil

        btnPredict.setOnClickListener {
            try {
                val inputValues = inputs.map { it.text.toString().toFloat() }.toFloatArray()

                // Normalisasi manual berdasarkan mean dan std dari StandardScaler
                val mean = floatArrayOf(14.13f, 92.91f, 654.89f, 0.088f, 0.048f, 16.31f, 107.26f, 880.58f, 0.114f, 0.067f)
                val std =  floatArrayOf(3.52f, 24.29f, 351.91f, 0.08f, 0.03f, 4.83f, 33.60f, 569.36f, 0.14f, 0.05f)

                val standardizedInput = FloatArray(10)
                for (i in inputValues.indices) {
                    standardizedInput[i] = (inputValues[i] - mean[i]) / std[i]
                }

                val input = arrayOf(standardizedInput)
                val output = Array(1) { FloatArray(1) }

                interpreter.run(input, output)

                val result = output[0][0]
                val confidence = (result * 100).toInt()
                val isMalignant = result > 0.5f
                val diagnosis = if (isMalignant) "Ganas (Malignant)" else "Jinak (Benign)"

                // Set hasil prediksi
                tvResult.text = Html.fromHtml("<b>${diagnosis ?: "Tidak diketahui"}</b>", Html.FROM_HTML_MODE_LEGACY)
                tvConfidence.text = "Tingkat Keyakinan: $confidence%"
                progressBar.progress = confidence

                // Ubah warna background box
                val backgroundRes = if (isMalignant) R.drawable.bg_result_red else R.drawable.bg_result_green
                boxResult.setBackgroundResource(backgroundRes)

            } catch (e: Exception) {
                tvResult.text = Html.fromHtml("<b>WARNING! Input tidak valid. Pastikan semua kolom diisi angka.</b>", Html.FROM_HTML_MODE_LEGACY)
                tvConfidence.text = ""
                progressBar.progress = 0
                boxResult.setBackgroundResource(R.drawable.bg_result_orange)
            }
        }
    }
}
