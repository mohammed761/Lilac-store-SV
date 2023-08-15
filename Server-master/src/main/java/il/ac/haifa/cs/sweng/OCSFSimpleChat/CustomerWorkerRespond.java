package il.ac.haifa.cs.sweng.OCSFSimpleChat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(
        name = "complainlistrespond"
)
public class CustomerWorkerRespond implements Serializable{
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int id;

    @Column(name = "answered_by")
    private String nameWorker;
    @Column(name = "client_name")
    private String name;

    @Column(name = "client_email")
    private String email;

    @Column(name = "client_phone")
    private String phone;

    @Column(length = 2000)
    private String message;
    @Column(name = "respond_message")
    private String respondMessage;

    @Column(name = "date")
    private String date;

    @Column(name = "refunds")
    private String refund;

    public CustomerWorkerRespond(){}
    public CustomerWorkerRespond(String nameWorker, String name, String email, String phone, String message, String respondMessage){
        this.nameWorker = nameWorker;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.message = message;
        this.respondMessage = respondMessage;
        this.date = new Date().toString();
    }

    public void setNameWorker(String nameWorker) {
        this.nameWorker = nameWorker;
    }

    public String getRefund() {
        return refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public String getNameWorker() {
        return nameWorker;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRespondMessage() {
        return respondMessage;
    }

    public void setRespondMessage(String respondMessage) {
        this.respondMessage = respondMessage;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

}

