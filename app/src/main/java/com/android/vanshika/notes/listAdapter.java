package com.android.vanshika.notes;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.android.vanshika.notes.data.user;
//import courses.android.com.todo.data.user;
import java.util.HashMap;
import java.util.List;

public class listAdapter extends RecyclerView.Adapter<listViewHolder> {
  private Context context;
  private List<user> items;
  public static HashMap<String, Boolean> myMap;
  public listAdapter(Context context, List<user> items) {
    this.context = context;
    this.items = items;
  }

  @NonNull @Override
  public  listViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    listViewHolder holder =
        new listViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view, parent, false));
    myMap= new HashMap<String, Boolean>();
    return holder;
  }

  @Override public void onBindViewHolder(@NonNull listViewHolder holder, int position) {
    holder.topic.setText(items.get(position).getmTitle());
    //holder.itemView.setBackgroundColor(items.get(position).isSelected()? Color.blue(R.color.colorAccent):Color.alpha(R.color.cardview_shadow_start_color));
    final Integer finalInt = position;
    final listViewHolder finalHolder=holder;
    holder.checkBox.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (myMap.get(finalHolder.topic.getText().toString())==null|| !myMap.get(finalHolder.topic.getText().toString()))
          myMap.put(finalHolder.topic.getText().toString(),true);
        else if(myMap.get(finalHolder.topic.getText().toString()))
        myMap.remove(finalHolder.topic.getText().toString());
      }//3 visible uninstall kriook sirkar diya
    });
     if(PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getBoolean("DeleteEnabled" ,false)){
      holder.checkBox.setVisibility(View.VISIBLE);
    }else{
      holder.checkBox.setVisibility(View.GONE);
    }
    holder.cardView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent intent = new Intent(context, editTodo.class);
        intent.putExtra("position", finalInt);
        intent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK);// iske bina 6.0 se neeche devices main error aane lagta hei agar background se foreground main app aae tph/
        intent.putExtra("item", items.get(finalInt).getSummary());
        intent.putExtra("name",items.get(finalInt).getmTitle());
        context.startActivity(intent);
      }
    });
  }
  @Override public int getItemCount() {
    return items.size();
  }
  public  void removeItem(int position) {

    items.remove(position);
    notifyItemRemoved(position);
    notifyItemRangeChanged(position, getItemCount());
  }
}

class listViewHolder extends RecyclerView.ViewHolder  {
  public TextView topic;
  CardView cardView;
  CheckBox checkBox;

  public listViewHolder(View itemView) {
    super(itemView);

    topic = (TextView) itemView.findViewById(R.id.title);
    cardView=(CardView)itemView.findViewById(R.id.cardView);
    checkBox=(CheckBox) itemView.findViewById(R.id.checkb);

  }

}
