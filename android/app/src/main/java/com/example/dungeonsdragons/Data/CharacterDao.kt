package com.example.dungeonsdragons.Data
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface CharacterDao {
    @Insert
    fun insert(character: CharacterEntity): Long

    @Update
    fun update(character: CharacterEntity)

    @Delete
    fun delete(character: CharacterEntity)

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): MutableList<CharacterEntity>

    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacterById(id: Int): CharacterEntity?
}