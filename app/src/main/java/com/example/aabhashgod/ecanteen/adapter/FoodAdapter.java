package com.example.aabhashgod.ecanteen.adapter;



import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aabhashgod.ecanteen.model.MenuModel;

import java.util.List;


import com.example.aabhashgod.ecanteen.R;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.holder> {

    private List<MenuModel> menuModelClasses;
    private Context context;
    private ClickListenerEvents clickListenerEvents;
    private int counter = 0;


    public FoodAdapter(List<MenuModel> menuModelClasses, Context context, ClickListenerEvents clickListenerEvents) {
        this.menuModelClasses = menuModelClasses;
        this.context = context;
        this.clickListenerEvents = clickListenerEvents;
    }

    @Override
    public FoodAdapter.holder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        final holder menuHolder = new holder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListenerEvents.onItemClick(v, menuHolder.getPosition());
            }
        });
        return menuHolder;
    }

    @Override
    public void onBindViewHolder(FoodAdapter.holder holder, int position) {
        holder.vehicleName.setText(menuModelClasses.get(position).getName());
        holder.vehicleShortDetail.setText(menuModelClasses.get(position).getShortDetail());
        holder.vehiclePrice.setText(menuModelClasses.get(position).getPrice());
        holder.vehicleImage.setImageResource(menuModelClasses.get(position).getFoodId());

    }

    @Override
    public int getItemCount() {
        return menuModelClasses.size();
    }

    class holder extends RecyclerView.ViewHolder {

        CardView vehicleCard;
        TextView vehicleName, vehicleShortDetail, vehiclePrice;
        ImageView vehicleImage;
        Button add_item;

        holder(View itemView) {
            super(itemView);
            vehicleCard = itemView.findViewById(R.id.card_view);
            vehicleName = itemView.findViewById(R.id.txt_name);
            vehicleShortDetail = itemView.findViewById(R.id.txt_short_details);
            vehiclePrice = itemView.findViewById(R.id.txt_rate);
            vehicleImage = itemView.findViewById(R.id.imageView);
            add_item = itemView.findViewById(R.id.add_item);
        }

        public void onClick() {

        }


    }
}


