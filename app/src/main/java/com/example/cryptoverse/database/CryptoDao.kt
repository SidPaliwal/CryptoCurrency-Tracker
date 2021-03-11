package com.example.cryptoverse.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

interface CryptoDao {

    @Insert
    fun insertCrypto(cryptoentity:CryptoEntity)

    @Delete
    fun deletecrypto(cryptoentity: CryptoEntity)

    @Query("SELECT * FROM CryptoTable")
    fun getAllcryptos():List<CryptoEntity>

    @Query("SELECT * FROM cryptotable where crypto_id=:cryptoid")
    fun getCryptosbyid(cryptoid:String):CryptoEntity
}