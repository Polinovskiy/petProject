package com.mypackage.model;

public class Detail implements Comparable<Detail> {
    String material;
    String name;
    int size;
    double length;
    double width;
    int count;
    public String getName() {
        return name;
    }

    public void setName(String name) {
         this.name=name;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = Integer.parseInt(size);
    }

    public double getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = Double.parseDouble(length);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width =Double.parseDouble(width);
    }

    public int getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = Integer.parseInt(count.replaceAll("[^\\d]", ""));
    }

    public void combine(Detail detail){
        this.name+="\n"+detail.name;
        this.count+=detail.count;
    }
    public boolean equals(Detail B){
        if (this.getMaterial().equals(B.getMaterial()) && this.getSize()==B.getSize()) {
            if (this.getLength() == B.getLength() && this.getWidth() == B.getWidth()) {
                return true;
            }
        }
        return false;
    }
    @Override
    public int compareTo(Detail u) {
        if (getMaterial() == null || u.getMaterial() == null) {
            return 0;
        }
        return getMaterial().compareTo(u.getMaterial());
    }

    @Override
    public String toString() {
        return "Detail{" +
                "material='" + material + '\'' +
                ", \t size=" + size +
                ", \t length=" + length +
                ", \t width=" + width +
                ", \t count=" + count +
                '}';
    }
    public String printToFile(){
        return name+" "+length+" "+width+" "+count;
    }
}
