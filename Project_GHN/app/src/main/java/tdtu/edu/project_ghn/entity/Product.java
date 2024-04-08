package tdtu.edu.project_ghn.entity;

import android.media.Image;

import java.io.File;
import java.io.Serializable;

public class Product implements Serializable {

    private float weight;
    private String productSize;
    private String productType;

    private String imgPath;
    private String typeOfInsurance;

    public Product() {
    }

    public Product(float weight, String productSize, String productType, byte[] imgData, String typeOfInsurance) {
        this.weight = weight;
        this.productSize = productSize;
        this.productType = productType;
        this.typeOfInsurance = typeOfInsurance;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }


    public String getTypeOfInsurance() {
        return typeOfInsurance;
    }

    public void setTypeOfInsurance(String typeOfInsurance) {
        this.typeOfInsurance = typeOfInsurance;
    }
}
