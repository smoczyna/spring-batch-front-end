/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.springbatch.schema.ejb;

import eu.squadd.springbatch.schema.BatchJobInstance;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author smoczyna
 */
@Stateless
public class BatchJobInstanceFacade extends AbstractFacade<BatchJobInstance> {

    @PersistenceContext(unitName = "SpringBatchJobBrowserPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BatchJobInstanceFacade() {
        super(BatchJobInstance.class);
    }
    
}
