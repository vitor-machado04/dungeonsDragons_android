package com.example.dungeonsdragons

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.dungeonsdragons.Data.AppApplication
import com.example.dungeonsdragons.Data.CharacterEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewActivity : ComponentActivity() {
    private val users = mutableStateListOf<CharacterEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            val characters = (application as AppApplication).connectDb.characterDao().getAllCharacters()
            users.addAll(characters)
        }

        setContent {
            MaterialTheme {
                CharacterList(
                    users = users,
                    onDelete = { character ->
                        users.remove(character)
                        CoroutineScope(Dispatchers.IO).launch {
                            (application as AppApplication).connectDb.characterDao().delete(character)
                        }
                    },
                    context = this
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val characterId = data?.getIntExtra("characterId", -1)
            val name = data?.getStringExtra("name")
            val strength = data?.getIntExtra("strength", 8)
            val dexterity = data?.getIntExtra("dexterity", 8)
            val constitution = data?.getIntExtra("constitution", 8)
            val intelligence = data?.getIntExtra("intelligence", 8)
            val wisdom = data?.getIntExtra("wisdom", 8)
            val charisma = data?.getIntExtra("charisma", 8)

            if (characterId != null && characterId != -1) {
                val characterIndex = users.indexOfFirst { it.id == characterId }
                if (characterIndex != -1) {
                    val updatedCharacter = users[characterIndex].copy(
                        name = name ?: users[characterIndex].name,
                        strength = strength ?: users[characterIndex].strength,
                        dexterity = dexterity ?: users[characterIndex].dexterity,
                        constitution = constitution ?: users[characterIndex].constitution,
                        intelligence = intelligence ?: users[characterIndex].intelligence,
                        wisdom = wisdom ?: users[characterIndex].wisdom,
                        charisma = charisma ?: users[characterIndex].charisma
                    )
                    users[characterIndex] = updatedCharacter
                }
            }
        }
    }
}

@Composable
fun CharacterList(users: MutableList<CharacterEntity>, onDelete: (CharacterEntity) -> Unit, context: Context) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(users) { character ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${character.race} - ${character.charClass}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Atributos:",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Força: ${character.strength}")
                            Text("Destreza: ${character.dexterity}")
                            Text("Constituição: ${character.constitution}")
                        }
                        Column {
                            Text("Inteligência: ${character.intelligence}")
                            Text("Sabedoria: ${character.wisdom}")
                            Text("Carisma: ${character.charisma}")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            onDelete(character)
                            Toast.makeText(context, "Personagem excluído", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                        )
                    ) {
                        Text("Excluir", color = Color.White)
                    }

                    Button(
                        onClick = {
                            val intent = Intent(context, UpdateCharacterActivity::class.java)
                            intent.putExtra("characterId", character.id)
                            (context as NewActivity).startActivityForResult(intent, 1)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                        )
                    ) {
                        Text("Atualizar", color = Color.White)
                    }
                }
            }
        }
        item {
            Button(
                onClick = {
                    startActivity(context, Intent(context, MainActivity::class.java), null)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                )
            ) {
                Text("Novo Personagem", color = Color.White)
            }
        }
    }
}