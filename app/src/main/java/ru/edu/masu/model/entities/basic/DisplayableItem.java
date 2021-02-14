package ru.edu.masu.model.entities.basic;

// элемент, который может быть представлен в виде картинки
// содержит информацию о отображаемой картинке (ImageInfo)
public class DisplayableItem implements IDisplayable {

    private ImageInfo imageInfo;

    public DisplayableItem(ImageInfo imageInfo) {
        this.imageInfo = imageInfo;
    }

    public DisplayableItem(){}

    public ImageInfo getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(ImageInfo imageInfo) {
        this.imageInfo = imageInfo;
    }
}
