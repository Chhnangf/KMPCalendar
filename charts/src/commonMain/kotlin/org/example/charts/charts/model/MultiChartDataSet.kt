package org.example.charts.charts.model

import org.example.charts.charts.internal.common.model.ChartDataItem
import org.example.charts.charts.internal.common.model.MultiChartData
import org.example.charts.charts.internal.common.model.toChartData

/**
 * A class that represents a data set for a chart.
 *
 * @property data The data item that represents the data set.
 * @constructor Creates a new ChartDataSet with the provided items, title, prefix, and postfix.
 *
 * @param items The list of data items.
 * @param title The title of the data set.
 * @param prefix The prefix to be added to each data item. Defaults to an empty string.
 * @param postfix The postfix to be added to each data item. Defaults to an empty string.
 */

class MultiChartDataSet(
    items: List<Pair<String, List<Float>>>,
    categories: List<String> = emptyList(),
    title: String,
    prefix: String = "",
    postfix: String = ""
) {
    internal val data: MultiChartData

    init {
        val dataItems = items.map {
            ChartDataItem(
                label = it.first,
                item = it.second.toChartData(prefix = prefix, postfix = postfix)
            )
        }

        data = MultiChartData(
            items = dataItems,
            categories = categories,
            title = title
        )
    }
}
