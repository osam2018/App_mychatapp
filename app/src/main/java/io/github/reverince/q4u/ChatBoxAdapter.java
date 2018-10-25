package io.github.reverince.q4u;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ChatBoxAdapter extends RecyclerView.Adapter<ChatBoxAdapter.MyViewHolder> {
    private List<Message> messageList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, message;

        public MyViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.text_name);
            message = view.findViewById(R.id.text_message);
        }
    }

    public ChatBoxAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public ChatBoxAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View messagesView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message, parent, false);
        return new ChatBoxAdapter.MyViewHolder(messagesView);
    }

    @Override
    public void onBindViewHolder(final ChatBoxAdapter.MyViewHolder holder, final int position) {
        //binding the data from our ArrayList of object to the item.xml using the viewholder
        Message m = messageList.get(position);
        holder.name.setText(m.getName());
        holder.name.setTextColor(m.getColor());
        holder.message.setText(m.getMessage());
    }
}
