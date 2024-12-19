package com.example.koursework666

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ItemActivity : AppCompatActivity() {
    private var itemCount: Int = 0
    private lateinit var countTextView: TextView
    private lateinit var itemTitle: String
    private lateinit var itemDesc: String
    private var itemId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_item)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val title: TextView = findViewById(R.id.item_list_title_one)
        val text: TextView = findViewById(R.id.item_list_text)
        countTextView = findViewById(R.id.item_list_count_two)
        val buttonBuy: Button = findViewById(R.id.button_buy)

        // Получаем данные из Intent
        itemId = intent.getIntExtra("itemId", 0)
        itemTitle = intent.getStringExtra("itemTitle") ?: ""
        itemDesc = intent.getStringExtra("itemText") ?: ""
        itemCount = intent.getIntExtra("itemCount", 0)

        // Устанавливаем текст на экране
        title.text = itemTitle
        text.text = itemDesc
        countTextView.text = "Осталось: $itemCount"

        // Обработчик нажатия кнопки "Купить"
        buttonBuy.setOnClickListener { showPurchaseDialog() }
    }

    private fun showPurchaseDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_purchase, null)
        val editTextQuantity: EditText = dialogView.findViewById(R.id.edit_quantity)

        AlertDialog.Builder(this)
            .setTitle("Выберите количество")
            .setView(dialogView)
            .setPositiveButton("Купить") { _, _ ->
                val quantity = editTextQuantity.text.toString().toIntOrNull() ?: 0
                if (quantity in 1..itemCount) {
                    handlePurchase(quantity)
                } else {
                    showError("Неверное количество")
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun handlePurchase(quantity: Int) {
        itemCount -= quantity
        countTextView.text = "Осталось: $itemCount"

        if (itemCount == 0) {
            countTextView.text = "Нет в наличии"
        }

        // Обновление количества товара в базе данных
        val db = DbHelper(this, null)
        db.updateItemCount(itemId, itemCount)

        // Переход на страницу с купленными товарами
        val intent = Intent(this, PurchasedActivity::class.java)
        intent.putExtra("itemTitle", itemTitle)
        intent.putExtra("itemQuantity", quantity)
        startActivity(intent)
    }

    private fun showError(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Ошибка")
            .setMessage(message)
            .setPositiveButton("ОК", null)
            .show()
    }
}
