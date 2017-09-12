/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.springbatch.schema.ejb;

import eu.squadd.springbatch.schema.BatchStepExecutionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author smoczyna
 */
@Stateless
public class BatchStepExecutionContextFacade extends AbstractFacade<BatchStepExecutionContext> {

    @PersistenceContext(unitName = "SpringBatchJobBrowserPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BatchStepExecutionContextFacade() {
        super(BatchStepExecutionContext.class);
    }
    
}
