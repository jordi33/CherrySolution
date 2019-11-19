package com.sogeti.inno.cherry.activities.ui.child

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.activities.models.Child
import kotlinx.android.synthetic.main.layout_child.view.*


class ChildListAdapter(val childs : List<Child>) : RecyclerView.Adapter<ChildListAdapter.ChildViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        return ChildViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_child, parent, false)
        )

    }

    override fun getItemCount() = childs.size


    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {

        val child = childs[position]

        holder.view.child_name.text = child.name
        holder.view.child_surname.text = child.surname
        holder.view.child_age.text = child.age.toString()
        holder.view.child_activities.text = child.activities
        holder.view.child_interest.text = child.CenterOfInterest


    }


    class ChildViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}