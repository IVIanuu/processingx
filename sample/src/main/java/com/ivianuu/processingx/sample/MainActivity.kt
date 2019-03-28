package com.ivianuu.processingx.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val property by lazy { "lol" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

class MyCustomProperty<T>(val init: () -> T) : ReadWriteProperty<T> {

}

@MyAnnotation
class MyClass {


}