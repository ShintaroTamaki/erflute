package org.insightech.er.editor.model.dbexport.ddl.validator.rule.view.impl;

import org.dbflute.erflute.core.DisplayMessages;
import org.eclipse.core.resources.IMarker;
import org.insightech.er.editor.model.dbexport.ddl.validator.ValidateResult;
import org.insightech.er.editor.model.dbexport.ddl.validator.rule.view.ViewRule;
import org.insightech.er.editor.model.diagram_contents.element.node.view.ERView;

public class NoViewNameRule extends ViewRule {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(ERView view) {
        if (view.getPhysicalName() == null || view.getPhysicalName().trim().equals("")) {
            ValidateResult validateResult = new ValidateResult();
            validateResult.setMessage(DisplayMessages.getMessage("error.validate.no.view.name"));
            validateResult.setLocation(view.getLogicalName());
            validateResult.setSeverity(IMarker.SEVERITY_WARNING);
            validateResult.setObject(view);

            this.addError(validateResult);
        }

        return true;
    }
}
