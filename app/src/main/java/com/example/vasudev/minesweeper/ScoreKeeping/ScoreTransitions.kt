package com.example.vasudev.minesweeper.ScoreKeeping

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import org.greenrobot.eventbus.EventBus

/**
 * Created by Vasudev on 3/27/2018.
 */
class ScoreTransitions {

    fun addScoreToDatabase(name:String,score:String){
        val firestore=FirebaseFirestore.getInstance()
        val scoreMap=HashMap<String,String>()
        scoreMap.put("name",name)
        scoreMap.put("score",score)
        firestore.collection("scores").add(scoreMap as Map<String, String>).addOnCompleteListener{
            if (it.isComplete){
                if(it.isSuccessful){

                }else{

                }
            }
        }
    }

    fun fetchScoreFromDatabase(){
        val firestore=FirebaseFirestore.getInstance()
        val scores=firestore.collection("scores")
                .get()
                .addOnCompleteListener { p0 ->
                    val scoreList=ArrayList<ScoreRenderModel>()
                    for(document:DocumentSnapshot in p0.result){
                        if(document.exists()){
                            val scoreRenderModel=ScoreRenderModel(name =document.getString("name"),time =document.getString("score"))
                            scoreList.add(scoreRenderModel)
                        }
                    }
                    EventBus.getDefault().post(ScoreRender(scoreList))
                }
    }

}