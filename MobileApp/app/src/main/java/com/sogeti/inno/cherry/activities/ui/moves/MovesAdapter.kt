package com.sogeti.inno.cherry.activities.ui.moves

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.annotation.DrawableRes
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.activities.models.ChoregraphyMove
import com.sogeti.inno.cherry.comm.CommandBuilder
import com.sogeti.inno.cherry.comm.MoveCommand
import com.sogeti.inno.cherry.core.ControlServer
import kotlinx.android.synthetic.main.choregraphy_grid_item.view.*
import kotlinx.android.synthetic.main.moves_gv_item.view.*

class MovesAdapter(var context: Context,var movesList: ArrayList<ChoregraphyMove>): BaseAdapter() {
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
        val moveView = inflator.inflate(R.layout.moves_gv_item, null)
        moveView.moves_gv_btn.setOnClickListener{
            ControlServer.sendCommand(CommandBuilder.toJson((MoveCommand(move.name))))
        }
        val img : Drawable? = context.getDrawable(move.imageId)
        moveView.moves_gv_btn.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null)
        moveView.moves_gv_btn.text = move.description

        return moveView
    }
}