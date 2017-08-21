package org.dbflute.erflute.editor.view.contributor;

import java.util.List;

import org.dbflute.erflute.editor.ERFluteMultiPageEditor;
import org.dbflute.erflute.editor.MainDiagramEditor;
import org.dbflute.erflute.editor.model.ViewableModel;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IWorkbenchPage;

public abstract class ComboContributionItem extends ContributionItem {

    private Combo combo;
    private ToolItem toolitem;
    private final IWorkbenchPage workbenchPage;

    public ComboContributionItem(String id, IWorkbenchPage workbenchPage) {
        super(id);
        this.workbenchPage = workbenchPage;
    }

    @Override
    public final void fill(Composite parent) {
        createControl(parent);
    }

    @Override
    public void fill(ToolBar parent, int index) {
        this.toolitem = new ToolItem(parent, SWT.SEPARATOR, index);
        final Control control = createControl(parent);
        toolitem.setControl(control);
    }

    protected Control createControl(Composite parent) {
        this.combo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
        setData(combo);

        combo.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                final List<?> selectedEditParts = ((IStructuredSelection) workbenchPage.getSelection()).toList();
                final CompoundCommand compoundCommand = new CompoundCommand();
                for (final Object editPart : selectedEditParts) {
                    final Object model = ((EditPart) editPart).getModel();
                    if (model instanceof ViewableModel) {
                        final ViewableModel viewableModel = (ViewableModel) model;
                        final Command command = createCommand(viewableModel);
                        if (command != null) {
                            compoundCommand.add(command);
                        }
                    }
                }

                if (!compoundCommand.getCommands().isEmpty()) {
                    executeCommand(compoundCommand);
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

        combo.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });

        toolitem.setWidth(computeWidth(combo));

        return combo;
    }

    abstract protected Command createCommand(ViewableModel viewableModel);

    private int computeWidth(Control control) {
        return control.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x;
    }

    abstract protected void setData(Combo combo);

    private void executeCommand(Command command) {
        final ERFluteMultiPageEditor multiPageEditor = (ERFluteMultiPageEditor) workbenchPage.getActiveEditor();
        final MainDiagramEditor editor = (MainDiagramEditor) multiPageEditor.getActiveEditor();
        editor.getGraphicalViewer().getEditDomain().getCommandStack().execute(command);
    }

    public void setText(String text) {
        if (combo != null && !combo.isDisposed() && text != null) {
            combo.setText(text);
        }
    }

    public String getText() {
        return combo.getText();
    }
}
