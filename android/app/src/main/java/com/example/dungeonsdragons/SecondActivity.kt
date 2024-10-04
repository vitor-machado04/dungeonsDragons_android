package com.example.dungeonsdragons

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class SecondActivity : ComponentActivity() {

    private var remainingPoints = 27

    private var strength = 8
    private var dexterity = 8
    private var constitution = 8
    private var intelligence = 8
    private var wisdom = 8
    private var charisma = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_main)

        val name = intent.getStringExtra("name")
        val characterName = findViewById<TextView>(R.id.characterName)
        characterName.text = name

        val txtRemainingPoints = findViewById<TextView>(R.id.remainingPoints)

        val txtStrength = findViewById<TextView>(R.id.txtStrength)
        val txtDexterity = findViewById<TextView>(R.id.txtDexterity)
        val txtConstitution = findViewById<TextView>(R.id.txtConstitution)
        val txtIntelligence = findViewById<TextView>(R.id.txtIntelligence)
        val txtWisdom = findViewById<TextView>(R.id.txtWisdom)
        val txtCharisma = findViewById<TextView>(R.id.txtCharisma)

        val btnMinusStrength = findViewById<Button>(R.id.btnMinusStrength)
        val btnPlusStrength = findViewById<Button>(R.id.btnPlusStrength)
        val btnMinusDexterity = findViewById<Button>(R.id.btnMinusDexterity)
        val btnPlusDexterity = findViewById<Button>(R.id.btnPlusDexterity)
        val btnMinusConstitution = findViewById<Button>(R.id.btnMinusConstitution)
        val btnPlusConstitution = findViewById<Button>(R.id.btnPlusConstitution)
        val btnMinusIntelligence = findViewById<Button>(R.id.btnMinusIntelligence)
        val btnPlusIntelligence = findViewById<Button>(R.id.btnPlusIntelligence)
        val btnMinusWisdom = findViewById<Button>(R.id.btnMinusWisdom)
        val btnPlusWisdom = findViewById<Button>(R.id.btnPlusWisdom)
        val btnMinusCharisma = findViewById<Button>(R.id.btnMinusCharisma)
        val btnPlusCharisma = findViewById<Button>(R.id.btnPlusCharisma)

        // Atualizar os pontos restantes no TextView no início
        updateRemainingPoints(txtRemainingPoints)

        // Configurando os botões de "+" e "-" para os atributos
        btnMinusStrength.setOnClickListener {
            if (strength > 8) {
                strength -= 1
                remainingPoints += 1
                txtStrength.text = strength.toString()
                updateRemainingPoints(txtRemainingPoints)
            }
        }

        btnPlusStrength.setOnClickListener {
            if (strength < 15 && remainingPoints > 0) {
                strength += 1
                remainingPoints -= 1
                txtStrength.text = strength.toString()
                updateRemainingPoints(txtRemainingPoints)
            } else {
                showToast("Pontos insuficientes!")
            }
        }

        btnMinusDexterity.setOnClickListener {
            if (dexterity > 8) {
                dexterity -= 1
                remainingPoints += 1
                txtDexterity.text = dexterity.toString()
                updateRemainingPoints(txtRemainingPoints)
            }
        }

        btnPlusDexterity.setOnClickListener {
            if (dexterity < 15 && remainingPoints > 0) {
                dexterity += 1
                remainingPoints -= 1
                txtDexterity.text = dexterity.toString()
                updateRemainingPoints(txtRemainingPoints)
            } else {
                showToast("Pontos insuficientes!")
            }
        }

        btnMinusConstitution.setOnClickListener {
            if (constitution > 8) {
                constitution -= 1
                remainingPoints += 1
                txtConstitution.text = constitution.toString()
                updateRemainingPoints(txtRemainingPoints)
            }
        }

        btnPlusConstitution.setOnClickListener {
            if (constitution < 15 && remainingPoints > 0) {
                constitution += 1
                remainingPoints -= 1
                txtConstitution.text = constitution.toString()
                updateRemainingPoints(txtRemainingPoints)
            } else {
                showToast("Pontos insuficientes!")
            }
        }

        btnMinusIntelligence.setOnClickListener {
            if (intelligence > 8) {
                intelligence -= 1
                remainingPoints += 1
                txtIntelligence.text = intelligence.toString()
                updateRemainingPoints(txtRemainingPoints)
            }
        }

        btnPlusIntelligence.setOnClickListener {
            if (intelligence < 15 && remainingPoints > 0) {
                intelligence += 1
                remainingPoints -= 1
                txtIntelligence.text = intelligence.toString()
                updateRemainingPoints(txtRemainingPoints)
            } else {
                showToast("Pontos insuficientes!")
            }
        }

        btnMinusWisdom.setOnClickListener {
            if (wisdom > 8) {
                wisdom -= 1
                remainingPoints += 1
                txtWisdom.text = wisdom.toString()
                updateRemainingPoints(txtRemainingPoints)
            }
        }

        btnPlusWisdom.setOnClickListener {
            if (wisdom < 15 && remainingPoints > 0) {
                wisdom += 1
                remainingPoints -= 1
                txtWisdom.text = wisdom.toString()
                updateRemainingPoints(txtRemainingPoints)
            } else {
                showToast("Pontos insuficientes!")
            }
        }

        btnMinusCharisma.setOnClickListener {
            if (charisma > 8) {
                charisma -= 1
                remainingPoints += 1
                txtCharisma.text = charisma.toString()
                updateRemainingPoints(txtRemainingPoints)
            }
        }

        btnPlusCharisma.setOnClickListener {
            if (charisma < 15 && remainingPoints > 0) {
                charisma += 1
                remainingPoints -= 1
                txtCharisma.text = charisma.toString()
                updateRemainingPoints(txtRemainingPoints)
            } else {
                showToast("Pontos insuficientes!")
            }
        }

        val btnCreateCharacter = findViewById<Button>(R.id.createMyCharacter)
        btnCreateCharacter.setOnClickListener {
            if (remainingPoints == 0) {
                createCharacter()
            } else {
                showToast("Distribua todos os pontos antes de continuar!")
            }
        }
    }

    // Função utilitária para exibir mensagens Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Função para criar o personagem e enviar os dados para a próxima activity
    private fun createCharacter() {
        val name = intent.getStringExtra("name")
        val raceString = intent.getStringExtra("race")
        val classString = intent.getStringExtra("charClass")

        // Enviar os dados para a CreateCharacterActivity
        val intent = Intent(this, CreateCharacterActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("race", raceString)
        intent.putExtra("charClass", classString)
        intent.putExtra("strength", strength)
        intent.putExtra("dexterity", dexterity)
        intent.putExtra("constitution", constitution)
        intent.putExtra("intelligence", intelligence)
        intent.putExtra("wisdom", wisdom)
        intent.putExtra("charisma", charisma)
        startActivity(intent)
    }

    // Função para atualizar os pontos restantes
    private fun updateRemainingPoints(txtRemainingPoints: TextView) {
        txtRemainingPoints.text = "Pontos Restantes: $remainingPoints"
    }
}
