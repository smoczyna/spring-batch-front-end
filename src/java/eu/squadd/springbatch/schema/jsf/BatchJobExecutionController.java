package eu.squadd.springbatch.schema.jsf;

import eu.squadd.springbatch.schema.BatchJobExecution;
import eu.squadd.springbatch.schema.jsf.util.JsfUtil;
import eu.squadd.springbatch.schema.jsf.util.JsfUtil.PersistAction;
import eu.squadd.springbatch.schema.ejb.BatchJobExecutionFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("batchJobExecutionController")
@SessionScoped
public class BatchJobExecutionController implements Serializable {

    @EJB
    private eu.squadd.springbatch.schema.ejb.BatchJobExecutionFacade ejbFacade;
    private List<BatchJobExecution> items = null;
    private BatchJobExecution selected;

    public BatchJobExecutionController() {
    }

    public BatchJobExecution getSelected() {
        return selected;
    }

    public void setSelected(BatchJobExecution selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private BatchJobExecutionFacade getFacade() {
        return ejbFacade;
    }

    public BatchJobExecution prepareCreate() {
        selected = new BatchJobExecution();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("BatchJobExecutionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("BatchJobExecutionUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("BatchJobExecutionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<BatchJobExecution> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public BatchJobExecution getBatchJobExecution(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<BatchJobExecution> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<BatchJobExecution> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = BatchJobExecution.class)
    public static class BatchJobExecutionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BatchJobExecutionController controller = (BatchJobExecutionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "batchJobExecutionController");
            return controller.getBatchJobExecution(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof BatchJobExecution) {
                BatchJobExecution o = (BatchJobExecution) object;
                return getStringKey(o.getJobExecutionId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), BatchJobExecution.class.getName()});
                return null;
            }
        }

    }

}
