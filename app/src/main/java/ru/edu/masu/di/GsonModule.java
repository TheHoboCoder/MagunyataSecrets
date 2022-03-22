package ru.edu.masu.di;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ru.edu.masu.model.data.gson.DescriptionItemAdapter;
import ru.edu.masu.model.data.gson.HintTypeAdapter;
import ru.edu.masu.model.data.gson.IEquipmentAdapter;
import ru.edu.masu.model.data.gson.IQuestPassAdapter;
import ru.edu.masu.model.data.gson.ITypeAdapter;
import ru.edu.masu.model.data.gson.MonsterItemAdapter;
import ru.edu.masu.model.data.gson.NamedItemAdapter;
import ru.edu.masu.model.data.gson.QuestItemAdapter;
import ru.edu.masu.model.data.gson.StandartTypeAdapter;
import ru.edu.masu.model.data.gson.StoryItemAdapter;
import ru.edu.masu.model.entities.basic.DescriptionItem;
import ru.edu.masu.model.entities.basic.NamedItem;
import ru.edu.masu.model.entities.equipment.IEquipment;
import ru.edu.masu.model.entities.quest.Hint;
import ru.edu.masu.model.entities.quest.Monster;
import ru.edu.masu.model.entities.quest.QuestItem;
import ru.edu.masu.model.entities.quest.StoryItem;
import ru.edu.masu.model.entities.questPass.IQuestPass;

@Module
public abstract class GsonModule {

    @Binds
    public abstract ITypeAdapter<Monster> bindMonsterAdapter(MonsterItemAdapter monsterItemAdapter);

    @Binds
    public abstract ITypeAdapter<IQuestPass> bindQuestPassAdapter(IQuestPassAdapter questPassAdapter);

    @Binds
    public abstract ITypeAdapter<StoryItem> bindStoryItemAdapter(StoryItemAdapter storyItemAdapter);

    @Binds
    public abstract ITypeAdapter<Hint> bindHintTypeAdapter(HintTypeAdapter hintTypeAdapter);

    @Provides
    public static NamedItemAdapter<NamedItem> provideNamedItemAdapter(){
        return new NamedItemAdapter<NamedItem>() {
            @Override
            public NamedItem create() {
                return new NamedItem();
            }
        };
    }

    @Binds
    public abstract ITypeAdapter<NamedItem> bindNamedItemAdapter(NamedItemAdapter<NamedItem> adapter);

    @Provides
    public static DescriptionItemAdapter<DescriptionItem> provideDescriptionItem(){
        return new DescriptionItemAdapter<DescriptionItem>() {
            @Override
            public DescriptionItem create() {
                return new DescriptionItem();
            }
        };
    }

    @Binds
    public abstract ITypeAdapter<DescriptionItem> bindDescriptionItemAdapter(DescriptionItemAdapter<DescriptionItem> adapter);

    @Provides
    public static ITypeAdapter<IEquipment> provideIEquipmentAdapter(ITypeAdapter<DescriptionItem> descriptionItemAdapter){
        return new IEquipmentAdapter(descriptionItemAdapter);
    }

    @Provides
    public static QuestItemAdapter provideQuestItemAdapter(ITypeAdapter<NamedItem> namedItemAdapter,
                                                    ITypeAdapter<StoryItem> storyItemAdapter,
                                                    ITypeAdapter<IEquipment> equipmentAdapter,
                                                    ITypeAdapter<Hint> hintTypeAdapter){
        return new QuestItemAdapter(namedItemAdapter, storyItemAdapter, equipmentAdapter, hintTypeAdapter);
    }

}
