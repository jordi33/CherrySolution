package com.sogeti.inno.cherry.activities.ui.players

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.sogeti.inno.cherry.R


class PlayersListAdapter(private val list: List<Players>) : RecyclerView.Adapter<PlayersListAdapter.PlayersViewHolder>() {

    // override fun onBindViewHolder(p0: PlayersListAdapter.PlayersViewHolder, p1: Int) {
    //     TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    // }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlayersViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: PlayersViewHolder, position: Int) {
        val player: Players = list[position]
        holder.bind(player)

    }

    override fun getItemCount(): Int = list.size


    class PlayersViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.activity_players_list, parent, false)), Parcelable {
        private var mNameView: TextView? = null
        private var nSurnameView: TextView? = null
        private var nAgeView: TextView? = null
        private var nActivitiesView: TextView? = null
        private var nCenterOfInterestView: TextView? = null

        constructor(parcel: Parcel) : this(
            TODO("inflater"),
            TODO("parent")
        )

        init {
            mNameView = itemView.findViewById(R.id.childName)
            nSurnameView = itemView.findViewById(R.id.childSurname)
            nAgeView = itemView.findViewById(R.id.childAge)
            nActivitiesView = itemView.findViewById(R.id.childActivities)
            nCenterOfInterestView = itemView.findViewById(R.id.childCenterOfInterest)

        }

        fun bind(player: Players) {
            mNameView?.text = player.Name
            nSurnameView?.text = player.Surname
            nAgeView?.text = player.Age.toString()
            nActivitiesView?.text = player.Activities
            nCenterOfInterestView?.text = player.CenterOfInterest

        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {

        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<PlayersViewHolder> {
            override fun createFromParcel(parcel: Parcel): PlayersViewHolder {
                return PlayersViewHolder(parcel)
            }

            override fun newArray(size: Int): Array<PlayersViewHolder?> {
                return arrayOfNulls(size)
            }
        }
    }
}



