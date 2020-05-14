package com.ekattorit.tishunote.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ekattorit.tishunote.R;
import com.ekattorit.tishunote.apapters.NotesAdapter;
import com.ekattorit.tishunote.apapters.NotesAdapterListener;
import com.ekattorit.tishunote.apapters.SpacesItemDecoration;
import com.ekattorit.tishunote.models.NoteModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btnAdd)
    FloatingActionButton btnAdd;

    private Realm realm;
    private RealmAsyncTask transaction;
    private RealmResults<NoteModel> noteModels;
    private List<NoteModel> noteModelList;
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();

        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
        recyclerView.addItemDecoration(new SpacesItemDecoration(4));
        notesAdapter = new NotesAdapter(MainActivity.this,new ArrayList<NoteModel>());
        recyclerView.setAdapter(notesAdapter);

        notesAdapter.setNotesAdapterListener(new NotesAdapterListener() {
            @Override
            public void onItemClick(int position) {
                realm.beginTransaction();
                noteModels.get(position).setCount(noteModels.get(position).getCount()+1);
                realm.commitTransaction();
                updateUI();
            }

            @Override
            public void onItemDelete(int position) {
                Toast.makeText(MainActivity.this, "Test Run (This feature is not implemented yet)", Toast.LENGTH_SHORT).show();
            }
        });

        updateUI();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (transaction != null && !transaction.isCancelled()) {
            transaction.cancel();
        }
    }

    @OnClick(R.id.btnAdd)
    public void onViewClicked() {
        showInputDialog();
    }

    private void showInputDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_dark_input);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Button submit;
        ImageButton cancel;
        EditText name,description;

        submit = dialog.findViewById(R.id.btnSubmit);
        cancel = dialog.findViewById(R.id.btnClose);

        name = dialog.findViewById(R.id.txtName);
        description = dialog.findViewById(R.id.txtDescription);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strname = name.getText().toString();
                String strDescription = description.getText().toString();

                if(!TextUtils.isEmpty(strname.trim()) && !TextUtils.isEmpty(strDescription.trim())){
                    saveData(strname,strDescription);
                    dialog.dismiss();
                }else {
                    Toast.makeText(MainActivity.this, "Field can't be empty!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void saveData(String name , String description) {
        transaction = realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                NoteModel noteModel = bgRealm.createObject(NoteModel.class);
                noteModel.setName(name);
                noteModel.setDescription(description);
                noteModel.setDate(new SimpleDateFormat("").format(new Date()));
                noteModel.setCount(noteModel.getCount() + 1);
                noteModel.setVisible(true);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                updateUI();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateUI() {
        noteModelList = new ArrayList<>();
        noteModelList.clear();
        noteModels = realm.where(NoteModel.class).sort("name").findAllAsync();
        noteModels.load();
        if(noteModels.isLoaded()){
            notesAdapter.setNoteModels(realm.copyFromRealm(noteModels));
        }
    }
}
