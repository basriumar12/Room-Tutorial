package co.twoh.roomtutorial;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.twoh.roomtutorial.data.factory.AppDatabase;
import co.twoh.roomtutorial.model.Barang;

/**
 * Created by Herdi_WORK on 21.01.18.
 */

public class RoomReadSingleActivity extends AppCompatActivity {
    private AppDatabase db;
    Barang barang;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "barangdb").build();

        EditText etNama = findViewById(R.id.et_namabarang);
        EditText etMerk = findViewById(R.id.et_merkbarang);
        EditText etHarga = findViewById(R.id.et_hargabarang);
        Button btSubmit = findViewById(R.id.bt_submit);

        etNama.setEnabled(false);
        etMerk.setEnabled(false);
        etHarga.setEnabled(false);
        btSubmit.setVisibility(View.GONE);

         barang = (Barang) getIntent().getSerializableExtra("data");
        if(barang!=null){
            etNama.setText(barang.getNamaBarang());
            etMerk.setText(barang.getMerkBarang());
            etHarga.setText(barang.getHargaBarang());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_and_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                startActivity(RoomCreateActivity.getActIntent((Activity) this).putExtra("data", barang));
                return true;
                case R.id.menu_delete:
                   hapusbarang(barang);
                // Action goes here
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void hapusbarang(final Barang barang){


        new AsyncTask<Void, Void, Long>(){
            @Override
            protected Long doInBackground(Void... voids) {
                long status = db.barangDAO().deleteBarang(barang);
                return status;
            }

            @Override
            protected void onPostExecute(Long status) {
                Toast.makeText(RoomReadSingleActivity.this, "status berhasil di hapaus ", Toast.LENGTH_SHORT).show();
                finish();
            }
        }.execute();
    }


    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, RoomReadSingleActivity.class);
    }
}
