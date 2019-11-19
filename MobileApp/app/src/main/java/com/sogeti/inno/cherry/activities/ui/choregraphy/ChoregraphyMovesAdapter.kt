package com.sogeti.inno.cherry.activities.ui.choregraphy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.activities.models.ChoregraphyMove
import kotlinx.android.synthetic.main.choregraphy_grid_item.view.*

class ChoregraphyMovesAdapter(var context: Context, var movesList: ArrayList<ChoregraphyMove>) : BaseAdapter(){

    override fun getCount(): Int {
        return movesList.size
    }

    override fun getItem(position: Int): Any {
        return movesList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val move = this.movesList[position]

        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val moveView = inflator.inflate(R.layout.choregraphy_grid_item, null)
        moveView.choregraphy_grid_item_image.setImageResource(move.imageId)
        moveView.choregraphy_grid_item_description.text = move.description
        moveView.setOnClickListener {
            movesList.remove(move)
            this.notifyDataSetChanged()
        }

        return moveView
    }
}