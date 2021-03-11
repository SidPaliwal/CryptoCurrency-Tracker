package com.example.cryptoverse.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [CryptoEntity::class],version = 1)
abstract class CryptoDatabase:RoomDatabase() {

    abstract fun cryptoDao():CryptoDao

}