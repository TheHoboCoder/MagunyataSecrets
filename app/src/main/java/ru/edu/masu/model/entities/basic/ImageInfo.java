package ru.edu.masu.model.entities.basic;

//  хранит информацию о отображаемом изображении
public class ImageInfo {

    // общий путь, по которому хранятся картинки
    // может быть как локальным ресурсом, так и сетевым
    // на данный момент картинки находятся в каталоге ассеты
    final String IMAGES_DESTINATION = "file:///android_asset/";

    // путь к картинке относительно IMAGES_DESTINATION
    private String imgPath;

    public ImageInfo(String imgPath) {
        this.imgPath = imgPath;
    }

    public ImageInfo(){
        this.imgPath = "";
    }

    public String getImgPath() { return IMAGES_DESTINATION+imgPath; }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
