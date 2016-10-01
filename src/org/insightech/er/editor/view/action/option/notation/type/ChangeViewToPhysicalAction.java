package org.insightech.er.editor.view.action.option.notation.type;

import org.insightech.er.editor.MainModelEditor;
import org.insightech.er.editor.model.settings.Settings;

public class ChangeViewToPhysicalAction extends AbstractChangeViewAction {

    public static final String ID = ChangeViewToPhysicalAction.class.getName();

    public ChangeViewToPhysicalAction(MainModelEditor editor) {
        super(ID, "physical", editor);
    }

    @Override
    protected int getViewMode() {
        return Settings.VIEW_MODE_PHYSICAL;
    }
}
