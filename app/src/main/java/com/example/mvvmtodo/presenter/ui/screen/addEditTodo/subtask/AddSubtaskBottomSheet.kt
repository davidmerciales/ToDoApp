package com.example.mvvmtodo.presenter.ui.screen.addEditTodo.subtask

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.mvvmtodo.presenter.theme.Purple40
import com.example.mvvmtodo.presenter.ui.screen.addEditTodo.BottomSaveButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSubtaskBottomSheet(
    subtaskDescription: String,
    isEditSubtask: Boolean,
    onDismissRequest: ()-> Unit,
    onKeyboardActionDone: ()-> Unit,
    onSubtaskDescriptionChange: (String)-> Unit,
    onSaveSubtask: ()-> Unit,
    onDeleteSubtask: ()-> Unit
){
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f),
        onDismissRequest = {
            onDismissRequest()
        },
        sheetState = sheetState
    ) {
        Column {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                value = subtaskDescription,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onKeyboardActionDone()
                    }
                ),
                placeholder = { Text(text = "Enter sub task") },
                onValueChange = {
                    onSubtaskDescriptionChange(it)
                })

            Spacer(modifier = Modifier.height(20.dp))


            Row {
                BottomSaveButton(
                    title = if (isEditSubtask) "Save" else "Add",
                    Modifier
                        .fillMaxWidth(if (isEditSubtask) 0.85f else 1f)
                        .fillMaxHeight(0.4f)
                        .padding(start = 10.dp, end = 5.dp)
                        .background(Purple40, RoundedCornerShape(10.dp))
                ) {
                    onSaveSubtask()
                }
                if (isEditSubtask) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.4f)
                            .padding(end = 10.dp, start = 5.dp)
                            .clickable {
                                onDeleteSubtask()
                            },
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "delete",
                            tint = Color.Red
                        )

                    }
                }
            }
        }
    }
}