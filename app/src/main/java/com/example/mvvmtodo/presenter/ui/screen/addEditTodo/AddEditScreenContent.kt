package com.example.mvvmtodo.presenter.ui.screen.addEditTodo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddEditScreenContent(
    paddingValues: PaddingValues,
    title: String,
    description: String,
    onTitleChange: (String)-> Unit,
    onDescriptionChange: (String)-> Unit,
    isComplete: Boolean,
    onCompletedChange: ()-> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(paddingValues)
            .wrapContentSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            EditTitleContent(title, onTitleChange)

            Spacer(modifier = Modifier.height(10.dp))

            EditDescriptionContent(description, onDescriptionChange)

            Spacer(modifier = Modifier.height(20.dp))

            EditStatusContent(isComplete, onCompletedChange)
        }
    }
}