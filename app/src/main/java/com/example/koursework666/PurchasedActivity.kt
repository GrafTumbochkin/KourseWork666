package com.example.koursework666

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PurchasedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchased)

        val purchasedItemTitle = intent.getStringExtra("itemTitle")
        val purchasedQuantity = intent.getIntExtra("itemQuantity", 0)

        val purchasedTextView: TextView = findViewById(R.id.purchased_text)
        purchasedTextView.text = "Вы купили $purchasedQuantity шт. товара: \"$purchasedItemTitle\""
    }
}