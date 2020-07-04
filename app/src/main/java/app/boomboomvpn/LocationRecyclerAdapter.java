package app.boomboomvpn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LocationRecyclerAdapter extends RecyclerView.Adapter<LocationRecyclerAdapter.LocationRecyclerViewHolder> {

    private String[] S_Data = null;
    private int[] F_data = null;
    private OnItemListener itemListener;

    public LocationRecyclerAdapter(String[] s_data,int[] f_data, OnItemListener listener)
    {
        S_Data = s_data;
        itemListener = listener;
        F_data = f_data;
    }



    @NonNull
    @Override
    public LocationRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.location_list_item,parent,false);
        return new LocationRecyclerViewHolder(view, itemListener);
    }


    @Override
    public void onBindViewHolder(@NonNull LocationRecyclerViewHolder holder, int position) {

        holder.LocationName.setText(S_Data[position]);
        holder.Flag.setImageResource(F_data[position]);
    }


    @Override
    public int getItemCount() {
        return S_Data.length;
    }

    public class LocationRecyclerViewHolder extends RecyclerView.ViewHolder{

        ImageView  Flag;
        TextView LocationName;
        public LocationRecyclerViewHolder(@NonNull View itemView, final OnItemListener listener) {
            super(itemView);
            Flag = (ImageView)itemView.findViewById(R.id.country_flag);
            LocationName = (TextView)itemView.findViewById(R.id.location_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(getAdapterPosition());
                }
            });

        }
    }

    public interface OnItemListener{
        void OnItemClick(int index);
    }

}
