package c14220120.roombaru.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryBarangDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(daftar: HistoryBarang)


    @Query("SELECT * FROM HistoryBarang ORDER BY id ASC")
    fun selectAll(): MutableList<HistoryBarang>

    @Query("SELECT * FROM HistoryBarang WHERE id = :isi_id")
    suspend fun getItem(isi_id : Int) : HistoryBarang
}