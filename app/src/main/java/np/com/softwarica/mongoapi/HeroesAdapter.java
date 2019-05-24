package np.com.softwarica.mongoapi;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HeroesAdapter extends RecyclerView.Adapter<HeroesAdapter.ViewHolder> {

    private List<Hero> heroesList;
    private Context context;

    public HeroesAdapter(List<Hero> heroesList, Context context) {
        this.heroesList = heroesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.heroes_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    private void StrictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void updateData(ArrayList<Hero> list){
        this.heroesList.clear();
        this.heroesList.addAll(list);
        this.notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Hero hero = heroesList.get(i);
        String imgPath = Url.BASE_URL + "uploads/" + hero.getImage();
        StrictMode();

        try {
            URL url = new URL(imgPath);
            viewHolder.imgImage.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewHolder.tvName.setText(hero.getName());
        viewHolder.tvDesc.setText(hero.getDesc());
    }

    @Override
    public int getItemCount() {
        return heroesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgImage;
        private TextView tvName, tvDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgImage = itemView.findViewById(R.id.imgImage);
            tvName = itemView.findViewById(R.id.tvName);
            tvDesc = itemView.findViewById(R.id.tvDesc);
        }
    }
}
