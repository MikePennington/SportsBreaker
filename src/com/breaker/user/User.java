package com.breaker.user;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.breaker.utils.Constants;
import com.breaker.utils.StringUtils;

/**
 * A class representing a member.
 * 
 * @author Mike Pennington
 */
public class User {

    private long    userId;
    private String  email;
    private String  encryptedPassword;
    private String  userName;
    private Date    creationDate;
    private String  hometown;
    private String  location;
    private String  college;
    private String  highSchool;
    private String  pictureFileName;
    private String  realName;
    private String  gender;
    private String  zipCode;
    private boolean newsletter;
    private Date    birthday;

    private boolean softLoggedIn;
    private boolean hardLoggedIn;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isHardLoggedIn() {
        return hardLoggedIn;
    }

    public void setHardLoggedIn(boolean hardLoggedIn) {
        this.hardLoggedIn = hardLoggedIn;
    }

    public boolean isSoftLoggedIn() {
        return softLoggedIn;
    }

    public void setSoftLoggedIn(boolean softLoggedIn) {
        this.softLoggedIn = softLoggedIn;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getHighSchool() {
        return highSchool;
    }

    public void setHighSchool(String highSchool) {
        this.highSchool = highSchool;
    }

    public String getPictureFileName() {
        return pictureFileName;
    }

    public void setPictureFileName(String pictureFileName) {
        this.pictureFileName = pictureFileName;
    }

    public String getPictureFilePath() {
        if (StringUtils.isEmpty(getPictureFileName()))
            return null;
        else
            return Constants.PICTURE_USERPIC_PATH + getUserId() + File.separator + getPictureFileName();

    }

    public String getPictureFileUrl() {
        if (StringUtils.isEmpty(getPictureFileName()))
            return "/images/default_profile_pic.jpg";
        else
            return Constants.PICTURE_USERPIC_URL + getUserId() + "/" + getPictureFileName();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public void setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBirthdayAsString() {
        if (this.birthday == null)
            return "";
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            return sdf.format(this.birthday);
        }
    }

    public String getBirthdayAsStringNoYear() {
        if (this.birthday == null)
            return "";
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd");
            return sdf.format(this.birthday);
        }
    }
}