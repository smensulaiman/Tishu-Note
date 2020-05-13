package com.ekattorit.tishunote.apapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.ekattorit.tishunote.R;
import com.ekattorit.tishunote.models.NoteModel;
import com.facebook.drawee.view.SimpleDraweeView;

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

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_dark, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final NoteModel noteModel = noteModels.get(position);

        holder.txtName.setText(noteModel.getName());
        holder.txtDescription.setText(noteModel.getDescription());
        holder.txtCount.setText(noteModel.getCount());

        holder.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notesAdapterListener != null) {
                    notesAdapterListener.onItemDelete(position);
                }
            }
        });

        holder.btnTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notesAdapterListener != null) {
                    notesAdapterListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.btnClose)
        ImageButton btnClose;
        @BindView(R.id.imgProfile)
        SimpleDraweeView imgProfile;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtDescription)
        TextView txtDescription;
        @BindView(R.id.txtCount)
        TextView txtCount;
        @BindView(R.id.btnTap)
        AppCompatButton btnTap;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
