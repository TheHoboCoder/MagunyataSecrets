package ru.edu.masu.view.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.edu.masu.R;
import ru.edu.masu.adapters.QuestItemRVMainAdapter;
import ru.edu.masu.model.data.entities.Hint;
import ru.edu.masu.model.data.entities.QuestItem;
import ru.edu.masu.model.IQuestPass;
import ru.edu.masu.model.data.repository.QuestRepository;
import ru.edu.masu.utils.QuestPassFragmentProvider;
import ru.edu.masu.view.dialogs.HintFragment;
import ru.edu.masu.viewmodel.BasicVMFactory;
import ru.edu.masu.viewmodel.MainVM;
import ru.edu.masu.viewmodel.MainVMFactory;

public class MainActivity extends AppCompatActivity {

    private MainVM mainVM;
    private TextView questNameTxt, questDescTxt, progressTxt;
    private ProgressBar progressBar;
    private ImageView questImage;
    private ImageButton hintBtn;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private HintFragment hintFragment;
    private DialogFragment questPassDialog;
    private Handler handler;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainVM = new ViewModelProvider(this, new MainVMFactory(new QuestRepository()))
                .get(MainVM.class);
        handler = new Handler();
        initUI();

        List<QuestItem> questItems = mainVM.getQuestItems();
        progressBar.setMax(questItems.size());
        adapter = new QuestItemRVMainAdapter(questItems, this);
        recyclerView.setAdapter(adapter);

        mainVM.getCurrentQuest().observe(this, questItem -> {
            if(questItem.getStatus() == QuestItem.Status.ACTIVE){
                initQuestItem(questItem);
            }
            if(questItem.getStatus() == QuestItem.Status.FINISHED){
                onQuestFinish(questItem);
            }
        });
        mainVM.getCurrentHint().observe(this, this::scheduleHintPopup);
        mainVM.getProgress().observe(this, progress -> {
            updateProgress(progress);
            refreshRecyclerView(progress);
        });

    }

    private void initUI(){
        questNameTxt = findViewById(R.id.questName);
        questDescTxt = findViewById(R.id.questDesc);
        progressTxt = findViewById(R.id.progressTxt);
        progressBar = findViewById(R.id.progressBar2);
        questImage = findViewById(R.id.imageView);
        hintBtn = findViewById(R.id.hintBtn);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }

    private void initQuestItem(QuestItem questItem){
         questNameTxt.setText(questItem.getName());
         questDescTxt.setText(questItem.getTask());
         questImage.setImageResource(questItem.getQuestProviderImg());
         hintBtn.setImageResource(questItem.getQuestHelperImg());
    }

    private void updateProgress(int progress){
        progressBar.setProgress(progress);
        progressTxt.setText(getString(R.string.progress,progress, 10));
    }

    private void refreshRecyclerView(int progress){
        if(progress > 0){
            //обновить текущий и завершенный квесты
            adapter.notifyItemRangeChanged(progress-1,2);
        }
    }

    private void scheduleHintPopup(Hint hint){
        handler.postDelayed(() -> {
            hintFragment = HintFragment.newInstance(hint.getHintImage(), hint.getHintText());
            hintFragment.setCancelable(false);
            showHint();
            mainVM.toNextHint();
            Log.d("M_DEBUG", "Hint "+hint.getHintText() + " is shown");
        },hint.getDelay());
    }

    private void showHint(){
        if(hintFragment == null){
            Toast t = Toast.makeText(this,"Подсказка недоступна", Toast.LENGTH_SHORT);
            t.show();
            return;
        }
        hintFragment.show(getSupportFragmentManager(), "hint");
    }

    private void onQuestFinish(QuestItem questItem){
        //TODO: вывести диалог об окончании квеста
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setMessage(questItem.getName()+" завершен!")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        clearDialogs();
                        mainVM.toNextQuest();
                    }
                })
                .setCancelable(false)
                .create();
        dialog.show();
    }

    private void clearDialogs(){
        questPassDialog = null;
        hintFragment = null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
               .setMessage(R.string.quitGame)
               .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                   }
                })
                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .create();
        dialog.show();
    }



    public void inventoryClick(View view) {
        Intent intent = new Intent(MainActivity.this, InventoryExplorerActivity.class);
        startActivity(intent);
    }

    public void showDescription(View view) {
        Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
        intent.putExtra("DESC","HELLO_THERE");
        startActivity(intent);
    }

    public void onQuestPassBtnClick(View view) {
        IQuestPass questPass = mainVM.getCurrentQuest().getValue().getQuestPass();
        if(QuestPassFragmentProvider.isDialog(questPass)){
            if(questPassDialog == null){
                questPassDialog = (DialogFragment) QuestPassFragmentProvider.get(questPass);
            }
            questPassDialog.show(getSupportFragmentManager(),"questPass");
        }
    }

    public void onHintBtnClick(View view) {
        showHint();
    }
}
