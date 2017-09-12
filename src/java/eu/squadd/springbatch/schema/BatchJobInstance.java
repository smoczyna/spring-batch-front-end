/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.springbatch.schema;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author smoczyna
 */
@Entity
@Table(name = "BATCH_JOB_INSTANCE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BatchJobInstance.findAll", query = "SELECT b FROM BatchJobInstance b")})
public class BatchJobInstance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "JOB_INSTANCE_ID")
    private Long jobInstanceId;
    @Column(name = "VERSION")
    private BigInteger version;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "JOB_NAME")
    private String jobName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "JOB_KEY")
    private String jobKey;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobInstanceId", fetch = FetchType.LAZY)
    private Collection<BatchJobExecution> batchJobExecutionCollection;

    public BatchJobInstance() {
    }

    public BatchJobInstance(Long jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }

    public BatchJobInstance(Long jobInstanceId, String jobName, String jobKey) {
        this.jobInstanceId = jobInstanceId;
        this.jobName = jobName;
        this.jobKey = jobKey;
    }

    public Long getJobInstanceId() {
        return jobInstanceId;
    }

    public void setJobInstanceId(Long jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }

    @XmlTransient
    public Collection<BatchJobExecution> getBatchJobExecutionCollection() {
        return batchJobExecutionCollection;
    }

    public void setBatchJobExecutionCollection(Collection<BatchJobExecution> batchJobExecutionCollection) {
        this.batchJobExecutionCollection = batchJobExecutionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobInstanceId != null ? jobInstanceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BatchJobInstance)) {
            return false;
        }
        BatchJobInstance other = (BatchJobInstance) object;
        if ((this.jobInstanceId == null && other.jobInstanceId != null) || (this.jobInstanceId != null && !this.jobInstanceId.equals(other.jobInstanceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.squadd.springbatch.schema.BatchJobInstance[ jobInstanceId=" + jobInstanceId + " ]";
    }
    
}
