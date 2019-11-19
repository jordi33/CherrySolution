package com.sogeti.inno.cherry.activities.ui.child

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.activities.models.Child
import kotlinx.android.synthetic.main.activity_child_list_main.*


class ChildListActivityMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_list_main)


    // comment to use dummy data
 /**       // refresh data from API on
        child_refresh_layout.setOnRefreshListener {
            fetchChild();
        }

        // refresh data on application load
        fetchChild()
**/
////////////////////////////////////////////////////////////////////////


        // Dummy data to delete when connected to API from here

        val childs = listOf(
            Child(
                id = 1,
                name = "name1",
                surname = "surname1",
                age = 1,
                activities = "activities1",
                CenterOfInterest = "interest1"
            ),
            Child(
                id = 1,
                name = "name11",
                surname = "surname11",
                age = 11,
                activities = "activities11",
                CenterOfInterest = "interest11"
            ),
            Child(
                id = 1,
                name = "name12",
                surname = "surname12",
                age = 12,
                activities = "activities12",
                CenterOfInterest = "interest12"
            ),
            Child(
                id = 1,
                name = "name13",
                surname = "surname13",
                age = 13,
                activities = "activities13",
                CenterOfInterest = "interest13"
            ),
            Child(
                id = 1,
                name = "name14",
                surname = "surname14",
                age = 14,
                activities = "activities14",
                CenterOfInterest = "interest14"
            ),
            Child(
                id = 1,
                name = "name15",
                surname = "surname15",
                age = 15,
                activities = "activities15",
                CenterOfInterest = "interest15"
            ),
            Child(
                id = 1,
                name = "name16",
                surname = "surname16",
                age = 16,
                activities = "activities16",
                CenterOfInterest = "interest16"
            ),
            Child(
                id = 1,
                name = "name17",
                surname = "surname17",
                age = 17,
                activities = "activities17",
                CenterOfInterest = "interest17"
            ),
            Child(
                id = 1,
                name = "name18",
                surname = "surname18",
                age = 18,
                activities = "activities18",
                CenterOfInterest = "interest18"
            ),
            Child(
                id = 1,
                name = "name19",
                surname = "surname19",
                age = 19,
                activities = "activities19",
                CenterOfInterest = "interest19"
            ),
            Child(
                id = 1,
                name = "name10",
                surname = "surname10",
                age = 10,
                activities = "activities10",
                CenterOfInterest = "interest10"
            )
        )

//        recyclerViewChild.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
//        recyclerViewChild.adapter = ChildListAdapter(childs)

        // To here : End of code to delete when connected to API

    }

/**

    private fun fetchChild() {


        // Comment to use dummy data from here

        ChildApi().getChild().enqueue(object : Callback<List<Child>> {

            override fun onFailure(call: Call<List<Child>>, t: Throwable) {
            child_refresh_layout.isRefreshing = false
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()

            }

            override fun onResponse(call: Call<List<Child>>, response: Response<List<Child>>) {
            child_refresh_layout.isRefreshing = false
                val child =  response.body()
                child?.let {
                    showChild(it)
                }
            }
        })

        // to here

    }


    //  Comment to use dummy data from here

    private fun showChild(child: List<Child>) {
        recyclerViewChild.layoutManager = LinearLayoutManager(this)
        recyclerViewChild.adapter = ChildListAdapter(child)
    }

    // to here
**/
}
