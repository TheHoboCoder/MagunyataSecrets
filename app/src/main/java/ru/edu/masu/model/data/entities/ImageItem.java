package ru.edu.masu.model.data.entities;
// базовый класс для объектов типа "картинка с подписью"
public class ImageItem {
    // так как мы будем загружать инфу о квестах из Json файла, то вместо
    // идентификатора ресурса будем использовать путь к файлам в папке assets
    final String ASSET_PATH = "file:///android_asset/";

    private String imgPath;
    private String name;
    private String desc;

    public ImageItem(String imgPath, String name, String desc){
        this.imgPath = imgPath;
        this.name = name;
        this.desc = desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getDesc() { return desc; }


    public String getImgPath() { return ASSET_PATH+imgPath; }
    public String getName() { return name; }
    public void setName(String name){ this.name = name; }


}
