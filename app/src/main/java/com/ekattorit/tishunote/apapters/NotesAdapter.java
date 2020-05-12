package com.ekattorit.tishunote.apapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ekattorit.tishunote.R;
import com.ekattorit.tishunote.models.NoteModel;

import java.util.List;

import butterknife.BindView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private Context context;
    private List<NoteModel> noteModels;
    private NotesAdapterListener notesAdapterListener;

    public NotesAdapter(Context context, List<NoteModel> noteModels) {
        this.context = context;
        this.noteModels = noteModels;
    }

    public void setNotesAdapterListener(NotesAdapterListener notesAdapterListener) {
        this.notesAdapterListener = notesAdapterListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final NoteModel noteModel = noteModels.get(position);

        holder.txtTitle.setText(noteModel.getTitle());
        holder.btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notesAdapterListener != null){
                    notesAdapterListener.onItemClick(position);
                }
            }
        });

        holder.cardRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(context, "Long Pressed", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtTitle)
        TextView txtTitle;
        @BindView(R.id.btnPreview)
        TextView btnPreview;
        @BindView(R.id.cardRow)
        CardView cardRow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
