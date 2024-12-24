package org.example.charts.charts.internal.common.model

internal data class MultiChartData(
    val items: List<ChartDataItem>,
    val categories: List<String> = emptyList(),
    val title: String
) {
    fun hasCategories(): Boolean {
        return categories.isNotEmpty()
    }
    fun hasSingleItem(): Boolean {
        return items.size == 1
    }
}

internal fun MultiChartData.minMax(): Pair<Double, Double> {
    val first = this.items.first()
    var min = first.item.points.min()
    var max = first.item.points.max()

    for (data in this.items) {
        val currentMin = data.item.points.minOrNull() ?: continue
        val currentMax = data.item.points.maxOrNull() ?: continue

        min = minOf(min, currentMin)
        max = maxOf(max, currentMax)
    }

    return min to max
}