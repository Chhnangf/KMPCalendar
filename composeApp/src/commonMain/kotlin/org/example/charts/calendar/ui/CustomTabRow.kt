package org.example.charts.calendar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun CustomTabRow(
    tabSelected: Int,
    onTabSelected: (Int) -> Unit,
    itemsName: List<String>,
    content: @Composable (Int) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

        Row(
            modifier = Modifier.width(180.dp).background(Color.LightGray),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            itemsName.forEachIndexed { index, s ->

                val selectedTab = if (tabSelected == index) Color.White else Color.Transparent
                val selectBorder = if (tabSelected == index) Color.Gray else Color.Transparent
                val selectedText = if (tabSelected == index) Color.Black else Color.White

                Box(
                    modifier = Modifier.weight(1f).height(25.dp).padding(1.dp).clip(RoundedCornerShape(4.dp)).border(
                        1.dp,
                        selectBorder,
                        RoundedCornerShape(4.dp)
                    ).background(selectedTab, RoundedCornerShape(4.dp))
                        .clickable { onTabSelected(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = s, color = selectedText)
                }
            }
        }
        Column() {
            content(tabSelected)
        }
    }
}