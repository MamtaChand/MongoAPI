package np.com.softwarica.mongoapi;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder {

    private ImageView imgImage;
    private TextView tvName,tvDesc;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        imgImage = itemView.findViewById(R.id.imgImage);
        tvName = itemView.findViewById(R.id.tvName);
        tvDesc = itemView.findViewById(R.id.tvDesc);
    }
}
