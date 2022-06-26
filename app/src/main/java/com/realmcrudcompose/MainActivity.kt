package com.realmcrudcompose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.realmcrudcompose.models.User
import com.realmcrudcompose.ui.theme.RealmCrudComposeTheme
import io.realm.Realm
import io.realm.kotlin.where
import java.util.*


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RealmCrudComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Formulario()
                }
            }

        }

    }
}

@Composable
fun Formulario() {
    val realm = Realm.getDefaultInstance()
    val mContext = LocalContext.current

    Column(modifier = Modifier.fillMaxWidth()) {
        var nome by remember { mutableStateOf(TextFieldValue("")) }

        TextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text(text = "Nome Completo") },
            placeholder = { Text(text = "Digite seu nome") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.White)
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    println(nome.text)
                    Salvar(nome.text)
                    mToast(mContext)
                },
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            ) {
                Text(text = "Salvar")
            }

            Button(
                onClick = { nome = TextFieldValue("") },
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            ) {
                Text(text = "Limpar")
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            NomesList(realm.where<User>().findAll().toList())
        }
    }

}

private fun mToast(context: Context) {
    Toast.makeText(context, "User salvo com sucesso", Toast.LENGTH_LONG).show()
}


fun Salvar(text: String) {
    val realm = Realm.getDefaultInstance()

    realm.executeTransactionAsync({
        //val user = realm.copyToRealm(User(text))
        val user = it.createObject(User::class.java, UUID.randomUUID().toString())
        user.nome = text
        // it.where<User>().findAll().deleteAllFromRealm()

    }, {
        realm.close()

    }, { error -> println(error.message) })

}

@Composable
fun NomesList(lista: List<User>) {
    println("lista size " + lista.size)
    LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
        items(lista.size) { index ->
            NomesRow(nome = lista[index].nome)
        }
    }

}

@Composable
fun NomesRow(nome: String) {
    Row(modifier = Modifier
        .border(border = BorderStroke(0.5.dp, Color.Gray))
        .padding(4.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        ) {
            Text(text = nome)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RealmCrudComposeTheme {
        Formulario()
    }
}

