package c14220120.roombaru

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import c14220120.room.database.daftarBelanja
import c14220120.room.database.daftarBelanjaDB
import c14220120.roombaru.helper.DateHelper.getCurrentDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class TambahDaftar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_daftar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var DB = daftarBelanjaDB.getDatabase(this)
        var tanggal = getCurrentDate()
        var btnTambah = findViewById<Button>(R.id.btnTambah)
        var _etItem = findViewById<EditText>(R.id.etItem)
        var _etJumlah = findViewById<EditText>(R.id.etJumlah)
        var _btnUpdate = findViewById<Button>(R.id.btnUpdate)
        var iID : Int = 0
        var iAddEdit = 0

        iID = intent.getIntExtra("id",0)
        iAddEdit = intent.getIntExtra("addEdit",0)

        if (iAddEdit == 0) {
            btnTambah.visibility = View.VISIBLE
            _btnUpdate.visibility = View.GONE
            _etItem.isEnabled = true
        }
        else{
            btnTambah.visibility = View.GONE
            _btnUpdate.visibility = View.VISIBLE
            _etItem.isEnabled = false

            CoroutineScope(Dispatchers.IO).async {
                val item = DB.fundaftarBelanjaDAO().getItem(iID)
                _etItem.setText(item.item)
                _etJumlah.setText(item.jumlah)
            }
        }

        btnTambah.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.fundaftarBelanjaDAO().insert(
                    daftarBelanja(
                        tanggal = tanggal,
                        item = _etItem.text.toString(),
                        jumlah = _etJumlah.text.toString()
                    )
                )
            }
        }
        _btnUpdate.setOnClickListener{
            CoroutineScope(Dispatchers.IO).async {
                DB.fundaftarBelanjaDAO().update(
                    isi_tanggal = tanggal,
                    isi_item = _etItem.text.toString(),
                    isi_jumlah = _etJumlah.text.toString(),
                    pilihid = iID
                )
            }
        }
    }
}