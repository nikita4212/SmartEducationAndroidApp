package com.example.smarteducation;

public class ProductModel {

    private String fName;
    private String  phone;
    private String department;
    private String status;
    private String id;

    private ProductModel(){}
    private ProductModel(String fName,String phone,String department,String status,String id){
        this.fName=fName;
        this.phone=phone;
        this.department=department;
        this.status = status;
        this.id = id;
    }
    public String getfName() {
        return fName;
    }

    public void setName(String fName) {
        this.fName = fName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getdepartment() {
        return department;
    }

    public void setDp(String dp) {
        this.department = dp;
    }

    public String getstatus() {
        return status;
    }

    public void setSt(String st) {
        this.status = st;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
