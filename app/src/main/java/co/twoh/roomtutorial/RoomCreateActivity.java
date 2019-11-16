package co.twoh.roomtutorial;

import android.app.Activity;
import androidx.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.twoh.roomtutorial.data.factory.AppDatabase;
import co.twoh.roomtutorial.model.Barang;

/**
 * Created by Herdi_WORK on 21.01.18.
 */

public class RoomCreateActivity extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "barangdb").build();

        final EditText etNamaBarang   = findViewById(R.id.et_namabarang);
        final EditText etMerkBarang   = findViewById(R.id.et_merkbarang);
        final EditText etHargaBarang  = findViewById(R.id.et_hargabarang);
        Button btSubmit         = findViewById(R.id.bt_submit);

        final Barang barang = (Barang) getIntent().getSerializableExtra("data");

        if(barang!=null){
            etNamaBarang.setText(barang.getNamaBarang());
            etMerkBarang.setText(barang.getMerkBarang());
            etHargaBarang.setText(barang.getHargaBarang());
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    barang.setNamaBarang(etNamaBarang.getText().toString());
                    barang.setMerkBarang(etMerkBarang.getText().toString());
                    barang.setHargaBarang(etHargaBarang.getText().toString());

                    updateBarang(barang);
                    finish();
                }
            });
        }else{
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Barang b = new Barang();
                    b.setHargaBarang(etHargaBarang.getText().toString());
                    b.setMerkBarang(etMerkBarang.getText().toString());
                    b.setNamaBarang(etNamaBarang.getText().toString());
                    insertData(b);
                    finish();
                }
            });
        }
    }

    private void updateBarang(final Barang barang){


        new AsyncTask<Void, Void, Long>(){
            @Override
            protected Long doInBackground(Void... voids) {
                long status = db.barangDAO().updateBarang(barang);
                return status;
            }

            @Override
            protected void onPostExecute(Long status) {
                Toast.makeText(RoomCreateActivity.this, "status berhasil update ", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    private void insertData(final Barang barang){
        // di room di wajibkan menggunakan proses background ketika melakukan crud data
        // tidak di rekomendasikan di buat di main trhead
        // disini kita coba pakai asyntask utk jalani di backgroudnya
//
//        Gunakan kelas AsyncTask untuk mengimplementasikan tugas asinkron yang berjalan lama di Worker Thread/Thread Pekerja. Worker Thread adalah Thread yang bukan Thread UI/Main Thread. AsyncTask memungkinkan anda menjalankan operasi latar belakang dan mempublikasikan hasil di Thread UI tanpa memanipulasi thread.
//
//        Bila AsyncTask dieksekusi, maka akan melalui empat langkah :
//
//        onPreExecute(), dipanggil di Thread UI sebelum tugas di eksekusi. Langkah ini biasanya digunakan untuk mempersiapkan tugas misalnya dengan menampilkan bilah kemajuan di UI.
//        doInBackground(Params…), dipanggil pada Background Thread setelah onPreExecute() selesai dijalankan. Langkah ini menjalankan komputasi latar belakang, mengembalikan hasil dan meneruskan hasilnya ke onPostExecute(). Metode doInBackground() juga bisa memanggil publishProgress(Progress…) untuk mempublikasikan satu atau beberapa unit kemajuan.
//                onProgressUpdate(Progress…), berjalan di Thread UI setelah publishProgress(Progress…) dipanggil. Gunakan onProgressUpdate() untuk melaporkan suatu bentuk kemajuan ke Thread UI sewaktu komputasi latar belakang dieksekusi. Misalnya, anda bisa menggunakannya untuk meneruskan data guna menganimasikan bilah kemajuan atau menampilkan log di bidang teks.
//                onPostExecute(Result), berjalan di Thread UI setelah komputasi latar belakang selesai.

    //submber https://medium.com/easyread/konsep-asynctask-dan-asynctaskloader-pada-android-b5ba2744dcdb
        //disini kita hanya menggunakan 2 method yaitu doInbackground dan onPostExcute

        new AsyncTask<Void, Void, Long>(){
            @Override
            // disni kita panggil function untuk insert
            protected Long doInBackground(Void... voids) {
                long status = db.barangDAO().insertBarang(barang);
                return status;
            }

            @Override
            //disni kita beritahu proses insert sudah selesai
            protected void onPostExecute(Long status) {
                Toast.makeText(RoomCreateActivity.this, "status berhasil di insert ", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, RoomCreateActivity.class);
    }
}
