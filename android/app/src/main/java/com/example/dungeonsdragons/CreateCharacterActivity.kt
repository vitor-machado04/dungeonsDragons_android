package com.example.dungeonsdragons

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.dungeonsdragons.Data.AppApplication
import com.example.dungeonsdragons.Data.CharacterEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.model.Abilities
import org.example.model.Ability
import org.example.model.CharClass
import org.example.model.Character
import org.example.model.Race

class CreateCharacterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_character)

        val name = intent.getStringExtra("name")
        val raceString = intent.getStringExtra("race")
        val classString = intent.getStringExtra("charClass")
        val strength = intent.getIntExtra("strength", 8)
        val dexterity = intent.getIntExtra("dexterity", 8)
        val constitution = intent.getIntExtra("constitution", 8)
        val intelligence = intent.getIntExtra("intelligence", 8)
        val wisdom = intent.getIntExtra("wisdom", 8)
        val charisma = intent.getIntExtra("charisma", 8)

        val race = Race.valueOf(raceString!!)
        val charClass = CharClass.valueOf(classString!!)

        val abilities = Abilities(
            strength = Ability(strength),
            dexterity = Ability(dexterity),
            constitution = Ability(constitution),
            intelligence = Ability(intelligence),
            wisdom = Ability(wisdom),
            charisma = Ability(charisma)
        )

        race.applyRacialBonuses(abilities)

        val character = Character(name!!, race, charClass, abilities)

        val characterEntity = CharacterEntity(
            name = character.name,
            race = character.race.description,
            charClass = character.charClass.description,
            strength = abilities.strength.score,
            dexterity = abilities.dexterity.score,
            constitution = abilities.constitution.score,
            intelligence = abilities.intelligence.score,
            wisdom = abilities.wisdom.score,
            charisma = abilities.charisma.score
        )

        CoroutineScope(Dispatchers.IO).launch {
            (application as AppApplication).connectDb.characterDao().insert(characterEntity)
        }

        findViewById<TextView>(R.id.characterName).text = character.name
        findViewById<TextView>(R.id.characterRace).text = character.race.description
        findViewById<TextView>(R.id.characterClass).text = character.charClass.description
        findViewById<TextView>(R.id.characterHp).text = "Vida: ${character.hitPoints}"

        findViewById<TextView>(R.id.strengthValue).text = abilities.strength.score.toString()
        findViewById<TextView>(R.id.strengthModifier).text =
            formatModifier(abilities.strength.modifier)

        findViewById<TextView>(R.id.dexterityValue).text = abilities.dexterity.score.toString()
        findViewById<TextView>(R.id.dexterityModifier).text =
            formatModifier(abilities.dexterity.modifier)

        findViewById<TextView>(R.id.constitutionValue).text =
            abilities.constitution.score.toString()
        findViewById<TextView>(R.id.constitutionModifier).text =
            formatModifier(abilities.constitution.modifier)

        findViewById<TextView>(R.id.intelligenceValue).text =
            abilities.intelligence.score.toString()
        findViewById<TextView>(R.id.intelligenceModifier).text =
            formatModifier(abilities.intelligence.modifier)

        findViewById<TextView>(R.id.wisdomValue).text = abilities.wisdom.score.toString()
        findViewById<TextView>(R.id.wisdomModifier).text = formatModifier(abilities.wisdom.modifier)

        findViewById<TextView>(R.id.charismaValue).text = abilities.charisma.score.toString()
        findViewById<TextView>(R.id.charismaModifier).text =
            formatModifier(abilities.charisma.modifier)


        val finalButton = findViewById<Button>(R.id.final_button)

        finalButton.setOnClickListener{
            startActivity(Intent(this, NewActivity::class.java))
        }
    }

    private fun formatModifier(modifier: Int): String {
        return if (modifier >= 0) "+$modifier" else modifier.toString()
    }
}
