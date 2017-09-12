package eu.squadd.springbatch.schema.jsf;

import eu.squadd.springbatch.schema.BatchStepExecutionSeq;
import eu.squadd.springbatch.schema.jsf.util.JsfUtil;
import eu.squadd.springbatch.schema.jsf.util.JsfUtil.PersistAction;
import eu.squadd.springbatch.schema.ejb.BatchStepExecutionSeqFacade;

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

@Named("batchStepExecutionSeqController")
@SessionScoped
public class BatchStepExecutionSeqController implements Serializable {

    @EJB
    private eu.squadd.springbatch.schema.ejb.BatchStepExecutionSeqFacade ejbFacade;
    private List<BatchStepExecutionSeq> items = null;
    private BatchStepExecutionSeq selected;

    public BatchStepExecutionSeqController() {
    }

    public BatchStepExecutionSeq getSelected() {
        return selected;
    }

    public void setSelected(BatchStepExecutionSeq selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private BatchStepExecutionSeqFacade getFacade() {
        return ejbFacade;
    }

    public BatchStepExecutionSeq prepareCreate() {
        selected = new BatchStepExecutionSeq();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("BatchStepExecutionSeqCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("BatchStepExecutionSeqUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("BatchStepExecutionSeqDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<BatchStepExecutionSeq> getItems() {
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

    public BatchStepExecutionSeq getBatchStepExecutionSeq(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<BatchStepExecutionSeq> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<BatchStepExecutionSeq> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = BatchStepExecutionSeq.class)
    public static class BatchStepExecutionSeqControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BatchStepExecutionSeqController controller = (BatchStepExecutionSeqController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "batchStepExecutionSeqController");
            return controller.getBatchStepExecutionSeq(getKey(value));
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
            if (object instanceof BatchStepExecutionSeq) {
                BatchStepExecutionSeq o = (BatchStepExecutionSeq) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), BatchStepExecutionSeq.class.getName()});
                return null;
            }
        }

    }

}
