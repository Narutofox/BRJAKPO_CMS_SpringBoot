/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brjakpo.cms.model;

/**
 *
 * @author Naruto
 */
public class Notification {
    
    private String text;	
    private String cssClass;

    public Notification(String text, NotificationStatus cssClass) {
        this.text = text;
        this.cssClass = cssClass.toString();
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the cssClass
     */
    public String getCssClass() {
        if (cssClass.equals(NotificationStatus.Success.toString())) {
            return "alert alert-success";
        }
        else if (cssClass.equals(NotificationStatus.Warning.toString())) {
            return "alert alert-warning";
        }
        else if (cssClass.equals(NotificationStatus.Info.toString())) {
            return "alert alert-info";
        }
        else if (cssClass.equals(NotificationStatus.Error.toString())) {
            return "alert alert-danger";
        }
        return "";
    }

    /**
     * @param cssClass the cssClass to set
     */
    public void setCssClass(NotificationStatus cssClass) {
        this.cssClass = cssClass.toString();
    }
        
    public enum NotificationStatus {
        Error, Warning, Info, Success;
    }
}
