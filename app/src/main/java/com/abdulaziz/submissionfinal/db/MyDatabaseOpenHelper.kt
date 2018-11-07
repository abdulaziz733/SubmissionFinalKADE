package com.abdulaziz.submissionfinal.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx : Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteEvent.db", null, 1) {

    companion object {
        private var instances: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instances == null) {
                instances = MyDatabaseOpenHelper(ctx.applicationContext)
            }

            return instances as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(FavoriteEvent.TABLE_FAVORITE_EVENT, true,
                FavoriteEvent.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteEvent.EVENT_ID to TEXT,
                FavoriteEvent.HOME_NAME to TEXT,
                FavoriteEvent.AWAY_NAME to TEXT,
                FavoriteEvent.HOME_SCORE to TEXT,
                FavoriteEvent.AWAY_SCORE to TEXT,
                FavoriteEvent.STR_DATE to TEXT,
                FavoriteEvent.STR_TIME to TEXT)

        db.createTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true,
                FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteTeam.TEAM_ID to TEXT,
                FavoriteTeam.TEAM_NAME to TEXT,
                FavoriteTeam.TEAM_BADGE to TEXT)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteEvent.TABLE_FAVORITE_EVENT, true)
        db.dropTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true)
    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)