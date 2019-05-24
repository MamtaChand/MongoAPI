package np.com.softwarica.mongoapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addHero(View view) {
        startActivity(new Intent(this, AddHeroesAPI.class));
    }

    public void showHero(View view) {
        startActivity(new Intent(this, DisplayHeroActivity.class));
    }
}
