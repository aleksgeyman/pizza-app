package com.example.f102258.data

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, dbName, null, dbVersion) {
    companion object {
        private const val dbVersion = 1
        private const val dbName = "PizzasDatabase"
        private const val tablePizzas = "PizzasTable"
        private const val keyId = "id"
        private const val keyName = "name"
        private const val keyDescription = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE " + tablePizzas + "("
                + keyId + " INTEGER PRIMARY KEY," + keyName + " TEXT,"
                + keyDescription + " TEXT" + ")")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $tablePizzas")
        onCreate(db)
    }

    fun addPizza(pizza: Pizza): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(keyId, pizza.id)
        contentValues.put(keyName, pizza.name)
        contentValues.put(keyDescription, pizza.description)
        val success = db.insert(tablePizzas, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range", "Recycle")
    fun getPizzas(): List<Pizza> {
        val pizzas = ArrayList<Pizza>()
        val selectQuery = "SELECT  * FROM $tablePizzas"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var pizzaId: Int
        var pizzaName: String
        var pizzaDescription: String
        if (cursor.moveToFirst()) {
            do {
                pizzaId = cursor.getInt(cursor.getColumnIndex(keyId))
                pizzaName = cursor.getString(cursor.getColumnIndex(keyName))
                pizzaDescription = cursor.getString(cursor.getColumnIndex(keyDescription))
                val emp = Pizza(
                    id = pizzaId,
                    name = pizzaName,
                    description = pizzaDescription,
                    size = "",
                    price = 0.0
                )
                pizzas.add(emp)
            } while (cursor.moveToNext())
        }
        return pizzas
    }

    fun updatePizza(pizza: Pizza): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(keyId, pizza.id)
        contentValues.put(keyName, pizza.name)
        contentValues.put(keyDescription, pizza.description)
        val success = db.update(tablePizzas, contentValues, "id=" + pizza.id, null)
        db.close()
        return success
    }

    fun deletePizza(pizza: Pizza): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(keyId, pizza.id)
        val success = db.delete(tablePizzas, "id=" + pizza.id, null)
        db.close()
        return success
    }
}
