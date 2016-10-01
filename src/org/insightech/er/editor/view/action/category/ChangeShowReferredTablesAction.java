package org.insightech.er.editor.view.action.category;

import org.dbflute.erflute.core.DisplayMessages;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Event;
import org.insightech.er.editor.MainModelEditor;
import org.insightech.er.editor.controller.command.category.ChangeShowReferredTablesCommand;
import org.insightech.er.editor.model.ERDiagram;
import org.insightech.er.editor.view.action.AbstractBaseAction;

public class ChangeShowReferredTablesAction extends AbstractBaseAction {

    public static final String ID = ChangeShowReferredTablesAction.class.getName();

    public ChangeShowReferredTablesAction(MainModelEditor editor) {
        super(ID, null, IAction.AS_CHECK_BOX, editor);
        this.setText(DisplayMessages.getMessage("action.title.category.show.referred.tables"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Event event) {
        ERDiagram diagram = this.getDiagram();

        ChangeShowReferredTablesCommand command = new ChangeShowReferredTablesCommand(diagram, this.isChecked());

        this.execute(command);
    }
}
