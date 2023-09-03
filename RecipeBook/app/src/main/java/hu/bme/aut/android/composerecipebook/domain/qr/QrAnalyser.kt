package hu.bme.aut.android.composerecipebook.domain.qr

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
class QrAnalyser(
    val onSuccess: (String) -> Unit
) : ImageAnalysis.Analyzer {
    override fun  analyze(imageProxy: ImageProxy) {
        val scannerOptions = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()

        val scanner = BarcodeScanning.getClient(scannerOptions)

        val image = imageProxy.image

        if(image != null) {
            val inputImage = InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)

            scanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.size > 0) {
                        barcodes[0].displayValue?.let { onSuccess(it) }
                    }
                }
                .addOnFailureListener {
                    it.printStackTrace()
                }
                .addOnCompleteListener {
                    image.close()
                    imageProxy.close()
                }
        }
    }
}