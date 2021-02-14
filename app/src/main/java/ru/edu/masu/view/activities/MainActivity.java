package ru.edu.masu.view.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.edu.masu.R;
import ru.edu.masu.adapters.QuestItemRVMainAdapter;
import ru.edu.masu.model.entities.quest.Hint;
import ru.edu.masu.model.entities.basic.NamedItem;
import ru.edu.masu.model.entities.quest.QuestItem;
import ru.edu.masu.model.entities.questPass.IQuestPass;
import ru.edu.masu.model.data.repository.QuestPassRepository;
import ru.edu.masu.model.data.repository.QuestRepository;
import ru.edu.masu.utils.ImageCaching;
import ru.edu.masu.utils.PreferencesWrapper;
import ru.edu.masu.view.dialogs.HintFragment;
import ru.edu.masu.viewmodel.MainVM;
import ru.edu.masu.viewmodel.MainVMFactory;

public class MainActivity extends AppCompatActivity implements IQuestPassPerformer {

    private MainVM mainVM;
    private TextView questNameTxt, questDescTxt, progressTxt;
    private ProgressBar progressBar;
    private ImageView questImage;
    private ImageView hintBtn;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private HintFragment hintFragment;
    private DialogFragment questPassDialog;
    private Handler handler;
    private PreferencesWrapper preferencesWrapper;
    private QuestPassProvider questPassProvider;

    private Hint lastHint;
    private QuestItem currentQuest;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferencesWrapper = new PreferencesWrapper(
                getSharedPreferences(PreferencesWrapper.PREFERENCES_FILE, MODE_PRIVATE));
        questPassProvider = new QuestPassProvider(this);
        AssetManager assetManager = getAssets();
        mainVM = new ViewModelProvider(this,
                new MainVMFactory(new QuestRepository(assetManager),
                        new QuestPassRepository(assetManager), preferencesWrapper))
                .get(MainVM.class);
        handler = new Handler();
        initUI();
        mainVM.getQuestItems().observe(this, this::onQuestItemsLoaded);
    }

    private void onQuestItemsLoaded(List<QuestItem> questItems){
        progressBar.setMax(questItems.size());
        adapter = new QuestItemRVMainAdapter(questItems, this);
        recyclerView.setAdapter(adapter);

        mainVM.getCurrentQuest().observe(this, questItem -> {
            if(questItem.getStatus() == QuestItem.Status.ACTIVE){
                currentQuest = questItem;
                initQuestItem(questItem);
            }
            if(questItem.getStatus() == QuestItem.Status.FINISHED){
//                //после сдачи квеста подсказки для него не должны показываться
//                handler.removeCallbacksAndMessages(null);
//                mainVM.getCurrentHint().removeObservers(this);
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
         questDescTxt.setText(questItem.getDesc());
         ImageCaching.loadIn(this, questItem.getQuestProviderImg().getImgPath(), questImage);
         ImageCaching.loadIn(this, questItem.getQuestHelperImg().getImgPath(),  hintBtn);
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
        if(hint == null){
            mainVM.toNextHint();
            return;
        }
        lastHint = hint;
        handler.postDelayed(() -> {
            hintFragment = HintFragment.newInstance(hint.getImageInfo().getImgPath(),
                    hint.getDesc());
            hintFragment.setCancelable(false);
            showHint();
            lastHint = null;
            mainVM.toNextHint();
            Log.d("M_DEBUG", "Hint "+hint.getDesc() + " is shown");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == QuestPassActivity.REQUEST_CODE &&
                resultCode == QuestPassActivity.QUEST_PASSED){
            mainVM.getQuestPassLiveData().observe(this, questPass -> {
                mainVM.getQuestPassLiveData().removeObservers(this);
                mainVM.finishQuest();
            });
        }
        else{
            // если квест не был сдан, то после возвращения на текущий экран нужно возобновить показ
            // подсказок. Отчет времени для показа текущей подсказки будет начат заново.
            //mainVM.getCurrentHint().observe(this, this::scheduleHintPopup);
            scheduleHintPopup(lastHint);
        }
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
                        preferencesWrapper.setIsGameRunned(false);
                        MainActivity.super.onBackPressed();
                    }
                })
                .create();
        dialog.show();
    }


    public void onInventoryClick(View view) {
       // todo: inventory
    }

    public void onStoryBtnClick(View view) {
        Intent intent = new Intent(MainActivity.this, StoryActivity.class);
        startActivity(intent);
    }

    public void showDescription(View view) {
        if(currentQuest != null){
            NamedItem questTask = currentQuest.getQuestTask();
            Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
            intent.putExtra(DescriptionActivity.DESC, questTask.getName());
            intent.putExtra(DescriptionActivity.IMG_PATH, questTask.getImageInfo().getImgPath());
            startActivity(intent);
        }

    }

    public void onQuestPassBtnClick(View view) {
        mainVM.getQuestPassLiveData().observe(this, questPass -> {
            // если null, то метод сдачи еще не подгрузился, ждем
            if(questPass == null)
                return;
            mainVM.getQuestPassLiveData().removeObservers(this);
            questPass.createVerifier(questPassProvider);
        });
    }

    public void onHintBtnClick(View view) {
        showHint();
    }

    @Override
    public boolean canDisplayFullScreen() {
        return false;
    }

    @Override
    public void showQuestPassFragment(Fragment questPassFragment) {
        //на время сдачи квеста отключаем показ подсказок
        handler.removeCallbacksAndMessages(null);
        if(questPassDialog == null){
            questPassDialog = (DialogFragment) questPassFragment;
        }
        FragmentManager fm = getSupportFragmentManager();
        questPassDialog.show(fm,"questPass");
        //после закрытия диалога
        // нужно включить показ подсказок. Отчет времени для текущей подсказки будет начат заново.
        fm.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentDestroyed(fm, f);
                // если квест был завершен, то показывать подсказки для него больше не нужно
                if (mainVM.getCurrentQuest().getValue().getStatus() != QuestItem.Status.FINISHED){
                    MainActivity.this.scheduleHintPopup(lastHint);
                }
                fm.unregisterFragmentLifecycleCallbacks(this);
            }
        }, false);
    }

    @Override
    public void navigateToAnother(IQuestPass questPass) {
        Intent intent = new Intent(MainActivity.this, QuestPassActivity.class);
        intent.putExtra(QuestPassActivity.QUEST_NAME, questPass.getName());
        startActivityForResult(intent, QuestPassActivity.REQUEST_CODE);
    }
}
