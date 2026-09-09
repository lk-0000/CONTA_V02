package com.zces.conta.ui.projects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zces.conta.R

/**
 * Manual §2.1: code, naziv, lokacija, naročnik, datumi — BOQ/Gantt stay empty, filled in later
 * via XML import (roadmap phase 7) if the user ever chooses that path for this project.
 */
@Composable
fun AddProjectManualDialog(
    onDismiss: () -> Unit,
    onConfirm: (code: String, name: String, location: String, client: String) -> Unit,
) {
    var code by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var client by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.projects_add_manual)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = code, onValueChange = { code = it }, label = { Text("Koda") }, singleLine = true)
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Naziv") }, singleLine = true)
                OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Lokacija") }, singleLine = true)
                OutlinedTextField(value = client, onValueChange = { client = it }, label = { Text("Naročnik") }, singleLine = true)
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(code, name, location, client) },
                enabled = code.isNotBlank() && name.isNotBlank(),
            ) { Text(stringResource(R.string.action_save)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("PREKLIČI") }
        },
    )
}
