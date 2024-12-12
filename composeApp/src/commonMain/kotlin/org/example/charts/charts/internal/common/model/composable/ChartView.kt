package org.example.charts.charts.internal.common.model.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.example.charts.charts.style.ChartViewStyle

@Composable
internal fun ChartView(chartViewsStyle: ChartViewStyle, content: @Composable () -> Unit) {

        Box(
            modifier = chartViewsStyle.modifierMain
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
            ) {
                content()
            }
        }

}
