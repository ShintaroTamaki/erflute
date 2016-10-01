package org.insightech.er.editor.view.action.tracking;

import org.dbflute.erflute.core.DisplayMessages;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.PlatformUI;
import org.insightech.er.editor.MainModelEditor;
import org.insightech.er.editor.model.ERDiagram;
import org.insightech.er.editor.view.action.AbstractBaseAction;
import org.insightech.er.editor.view.dialog.tracking.ChangeTrackingDialog;

public class ChangeTrackingAction extends AbstractBaseAction {

    public static final String ID = ChangeTrackingAction.class.getName();

    public ChangeTrackingAction(MainModelEditor editor) {
        super(ID, DisplayMessages.getMessage("action.title.change.tracking"), editor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Event event) {
        ERDiagram diagram = this.getDiagram();

        ChangeTrackingDialog dialog =
                new ChangeTrackingDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), this.getGraphicalViewer(),
                        diagram);

        dialog.open();
    }

}
