package org.example.charts.charts.internal.common.model

internal data class MultiChartData(
    val items: List<ChartDataItem>,
    val categories: List<String> = emptyList(),
    val title: String
) {
    fun hasCategories(): Boolean {
        return categories.isNotEmpty()
    }
}