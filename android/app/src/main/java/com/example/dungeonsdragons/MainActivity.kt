package com.example.dungeonsdragons

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnNextPage = findViewById<Button>(R.id.button2)
        btnNextPage.setOnClickListener {
            val nameInput = findViewById<EditText>(R.id.nameInput)
            val name = nameInput.text.toString()

            val raceGroup = findViewById<RadioGroup>(R.id.raceGroup)
            val classGroup = findViewById<RadioGroup>(R.id.classGroup)

            val selectedRaceId = raceGroup.checkedRadioButtonId
            val selectedClassId = classGroup.checkedRadioButtonId

            // Determinar raça selecionada
            val race = when (selectedRaceId) {
                R.id.radioDwarf -> "DWARF"
                R.id.radioElf -> "ELF"
                R.id.radioHuman -> "HUMAN"
                R.id.radioHalfling -> "HALFLING"
                else -> null
            }

            // Determinar classe selecionada
            val charClass = when (selectedClassId) {
                R.id.radioWarrior -> "WARRIOR"
                R.id.radioMage -> "MAGE"
                R.id.radioRogue -> "ROGUE"
                R.id.radioCleric -> "CLERIC"
                else -> null
            }

            if (name.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha o nome do personagem", Toast.LENGTH_SHORT).show()
            } else if (race == null) {
                Toast.makeText(this, "Por favor, selecione uma raça", Toast.LENGTH_SHORT).show()
            } else if (charClass == null) {
                Toast.makeText(this, "Por favor, selecione uma classe", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("name", name)
                intent.putExtra("race", race)
                intent.putExtra("charClass", charClass)
                startActivity(intent)
            }
        }
    }
}
