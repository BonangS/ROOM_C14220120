package c14220120.roombaru.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import c14220120.room.database.daftarBelanja
import c14220120.room.database.daftarBelanjaDAO
import c14220120.room.database.daftarBelanjaDB

@Database(entities = [HistoryBarang::class], version = 1)
abstract class HistoryBarangDB : RoomDatabase() {
    abstract fun funHistoryBarangDAO(): HistoryBarangDAO

    companion object {
        @Volatile
        private var INSTANCE: HistoryBarangDB? = null

        @JvmStatic
        fun getDatabase(context: Context): HistoryBarangDB {
            if (INSTANCE == null) {
                synchronized(HistoryBarangDB::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryBarangDB::class.java, "historybarang_db"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as HistoryBarangDB
        }
    }
}