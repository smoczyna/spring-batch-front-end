package eu.squadd.springbatch.schema.jsf;

import eu.squadd.springbatch.schema.BatchJobSeq;
import eu.squadd.springbatch.schema.jsf.util.JsfUtil;
import eu.squadd.springbatch.schema.jsf.util.JsfUtil.PersistAction;
import eu.squadd.springbatch.schema.ejb.BatchJobSeqFacade;

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

@Named("batchJobSeqController")
@SessionScoped
public class BatchJobSeqController implements Serializable {

    @EJB
    private eu.squadd.springbatch.schema.ejb.BatchJobSeqFacade ejbFacade;
    private List<BatchJobSeq> items = null;
    private BatchJobSeq selected;

    public BatchJobSeqController() {
    }

    public BatchJobSeq getSelected() {
        return selected;
    }

    public void setSelected(BatchJobSeq selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private BatchJobSeqFacade getFacade() {
        return ejbFacade;
    }

    public BatchJobSeq prepareCreate() {
        selected = new BatchJobSeq();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("BatchJobSeqCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("BatchJobSeqUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("BatchJobSeqDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<BatchJobSeq> getItems() {
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

    public BatchJobSeq getBatchJobSeq(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<BatchJobSeq> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<BatchJobSeq> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = BatchJobSeq.class)
    public static class BatchJobSeqControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BatchJobSeqController controller = (BatchJobSeqController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "batchJobSeqController");
            return controller.getBatchJobSeq(getKey(value));
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
            if (object instanceof BatchJobSeq) {
                BatchJobSeq o = (BatchJobSeq) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), BatchJobSeq.class.getName()});
                return null;
            }
        }

    }

}
