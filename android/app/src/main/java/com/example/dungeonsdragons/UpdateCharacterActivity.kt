package com.example.dungeonsdragons

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.dungeonsdragons.Data.AppApplication
import com.example.dungeonsdragons.Data.CharacterEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateCharacterActivity : ComponentActivity() {
    private lateinit var character: CharacterEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_character)

        val characterId = intent.getIntExtra("characterId", -1)
        if (characterId == -1) {
            Toast.makeText(this, "Personagem não encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            character = (application as AppApplication).connectDb.characterDao().getCharacterById(characterId) ?: return@launch

            runOnUiThread {
                findViewById<EditText>(R.id.editName).setText(character.name)
                findViewById<EditText>(R.id.editStrength).setText(character.strength.toString())
                findViewById<EditText>(R.id.editDexterity).setText(character.dexterity.toString())
                findViewById<EditText>(R.id.editConstitution).setText(character.constitution.toString())
                findViewById<EditText>(R.id.editIntelligence).setText(character.intelligence.toString())
                findViewById<EditText>(R.id.editWisdom).setText(character.wisdom.toString())
                findViewById<EditText>(R.id.editCharisma).setText(character.charisma.toString())
            }
        }

        findViewById<Button>(R.id.buttonUpdate).setOnClickListener {
            updateCharacter()
        }
    }

    private fun updateCharacter() {
        val name = findViewById<EditText>(R.id.editName).text.toString()
        val strength = findViewById<EditText>(R.id.editStrength).text.toString().toIntOrNull() ?: 8
        val dexterity = findViewById<EditText>(R.id.editDexterity).text.toString().toIntOrNull() ?: 8
        val constitution = findViewById<EditText>(R.id.editConstitution).text.toString().toIntOrNull() ?: 8
        val intelligence = findViewById<EditText>(R.id.editIntelligence).text.toString().toIntOrNull() ?: 8
        val wisdom = findViewById<EditText>(R.id.editWisdom).text.toString().toIntOrNull() ?: 8
        val charisma = findViewById<EditText>(R.id.editCharisma).text.toString().toIntOrNull() ?: 8

        val updatedCharacter = character.copy(
            name = name,
            strength = strength,
            dexterity = dexterity,
            constitution = constitution,
            intelligence = intelligence,
            wisdom = wisdom,
            charisma = charisma
        )

        CoroutineScope(Dispatchers.IO).launch {
            (application as AppApplication).connectDb.characterDao().update(updatedCharacter)

            runOnUiThread {
                val resultIntent = Intent().apply {
                    putExtra("characterId", updatedCharacter.id)
                    putExtra("name", updatedCharacter.name)
                    putExtra("strength", updatedCharacter.strength)
                    putExtra("dexterity", updatedCharacter.dexterity)
                    putExtra("constitution", updatedCharacter.constitution)
                    putExtra("intelligence", updatedCharacter.intelligence)
                    putExtra("wisdom", updatedCharacter.wisdom)
                    putExtra("charisma", updatedCharacter.charisma)
                }
                setResult(RESULT_OK, resultIntent)
                Toast.makeText(this@UpdateCharacterActivity, "Personagem atualizado", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
