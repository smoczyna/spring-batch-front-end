/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.springbatch.schema;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author smoczyna
 */
@Entity
@Table(name = "BATCH_JOB_EXECUTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BatchJobExecution.findAll", query = "SELECT b FROM BatchJobExecution b")})
public class BatchJobExecution implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "JOB_EXECUTION_ID")
    private Long jobExecutionId;
    @Column(name = "VERSION")
    private BigInteger version;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "START_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "END_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Size(max = 10)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 2500)
    @Column(name = "EXIT_CODE")
    private String exitCode;
    @Size(max = 2500)
    @Column(name = "EXIT_MESSAGE")
    private String exitMessage;
    @Column(name = "LAST_UPDATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;
    @Size(max = 2500)
    @Column(name = "JOB_CONFIGURATION_LOCATION")
    private String jobConfigurationLocation;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "batchJobExecution", fetch = FetchType.LAZY)
    private BatchJobExecutionContext batchJobExecutionContext;
    @JoinColumn(name = "JOB_INSTANCE_ID", referencedColumnName = "JOB_INSTANCE_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BatchJobInstance jobInstanceId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobExecutionId", fetch = FetchType.LAZY)
    private Collection<BatchStepExecution> batchStepExecutionCollection;

    public BatchJobExecution() {
    }

    public BatchJobExecution(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    public BatchJobExecution(Long jobExecutionId, Date createTime) {
        this.jobExecutionId = jobExecutionId;
        this.createTime = createTime;
    }

    public Long getJobExecutionId() {
        return jobExecutionId;
    }

    public void setJobExecutionId(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExitCode() {
        return exitCode;
    }

    public void setExitCode(String exitCode) {
        this.exitCode = exitCode;
    }

    public String getExitMessage() {
        return exitMessage;
    }

    public void setExitMessage(String exitMessage) {
        this.exitMessage = exitMessage;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getJobConfigurationLocation() {
        return jobConfigurationLocation;
    }

    public void setJobConfigurationLocation(String jobConfigurationLocation) {
        this.jobConfigurationLocation = jobConfigurationLocation;
    }

    public BatchJobExecutionContext getBatchJobExecutionContext() {
        return batchJobExecutionContext;
    }

    public void setBatchJobExecutionContext(BatchJobExecutionContext batchJobExecutionContext) {
        this.batchJobExecutionContext = batchJobExecutionContext;
    }

    public BatchJobInstance getJobInstanceId() {
        return jobInstanceId;
    }

    public void setJobInstanceId(BatchJobInstance jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }

    @XmlTransient
    public Collection<BatchStepExecution> getBatchStepExecutionCollection() {
        return batchStepExecutionCollection;
    }

    public void setBatchStepExecutionCollection(Collection<BatchStepExecution> batchStepExecutionCollection) {
        this.batchStepExecutionCollection = batchStepExecutionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobExecutionId != null ? jobExecutionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BatchJobExecution)) {
            return false;
        }
        BatchJobExecution other = (BatchJobExecution) object;
        if ((this.jobExecutionId == null && other.jobExecutionId != null) || (this.jobExecutionId != null && !this.jobExecutionId.equals(other.jobExecutionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.squadd.springbatch.schema.BatchJobExecution[ jobExecutionId=" + jobExecutionId + " ]";
    }
    
}
