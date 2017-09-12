package eu.squadd.springbatch.schema.jsf;

import eu.squadd.springbatch.schema.BatchStepExecutionContext;
import eu.squadd.springbatch.schema.jsf.util.JsfUtil;
import eu.squadd.springbatch.schema.jsf.util.JsfUtil.PersistAction;
import eu.squadd.springbatch.schema.ejb.BatchStepExecutionContextFacade;

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

@Named("batchStepExecutionContextController")
@SessionScoped
public class BatchStepExecutionContextController implements Serializable {

    @EJB
    private eu.squadd.springbatch.schema.ejb.BatchStepExecutionContextFacade ejbFacade;
    private List<BatchStepExecutionContext> items = null;
    private BatchStepExecutionContext selected;

    public BatchStepExecutionContextController() {
    }

    public BatchStepExecutionContext getSelected() {
        return selected;
    }

    public void setSelected(BatchStepExecutionContext selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private BatchStepExecutionContextFacade getFacade() {
        return ejbFacade;
    }

    public BatchStepExecutionContext prepareCreate() {
        selected = new BatchStepExecutionContext();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("BatchStepExecutionContextCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("BatchStepExecutionContextUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("BatchStepExecutionContextDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<BatchStepExecutionContext> getItems() {
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

    public BatchStepExecutionContext getBatchStepExecutionContext(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<BatchStepExecutionContext> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<BatchStepExecutionContext> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = BatchStepExecutionContext.class)
    public static class BatchStepExecutionContextControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BatchStepExecutionContextController controller = (BatchStepExecutionContextController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "batchStepExecutionContextController");
            return controller.getBatchStepExecutionContext(getKey(value));
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
            if (object instanceof BatchStepExecutionContext) {
                BatchStepExecutionContext o = (BatchStepExecutionContext) object;
                return getStringKey(o.getStepExecutionId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), BatchStepExecutionContext.class.getName()});
                return null;
            }
        }

    }

}
