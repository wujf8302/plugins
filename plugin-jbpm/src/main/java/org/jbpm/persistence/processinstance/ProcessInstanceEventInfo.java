package org.jbpm.persistence.processinstance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="JBPM_INSTANCE_EVENT_TBL")
public class ProcessInstanceEventInfo {

    @Id
    @SequenceGenerator(name = "C_SEQ", sequenceName = "S_JBPM_PROCESS_INSTANCE_EVENT", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "C_SEQ")
    private long   id;

    @Version
    @Column(name = "OPTLOCK")
    private int    version;

    private String eventType;
    private long   processInstanceId;

    protected ProcessInstanceEventInfo() {
    }
    
    public long getId() {
        return this.id;
    }
    
    public int getVersion() {
        return this.version;
    }    

    public ProcessInstanceEventInfo(long processInstanceId,
                                    String eventType) {
        this.processInstanceId = processInstanceId;
        this.eventType = eventType;
    }

    public long getProcessInstanceId() {
        return processInstanceId;
    }

    public String getEventType() {
        return eventType;
    }

}
