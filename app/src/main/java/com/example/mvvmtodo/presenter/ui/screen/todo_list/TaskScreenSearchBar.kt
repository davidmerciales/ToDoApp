package com.example.mvvmtodo.presenter.ui.screen.todo_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreenSearchBar(
    modifier: Modifier,
    searchText: String,
    onQueryChange: ()-> Unit,
    onSearch: ()-> Unit,
    list: List<String> = emptyList()
) {
    SearchBar(
        query = searchText,
        onQueryChange = { onQueryChange() },
        onSearch = { onSearch() },
        active = false,
        onActiveChange = { },
        shape = RoundedCornerShape(20.dp),
        colors = SearchBarDefaults.colors(
            containerColor = Color.LightGray
        ),
        placeholder = { Text(text = "Search task") },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable { },
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        modifier = modifier
    ) {
        //List
    }
}