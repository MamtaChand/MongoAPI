package np.com.softwarica.mongoapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayHeroActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HeroesAdapter adapter;
    private ArrayList<Hero> listHeroes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_hero);

        recyclerView = findViewById(R.id.recyclerView);
        listHeroes = new ArrayList<>();
        adapter = new HeroesAdapter(listHeroes, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadData();
    }

    private void loadData() {
        Url.getInstance().create(HeroAPI.class).getHeroes(Url.Cookie).enqueue(new Callback<ArrayList<Hero>>() {
            @Override
            public void onResponse(Call<ArrayList<Hero>> call, Response<ArrayList<Hero>> response) {
                if (response.isSuccessful()) {
                    adapter.updateData(response.body());
                } else {
                    Toast.makeText(DisplayHeroActivity.this, "Failed to get hero list.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Hero>> call, Throwable t) {
                Toast.makeText(DisplayHeroActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
