package com.example.tprecycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.tprecycleview.adapter.StarAdapter;
import com.example.tprecycleview.beans.Star;
import com.example.tprecycleview.service.StarService;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private List<Star> stars;
    StarService service = StarService.getInstance();
    private RecyclerView recyclerView;
    private StarAdapter starAdapter = null;
    private Toolbar toolbar; // Added Toolbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        stars = new ArrayList<>();
        init();
        recyclerView = findViewById(R.id.recycle_view);
        starAdapter = new StarAdapter(this, service.findAll());
        recyclerView.setAdapter(starAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void init() {
        service.create(new Star("kate bosworth", "https://www.milleworld.com/wp-content/uploads/2022/09/Tamino-1536x872.jpg", 3.5f));
        service.create(new Star("george clooney", "https://www.milleworld.com/wp-content/uploads/2022/09/Tamino-1536x872.jpg", 3));
        service.create(new Star("michelle rodriguez", "https://www.milleworld.com/wp-content/uploads/2022/09/Tamino-1536x872.jpg", 5));
        service.create(new Star("george clooney", "https://www.milleworld.com/wp-content/uploads/2022/09/Tamino-1536x872.jpg", 1));
        service.create(new Star("louise bouroin", "https://www.milleworld.com/wp-content/uploads/2022/09/Tamino-1536x872.jpg", 5));
        service.create(new Star("louise bouroin", "https://www.milleworld.com/wp-content/uploads/2022/09/Tamino-1536x872.jpg", 1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (starAdapter != null){
                    starAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.search){
            String txt = "Stars";
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle("Stars")
                    .setText(txt)
                    .startChooser();
        }
        return super.onOptionsItemSelected(item);
    }
}
