package com.sogeti.inno.cherry.activities.ui.players

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sogeti.inno.cherry.R
import kotlinx.android.synthetic.main.activity_players_list.*



data class Players(val Name: String,
                   val Surname: String,
                   val Age: Int,
                   val Activities: String,
                   val CenterOfInterest: String)

class PlayersMainFragment : Fragment() {
    private val forDevUseList = listOf(
        Players(
            "name1",
            "surname1",
            1,
            "activities1",
            "CenterOfInterest1"
        ),
        Players(
            "name2",
            "surname2",
            2,
            "activities2",
            "CenterOfInterest2"
        ),
        Players(
            "name3",
            "surname3",
            3,
            "activities3",
            "CenterOfInterest3"
        ),
        Players(
            "name4",
            "surname4",
            4,
            "activities4",
            "CenterOfInterest4"
        ),
        Players(
            "name5",
            "surname5",
            5,
            "activities5",
            "CenterOfInterest5"
        ),
        Players(
            "name6",
            "surname6",
            6,
            "activities6",
            "CenterOfInterest6"
        ),
        Players(
            "name7",
            "surname7",
            7,
            "activities7",
            "CenterOfInterest7"
        ),
        Players(
            "name8",
            "surname8",
            8,
            "activities8",
            "CenterOfInterest8"
        ),
        Players(
            "name9",
            "surname9",
            9,
            "activities9",
            "CenterOfInterest9"
        ),
        Players(
            "name10",
            "surname10",
            10,
            "activities10",
            "CenterOfInterest10"
        ),
        Players(
            "name11",
            "surname11",
            11,
            "activities11",
            "CenterOfInterest11"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        inflater.inflate(R.layout.playersrecyclerview_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playersRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = PlayersListAdapter(forDevUseList)
        }

    }

    companion object {
        fun newInstance(): PlayersMainFragment =
            PlayersMainFragment()
    }



}