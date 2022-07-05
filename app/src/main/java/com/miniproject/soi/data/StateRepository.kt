package com.miniproject.soi.data

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import java.util.concurrent.Executors

class StateRepository(application: Application) {

    private val stateDao: StateDao
    private val executorService = Executors.newSingleThreadExecutor()

    init {
        val stateDatabase = StateDatabase.getInstance(application)
        stateDao = stateDatabase.dao
    }

    companion object{
        private var stateRepository: StateRepository? = null
        fun getRepository(application: Application): StateRepository? {
            if (stateRepository==null){
                synchronized(StateRepository::class.java){
                    if (stateRepository==null){
                        stateRepository = StateRepository(application)
                    }
                }
            }
            return stateRepository
        }
    }

    fun insertState(s: State){
        executorService.execute {
            stateDao.insertState(s)
        }
    }

    fun updateState(s: State){
        executorService.execute {
            stateDao.updateState(s)
        }
    }

    fun deleteState(s: State){
        executorService.execute {
            stateDao.deleteState(s)
        }
    }

    fun getAllStates(): LiveData<PagedList<State>> {
        return LivePagedListBuilder(stateDao.getAllStates(), 15).build()
    }

    fun getAllStates(sortBy: String): LiveData<PagedList<State>>{
        return LivePagedListBuilder(stateDao.getAllStates(constructQuery(sortBy)), 15).build()
    }

    private fun constructQuery(sortBy: String): SupportSQLiteQuery {
        val query = "SELECT * FROM StateAndCapital ORDER BY "+sortBy+" ASC";
        return SimpleSQLiteQuery(query)
    }

    @WorkerThread
    fun getRandomState(): State{
        return stateDao.getRandomState()
    }

    fun getQuizStates(value: Int): LiveData<List<State>>{
        return stateDao.getQuizStates(value)
    }
}