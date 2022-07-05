package com.miniproject.soi.data

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import androidx.paging.DataSource

@Dao
interface StateDao {
    @Query("SELECT * FROM StateAndCapital")
    fun getAllStates(): DataSource.Factory<Int, State>

    @Insert
    fun insertState(s: State)

    @Update
    fun updateState(s: State)

    @Delete
    fun deleteState(s: State)

    @RawQuery(observedEntities = [State::class])
    fun getAllStates(supportSQLiteQuery: SupportSQLiteQuery): DataSource.Factory<Int, State>

    @Query("SELECT * FROM StateAndCapital ORDER BY RANDOM() LIMIT :value")
    fun getQuizStates(value: Int): LiveData<List<State>>

    @Query("SELECT * FROM StateAndCapital ORDER BY RANDOM() LIMIT 1")
    fun getRandomState(): State
}