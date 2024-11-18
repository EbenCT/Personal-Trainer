package com.example.personaltrainer

import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding

class TableDynamic(private val tableLayout: TableLayout, private val context: Context) {

    private lateinit var header: Array<String>
    private lateinit var data: ArrayList<Array<String>>

    fun addHeader(header: Array<String>) {
        this.header = header
        createHeader()
    }

    fun addData(data: ArrayList<Array<String>>) {
        this.data = data
        createDataTable()
    }

    private fun createHeader() {
        val row = TableRow(context).apply {
            layoutParams = TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setBackgroundColor(ContextCompat.getColor(context, R.color.header_background))
        }

        for (headerText in header) {
            val cell = TextView(context).apply {
                text = headerText
                gravity = Gravity.CENTER
                setPadding(16)
                setTypeface(null, Typeface.BOLD)
                textSize = 16f
                setTextColor(ContextCompat.getColor(context, R.color.header_text))
                layoutParams = TableRow.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1f
                )
            }
            row.addView(cell)
        }
        tableLayout.addView(row)
    }
    private fun createDataTable() {
        for (rowArray in data) {
            val row = TableRow(context).apply {
                layoutParams = TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setBackgroundColor(ContextCompat.getColor(context, R.color.row_background))
            }

            for (cellText in rowArray) {
                val cell = TextView(context).apply {
                    text = cellText
                    gravity = Gravity.CENTER
                    setPadding(16)
                    textSize = 14f
                    setTextColor(ContextCompat.getColor(context, R.color.data_text))
                    layoutParams = TableRow.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                }
                row.addView(cell)
            }
            tableLayout.addView(row)
        }
    }

    fun backgroundHeader(color: Int) {
        val row = tableLayout.getChildAt(0) as TableRow
        for (i in 0 until row.childCount) {
            val cell = row.getChildAt(i) as TextView
            cell.setBackgroundColor(color)
        }
    }

    fun textColorHeader(color: Int) {
        val row = tableLayout.getChildAt(0) as TableRow
        for (i in 0 until row.childCount) {
            val cell = row.getChildAt(i) as TextView
            cell.setTextColor(color)
        }
    }
    // Alternate row coloring for better readability
    fun alternateRowColors() {
        for (i in 1 until tableLayout.childCount) {
            val row = tableLayout.getChildAt(i) as TableRow
            row.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    if (i % 2 == 0) R.color.row_background_alt1
                    else R.color.row_background_alt2
                )
            )
        }
    }
/*
    // Add divider between rows
    fun addRowDividers() {
        tableLayout.isStretchAllColumns = true
        tableLayout.setColumnShrinkable(0, true)
        for (i in 1 until tableLayout.childCount) {
            val row = tableLayout.getChildAt(i) as TableRow
            row.setBackgroundResource(R.drawable.row_divider)
        }
    }*/

    // Customize cell padding
    fun setCellPadding(padding: Int) {
        for (i in 0 until tableLayout.childCount) {
            val row = tableLayout.getChildAt(i) as TableRow
            for (j in 0 until row.childCount) {
                val cell = row.getChildAt(j) as TextView
                cell.setPadding(padding)
            }
        }
    }

}
