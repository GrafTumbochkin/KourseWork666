package com.example.koursework666

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "company", factory, 7) {

    override fun onCreate(db: SQLiteDatabase?) {
        var query = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, login TEXT, email TEXT, pass TEXT, role TEXT)"
        db!!.execSQL(query)

        query = "CREATE TABLE items (id INTEGER PRIMARY KEY AUTOINCREMENT, image TEXT, title TEXT, desc TEXT, text TEXT, count INTEGER, price INTEGER)"
        db.execSQL(query)

        // Добавление администратора при первом запуске
        addAdminUser(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS items")
        onCreate(db)
    }

    fun addUser(user: User) {
        val values = ContentValues()
        values.put("login", user.login)
        values.put("email", user.email)
        values.put("pass", user.pass)
        values.put("role", user.role)

        val db = this.writableDatabase
        db.insert("users", null, values)
        db.close()
    }

    fun getUser(login: String, pass: String): Pair<Boolean, String?> {
        val db = this.readableDatabase
        val result = db.rawQuery("SELECT * FROM users WHERE login = '$login' AND pass = '$pass'", null)
        return if (result.moveToFirst()) {
            val roleIndex = result.getColumnIndex("role")
            if (roleIndex >= 0) {
                Pair(true, result.getString(roleIndex))
            } else {
                Pair(false, null)
            }
        } else {
            Pair(false, null)
        }
    }

    fun addItem(item: Item) {
        val values = ContentValues()
        values.put("image", item.image)
        values.put("title", item.title)
        values.put("desc", item.desc)
        values.put("text", item.text)
        values.put("count", item.count)
        values.put("price", item.price)

        val db = this.writableDatabase
        db.insert("items", null, values)
        db.close()
    }

    fun updateItem(item: Item) {
        val values = ContentValues()
        values.put("image", item.image)
        values.put("title", item.title)
        values.put("desc", item.desc)
        values.put("text", item.text)
        values.put("count", item.count)
        values.put("price", item.price)

        val db = this.writableDatabase
        db.update("items", values, "id = ?", arrayOf(item.id.toString()))
        db.close()
    }

    fun deleteItem(itemId: Int) {
        val db = this.writableDatabase
        db.delete("items", "id = ?", arrayOf(itemId.toString()))
        db.close()
    }

    fun getAllItems(): List<Item> {
        val items = mutableListOf<Item>()
        val db = this.readableDatabase
        val result = db.rawQuery("SELECT * FROM items", null)

        if (result.moveToFirst()) {
            do {
                val idIndex = result.getColumnIndex("id")
                val imageIndex = result.getColumnIndex("image")
                val titleIndex = result.getColumnIndex("title")
                val descIndex = result.getColumnIndex("desc")
                val textIndex = result.getColumnIndex("text")
                val countIndex = result.getColumnIndex("count")
                val priceIndex = result.getColumnIndex("price")

                if (idIndex >= 0 && imageIndex >= 0 && titleIndex >= 0 && descIndex >= 0 && textIndex >= 0 && countIndex >= 0 && priceIndex >= 0) {
                    val item = Item(
                        id = result.getInt(idIndex),
                        image = result.getString(imageIndex),
                        title = result.getString(titleIndex),
                        desc = result.getString(descIndex),
                        text = result.getString(textIndex),
                        count = result.getInt(countIndex),
                        price = result.getInt(priceIndex)
                    )
                    items.add(item)
                }
            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return items
    }

    private fun addAdminUser(db: SQLiteDatabase) {
        val adminUser = User(login = "admin", email = "admin@example.com", pass = "adminpass", role = "admin")
        val values = ContentValues()
        values.put("login", adminUser.login)
        values.put("email", adminUser.email)
        values.put("pass", adminUser.pass)
        values.put("role", adminUser.role)
        db.insert("users", null, values)
    }

    fun updateItemCount(itemId: Int, newCount: Int) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("count", newCount)
        db.update("items", values, "id = ?", arrayOf(itemId.toString()))
        db.close()
    }
}
