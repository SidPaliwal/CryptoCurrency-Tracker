package com.example.cryptoverse.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CryptoTable")
data class CryptoEntity(@PrimaryKey(autoGenerate = true) val crypto_id:Int,
                        val name:String,
                        val imageurl:String,
                        val open:Double,
                        val high:Double,
                        val low:Double,
                        val close:Double
)