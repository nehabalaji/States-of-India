package com.miniproject.soi.data

import android.content.Context
import android.content.res.AssetManager
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.Executors

@Database(entities = [State::class], version = 1, exportSchema = true)
abstract class StateDatabase: RoomDatabase() {

    abstract val dao: StateDao

    companion object {
        val executorService = Executors.newSingleThreadExecutor()

        @Volatile
        private var INSTANCE: StateDatabase? = null

        fun getInstance(context: Context): StateDatabase {
            synchronized(this) {
                var instance: StateDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StateDatabase::class.java,
                        "QuizDatabase"
                    )
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                executorService.execute {
                                    populateDb(context.assets, getInstance(context))
                                }
                            }
                        })
                        .fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }

        fun populateDb(assetManager: AssetManager, quizDatabase: StateDatabase){
            var jsonString: String? = null
            try {
                jsonString = assetManager.open("state-capital.json").bufferedReader().use {  it.readText()}
            }catch (ioException: IOException){
                ioException.printStackTrace()
            }

            val quizDao = quizDatabase.dao
            try {
                val states = JSONObject(jsonString!!)
                val section = states.getJSONObject("sections")
                parseJson(section.getJSONArray("States (A-L)"), quizDao)
                parseJson(section.getJSONArray("States (M-Z)"), quizDao)
                parseJson(section.getJSONArray("Union Territories"), quizDao)
            }catch (e: JSONException){
                e.printStackTrace()
            }
        }

        fun parseJson(jsonArray: JSONArray, quizDao: StateDao){
            try {
                for (i in 0 until jsonArray.length()){
                    val state = jsonArray.getJSONObject(i)
                    val stateName = state.getString("key")
                    val capitalName = state.getString("val")
                    val newState = State(stateName, capitalName)
                    quizDao.insertState(newState)
                }
            }catch (e: JSONException){
                e.printStackTrace()
            }
        }
    }
}