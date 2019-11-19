package com.sogeti.inno.cherry.activities.models

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

data class Child(
    val id: Int,
    val name: String,
    val surname: String,
    val age: Int,
    val activities: String,
    val CenterOfInterest: String
)


/**
class Child(childJson: JSONObject) : Serializable {

private lateinit var name: String
lateinit var surname: String
private set
var age: Int = 0
private set
var room: Int = 0
private set
var activities: String = ""
private set
var centerOfInterest: String = ""

init {
try {
name = childJson.getString(CHILD_NAME)
surname = childJson.getString(CHILD_SURNAME)
age = childJson.getInt(CHILD_AGE)
room = childJson.getInt(CHILD_ROOM)
activities = childJson.getString(CHILD_ACTIVITIES)
centerOfInterest = childJson.getString((CHILD_CENTEROFINTEREST))

} catch (e:JSONException) {
e.printStackTrace()
}
}

companion object {
private const val CHILD_NAME = "name"
private const val CHILD_SURNAME = "surname"
private const val CHILD_AGE = "age"
private const val CHILD_ROOM = "room"
private const val CHILD_ACTIVITIES = "activities"
private const val CHILD_CENTEROFINTEREST = "center of interest"

}

}

 **/