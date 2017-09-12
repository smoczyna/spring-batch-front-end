package eu.squadd.springbatch.schema.jsf;

import eu.squadd.springbatch.schema.BatchJobExecutionContext;
import eu.squadd.springbatch.schema.jsf.util.JsfUtil;
import eu.squadd.springbatch.schema.jsf.util.JsfUtil.PersistAction;
import eu.squadd.springbatch.schema.ejb.BatchJobExecutionContextFacade;

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

@Named("batchJobExecutionContextController")
@SessionScoped
public class BatchJobExecutionContextController implements Serializable {

    @EJB
    private eu.squadd.springbatch.schema.ejb.BatchJobExecutionContextFacade ejbFacade;
    private List<BatchJobExecutionContext> items = null;
    private BatchJobExecutionContext selected;

    public BatchJobExecutionContextController() {
    }

    public BatchJobExecutionContext getSelected() {
        return selected;
    }

    public void setSelected(BatchJobExecutionContext selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private BatchJobExecutionContextFacade getFacade() {
        return ejbFacade;
    }

    public BatchJobExecutionContext prepareCreate() {
        selected = new BatchJobExecutionContext();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("BatchJobExecutionContextCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("BatchJobExecutionContextUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("BatchJobExecutionContextDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<BatchJobExecutionContext> getItems() {
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

    public BatchJobExecutionContext getBatchJobExecutionContext(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<BatchJobExecutionContext> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<BatchJobExecutionContext> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = BatchJobExecutionContext.class)
    public static class BatchJobExecutionContextControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BatchJobExecutionContextController controller = (BatchJobExecutionContextController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "batchJobExecutionContextController");
            return controller.getBatchJobExecutionContext(getKey(value));
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
            if (object instanceof BatchJobExecutionContext) {
                BatchJobExecutionContext o = (BatchJobExecutionContext) object;
                return getStringKey(o.getJobExecutionId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), BatchJobExecutionContext.class.getName()});
                return null;
            }
        }

    }

}
