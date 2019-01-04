package org.drools.persistence.info;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.drools.persistence.SessionMarshallingHelper;

@Entity
@Table(name="JBPM_SESSION_INFO_TBL")
public class SessionInfo {
    private @Id
    @SequenceGenerator(name = "C_SEQ", sequenceName = "S_JBPM_SESSION_INFO", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "C_SEQ")
    Integer                        id;

    @Version
    @Column(name = "OPTLOCK")     
    private int                version;

    private Date               startDate;
    private Date               lastModificationDate;
    
    @Lob
    private byte[]             rulesByteArray;

    @Transient
    SessionMarshallingHelper helper;
    
    public SessionInfo() {
        this.startDate = new Date();
    }

    public Integer getId() {
        return this.id;
    }
    
    public int getVersion() {
        return this.version;
    }

    public void setJPASessionMashallingHelper(SessionMarshallingHelper helper) {
        this.helper = helper;
    }

    public SessionMarshallingHelper getJPASessionMashallingHelper() {
        return helper;
    }
    
    public void setData( byte[] data) {
        this.rulesByteArray = data;
    }
    
    public byte[] getData() {
        return this.rulesByteArray;
    }
    
    public Date getStartDate() {
        return this.startDate;
    }

    public Date getLastModificationDate() {
        return this.lastModificationDate;
    }

    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date;
    }
    

    @PrePersist 
    @PreUpdate 
    public void update() {
        this.rulesByteArray  = this.helper.getSnapshot();
    }

    public void setId(Integer ksessionId) {
        this.id = ksessionId;
    }

}
