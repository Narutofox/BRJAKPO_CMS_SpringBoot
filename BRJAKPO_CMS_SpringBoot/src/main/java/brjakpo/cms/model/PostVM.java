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
public class PostVM {
    private int postId;
    private String content;
    private String ime;
    private String prezime;
    private boolean canDelete;

    public PostVM(int postId, String content, String ime, String prezime, boolean canDelete) {
        this.content = content;
        this.ime = ime;
        this.prezime = prezime;
        this.canDelete = canDelete;
        this.postId = postId;
    }
    
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }
}
