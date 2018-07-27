package com.android.vanshika.notes.frag;

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
import com.android.vanshika.notes.R;
import com.android.vanshika.notes.data.myuser;
//import courses.android.com.todo.R;
//import courses.android.com.todo.data.myuser;
import java.util.HashMap;
import java.util.List;

public class infoAdapter extends RecyclerView.Adapter<InfoViewHolder> {
  private Context context;
  private List<myuser> items;
  public static HashMap<String, Boolean> myMap2;
  public infoAdapter(Context context, List<myuser> items) {
    this.context = context;
    this.items = items;
  }

  @NonNull @Override
  public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    InfoViewHolder holder =
        new InfoViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view, parent, false));
    myMap2= new HashMap<String, Boolean>();
    return holder;
  }


  @Override public void onBindViewHolder(@NonNull InfoViewHolder holder,  int position) {
    holder.topic.setText(items.get(position).getmTitle());
    final Integer finalInt = position;
    final  InfoViewHolder finalHolder=holder;
    holder.checkBox.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (myMap2.get(finalHolder.topic.getText().toString())==null|| !myMap2.get(finalHolder.topic.getText().toString()))
          myMap2.put(finalHolder.topic.getText().toString(),true);
        else if(myMap2.get(finalHolder.topic.getText().toString()))
          myMap2.remove(finalHolder.topic.getText().toString());
      }
    });
    if(PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getBoolean("DeleteEnabled" ,false)){
      holder.checkBox.setVisibility(View.VISIBLE);
    }else{
      holder.checkBox.setVisibility(View.GONE);
    }
    holder.cardView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent intent = new Intent(context, editCredentials.class);
        intent.putExtra("fresh",1);
        intent.putExtra("position", finalInt);
        intent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("heading",items.get(finalInt).getmTitle());// iske bina 6.0 se neeche devices main error aane lagta hei agar background se foreground main app aae tph/
        intent.putExtra("name", items.get(finalInt).getmUser());
        intent.putExtra("mailid",items.get(finalInt).getMid());
        intent.putExtra("phone",items.get(finalInt).getmPhone());
        intent.putExtra("password",items.get(finalInt).getmPass());

          intent.putExtra("note",items.get(finalInt).getmNote());


        context.startActivity(intent);
      }
    });
  }

  @Override public int getItemCount() {
    return items.size();
  }
}

class InfoViewHolder extends RecyclerView.ViewHolder{
  public TextView topic;
  CardView cardView;
  CheckBox checkBox;
  public InfoViewHolder(View itemView) {
    super(itemView);
    topic=(TextView) itemView.findViewById(R.id.title);
    cardView=itemView.findViewById(R.id.cardView);
    checkBox=itemView.findViewById(R.id.checkb);
  }
}
