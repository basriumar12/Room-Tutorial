package co.twoh.roomtutorial.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import co.twoh.roomtutorial.model.Barang;

/**
 * Created by Herdi_WORK on 21.01.18.
 */

@Dao
public interface BarangDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertBarang(Barang barang);

    @Update
    int updateBarang(Barang barang);

    @Delete
    int deleteBarang(Barang barang);

    @Query("SELECT * FROM tbarang")
    Barang[] selectAllBarangs();

    @Query("SELECT * FROM tbarang WHERE barangId = :id LIMIT 1")
    Barang selectBarangDetail(int id);
}
