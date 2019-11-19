package com.sogeti.inno.cherry.comm

import com.google.gson.Gson
import com.sogeti.inno.cherry.activities.models.CalculatorGameItem
import com.sogeti.inno.cherry.activities.models.Tale

class MoveCommand(name:String) : Command(){
    var Command:String = "PlayMove"
    var MoveName:String = ""

    init {
        MoveName = name
    }
}

class SpeakCommand(content: String): Command() {
    var Command: String = "Speak"
    var Content: String = ""
    init {
        Content = content
    }
}

class ConnectToPoppy(id:String) : Command(){
    var Command:String = "SetAppPoppy"
    var PoppyId:String = ""

    init {
        PoppyId = id
    }
}

class GetChoregraphies(): Command() {
    val Command: String = "GetChoregraphies"
}


data class ServerSideChoregraphy(var Name: String, var Movements: List<String>, var Music: String)
class GetChoregraphiesResponse(var Choregraphies: List<ServerSideChoregraphy>): Command()

class AddChoregraphy(var Moves: List<String>, var Name: String, var Music: String): Command()
{
    var Command:String = "AddChoregraphy"
}

class RemoveChoregraphy(var Name:String): Command() {
    var Command:String = "RemoveChoregraphy"
}

class PlayChoregraphy(var Name: String) : Command() {
    var Command: String = "PlayChoregraphy"
}

class RetrieveConnectedPoppies(): Command() {
    var Command:String = "GetListPoppy"
}
class RetrieveConnectedPoppiesResponse(var ConnectedPoppies: List<String>): Command()

class ConnectToPoppyResponse(PoppyId: String): Command(){
    var PoppyId : String = ""
}

class ConnectionToPoppyLost(var PoppyState: String): Command()

class GetJokes : Command() {
    val Command = "GetJokes"
}

class GetCalculatorItems : Command() {
    val Command = "GetCalculatorItems"
}

class CalculatorItemsResponse(var CalculatorItems: ArrayList<CalculatorGameItem>): Command()

class JokesResponse(var Jokes: List<String>): Command()

data class Joke(val Content: String)

class ChangeEmotion(val Emotion: Int): Command() { // Here the Emotion integer is an id of an EmotionsEnum object
    val Command = "ChangeEmotion"
}

class GetTales: Command() {
    val Command = "GetTales"
}

class GetTalesResponse(var Tales: ArrayList<Tale>): Command()


data class ReviveDialog(val Command: String = "ReviveDialog"): Command()

data class TimerReset(val Command: String = "TimerReset"): Command()

data class ResetPoppy(val Command: String = "ResetPoppy"): Command()