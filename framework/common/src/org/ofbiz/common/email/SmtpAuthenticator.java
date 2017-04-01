package org.ofbiz.common.email;

/**
 * Created by changsy on 16/3/29.
 */
public class SmtpAuthenticator extends javax.mail.Authenticator {
    private String username,password;
    public SmtpAuthenticator(String username,String password){
        this.username = username;
        this.password = password;
    }
    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
        return new javax.mail.PasswordAuthentication(username,password);
    }
}
